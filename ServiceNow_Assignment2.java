package assignments.week4.day1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServiceNow_Assignment2 {

	public static void main(String[] args) throws InterruptedException, IOException {
		// Driver setup
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		//Step1: Load ServiceNow application URL given above
		driver.get("https://dev113545.service-now.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Step2: Enter username as admin
		driver.switchTo().frame("gsft_main");
		driver.findElement(By.id("user_name")).sendKeys("admin");
		
		//Step3: Enter password as w6hnF2FRhwLC
		driver.findElement(By.id("user_password")).sendKeys("w6hnF2FRhwLC");
		
		//Step4: Click Login
		driver.findElement(By.id("sysverb_login")).click();
		
		//Step5: Search “incident “ Filter Navigator
		driver.switchTo().defaultContent();
		driver.findElement(By.id("filter")).sendKeys("incident");
		
		//Step6: Click “All”
		driver.findElement(By.xpath("//div[text()='Resolved']//following::div[text()='All']")).click();
		
		//Step7: Click New button
		driver.switchTo().defaultContent();
		driver.switchTo().frame("gsft_main");
		driver.findElement(By.xpath("//button[text()='New']")).click();
		
		//Step8: Select a value for Caller and Enter value for short_description
		driver.findElement(By.id("lookup.incident.caller_id")).click();
		Set<String> windowHandlesSet = driver.getWindowHandles();
		List<String> windowHandlesList = new ArrayList<String>(windowHandlesSet);
		driver.switchTo().window(windowHandlesList.get(1));
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[contains(text(),'within the input')]/following::input")).sendKeys("guest");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[contains(text(),'within the input')]/following::input")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//tr[2]//a[@role='button']")).click();
		driver.findElement(By.xpath("//a[@role='button' and text()='Guest']")).click();
		driver.switchTo().window(windowHandlesList.get(0));
		driver.switchTo().frame("gsft_main");
		driver.findElement(By.id("incident.short_description")).sendKeys("Example for Frames and Window Handles");
		
		//Step9: Read the incident number and save it a variable
		String incidentNumber = driver.findElement(By.id("incident.number")).getAttribute("value");
		System.out.println("incident Number : "+incidentNumber);
		Thread.sleep(3000);
		
		//Step10: Click on Submit button
		driver.findElement(By.id("sysverb_insert")).click();
		
		//Step 11: Search the same incident number in the next search screen as below
		WebElement elementSearch = driver.findElement(By.xpath("//option[text()='for text']/parent::select"));
		Select searchSelect = new Select(elementSearch);
		searchSelect.selectByValue("number");
		
		driver.findElement(By.xpath("//span[contains(text(),'within the input')]/following::input[1]")).sendKeys(incidentNumber);
		driver.findElement(By.xpath("//span[contains(text(),'within the input')]/following::input[1]")).sendKeys(Keys.ENTER);
		
		//Step12: Verify the incident is created successful and take snapshot of the created incident.
		if (driver.findElement(By.xpath("//a[contains(@aria-label,'Open record')]")).isDisplayed()) {
			String actualIncident = driver.findElement(By.xpath("//a[contains(@aria-label,'Open record')]"))
					.getAttribute("aria-label");
			if (actualIncident.contains(incidentNumber)) {
				System.out.println("Incident is created succesfully");
				File src1 = driver.getScreenshotAs(OutputType.FILE);
				File dst = new File("./snaps/incidentNumber.png");
				FileUtils.copyFile(src1, dst);
			} else
				System.out.println("Incident is not created succesfully.Expected : " + incidentNumber + " Actual : "
						+ actualIncident);

		} else
			System.out.println("No results displayed for the incident number.");

	}

}
