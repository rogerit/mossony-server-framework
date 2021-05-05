package com.mossony.framwork.exception;

public class CrudException extends MossException {

    public CrudException(int group, int detail, String msg) {
        super(CRUD_LEVEL, group, detail, msg);
    }

}
