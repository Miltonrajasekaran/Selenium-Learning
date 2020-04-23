package nykaa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.MediaSize.Engineering;

import org.openqa.selenium.By;
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

public class Honda {
	
	public static ChromeDriver driver;
	 public static WebDriverWait wt;
	
	

	public static void main(String[] args) {
		// Driver
		System.setProperty("webdriver.chrome.driver", "./Drive/chromedriver.exe");
		
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
	
		//1 Go to honda
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElementByXPath("(//button[@type='button'])[2]").click();
		
		//2 Click on scooters and click dio 
		driver.findElementByXPath("//a[@id='link_Scooter']").click();
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/assets/images/thumb/dioBS6-icon.png']")));
		driver.findElementByXPath("//img[@src='/assets/images/thumb/dioBS6-icon.png']").click();
		
		//3 Click on Specifications and mouseover on ENGINE 
		driver.findElement(By.xpath("(//a[text()='Specifications'])[1]")).click();
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='ENGINE'])[1]")));
		driver.findElement(By.xpath("(//a[text()='ENGINE'])[1]")).click();
		
		//4 Get Displacement value 
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Displacement']/following-sibling::span")));
		String dioCC = driver.findElement(By.xpath("//span[text()='Displacement']/following-sibling::span")).getText();
		System.out.println("The Engine Capacity of Dio is: "+dioCC);
		
		//5 go to scooters and select Activa
		driver.findElementByXPath("(//a[text()='Scooter'])[1]").click();
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//img[contains(@src,'activa')])[1]")));
		driver.findElementByXPath("(//img[contains(@src,'activa-125')])[1]").click();
		
		//6 Spec and engine
		driver.findElement(By.xpath("(//a[text()='Specifications'])[1]")).click();
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[text()='ENGINE'])[1]")));
		driver.findElement(By.xpath("(//a[text()='ENGINE'])[1]")).click();
		
		//7 des value
		String activaCC = driver.findElement(By.xpath("//span[text()='Displacement']/following-sibling::span")).getText();
		System.out.println("The Engine Capacity of Activa is: "+activaCC);
		
		//8 compare dic value
		dioCC =  dioCC.replaceAll("[^0-9.]", "");
		activaCC = activaCC.replaceAll("[^0-9.]", "");
		double dioCCInt = Double.parseDouble(dioCC);
		double ActivaCCInt = Double.parseDouble(activaCC);
		
		if(dioCCInt>ActivaCCInt)
			System.out.println("Honda Dio has more Cubic Capacity than Activa");
		else
			System.out.println("Activa has more Cubic Capacity than Dio");
		
		//9 click FAQ
		driver.findElement(By.xpath("(//a[text()='FAQ'])[1]")).click();
		
		//10 click Ativa 125
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Activa 125 BS-VI']")));
		driver.findElement(By.xpath("//a[text()='Activa 125 BS-VI']")).click();
		
		//11 click vehicle price
		wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Vehicle Price']")));
		driver.findElement(By.xpath("//a[text()=' Vehicle Price']")).click();
		
		//12 make sure activa is selected
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ModelID6']")));
		WebElement activa = driver.findElementByXPath("//select[@id='ModelID6']");
		Select activdrop = new Select(activa);
		String selectedVal = activdrop.getFirstSelectedOption().getText();
		if(selectedVal.equalsIgnoreCase("Activa 125 BS-VI")){
			System.out.println("Activa 125 BS-VI has been selected");
			driver.findElementById("submit6").click();
		}
		else
		System.out.println("Wrong Model Selected");
		
		//13 click the price link
		driver.findElement(By.partialLinkText("Click here to know the price of Activa")).click();
		
		//14 go to new window and select state and city
		Set<String> windows = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(windows);
		driver.switchTo().window(winList.get(1));
		Select state = new Select(driver.findElement(By.id("StateID")));
		state.selectByVisibleText("Tamil Nadu");
		
		Select city = new Select(driver.findElementByXPath("//select[@name='CityID']"));
		city.selectByVisibleText("Chennai");
		
		//15 click search
		driver.findElement(By.xpath("//button[text()='Search']")).click();
		
		//16 store value in table
		String model = "";
		String price = "";
		Map<String, String> map1 =  new LinkedHashMap<String, String>();
		WebElement scooters = driver.findElementByXPath("//table[@id='gvshow']/tbody");
		List<WebElement> allRows = scooters.findElements(By.tagName("tr"));
		for(int i = 0; i < allRows.size(); i++) {
			List<WebElement> allCells = allRows.get(i).findElements(By.tagName("td"));
			if(i == 0) {
				model = allCells.get(1).getText();
				price = allCells.get(2).getText();
			}
			else {
				model = allCells.get(0).getText();
				price = allCells.get(1).getText();
			}
			map1.put(model, price);				
		}
		for (Entry<String,String> e : map1.entrySet()) {
			System.out.println("The Price for model "+e.getKey()+" is "+e.getValue());
		}
		
		//17close
		driver.manage().deleteAllCookies();
		driver.quit();
	
	

		
	}
	

}
