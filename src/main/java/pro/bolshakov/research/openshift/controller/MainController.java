package pro.bolshakov.research.openshift.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.bolshakov.research.openshift.config.OpenshiftPropertiesConfig;

@RestController
public class MainController {

    @Value("${test.var.message:not_found}")
    private String testText;

    @Value("${vtb.application.prop.value}")
    private String vtbAppPropValue;

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
