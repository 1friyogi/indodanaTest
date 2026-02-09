import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProductCategory extends BaseTest {
    @Test
    public void searchByConditionPriceAndItemLocation() {

        // Open ebay homepage
        driver.get("https://www.ebay.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Wait until dropdown is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gh-cat"))).click();

        // Click option "Cell Phones & Accessories"
        driver.findElement(By.xpath("//select[@id='gh-cat']/option[text()='Cell Phones & Accessories']")).click();

        // Click search button
        driver.findElement(By.xpath("//button[@id='gh-search-btn']")).click();

        // Click Cell Phones & Smartphones
        driver.findElement(By.xpath("//a[@href='https://www.ebay.com/b/Cell-Phones-Smartphones/9355/bn_320094']")).click();

        //driver.findElement(By.xpath("//button[.//span[contains(text(),'All Filters')]]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[text()='All Filters']]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3.seo-accordion__title[aria-label='Network']")));

        // Scroll to "Show Only" section
        WebElement element = driver.findElement(By.cssSelector("h3.seo-accordion__title[aria-label='Show Only']"));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                element
        );

        // Click "Condition" accordion to expand the options
        WebElement networkAccordion = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//h3[contains(@class,'seo-accordion__title') and normalize-space()='Condition']")
                )
        );
        networkAccordion.click();

        //click checkbox New condition
        WebElement conditionNewLabel = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='group-checkbox-New']")));
        conditionNewLabel.click();

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                element
        );

        //Set Price
        WebElement priceFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Under $200']/ancestor::label")));
        priceFilter.click();


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Apply']"))).click();

    }

    @Test
    public void searchUsingKeyword() {
        // Open ebay homepage
        driver.get("https://www.ebay.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-ac")));
        searchBox.clear();

        // Input product keyword ex : iphone 13
        String productKeyword = "iPhone 13";
        searchBox.sendKeys(productKeyword);

        // Wait until dropdown is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gh-cat"))).click();

        // Click option "Cell Phones & Accessories"
        driver.findElement(By.xpath("//select[@id='gh-cat']/option[text()='Computers/Tablets & Networking']")).click();

        // Click search button
        driver.findElement(By.id("gh-search-btn")).click();

        // Verify that the page loads completly with the search results already loaded
        WebElement results = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='srp-results srp-list clearfix']")));
        Assert.assertTrue(results.isDisplayed(), "No exact matches found");

        // Verify that the first product in the search results matches with the search string
        WebElement firstResultProduct = driver.findElement(By.xpath("(//ul[@class='srp-results srp-list clearfix']/li//h3)[1]"));
        String firstResultProductName = firstResultProduct.getText();
        System.out.println("First product name: " + firstResultProductName);
        Assertions.assertTrue(firstResultProduct.getText().toLowerCase().contains(productKeyword.toLowerCase()),
                "The first product does not match with the search string.");


    }
}
