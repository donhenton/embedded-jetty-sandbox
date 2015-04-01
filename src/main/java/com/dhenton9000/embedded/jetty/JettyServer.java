/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.embedded.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

 //http://eclipse.org/jetty/documentation/current/embedded-examples.html
/**
 * This server can serve jsp pages.
 * 
 * @author dhenton
 */
public class JettyServer {

    private Server jettyServer;
    /**
     * the location of the web-inf folder and the web pages
     */
    private String resourceBase = "src/main/webapp";

    /**
     * Unit Test Jetty Server.
     * This server is capable of supporting jsp pages.
     * 
     * @param port the port for the web server
     *
     * @param contextPath in the test environment something like '/app'
     * @throws Exception
     */
    public JettyServer(int port, String contextPath) throws Exception {
        //String resourceBasePath = this.getClass().getResource(warLocation).toExternalForm();

        jettyServer = new Server(port);
        WebAppContext webApplication = new WebAppContext();

        webApplication.setContextPath(contextPath);
        webApplication.setResourceBase(resourceBase);

        Configuration.ClassList classlist = Configuration.ClassList
                .setServerDefault(jettyServer);
        classlist.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        // Set the ContainerIncludeJarPattern so that jetty examines these
        // container-path jars for tlds, web-fragments etc.
        // If you omit the jar that contains the jstl .tlds, the jsp engine will
        // scan for them instead.
        webApplication.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");

        jettyServer.setHandler(webApplication);
        jettyServer.start();
    }

    public void stopJettyServer() throws Exception {
        jettyServer.stop();
    }

    /**
     * @return the resourceBase
     */
    public String getResourceBase() {
        return resourceBase;
    }

    /**
     * @param resourceBase the resourceBase to set
     */
    public void setResourceBase(String resourceBase) {
        this.resourceBase = resourceBase;
    }

}
