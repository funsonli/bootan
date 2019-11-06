package com.funsonli.bootan.module.base.controller;

import cn.hutool.core.date.DateUtil;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.PageUtil;
import com.funsonli.bootan.common.vo.PageVO;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.vo.RedisInfo;
import com.funsonli.bootan.module.base.vo.RedisVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Redis管理接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("Redis管理接口")
@RequestMapping("/bootan/redis")
public class RedisController {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping({"/", "index"})
    @ApiOperation("角色部门数据范围列表搜索分页")
    @BootanLog(value = "角色部门数据范围列表搜索分页", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult index(@ModelAttribute SearchVO searchVO,
                            @ModelAttribute PageVO pageVO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            return BaseResult.error();
        }

        List<RedisVO> list = new ArrayList<>();

        String key = request.getParameter("key");
        Set<String> keys;
        if (key != null) {
            keys = redisTemplate.keys("*" + key + "*");
        } else {
            keys = redisTemplate.keys("*");
        }

        if (keys != null && keys.size() > 0) {
            for (String item : keys) {
                RedisVO redisVO = new RedisVO(item, "");
                list.add(redisVO);
            }
        }

        Pageable pageable = PageUtil.initPage(pageVO);

        Page<RedisVO> page = new PageImpl<>(PageUtil.listToPage(pageable, list), pageable, list.size());
        page.getContent().forEach(e -> {
            String value = "";
            try {
                value = redisTemplate.opsForValue().get(e.getKey());
                if (value != null && value.length() > 100) {
                    value = value.substring(0, 100) + "...";
                }
            } catch (Exception exception) {
                value = "非字符数据";
            }
            e.setValue(value);
        });

        return BaseResult.success(page);
    }


    @PostMapping("/save")
    @ApiOperation("保存")
    @BootanLog(value = "保存", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult save(@ModelAttribute RedisVO modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return BaseResult.error();
        }

        redisTemplate.opsForValue().set(modelAttribute.getKey(), modelAttribute.getValue());
        return BaseResult.success();
    }

    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    @BootanLog(value = "批量删除", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult delete(@PathVariable String[] ids) {

        for (String id : ids) {
            redisTemplate.delete(id);
        }

        if (1 < ids.length) {
            return BaseResult.success("批量删除数据成功");
        }
        return BaseResult.success();
    }

    @GetMapping("/view/{id}")
    @ApiOperation("查看单个数据详情")
    @BootanLog(value = "查看单个数据详情", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult view(@PathVariable String id) {

        String value = redisTemplate.opsForValue().get(id);

        if (null == value) {
            return BaseResult.error();
        }

        return BaseResult.success(value);
    }

    @GetMapping("/info")
    @ApiOperation("Redis运行信息")
    @BootanLog(value = "Redis运行信息", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult info() {

        List<RedisInfo> redisInfoList = new ArrayList<>(10);
        Jedis jedis = jedisPool.getResource();

        String[] infos = jedis.info().split("\n");
        for (String info : infos) {
            RedisInfo redisInfo = new RedisInfo();
            String[] item = info.split(":");
            if (item.length > 1) {
                redisInfo.setKey(item[0]);
                redisInfo.setValue(item[1].replace("\r", ""));
                redisInfoList.add(redisInfo);

            }
        }

        jedis.close();

        return BaseResult.success(redisInfoList);
    }

    @GetMapping("/memory")
    @ApiOperation("Redis运行内存")
    @BootanLog(value = "Redis运行内存", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult memory() {
        Map<String, Object> map = new HashMap<>(16);

        Jedis jedis = jedisPool.getResource();
        String[] infos = jedis.info().split("\n");
        for (String info : infos) {
            String[] item = info.split(":");
            if ("used_memory".equals(item[0])) {
                map.put("memory", item[1].replace("\r", ""));
                break;
            }
        }
        map.put("time", DateUtil.format(new Date(), "HH:mm:ss"));

        jedis.close();

        return BaseResult.success(map);
    }

    @GetMapping("/key-size")
    @ApiOperation("Redis实时Key数量")
    @BootanLog(value = "Redis实时Key数量", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult keySize() {
        Map<String, Object> map = new HashMap<>(16);

        Jedis jedis = jedisPool.getResource();
        map.put("keySize", jedis.dbSize());
        map.put("time", DateUtil.format(new Date(), "HH:mm:ss"));

        jedis.close();

        return BaseResult.success(map);
    }
}
