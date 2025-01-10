package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    public static int generateRandom(int max,int min){
        double number=Math.random()*(max-min)+min;
        return (int) number;
    }
    public static void setEnvVar(String key,String value) throws ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }

}
