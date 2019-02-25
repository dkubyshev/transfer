package com.revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {

        ServiceLocator serviceLocator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        final ResourceConfig rc = new ResourceConfig();
        rc.register(JacksonFeature.class);
        rc.packages(true, "com.revolut");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, serviceLocator);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        new Setup().run();

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }

}

