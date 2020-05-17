package com.mossony.framwork;

import lombok.Data;

/**
 * @Author roger
 * @Date $ $
 */
@Data
public class MossException extends RuntimeException {

    private Integer code;

    public MossException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
