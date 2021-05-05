package com.mossony.framwork.exception;

public class ServiceException extends MossException {

    public ServiceException(int group, int detail, String msg) {
        super(SERVICE_LEVEL, group, detail, msg);
    }

}
