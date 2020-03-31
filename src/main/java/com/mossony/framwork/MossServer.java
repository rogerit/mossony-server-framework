package com.mossony.framwork;

import com.mossony.framwork.postman.PostManServlet;

import javax.inject.Inject;
import javax.servlet.ServletException;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.util.ImmediateInstanceHandle;
import lombok.extern.slf4j.Slf4j;

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

    public void startServer(int port) {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(this.getClass().getClassLoader())
                    .setContextPath("/")
                    .setDeploymentName(this.getClass().getSimpleName())
                    .addServlets(servlet("postman", PostManServlet.class, () -> new ImmediateInstanceHandle(postManServlet)).addMapping("/postman"))
                    .addServlets(servlet("MossServlet", MossServlet.class, () -> new ImmediateInstanceHandle(mossServlet)).addMapping("/*"));

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();

            Undertow server = Undertow.builder()
                                      .addHttpListener(port, "0.0.0.0")
                                      .setHandler(servletHandler)
                                      .build();

            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
