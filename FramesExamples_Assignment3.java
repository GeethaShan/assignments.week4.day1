package assignments.week4.day1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FramesExamples_Assignment3 {

	public static void main(String[] args) throws InterruptedException {
		// Driver setup
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1. Launch URL "http://leaftaps.com/opentaps/control/login"
		driver.get("https://chercher.tech/practice/frames-example-selenium-webdriver");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// 2. Get the text displayed as Topic
		System.out.println(driver.findElement(By.xpath("//label[contains(text(),'Topic : ')]")).getText());

		// Enter text in Topic: text box
		driver.switchTo().frame("frame1");
		WebElement topicTextBox = driver.findElement(By.xpath("//b[@id='topic']/following::input"));
		topicTextBox.sendKeys("Selenium Tutorial");
		
		// Check the Inner frame check box
		driver.switchTo().frame("frame3");
		driver.findElement(By.id("a")).click();
		// Check if its checked
		boolean isSelected = driver.findElement(By.id("a")).isSelected();
		if (isSelected) {
			System.out.println("PASS - Check box is checked properly");
		} else
			System.out.println("FAIL - Check box is not checked");

		
		// Go back to the main page
		driver.switchTo().defaultContent();

		// Move to Animals frame to select Big Baby Cat
		driver.switchTo().frame("frame2");
		WebElement animalsSelBox = driver.findElement(By.id("animals"));
		Select dropDownAnimals = new Select(animalsSelBox);
		dropDownAnimals.selectByValue("big baby cat");
		if (animalsSelBox.getAttribute("value").equals("big baby cat")) {
			System.out.println("Option is selected properly");
		} else
			System.out.println("Option is not selected. Actual : " + animalsSelBox.getAttribute("value"));

		// Switch back to the main page
		driver.switchTo().defaultContent();

	}

}
