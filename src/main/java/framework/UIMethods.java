package framework;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.ConfigFileReader;

import java.util.concurrent.TimeUnit;


/**
 * User: Ronald Butron
 * Date: 12/07/15
 */
public class UIMethods {

    public static WebDriver driver =  DriverManager.getInstance().getWebDriver();
    private static Logger log = Logger.getLogger("UIMethods");
    public UIMethods(){

    }

    /**
     * This method try to find a element
     * @param element element to search
     * @return true if the element is found
     */
    public static boolean isElementPresent(By element){
          try{
              driver.findElement(element);
              return true;
          } catch(NoSuchElementException e){
              return  false;
          }
     }

    /**
     * The driver wait to a element disappear
     * @param maxCount number of times to wait
     * @param element to search
     * @return true or false
     */
    public static boolean waitElementIsNotPresent(int maxCount, By element){
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        boolean result = true;
        int count = 1;
        try {
            while (result && count <= maxCount){
                Thread.sleep(50);
                result = isElementPresent(element);
                System.out.println("Element not present======="+ result);
                count++;
            }

        } catch (InterruptedException e){
           log.error("Exception Element is not Present");
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return !result;
    }

    /**
     * The driver wait to find a element
     * @param maxCount number of times to wait
     * @param element to search
     * @return true or false
     */
    public static boolean waitElementIsPresent(int maxCount, By element){
        boolean result = false;
        int count = 1;
        while (!result && count <= maxCount){
            result = isElementPresent(element);
            count++;
        }
        return result;
    }
}
