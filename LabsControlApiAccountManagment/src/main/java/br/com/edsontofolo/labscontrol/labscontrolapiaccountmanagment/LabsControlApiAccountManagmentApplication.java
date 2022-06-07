package br.com.edsontofolo.labscontrol.labscontrolapiaccountmanagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LabsControlApiAccountManagmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabsControlApiAccountManagmentApplication.class, args);
    }

}
