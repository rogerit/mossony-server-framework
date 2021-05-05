package com.mossony.framwork;

import com.mossony.framwork.filter.CORSFilter;
import com.mossony.framwork.postman.PostManServlet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.predicate.Predicates;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PredicateHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.util.ImmediateInstanceHandle;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;

/**
 * @Author roger
 */
@Slf4j
public class MossServer {

    @Inject
    private PostManServlet postManServlet;

    @Inject
    private MossServlet mossServlet;

    @Inject
    @Named("moss.server.port")
    private String port;

    public void startServer() {
        try {
            ServletInfo mossServlet = servlet("MossServlet", MossServlet.class, () -> new ImmediateInstanceHandle(this.mossServlet)).addMapping("/*");
            mossServlet.setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir")));
            ServletInfo postmanServlet = servlet("postman", PostManServlet.class, () -> new ImmediateInstanceHandle(postManServlet)).addMapping("/postman");
            DeploymentInfo servletBuilder = deployment()
                    .setDefaultEncoding("UTF-8")
                    .setClassLoader(this.getClass().getClassLoader())
                    .setContextPath("/")
                    .setDeploymentName(this.getClass().getSimpleName())
                    .addFilter(new FilterInfo("CORS", CORSFilter.class))
                    .addFilterUrlMapping("CORS", "/*", DispatcherType.REQUEST)
                    .addServlets(postmanServlet)
                    .addServlets(mossServlet);
            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();
            HttpHandler servletHandler = manager.start();

            /**
             *处理静态资源文件：css、js
             * prefix: asserts
             * suffix: .css .js
             */
            ResourceHandler resourceHandler = Handlers.resource(new ClassPathResourceManager(this.getClass().getClassLoader(), "asserts"));
            PredicateHandler predicateHandler = Handlers.predicate(Predicates.suffixes(".css", ".js", ".ico"), resourceHandler, servletHandler);

            Undertow server = Undertow.builder()
                    .addHttpListener(Integer.parseInt(port), "0.0.0.0")
                    .setHandler(predicateHandler)
                    .build();
            server.start();
            log.info("http://127.0.0.1:{}", port);
        } catch (ServletException servletException) {

        }
    }
}
