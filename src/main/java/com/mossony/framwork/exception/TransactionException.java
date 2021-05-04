package com.mossony.framwork.exception;

public class TransactionException extends MossException {

    TransactionException(int group, int detail, String msg) {
        super(TRANSACTIONAL_LEVEL, group, detail, msg);
    }

}
