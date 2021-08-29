package assignments.week4.day1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LeafGroundFrames_Assignment4 {

	public static void main(String[] args) throws IOException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		
		//  Launch URL "http://leafground.com/pages/frame.html"
		driver.get("http://leafground.com/pages/frame.html");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//1.Take the the screenshot of the click me button of first frame
		WebElement frameOne = driver.findElement(By.xpath("//div[@id='wrapframe'][1]/iframe"));
		driver.switchTo().frame(frameOne);
		WebElement buttonElement = driver.findElement(By.id("Click"));
		
		File src = buttonElement.getScreenshotAs(OutputType.FILE);
		File dest = new File("./snaps/ClickButton.png");
		FileUtils.copyFile(src, dest);
		
		//Navigate back to the main page
		driver.switchTo().defaultContent();
		//Find the number of frames
		List<WebElement> frameElements = driver.findElements(By.tagName("iframe"));
		System.out.println("Total count of frames visible to main page is : "+frameElements.size());
		
		
	}

}
