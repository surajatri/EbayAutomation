package ebay.qa.testautomation.utils;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class Common {

    public void setText(WebElement element, String text)
    {
        Assert.assertTrue(element.isEnabled());
        element.clear();
        element.sendKeys(text);
    }

    public void click(WebElement element)
    {
        Assert.assertTrue(element.isEnabled());
        element.click();
    }

    public void elementsListExist(List<WebElement> elements)
    {
        if(!(elements==null) || !(elements.isEmpty())) {
            Assert.assertTrue(elements.size() > 0);
        }
    }




}
