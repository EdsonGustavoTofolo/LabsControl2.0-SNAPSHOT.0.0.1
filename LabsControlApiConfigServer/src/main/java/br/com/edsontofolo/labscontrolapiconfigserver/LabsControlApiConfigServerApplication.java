package br.com.edsontofolo.labscontrolapiconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class LabsControlApiConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabsControlApiConfigServerApplication.class, args);
    }

}
