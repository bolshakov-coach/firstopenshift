package pro.bolshakov.research.openshift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pro.bolshakov.research.openshift.config.OpenshiftPropertiesConfig;

@SpringBootApplication
@EnableConfigurationProperties({OpenshiftPropertiesConfig.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
