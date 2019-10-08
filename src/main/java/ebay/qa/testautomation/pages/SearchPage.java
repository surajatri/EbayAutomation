package ebay.qa.testautomation.pages;

import ebay.qa.testautomation.utils.Common;
import ebay.qa.testautomation.utils.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import javax.xml.bind.Element;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

public class SearchPage extends Common {

    WebDriver driver;

    public SearchPage() {
        Hooks hooks = new Hooks();
        this.driver = hooks.driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='gh-ac']")
    public WebElement searchInputBox;

    @FindBy(xpath = "//input[@id='gh-btn']")
    public WebElement searchButton;

    @FindBy(xpath = "//a[@id='viewType']")
    public WebElement viewTypeLink;

    @FindBy(xpath = "//ul[@id='ViewTypeMenu']//span[text()='Gallery view']")
    public WebElement galleryViewOption;

    @FindBy(xpath = "//*[@id='gh-ug']/a[contains(text(), 'Sign in') or contains(text(), 'register')]")
    public WebElement signInOrRegisterLabel;

    @FindBy(xpath = "//div[@class='gvprices']//span[contains(@class,'bold')]")
    public List<WebElement> elementsPrice;

    @FindBy(xpath = "//span[@class='lbl gvformat']")
    public List<WebElement> elementsBidCount;

    @FindBy(xpath = "//div[@class='gvprices']//span[contains(@class,'logoBin')]")
    public List<WebElement> elementsBuyItNowTag;

    @FindBy(xpath = "//*[@class='gvtitle']//a")
    public List<WebElement> elementsItemName;

    @FindBy(xpath = "//a[@class='btn btn-s btn-ter dropdown-toggle']")
    public WebElement sortOptionLink;

    @FindBy(xpath = "//a[text()='Lowest price']")
    public WebElement lowestPriceOption;

    @FindBy(xpath = "//a[text()='Highest price']")
    public WebElement highestPriceOption;

    @FindBy(xpath = "//div[@class='asp pnl left']//*[@title='Buy it now']")
    public WebElement buyItNowFilterLink;

    @FindBy(xpath = "//a[@class='btn btn-s small btn-ter dropdown-toggle']")
    public WebElement itemPerPageLink;

    @FindBy(xpath = "//td[@class='pages']/a")
    public List<WebElement> elementsResultPageNumber;

    @FindBy(xpath = "//a[@class='gspr next']")
    public WebElement nextResultPageLink;


    public void verifyNonRegisterUser() {
        Assert.assertTrue(signInOrRegisterLabel.isDisplayed());
    }

    public void launch() {
        driver.get("https://www.ebay.co.uk");
    }

    public void searchItem(String searchedString) {
        setText(searchInputBox, searchedString);
        click(searchButton);
        click(viewTypeLink);
        if (galleryViewOption.isDisplayed()) {
            click(galleryViewOption);
        }
    }

    public void validateSearchListExists() {
        elementsListExist(elementsPrice);
    }

    public void validatePriceBidAndBuyNowTag() {
        elementsListExist(elementsBidCount);
        elementsListExist(elementsBuyItNowTag);
    }

    public void validateResultSortedInOrder(String sortOrder) {

        List<Double> actualPriceList = new ArrayList<Double>();
        List<Double> expectedSortedList = new ArrayList<Double>();
        if (elementsPrice.size() > 0 && !(elementsPrice.isEmpty())) {
            for (WebElement element : elementsPrice) {
                actualPriceList.add(getPrice(element.getText(), sortOrder));
            }
            ;
        }

        expectedSortedList.addAll(actualPriceList);
        if (sortOrder.equalsIgnoreCase("lowest")) {
            Collections.sort(expectedSortedList);
        } else {
            Collections.sort(expectedSortedList, Collections.reverseOrder());
        }
        Assert.assertTrue(expectedSortedList.equals(actualPriceList));
    }

    public Double getPrice(String initialPrice, String sortOrder) {

        String finalPrice = "";
        String[] arrOfStr = initialPrice.split("to");

        if (arrOfStr.length > 1) {
            if (sortOrder.equalsIgnoreCase("lowest")) {
                finalPrice = arrOfStr[0];
            } else {
                finalPrice = arrOfStr[1];
            }
        } else {
            finalPrice = initialPrice;
        }
        finalPrice = finalPrice.replace("£", "");
        double dblFinalPrice = 0;
        try {
            dblFinalPrice = DecimalFormat.getNumberInstance().parse(finalPrice).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dblFinalPrice;
    }

    public void validateAllResultHasBuyItNowTag() {
        int itemCount = elementsBuyItNowTag.size();
        int expectedItemCount = Integer.parseInt(itemPerPageLink.getText());
        Assert.assertTrue(itemCount == expectedItemCount);
    }

    public void validateResultReturnMoreThanOnePage() {
        Assert.assertTrue(elementsResultPageNumber.size() > 1);
        nextResultPageLink.isEnabled();
    }

    public void validateUserNavigateNextPageForOtherProducts() {
        List<String> currentPageItemList = new ArrayList<String>();
        List<String> nextPageItemList = new ArrayList<String>();
        currentPageItemList =getElementTextList(elementsItemName);

        Collections.sort(currentPageItemList);

        click(nextResultPageLink);

        nextPageItemList =getElementTextList(elementsItemName);
        Collections.sort(nextPageItemList);
        System.out.println(nextPageItemList);

        Assert.assertFalse(currentPageItemList.equals(nextPageItemList));


    }

    public List<String> getElementTextList(List<WebElement> objElement) {
        List<String> elementTextList = new ArrayList<String>();
        if (objElement.size() > 0 && !(objElement.isEmpty())) {
            for (WebElement element : objElement) {
                elementTextList.add(element.getText());
            }
        }
        return elementTextList;
    }

    public void selectBrand(String brandName) {
        driver.findElement(By.xpath("//a[@class='cbx']//span[text()='" + brandName + "']")).click();
    }

    public void validateResultShownAsPerSearchCategory(String CategoryName){

        List<String> searchCategoryItemName = new ArrayList<String>();
        searchCategoryItemName = getElementTextList(elementsItemName);
        if (searchCategoryItemName.size() > 0 && !(searchCategoryItemName.isEmpty())) {
            for (String strText : searchCategoryItemName) {
                Assert.assertTrue(strText.toLowerCase().contains(CategoryName.toLowerCase()));
            }
        }


    }
}
