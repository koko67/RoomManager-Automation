package utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: RonaldButron
 * Date: 12/03/15
 */
public class ConfigFileReader {

    private String  returnValue = null;
    Logger log = Logger.getLogger("ConfigFileReader");
    public ConfigFileReader(){

    }

    /**
     * This method read values from a config file
     * @param value value
     * @return a string with the value
     */
    public String getPropertiesValues(String value) {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
            returnValue = prop.getProperty(value);

        } catch (IOException ex) {
            log.error("Error to open file" + ex);
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();

                } catch (IOException e) {

                    log.error("Not able to close read file" + e);
                }
            }
        }
        return returnValue;
    }
}



