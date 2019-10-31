package com.funsonli.bootan.common.constant;

/**
 * 常量
 *
 * @author Funsonli
 * @date 2019/10/31
 */
public class CommonConstant {
    public final static Integer STATUS_ENABLE = 1;
    public final static Integer STATUS_DISABLE = 0;
    public final static Integer STATUS_DELETED = -1;

    public final static Integer TYPE_DEFAULT = 1;

    public final static Integer ROLE_DEPARTMENT_TYPE = 0;
    public final static Integer ROLE_DEFAULT_NO = 0;
    public final static Integer ROLE_DEFAULT_YES = 1;

    public final static Integer SORT_ORDER_DEFAULT = 50;

    public final static String SECURITY_ACCESS_TOKEN = "access-token";
    public final static String SECURITY_TOKEN_SPLIT = "Bearer ";

    public final static String REDIS_USER_TOKEN = "BOOTAN_USER_TOKEN:";
    public final static String REDIS_TOKEN_DETAIL = "BOOTAN_TOKEN_DETAIL:";

    public final static String DEFAULT_PARENT_ID = "0";

    public final static Integer PERMISSION_LEVEL_0 = 0;
    public final static Integer PERMISSION_LEVEL_1 = 1;
    public final static Integer PERMISSION_LEVEL_2 = 2;
    public final static Integer PERMISSION_LEVEL_3 = 3;

    public final static int LOG_TYPE_LOGIN = 1;
    public final static int LOG_TYPE_OPERATION = 2;
    public final static int LOG_TYPE_ACCESS = 3;

}
