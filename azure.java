package nykaa;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class azure {
	
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
					
		//1 Go to azure
		driver.get("https://azure.microsoft.com/en-in/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2 click pricing
		driver.findElementByLinkText("Pricing").click();
		
		//3 click pricing calculator
		driver.findElementByLinkText("Pricing calculator").click();
		
		//4 click on containers
		driver.findElementByXPath("//button[@value='containers']").click();
		
		//5 select container Instants
		driver.findElementByXPath("(//span[text()='Container Instances'])[3]").click();
		
		//6 Click on Container Instance Added View
		Thread.sleep(500);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='View']")));
		driver.findElementByXPath("//a[text()='View']").click();
		
		//7 region is southindia
		Select region = new Select(driver.findElementByXPath("//select[@name='region']"));
		region.selectByVisibleText("South India");
		
		//8 Set the Duration as 180000 seconds
		driver.findElementByXPath("//input[@name='seconds']").click();
		driver.findElementByXPath("//input[@name='seconds']").sendKeys(Keys.DELETE);
		driver.findElementByXPath("//input[@name='seconds']").sendKeys("18000");
		
		//9 select the memory as 4 GB
		Select memory = new Select(driver.findElementByXPath("//select[@name='memory']"));
		memory.selectByVisibleText("4 GB");
		
		//10 Enable SHOW DEV/TEST PRICING
		driver.findElementById("devtest-toggler").click();
		
		//11 Select Indian Rupee  as currency 
		Select currency = new Select(driver.findElementByXPath("//select[@class='select currency-dropdown']"));
		currency.selectByVisibleText("Indian Rupee (₹)");
		
		//12 Print the Estimated monthly price
		Thread.sleep(500);
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='column large-3 text-right total']//child::div[2]/span/span")));
		String monthlyCost = driver.findElementByXPath("//div[@class='column large-3 text-right total']//child::div[2]/span/span").getText();
		System.out.println("Monthly cost is : "+ monthlyCost);
		
		//13 Click on Export to download the estimate as excel
		driver.findElementByXPath("(//button[contains(@class,'calculator-button button-transparent')])[4]").click();
		
		//14 verify the file in local folder
		File dir = new File("C:\\Users\\Milton\\Downloads");
		  File[] dirContents = dir.listFiles();
		  int flag=0;

		  for (int i = 0; i < dirContents.length; i++) {
		      if (dirContents[i].getName().equals("ExportedEstimate.xlsx")) {
		          // File has been found, it can now be deleted:
		          
		          flag=1;
		          break;
		      }
		          }
		      
		if(flag==1)
			System.out.println("file found");
		else
			System.out.println("file not found");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		
		//15 Navigate to Example Scenarios and Select CI/CD for Containers
		//wt.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Example Scenarios")));
		
		driver.findElementByXPath("//a[text()='Example Scenarios']").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//span[text()='CI/CD for Containers']").click();
		
		//16 click add 
		Thread.sleep(2000);
		driver.findElementByXPath("//button[contains(@class,'button button--secondary01')]").click();
		//17 select curreny
		Thread.sleep(2000);
		Select currency1 = new Select(driver.findElementByXPath("//select[@class='select currency-dropdown']"));
		currency1.selectByVisibleText("Indian Rupee (₹)");
		
		//18 Enable SHOW DEV/TEST PRICING
		Thread.sleep(2000);
		driver.findElementByXPath("//button[@id='devtest-toggler']").click();
		
		//19 click export
		Thread.sleep(2000);
		driver.findElementByXPath("(//button[contains(@class,'calculator-button button-transparent')])[4]").click();
		
		//20 verify the file in local folder
				File dir1 = new File("C:\\Users\\Milton\\Downloads");
				  File[] dirContents1 = dir1.listFiles();
				  int flag1=0;

				  for (int i = 0; i < dirContents1.length; i++) {
				      if (dirContents1[i].getName().equals("ExportedEstimate(1).xlsx")) {
				          // File has been found, it can now be deleted:
				          
				          flag1=1;
				          break;
				      }
				          }
				      
				if(flag1==1)
					System.out.println("2nd file found");
				else
					System.out.println("2nd file not found");
		
				//21 Close all the browsers
				driver.close();
									
		

	}

}
