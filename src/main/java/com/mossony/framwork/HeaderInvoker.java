package com.mossony.framwork;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class HeaderInvoker extends Invoker {

    protected boolean match(ParameterMethod method) {
        return false;
    }


}

