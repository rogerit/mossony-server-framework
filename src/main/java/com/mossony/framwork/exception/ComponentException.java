package com.mossony.framwork.exception;

public class ComponentException extends MossException {

    ComponentException(int group, int detail, String msg) {
        super(COMPONENT_LEVEL, group, detail, msg);
    }

}
