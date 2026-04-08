/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.c.s.coursework;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class CSCoursework {
    
    public static final String BaseURI = "http://localhost:8081/api/v1";
    
    public static HttpServer startServer()  {
        final ResourceConfig config = new ResourceConfig().packages("com.mycompany.c.s.coursework");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BaseURI), config);
    }

    public static void main(String[] args) throws IOException {
       HttpServer server = startServer();
       System.out.println("Server started at: " + BaseURI);
       System.out.println("Press Enter to stop the server...");
       System.in.read();
       server.shutdownNow();
    }
}
