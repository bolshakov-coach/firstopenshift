package pro.bolshakov.research.openshift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pro.bolshakov.research.openshift.config.OpenshiftPropertiesConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @Value("${test.var.message:not_found}")
    private String testText;

    @Value("${vtb.application.prop.value}")
    private String vtbAppPropValue;

    @Value("${db.username}")
    private String dbUsername;

    @Autowired
    private Environment env;

    @Autowired
    private OpenshiftPropertiesConfig openshiftProperties;

    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("")
    public String index(){

        StringBuilder builder = new StringBuilder();
        builder.append("Values from props -> <ul>");
        builder.append("<li>").append("test.var.message = ").append(testText).append("</li>");
        builder.append("<li>").append("vtb.application.prop.value = ").append(vtbAppPropValue).append("</li>");
        builder.append("<li>").append("db.username = ").append(dbUsername).append("</li>");
        builder.append("</ul>");

        builder.append("<br>");

        PropertiesHolder propertiesHolder = openshiftProperties == null ?
                new PropertiesHolder() : new PropertiesHolder(openshiftProperties);

        builder.append(propertiesHolder.toString());

        return builder.toString();
    }

    @GetMapping("/all")
    public String allProperties(){
        StringBuilder builder = new StringBuilder();
        for(Iterator it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            if (propertySource instanceof MapPropertySource) {
                builder.append("<br><b>Source from ").append(propertySource.getName()).append("</b><br><ul>");
                for (Map.Entry<String, Object> entry : ((MapPropertySource) propertySource).getSource().entrySet()) {
                    builder.append("<li>Key -> ")
                            .append(entry.getKey())
                            .append(" = ")
                            .append(entry.getValue())
                            .append("</li>");
                }
                builder.append("</ul>");
            }
            else if(propertySource instanceof EnumerablePropertySource){
                addInfoAboutEnumerablePropertySource(builder, (EnumerablePropertySource) propertySource);
            }
            else {
                System.out.println(propertySource.getName() + " -> " + propertySource.getClass().getName());
            }
        }
        return builder.toString();
    }

    @GetMapping("/discovery")
    public String tryDiscovery(){
        System.out.println(discoveryClient.description());
        System.out.println("Services");
        for (String service : discoveryClient.getServices()) {
            System.out.println(service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("rest-hello-world");
        if(instances != null){
            System.out.println("Instances for rest-hello-world");
            instances.forEach(System.out::println);
        }
        return "<h1>It is answer from second service</h1>";
    }

    private void addInfoAboutEnumerablePropertySource(StringBuilder builder, EnumerablePropertySource source){
        builder.append("<br><b>Source from ")
                .append(source.getName())
                .append(" - Class = ").append(source.getClass().getName())
                .append("</b><br><ul>");

        for (String propertyName : source.getPropertyNames()) {
            builder.append("<li>Key -> ")
                    .append(propertyName)
                    .append(" = ")
                    .append(source.getProperty(propertyName))
                    .append("</li>");
        }
        builder.append("</ul>");
    }


    public class PropertiesHolder{
        private String anotherValue = "default";
        private String test = "default";
        private String username = "default";
        private String twoWords = "default";
        private String mongodbUsername = "default";

        public PropertiesHolder() {
        }

        PropertiesHolder(OpenshiftPropertiesConfig openshiftProps) {
            this.anotherValue = openshiftProps.getAnotherValue();
            this.test = openshiftProps.getTest();
            this.username = openshiftProps.getUsername();
            this.twoWords = openshiftProps.getTwoWords();
            this.mongodbUsername = openshiftProps.getMongodbUsername();
        }
        @Override
        public String toString() {
            return "PropertiesHolder ->" +
                    "<ul>" +
                    "<li>anotherValue=" + anotherValue + "</li>" +
                    "<li>test=" + test + "</li>" +
                    "<li>username=" + username + "</li>" +
                    "<li>twoWords=" + twoWords + "</li>" +
                    "<li>mongodbUsername=" + mongodbUsername + "</li>" +
                    "</ul>";
        }
    }

}
