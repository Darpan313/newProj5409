package com.service.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import com.service.main.Main;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.log4j.Category.getRoot;

public class CalculationHttpService {
    private final HttpServer server;
    private final static String PACKAGE_NAMESPACE = "com.serice.api";
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationHttpService.class);
    private static final int SERVICE_PORT = 5557;

    public CalculationHttpService() {
        ResourceConfig httpConf = new ResourceConfig()
                .register(new ClaculationRestApi())
                .packages(PACKAGE_NAMESPACE);

        URI httpUri = URI.create("http://" + Main.host() + ":" + SERVICE_PORT + "/");
        LOGGER.info("API access point: " + httpUri.toString());
        server = GrizzlyHttpServerFactory.createHttpServer(httpUri, httpConf);
        server.getServerConfiguration().addHttpHandler(
                new StaticHttpHandler(getRoot()),
                "/static"
        );
    }

    public static URL jarLocation() {
        return com.service.main.Main.class.getProtectionDomain().getCodeSource().getLocation();
    }

    private static String getRoot() {
        String jarPath = null;
        try {
            jarPath = URLDecoder.decode(jarLocation().getFile(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return new File(jarPath).getParentFile().getPath();
    }

    public void start() throws IOException {
        server.start();
    }

    public void stop() {
        server.shutdown(30000, TimeUnit.MILLISECONDS);
    }
}
