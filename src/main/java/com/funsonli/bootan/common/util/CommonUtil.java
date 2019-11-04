package com.funsonli.bootan.common.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.funsonli.bootan.base.BaseResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 通用方法
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
public class CommonUtil {
    public static void responseOut(HttpServletResponse response, BaseResult result, Integer status) {
        try {
            response.setStatus(status);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.write(JSONObject.toJSONBytes(result));
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error(e + "output json error");
            e.printStackTrace();
        }
    }

    public static void responseOut(HttpServletResponse response, BaseResult result) {
        responseOut(response, result, 200);
    }

    public static String map2JsonString(Map<String, String[]> map) throws JSONException {
        if (map == null || map.size() == 0) {
            return null;
        }
        Iterator<Map.Entry<String, String[]>> iterator = map.entrySet().iterator();
        JSONObject jsonObject = new JSONObject();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> mapNext = iterator.next();
            String[] value = mapNext.getValue();
            if ("password".equals(mapNext.getKey())) {
                value[0] = "保密";
            }
            jsonObject.put(mapNext.getKey(), value);
        }

        return jsonObject.toString();
    }

}
