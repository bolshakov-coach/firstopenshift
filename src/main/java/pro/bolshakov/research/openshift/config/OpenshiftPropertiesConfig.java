package pro.bolshakov.research.openshift.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.data")
public class OpenshiftPropertiesConfig {

    private String test;
    private String username;
    private String twoWords;
    private String anotherValue;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        System.out.println("Invoked set for Test");
        this.test = test;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        System.out.println("Invoked set for username");
        this.username = username;
    }

    public String getTwoWords() {
        return twoWords;
    }

    public void setTwoWords(String twoWords) {
        System.out.println("Invoked set for two words");
        this.twoWords = twoWords;
    }

    public String getAnotherValue() {
        return anotherValue;
    }

    public void setAnotherValue(String anotherValue) {
        System.out.println("Invoked set for another value");
        this.anotherValue = anotherValue;
    }
}
