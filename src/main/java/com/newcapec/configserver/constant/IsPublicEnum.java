package com.newcapec.configserver.constant;

/**
 * @author jqq
 * @version 1.0
 * @description 是否公共枚举类
 * @date 2019/6/17 10:37
 **/
public enum IsPublicEnum {
    NO(0, "否"),
    YES(1, "是");

    private final int code;
    private final String msg;

    IsPublicEnum(int code, String msg) {
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
    public static IsPublicEnum valueOf(int code) {
        for (IsPublicEnum isPublicEnum : IsPublicEnum.values()) {
            if (isPublicEnum.code == code) {
                return isPublicEnum;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }
}
