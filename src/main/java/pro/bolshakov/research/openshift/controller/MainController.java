package pro.bolshakov.research.openshift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.bolshakov.research.openshift.config.OpenshiftPropertiesConfig;

import java.util.Iterator;
import java.util.Map;

@RestController
public class MainController {

    @Value("${test.var.message:not_found}")
    private String testText;

    @Value("${vtb.application.prop.value}")
    private String vtbAppPropValue;

    @Autowired
    private Environment env;

    @Autowired
    private OpenshiftPropertiesConfig openshiftProperties;

    @GetMapping("")
    public String index(){

        StringBuilder builder = new StringBuilder();
        builder.append("Values from props -> <ul>");
        builder.append("<li>").append("test.var.message = ").append(testText).append("</li>");
        builder.append("<li>").append("vtb.application.prop.value = ").append(vtbAppPropValue).append("</li>");
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
        }
        return builder.toString();
    }

    public class PropertiesHolder{
        private String anotherValue = "default";
        private String test = "default";
        private String username = "default";
        private String twoWords = "default";

        public PropertiesHolder() {
        }

        PropertiesHolder(OpenshiftPropertiesConfig openshiftProps) {
            this.anotherValue = openshiftProps.getAnotherValue();
            this.test = openshiftProps.getTest();
            this.username = openshiftProps.getUsername();
            this.twoWords = openshiftProps.getTwoWords();
        }
        @Override
        public String toString() {
            return "PropertiesHolder ->" +
                    "<ul>" +
                    "<li>anotherValue=" + anotherValue + "</li>" +
                    "<li>test=" + test + "</li>" +
                    "<li>username=" + username + "</li>" +
                    "<li>twoWords=" + twoWords + "</li>" +
                    "</ul>";
        }
    }

}
