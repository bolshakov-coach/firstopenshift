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

    @Autowired
    private OpenshiftPropertiesConfig openshiftProperties;

    @GetMapping("")
    public String index(){
        return "It is working. Value from test.var.message -> " + testText;
    }

    @GetMapping("/props")
    public PropertiesHolder valueTest(){
        return openshiftProperties == null ? new PropertiesHolder() : new PropertiesHolder(openshiftProperties);
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

        public String getAnotherValue() {
            return anotherValue;
        }

        public String getTest() {
            return test;
        }

        public String getUsername() {
            return username;
        }

        public String getTwoWords() {
            return twoWords;
        }
    }

}
