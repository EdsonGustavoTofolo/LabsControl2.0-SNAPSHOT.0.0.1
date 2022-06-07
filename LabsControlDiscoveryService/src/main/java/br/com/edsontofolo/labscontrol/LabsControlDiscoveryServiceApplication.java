package br.com.edsontofolo.labscontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LabsControlDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabsControlDiscoveryServiceApplication.class, args);
    }

}
