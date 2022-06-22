package Assignments;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Question6 
{
	static ExtentTest test;
	static ExtentReports extent;
	
	public static void main(String[] args) throws Exception
	{
		//Generating a .html report
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport_Q6.html",true);
		test = extent.startTest("Test Started!");

		//Setting up chrome from WebDriverManager class so that it doesn't need to instantiate every time
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		//hitting the url
		String url = "https://www.drbatras.com/";
		driver.get(url);
		driver.navigate().refresh();
		test.log(LogStatus.PASS,"Page navigated to "+ url);
		System.out.println("Page navigated to "+ url);

		//Hover over the 'Treatments Tab'
		Actions act = new Actions(driver);
		WebElement treatment = driver.findElement(By.xpath("//*[@id='navbarDropdown0'][contains(text(),'TREATMENTS')]"));
		act.moveToElement(treatment).build().perform();

		//Click on 'Child Health option'
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='navbarDropdown0']//following::a[@class][contains(text(),'Child Health')]")).click();
		test.log(LogStatus.PASS,"Clicked on Child Health");

		//Click on 'Immunity'
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='navbarDropdown0']//following::a[contains(@title,'Immunity')]")).click();
		test.log(LogStatus.PASS,"Clicked on Immunity");

		//Verify the 'Immunity' page navigation
		String expectedTitle = "Boost Immunity of your Child, Low Immunity Causes, Homeopathic Treatment & Medicine - Dr Batra's®";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		test.log(LogStatus.PASS,"Page navigated to "+ expectedTitle);
		System.out.println("Landed on Immunity page");

		//Verifying the chat bot instance
		WebElement chatBot = driver.findElement(By.xpath("//button[@id='chatNowBtn']"));		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='chatNowBtn']")));

		if(chatBot.isDisplayed())
		{
			//Open the chat bot
			Thread.sleep(2000);
			chatBot.click();
			test.log(LogStatus.PASS,"Chat bot opened");
			System.out.println("Chat bot opened");

			//As the chat bot is an iframe, switching to iframe...
			WebElement chatbot_iframe = driver.findElement(By.xpath("//*[@id='kenytChatWindow']"));
			driver.switchTo().frame(chatbot_iframe);
			Thread.sleep(3000);			
			//Close the chat bot
			driver.findElement(By.xpath("//*[@id='chatWindow']//*[@id='headerCloseButton']")).click();
			test.log(LogStatus.PASS,"Chat bot closed");
			System.out.println("Chat bot closed");

			driver.switchTo().defaultContent();
		}

		//Entering the details to book an appointment
		driver.findElement(By.xpath("//input[@id='edit-name']")).sendKeys("username");
		driver.findElement(By.xpath("//input[@name='email_id']")).sendKeys("username@test.com");
		driver.findElement(By.xpath("//input[@id='edit-mobile-no']")).sendKeys("0987654321");

		//Selecting the 'TnCs' checkbox
		driver.findElement(By.xpath("//input[@id='edit-terms-and-conditions']")).click();

		//Click on 'Consult Now' button
		driver.findElement(By.xpath("//input[@id='edit-actions-submit']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebElement successMessage_xpath = driver.findElement(By.xpath("//*[@id='ui-id-1']//*[contains(.,'Thank you')]"));
		if(successMessage_xpath.isDisplayed())
		{
			//Verify the success message pop up
			Assert.assertTrue(successMessage_xpath.isDisplayed());
			test.log(LogStatus.PASS,"Verified: Success Message displayed "+ successMessage_xpath.getText());
			System.out.println("Verified: Success Message displayed "+ successMessage_xpath.getText());
			System.out.println("Please find the generated report at .\\TabSquare\\TabSquare\\test-output");
		}
		
		//Saving .html report
		extent.endTest(test);
		extent.flush();
		extent.close();
		driver.quit();
	}
}