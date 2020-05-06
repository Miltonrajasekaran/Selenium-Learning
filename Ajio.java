package nykaa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ajio {
	
	public static ChromeDriver driver;
	public static WebDriverWait wt;
	

	public static void main(String[] args) throws InterruptedException {
		
		// Driver
		System.setProperty("webdriver.chrome.driver", "./Drive/chromedriver.exe");
		 System.setProperty("webdriver.chrome.silentOutput", "true");
				
		//notification
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--disable-notifications");
				
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
				
		//startup
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		WebDriverWait wt = new WebDriverWait(driver,30);
		Actions mouse = new Actions(driver);
			
		//1 Go to Ajio
		driver.get("https://www.ajio.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElementByXPath("//img[@class='btn-cat-nav img-animate']").click();
		
		//2 Enter Bags in the Search field and Select Bags in Women Handbags
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='searchVal']")));
		driver.findElementByXPath("//input[@name='searchVal']").click();
		driver.findElementByXPath("//input[@name='searchVal']").sendKeys("bags");
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Handbags in ']")));
		driver.findElementByXPath("//span[text()='Handbags in ']").click();
		
		//3 Click on five grid and Select SORT BY as "What's New"
		driver.findElementByXPath("//div[@class='five-grid']").click();
		Select sortBy = new Select(driver.findElementByXPath("//div[@class='filter-dropdown']//select[1]"));
		sortBy.selectByVisibleText("What's New");
		
		//4 Enter Price Range Min as 2000 and Max as 5000 
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='price']")));
		driver.findElementByXPath("//span[text()='price']").click();
		Thread.sleep(500);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='minPrice']")));
		driver.findElementByXPath("//input[@id='minPrice']").click();
		driver.findElementByXPath("//input[@id='minPrice']").sendKeys("2000",Keys.TAB);
		Thread.sleep(500);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='maxPrice']")));
		driver.findElementByXPath("//input[@id='maxPrice']").click();
		driver.findElementByXPath("//input[@id='maxPrice']").sendKeys("5000",Keys.ENTER);
		
		//5 Click on the product "Puma Ferrari LS Shoulder Bag" 
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Ferrari LS Shoulder Bag']")));
		driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']").click();
		
		//6 Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon
		Set<String> windows = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(windows);
		driver.switchTo().window(winList.get(1));
		
		String proPrice = driver.findElementByXPath("//div[@class='prod-sp']").getText();
		proPrice = proPrice.replaceAll("[\\D]", "");
		System.out.println(proPrice);
		
		int orgPrice = Integer.parseInt(proPrice);
		
		int price = 2690;
		
		if (price < orgPrice) {
			
			System.out.println("product available for coupon code");
		}
		
		String couponCode = driver.findElementByXPath("//div[@class='promo-title']").getText().substring(9);
		System.out.println("Get coupon code :" + couponCode);
		
		String disAmo = driver.findElementByXPath("//span[text()='2141']").getText();
		disAmo = disAmo.replaceAll("[\\D]", "");
		int finAmo = Integer.parseInt(disAmo);
		System.out.println(finAmo);
		
		int disValue;
		
		disValue = orgPrice-finAmo;
		
		System.out.println("discount value is " + disValue);
		
		//7 Check the availability of the product for pincode 560043, print the expected delivery date if it is available 
		driver.findElementByXPath("//span[text()='Enter pin-code to know estimated delivery date.']").click();
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='number']")));
		driver.findElementByXPath("//input[@type='number']").click();
		driver.findElementByXPath("//input[@type='number']").sendKeys("560043", Keys.ENTER);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='13 May']")));
		String expDelDate = driver.findElementByXPath("//span[text()='13 May']").getText();
		System.out.println(expDelDate);
		
		//8 Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Other information']")));
		driver.findElementByXPath("//div[text()='Other information']").click();
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='other-info'])[6]")));
		String cusCareInfo = driver.findElementByXPath("(//span[@class='other-info'])[6]").getText();
		System.out.println(cusCareInfo);
		
		//9 Click on ADD TO BAG and then GO TO BAG
		driver.findElementByXPath("//div[@class='btn-gold']").click();
		Thread.sleep(1000);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='PROCEED TO BAG']")));
		driver.findElementByXPath("//div[text()='PROCEED TO BAG']").click();;
		
		//10 Check the Order Total before apply coupon
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Rs. 2,974.00']")));
		String orderTotal = driver.findElementByXPath("//span[text()='Rs. 2,974.00']").getText();
		orderTotal = orderTotal.replaceAll("[\\D].", "");
		int finOrderTotal = Integer.parseInt(orderTotal);
		System.out.println(finOrderTotal);
		
		if (finOrderTotal == orgPrice) {
			
			System.out.println("total is matching - and the amount without discount is = " + finOrderTotal);
		}
		else 
			
			System.out.println("not matched");
		
		//11 Enter Coupon Code and Click Apply 
		driver.findElementByXPath("//input[@id='couponCodeInput']").sendKeys("" + couponCode + "");
		driver.findElementByXPath("//button[text()='Apply']").click();
		//12 Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details 
		Thread.sleep(3000);
		String text = driver.findElementByXPath("(//span[@class='price-value discount-price'])[2]").getText()
				.substring(4);
		double savingsAmt = Double.parseDouble(text);
		int amt = (int) Math.round(savingsAmt);
		System.out.println("Saved amount is: " + amt);
		if (amt == disValue) {
			System.out.println("Coupon savings amount is correct.Discount price is:" + amt);
		}
		
		//13 Click on Delete and Delete the item from Bag
		driver.findElementByXPath("//div[@class='delete-btn']").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//div[text()='DELETE']").click();
		System.out.println("Product removed sucessfully");
		//14 Close all the browsers
		driver.close();
						

	}

}
