package com.mossony.framwork.exception;

/**
 * 定义无需被捕获的异常类型，由最外层统一处理并将错误编码和原因一并返回给前端
 */
public class MossException extends RuntimeException {

    static int CONTROLLER_LEVEL = 11_00_00;

    static int SERVICE_LEVEL = 21_00_00;

    static int TRANSACTIONAL_LEVEL = 31_00_00;

    static int COMPONENT_LEVEL = 32_00_00;

    static int CRUD_LEVEL = 41_00_00;

    private int level = 10_00_00;

    private int group = 10_00;

    private int detail = 10;

    MossException(int level, int group, int detail, String msg) {
        super(msg);
        this.level = level;
        this.group = group;
        this.detail = detail;
    }

    public int getCode() {
        return this.level + this.group + this.detail;
    }

}
