package com.newcapec.configserver.constant;

/**
 * @author jqq
 * @version 1.0
 * @description 软删除枚举类
 * @date 2019/6/17 10:27
 **/
public enum IsDeletedEnum {

    NO(0, "否"),
    YES(1, "是");

    private final int code;
    private final String msg;

    IsDeletedEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    /**
      * @author jqq
      * @description 根据状态码获取枚举实例
      * @date 2019/6/17 10:35
      * @param code
      * @return
      */
    public static IsDeletedEnum valueOf(int code) {
        for (IsDeletedEnum isDeletedEnum : IsDeletedEnum.values()) {
            if (isDeletedEnum.code == code) {
                return isDeletedEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
