package com.mossony.framwork.exception;

public class ControllerException extends MossException {

    public ControllerException(int group, int detail, String msg) {
        super(CONTROLLER_LEVEL, group, detail, msg);
    }

}
