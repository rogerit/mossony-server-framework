package com.mossony.framwork.postman;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author roger
 * @Date $ $
 */
@Singleton
public class PostManServlet extends HttpServlet {

    @Inject
    private PostManParser postManParser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "moss-" + req.getParameter("name");
        String host = req.getHeader("HOST");
        String json = postManParser.parse(name, host);
        resp.getWriter().write(json);
    }
}
