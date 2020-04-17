package nykaa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class makeMyTrip {

	public static void main(String[] args) throws InterruptedException {
		
		//driver
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
		//notification
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		//startup
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		WebDriverWait wt = new WebDriverWait(driver,30);
	
		//1 Go to https://www.makemytrip.com/
		driver.get("https://www.makemytrip.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2 click hotels
		driver.findElementByXPath("(//span[contains(@class,'chNavIcon appendBottom2')])[2]").click();
		
		//3 Enter city as Goa, and choose Goa, India 
		driver.findElementByXPath("//input[@id='city']").click();
		driver.findElementByXPath("//input[@placeholder='Enter city/ Hotel/ Area/ Building']").sendKeys("Goa");
		Thread.sleep(3000);
		driver.findElementByXPath("//p[text()='Goa, India']").click();
		
		//4 Enter Check in date as Next month 15th (May 15) and Check out as start date+5 
		driver.findElementByXPath("(//div[text()='20'])[2]").click();
		driver.findElementByXPath("(//div[text()='25'])[2]").click();
		
		//5 Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply Button.
		driver.findElementByXPath("//input[@class='hsw_inputField guests font20']").click();
		driver.findElementByXPath("//li[@data-cy='adults-2']").click();
		driver.findElementByXPath("//li[@data-cy='children-1']").click();
		
		WebElement childAge = driver.findElementByClassName("ageSelectBox");
		
		Select chAge = new Select(childAge);
		chAge.selectByVisibleText("12");
		
		driver.findElementByXPath("//button[text()='APPLY']").click();
		
		//6 Click Search button 
		driver.findElementByXPath("//button[@data-cy='submit']").click();
		
		//7 Select locality as Baga 
		driver.findElementByXPath("//body[@class='bodyFixed overlayWholeBlack']").click();
		WebElement eleBaga = driver.findElementByXPath("//label[text()='Baga']");
		Thread.sleep(500);
		wt.until(ExpectedConditions.elementToBeClickable(eleBaga));
		eleBaga.click();
		
		//8 Select 5 start in Star Category under Select Filters
		WebElement eleRating = driver.findElementByXPath("//label[text()='5 Star']");
		Thread.sleep(500);
		wt.until(ExpectedConditions.elementToBeClickable(eleRating));
		eleRating.click();
		
		//9 Click on the first resulting hotel and go to the new window 
		driver.findElementByXPath("(//div[contains(@class,'makeFlex top')])[6]").click();
		
		Set<String> searchSet = driver.getWindowHandles();
		List<String> searchList = new ArrayList<String>(searchSet);
		driver.switchTo().window(searchList.get(1));
		
		//10 Print the Hotel Name 
		WebElement hotelName = driver.findElementByXPath("//h1[@id='detpg_hotel_name']");
		String hotel = hotelName.getText();
		System.out.println("Hotel name is : "+hotel);
		
		//11 Click MORE OPTIONS link and Select 3Months plan and close 
		driver.findElementByXPath("(//span[text()='MORE OPTIONS'])[1]").click();
		driver.findElementByXPath("(//span[text()='SELECT'])[1]").click();
		driver.findElementByClassName("close").click();
		
		//12 Click Book Now
		driver.findElementByXPath("//a[text()='BOOK THIS NOW']").click();
		
		//13 Print the total Amount
		WebElement eleAmount = driver.findElementById("revpg_total_payable_amt");
		String amount = eleAmount.getText();
		System.out.println("Amount is "+amount);
		
		driver.close();

	}

}