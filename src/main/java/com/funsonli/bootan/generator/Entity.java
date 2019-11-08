package com.funsonli.bootan.generator;

import lombok.Data;

/**
 * @author funsonli
 */
@Data
public class Entity {

    private String entityPackage;

    private String daoPackage;

    private String mapperPackage;

    private String servicePackage;

    private String serviceImplPackage;

    private String controllerPackage;

    private String author;

    private String className;

    private String classNameLowerCase;

    private String url;

    private String tableName;

    private String description;

    private String primaryKeyType;

}
