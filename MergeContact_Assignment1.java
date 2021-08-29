package assignments.week4.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MergeContact_Assignment1 {

	public static void main(String[] args) throws InterruptedException {
		//Driver setup
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		
		//1. Launch URL "http://leaftaps.com/opentaps/control/login"
		driver.get("http://leaftaps.com/opentaps/control/login");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//2. Enter UserName and Password Using Id Locator
		driver.findElement(By.id("username")).sendKeys("demosalesmanager");
		driver.findElement(By.id("password")).sendKeys("crmsfa");
		
		//3. Click on Login Button using Class Locator
		driver.findElement(By.className("decorativeSubmit")).click();
		
		//4. Click on CRM/SFA Link
		driver.findElement(By.partialLinkText("CRM")).click();
		
		//5. Click on contacts Button
		driver.findElement(By.linkText("Contacts")).click();
		
		//6. Click on Merge Contacts using Xpath Locator
		driver.findElement(By.linkText("Merge Contacts")).click();
		
		//7. Click on Widget of From Contact
		driver.findElement(By.xpath("//input[@name='partyIdFrom']/following::img")).click();
		
		//8. Click on First Resulting Contact
		Set<String> windowHandlesSet = driver.getWindowHandles();
		List<String> windowHandlesList = new ArrayList<String>(windowHandlesSet);
		driver.switchTo().window(windowHandlesList.get(1));
		driver.findElement(By.xpath("//tr/td//a[@class='linktext']")).click();
		
		// 9. Click on Widget of To Contact
		driver.switchTo().window(windowHandlesList.get(0));
		driver.findElement(By.xpath("//input[@name='partyIdTo']/following::img")).click();
		
		//10. Click on Second Resulting Contact
		Set<String> windowHandlesSet2 = driver.getWindowHandles();
		List<String> windowHandlesList2 = new ArrayList<String>(windowHandlesSet2);
		driver.switchTo().window(windowHandlesList2.get(1));
		driver.findElement(By.xpath("//tr/td//a[@class='linktext']/ancestor::tr/following::tr/td//a")).click();
		
		// 11. Click on Merge button using Xpath Locator
		driver.switchTo().window(windowHandlesList2.get(0));
		driver.findElement(By.className("buttonDangerous")).click();
		Thread.sleep(3000);
		
		//12. Accept the Alert
		Alert alertDialog = driver.switchTo().alert();
		alertDialog.accept();
		
		//13. Verify the title of the page
		String titleText = driver.getTitle();
		if (titleText.equals("View Contact | opentaps CRM")) {
			System.out.println("PASS - Title is : "+titleText);
		}
		else
			System.out.println("FAIL - Title is : "+titleText);
	}

}
