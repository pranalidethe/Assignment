package Assignments;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Question4 
{
	static ExtentTest test;
	static ExtentReports extent;

	public static void main(String[] args) 
	{
		//Generating a .html report
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport_Q4.html",true);
		test = extent.startTest("Test Started!");

		//Setting up chrome from WebDriverManager class so that it doesn't need to instantiate every time
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		//hitting the url
		String url = "https://smartweb-ecms.tabsquare.com/scan/ac74085b-8afd-4475-91f1-f4e8ba642dce";
		driver.get(url);
		test.log(LogStatus.PASS,"Page navigated to "+ url);
		System.out.println("Page navigated to "+ url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Selecting the country as India i.e. '+91'
		WebElement countryDropdown = driver.findElement(By.xpath("//select[@name='country_code']"));
		Select sel = new Select(countryDropdown);
		sel.selectByValue("+91");

		//Entering the mobile number 
		driver.findElement(By.xpath("//input[@type='tel']")).sendKeys("7620184112");

		//Logging in
		driver.findElement(By.xpath("//button[@class]")).click();
		test.log(LogStatus.PASS,"Page navigated to "+ driver.getTitle());
		System.out.println("Page navigated to "+ driver.getTitle());

		//Clicking the 'Dine-In' button
		driver.findElement(By.xpath("//*[@*='Dine In']")).click();	
		test.log(LogStatus.PASS,"Click on Dine-In Button");
		System.out.println("Click on Dine-In Button");

		//Choosing the food item as 'Rice Plate'
		driver.findElement(By.xpath("//*[@*='Rice Plate']//following::a[1]")).click();	
		test.log(LogStatus.PASS,"Food Item Added");
		System.out.println("Food Item Added"+ driver.getTitle());

		//Clicking on 'ADD+' button from pop up
		driver.findElement(By.xpath("//*[@id='skuContainer']//a[contains(.,'Add')]")).click();	

		//For loop to make the order quantity as 3, 
		int orderCount = 3;
		for (int i = 0; i < orderCount; i++) 
		{
			driver.findElement(By.xpath("//a[@class='to-plus btn-primary test']")).click();
		}

		//Clicking on 'Check Out'
		driver.findElement(By.xpath("//a[@id='checkoutButton']")).click();		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		test.log(LogStatus.PASS,"Clicked on Checkout");
		System.out.println("Clicked on Checkout");

		//Getting the added items' text from the cart
		String cartValue = driver.findElement(By.xpath("//*[@*='cart-list']//*[@*='add-val']")).getText();

		//Converting the string to integer
		int i = Integer.parseInt(cartValue);  

		//Verify the number of food items added and number of items in the cart are same
		Assert.assertEquals(i, orderCount);
		test.log(LogStatus.PASS,"Verified: The number of food items added is " + orderCount + " and number of items in the cart is " + i);
		System.out.println("Verified: Food items in the cart");
		System.out.println("Please find the generated report at .\\TabSquare\\TabSquare\\test-output");

		//Saving .html report
		extent.endTest(test);
		extent.flush();
		extent.close();
		driver.quit();
	}
}