package com.mossony.framwork.exception;

public class CrudException extends MossException {

    CrudException(int group, int detail, String msg) {
        super(CRUD_LEVEL, group, detail, msg);
    }

}
