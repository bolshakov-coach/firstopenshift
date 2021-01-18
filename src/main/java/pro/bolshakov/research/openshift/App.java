package pro.bolshakov.research.openshift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import pro.bolshakov.research.openshift.config.OpenshiftPropertiesConfig;

@SpringBootApplication
@EnableConfigurationProperties({OpenshiftPropertiesConfig.class})
@EnableDiscoveryClient
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
