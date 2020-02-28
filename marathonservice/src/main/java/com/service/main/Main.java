package com.service.main;

import com.service.api.CalculationHttpService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
        final CalculationHttpService service = new CalculationHttpService();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                LOGGER.info("Stopping server..");
                service.stop();
            }
        }, "shutdownHook"));

        try {
            service.start();
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error("There was an error while starting Grizzly HTTP server.", e);
        }
    }

    public static String host() {
        return ObjectUtils.firstNonNull(System.getenv("HOST"),
                System.getenv("MESOS_HOSTNAME"),
                currentHost());
    }

    public static String currentHost() {
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOGGER.warn("Could not obytian hostname. Using localhost", e);
            host = "127.0.0.1";
        }
        return host;
    }
}
