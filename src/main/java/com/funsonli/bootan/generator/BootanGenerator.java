package com.funsonli.bootan.generator;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 * 1.需要增加XxxEntity文件中除了existField其他字段
 * 2.需要增加XxxServiceImpl文件中对应的搜索字段
 * 3.需要增加XxxServiceImpl文件中beforeSave增加对应字段
 * @author funsonli
 */
@Slf4j
public class BootanGenerator {

    private static String iniName = "template.ini";

    private static String[] existField = {"serialVersionUID", "id", "name", "type", "sortOrder", "createdAt", "createdBy", "updatedAt", "updatedBy"};

    private static String[] iniField = {"className", "description", "author", "tablePre", "primaryKeyType", "packagePath"};

    private static String className; // 实体类名称 Dict
    private static String description; // 实体类描述
    private static String author = "Funsonli"; // 作者
    private static String tablePre = "tbl_"; // 表前缀
    private static String primaryKeyType = "String"; // 主键Key类型
    private static String packagePath = "com.funsonli.bootan.module.base."; //包路径，一般修改my为对应的模块即可

    private static String entityPackage =  packagePath + "entity";
    private static String daoPackage = packagePath + "dao";
    private static String mapperPackage = packagePath + "mapper";
    private static String servicePackage = packagePath + "service";
    private static String serviceImplPackage = packagePath + "service.impl";
    private static String controllerPackage = packagePath + "controller";

    public static void main(String[] args) throws IOException {
        parseIni();

        //模板路径
        String root = System.getProperty("user.dir") + "/src/main/java/com/funsonli/bootan/generator/template";
        FileResourceLoader resourceLoader = new FileResourceLoader(root, "utf-8");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        //生成代码
        generateCode(gt);


        /*Map<String, String> mapField = new HashMap<>();
        try {
            Class clazz = Class.forName(entityPackage + "." + className);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Arrays.asList(existField).contains(field.getName())) {
                    continue;
                }

                System.out.println(field.getName());
                System.out.println(field.getType());
                Annotation[] annotations = field.getAnnotations();
                for (Annotation a : annotations) {
                    System.out.println(a.annotationType());
                }
                if (field.getGenericType().toString().equals("class java.lang.Integer") || field.getGenericType().toString().equals("int")) {
                    mapField.put(field.getName(), "Integer");
                } else {
                    mapField.put(field.getName(), "String");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //根据类名删除生成的代码
        //deleteCode(className);
    }

    /**
     * 分析配置文件
     * @author Funsonli
     * @date 2019/11/6
     * @return : null
     */
    private static void parseIni() {
        File file = new File(System.getProperty("user.dir") + "/src/main/java/com/funsonli/bootan/generator/ini/" + iniName);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] conf = line.split("=");
                if ("className".equals(conf[0].trim())) {
                    className = conf[1].trim();
                }
                if ("description".equals(conf[0].trim())) {
                    description = conf[1].trim();
                }
                if ("author".equals(conf[0].trim())) {
                    author = conf[1].trim();
                }
                if ("tablePre".equals(conf[0].trim())) {
                    tablePre = conf[1].trim();
                }
                if ("primaryKeyType".equals(conf[0].trim())) {
                    primaryKeyType = conf[1].trim();
                }
                if ("packagePath".equals(conf[0].trim())) {
                    packagePath = conf[1].trim();
                }
            }

            entityPackage =  packagePath + "entity";
            daoPackage = packagePath + "dao";
            mapperPackage = packagePath + "mapper";
            servicePackage = packagePath + "service";
            serviceImplPackage = packagePath + "service.impl";
            controllerPackage = packagePath + "controller";

        } catch (Exception e) {
            log.error(e.toString());
        }
    }
    /**
     * 生成代码
     * @param gt
     * @throws IOException
     */
    private static void generateCode(GroupTemplate gt) throws IOException{

        Template entityTemplate = gt.getTemplate("entity.btl");
        Template daoTemplate = gt.getTemplate("dao.btl");
        Template mapperTemplate = gt.getTemplate("mapper.btl");
        Template serviceTemplate = gt.getTemplate("service.btl");
        Template serviceImplTemplate = gt.getTemplate("serviceImpl.btl");
        Template controllerTemplate = gt.getTemplate("controller.btl");
        Template mapperXmlTemplate = gt.getTemplate("mapperXml.btl");
        Template indexTemplate = gt.getTemplate("index.btl");

        Entity entity = new Entity();
        entity.setEntityPackage(entityPackage);
        entity.setDaoPackage(daoPackage);
        entity.setMapperPackage(mapperPackage);
        entity.setServicePackage(servicePackage);
        entity.setServiceImplPackage(serviceImplPackage);
        entity.setControllerPackage(controllerPackage);
        entity.setAuthor(author);
        entity.setClassName(className);
        entity.setTableName(tablePre + camel2Underline(className));
        entity.setClassNameLowerCase(first2LowerCase(className));
        entity.setUrl(camel2Hyphen(className));
        entity.setDescription(description);
        entity.setPrimaryKeyType(primaryKeyType);

        OutputStream out = null;

        //生成实体类代码
        entityTemplate.binding("entity", entity);
        String entityResult = entityTemplate.render();
        log.info(entityResult);
        //创建文件
        String entityFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(entityPackage) + "/" + className + ".java";
        File entityFile = new File(entityFileUrl);
        File entityDir = entityFile.getParentFile();
        if (!entityDir.exists()) {
            entityDir.mkdirs();
        }
        if(!entityFile.exists()){
            // 若文件存在则不重新生成
            entityFile.createNewFile();
            out = new FileOutputStream(entityFile);
            entityTemplate.renderTo(out);
        }

        //生成dao代码
        daoTemplate.binding("entity",entity);
        String daoResult = daoTemplate.render();
        log.info(daoResult);
        //创建文件
        String daoFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(daoPackage) + "/" +className + "Dao.java";
        File daoFile = new File(daoFileUrl);
        File daoDir = daoFile.getParentFile();
        if (!daoDir.exists()) {
            daoDir.mkdirs();
        }
        if(!daoFile.exists()) {
            // 若文件存在则不重新生成
            daoFile.createNewFile();
            out = new FileOutputStream(daoFile);
            daoTemplate.renderTo(out);
        }

        //生成mapper代码
        mapperTemplate.binding("entity",entity);
        String mapperResult = mapperTemplate.render();
        log.info(mapperResult);
        //创建文件
        String mapperFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(mapperPackage) + "/" + className + "Mapper.java";
        File mapperFile = new File(mapperFileUrl);
        File mapperDir = mapperFile.getParentFile();
        if (!mapperDir.exists()) {
            mapperDir.mkdirs();
        }
        if(!mapperFile.exists()) {
            // 若文件存在则不重新生成
            mapperFile.createNewFile();
            out = new FileOutputStream(mapperFile);
            mapperTemplate.renderTo(out);
        }

        //生成service代码
        serviceTemplate.binding("entity",entity);
        String serviceResult = serviceTemplate.render();
        log.info(serviceResult);
        //创建文件
        String serviceFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(servicePackage) + "/" + className + "Service.java";
        File serviceFile = new File(serviceFileUrl);
        File serviceDir = serviceFile.getParentFile();
        if (!serviceDir.exists()) {
            serviceDir.mkdirs();
        }
        if(!serviceFile.exists()) {
            // 若文件存在则不重新生成
            serviceFile.createNewFile();
            out = new FileOutputStream(serviceFile);
            serviceTemplate.renderTo(out);
        }

        //生成serviceImpl代码
        serviceImplTemplate.binding("entity",entity);
        String serviceImplResult = serviceImplTemplate.render();
        log.info(serviceImplResult);
        //创建文件
        String serviceImplFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(serviceImplPackage) + "/" + className + "ServiceImpl.java";
        File serviceImplFile = new File(serviceImplFileUrl);
        File serviceImplDir = serviceImplFile.getParentFile();
        if (!serviceImplDir.exists()) {
            serviceImplDir.mkdirs();
        }
        if(!serviceImplFile.exists()) {
            // 若文件存在则不重新生成
            serviceImplFile.createNewFile();
            out = new FileOutputStream(serviceImplFile);
            serviceImplTemplate.renderTo(out);
        }

        //生成controller代码
        controllerTemplate.binding("entity",entity);
        String controllerResult = controllerTemplate.render();
        log.info(controllerResult);
        //创建文件
        String controllerFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(controllerPackage) + "/" + className + "Controller.java";
        File controllerFile = new File(controllerFileUrl);
        File controllerDir = controllerFile.getParentFile();
        if (!controllerDir.exists()) {
            controllerDir.mkdirs();
        }
        if(!controllerFile.exists()) {
            // 若文件存在则不重新生成
            controllerFile.createNewFile();
            out = new FileOutputStream(controllerFile);
            controllerTemplate.renderTo(out);
        }

        //生成mapperXml代码
        mapperXmlTemplate.binding("entity",entity);
        String mapperXmlResult = mapperXmlTemplate.render();
        log.info(mapperXmlResult);
        //创建文件
        String mapperXmlFileUrl = System.getProperty("user.dir")+"/src/main/resources/mapper/" + className + "Mapper.xml";
        File mapperXmlFile = new File(mapperXmlFileUrl);
        File mapperXmlDir = mapperXmlFile.getParentFile();
        if (!mapperXmlDir.exists()) {
            mapperXmlDir.mkdirs();
        }
        if(!mapperXmlFile.exists()) {
            // 若文件存在则不重新生成
            mapperXmlFile.createNewFile();
            out = new FileOutputStream(mapperXmlFile);
            mapperXmlTemplate.renderTo(out);
        }

        //生成index.vue代码
        indexTemplate.binding("entity",entity);

        Map<String, String> mapField = new HashMap<>();
        try {
            Class clazz = Class.forName(entityPackage + "." + className);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (Arrays.asList(existField).contains(field.getName())) {
                    continue;
                }

                ApiModelProperty[] apiModelProperties = field.getAnnotationsByType(ApiModelProperty.class);
                for (ApiModelProperty amp : apiModelProperties) {
                    mapField.put(field.getName(), amp.value());
                }

                /*if (field.getGenericType().toString().equals("class java.lang.Integer") || field.getGenericType().toString().equals("int")) {
                    mapField.put(field.getName(), "Integer");
                } else {
                    mapField.put(field.getName(), "String");
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        indexTemplate.binding("mapField",mapField);
        System.out.println("mapField" + mapField);

        String indexResult = indexTemplate.render();
        log.info(indexResult);
        //创建文件
        String indexFileUrl = System.getProperty("user.dir")+"/src/main/resources/vue/" + camel2Hyphen(className) + "/index.vue";
        File indexFile = new File(indexFileUrl);
        File indexDir = indexFile.getParentFile();
        if (!indexDir.exists()) {
            indexDir.mkdirs();
        }
        if(!indexFile.exists()) {
            // 若文件存在则不重新生成
            indexFile.createNewFile();
            out = new FileOutputStream(indexFile);
            indexTemplate.renderTo(out);
        }

        if(out!=null){
            out.close();
        }
        log.info("生成代码成功！");
    }

    /**
     * 删除指定类代码
     * @param className
     * @throws IOException
     */
    private static void deleteCode(String className) throws IOException{

        String entityFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(entityPackage) + "/" +className+".java";
        File entityFile = new File(entityFileUrl);
        if(entityFile.exists()){
            entityFile.delete();
        }
        String daoFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(daoPackage) + "/" +className+"Dao.java";
        File daoFile = new File(daoFileUrl);
        if(daoFile.exists()){
            daoFile.delete();
        }

        String mapperFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(mapperPackage) + "/" +className+"Mapper.java";
        File mapperFile = new File(mapperFileUrl);
        if(mapperFile.exists()){
            mapperFile.delete();
        }

        String serviceFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(servicePackage) + "/" +className+"Service.java";
        File serviceFile = new File(serviceFileUrl);
        if(serviceFile.exists()){
            serviceFile.delete();
        }

        String serviceImplFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(serviceImplPackage) + "/" +className+"ServiceImpl.java";
        File serviceImplFile = new File(serviceImplFileUrl);
        if(serviceImplFile.exists()){
            serviceImplFile.delete();
        }

        String controllerFileUrl = System.getProperty("user.dir")+"/src/main/java/"+ dotToLine(controllerPackage) + "/" +className+"Controller.java";
        File controllerFile = new File(controllerFileUrl);
        if(controllerFile.exists()){
            controllerFile.delete();
        }

        String mapperXmlFileUrl = System.getProperty("user.dir")+"/src/main/resources/mapper/" + className + "Mapper.xml";
        File mapperXmlFile = new File(mapperXmlFileUrl);
        if(mapperXmlFile.exists()){
            mapperXmlFile.delete();
        }

        String indexFileUrl = System.getProperty("user.dir")+"/src/main/resources/vue/" + camel2Hyphen(className) + "/index.vue";
        File indexFile = new File(indexFileUrl);
        if(indexFile.exists()){
            indexFile.delete();
        }

        log.info("删除代码完毕！");
    }
    /**
     * 点转斜线
     * @param str
     * @return
     */
    public static String dotToLine(String str){
        return str.replace(".", "/");
    }

    /**
     * 驼峰法转下划线
     */
    public static String camel2Underline(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        if(str.length()==1){
            return str.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<str.length();i++){
            if(Character.isUpperCase(str.charAt(i))){
                sb.append("_"+Character.toLowerCase(str.charAt(i)));
            }else{
                sb.append(str.charAt(i));
            }
        }
        return (str.charAt(0)+sb.toString()).toLowerCase();
    }
    /**
     * 驼峰法转划线
     */
    public static String camel2Hyphen(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        if(str.length()==1){
            return str.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<str.length();i++){
            if(Character.isUpperCase(str.charAt(i))){
                sb.append("-"+Character.toLowerCase(str.charAt(i)));
            }else{
                sb.append(str.charAt(i));
            }
        }
        return (str.charAt(0)+sb.toString()).toLowerCase();
    }

    /**
     * 首字母小写
     */
    public static String first2LowerCase(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        if(str.length()==1){
            return str.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toLowerCase(str.charAt(0)));
        sb.append(str.substring(1,str.length()));
        return sb.toString();
    }
}
