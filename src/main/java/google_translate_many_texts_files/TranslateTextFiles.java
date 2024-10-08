package google_translate_many_texts_files;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;

public class TranslateTextFiles {

	public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
	String folderPath = "C:\\Users\\moham\\OneDrive\\Desktop\\Islamic_stories_dataset\\islamic_stories";
	String outputFolderPath = "C:\\Users\\moham\\OneDrive\\Desktop\\Islamic_stories_dataset\\islamic_storis_arabic";

	public static ChromeOptions chromeOption() {
		ChromeOptions chromeOptions = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default.content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		chromeOptions.addArguments("--headless");
		return chromeOptions;
	}

	@Test
	public void main() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(chromeOption());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 5);

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		driver.get("https://translate.google.com/?sl=en");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//button[@class=\"VfPpkd-Bz112c-LgbsSe VfPpkd-Bz112c-LgbsSe-OWXEXe-e5LLRc-SxQuSe yHy1rc eT1oJ mN1ivc ZihNHd KY3GZb\"]/div[@class=\"VfPpkd-Bz112c-RLmnJb\"]")));

		List<WebElement> langChoiceListBtn = driver.findElements(By.xpath(
				"//button[@class=\"VfPpkd-Bz112c-LgbsSe VfPpkd-Bz112c-LgbsSe-OWXEXe-e5LLRc-SxQuSe yHy1rc eT1oJ mN1ivc ZihNHd KY3GZb\"]/div[@class=\"VfPpkd-Bz112c-RLmnJb\"]"));
		langChoiceListBtn.get(1).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-language-code=\"ar\"]")));
		List<WebElement> langChoiceList = driver.findElements(By.xpath("//div[@data-language-code=\"ar\"]"));
		langChoiceList.get(1).click();
		Thread.sleep(200);
		langChoiceListBtn.get(1).click();
		Thread.sleep(200);

		for (File file : listOfFiles) {
			if (file.isFile()) {
				try {
					Thread.sleep(1000);
					// Read the contents of the text file
					Scanner scanner = new Scanner(file);
					StringBuilder textContent = new StringBuilder();
					while (scanner.hasNextLine()) {
						textContent.append(scanner.nextLine()).append("\n");
					}
					scanner.close();
					wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("textarea")));
					WebElement englishTxtField = driver.findElement(By.tagName("textarea"));
					englishTxtField.clear();
					englishTxtField.sendKeys(textContent.toString());
					Thread.sleep(500);

					 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class=\"HwtZe\"]")));
					String translatedText = driver.findElement(By.xpath("//span[@class=\"HwtZe\"]")).getText();
					Thread.sleep(500);

					File outputFile = new File(outputFolderPath + "/" + file.getName());
					Thread.sleep(500);
					FileWriter writer = new FileWriter(outputFile);
					Thread.sleep(500);

					writer.write(translatedText);
					Thread.sleep(500);

					writer.close();

					System.out.println("Translation for " + file.getName() + " completed.");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		driver.quit();
	}
}
