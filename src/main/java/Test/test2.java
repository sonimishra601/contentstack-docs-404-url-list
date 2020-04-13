package Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import data.ReadPropertiesFile;


public class test2 {

	public static String propFileName = null;
	public static WebDriver driver;

	public static ReadPropertiesFile fileProp = null;

	@BeforeTest
	public static void setup() {

		String type = "chromeHeadless";
		if (type.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", "./src/main/driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");

			options.addArguments("window-size=1366x768");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(1366, 768));
			Dimension dim = driver.manage().window().getSize();
			System.out.println(dim);
			driver.manage().window().maximize();

		} else if (type.equalsIgnoreCase("chromeHeadless")) {

			System.setProperty("webdriver.chrome.driver", "./src/main/driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");
			options.addArguments("headless");
			options.addArguments("window-size=1366x768");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(1366, 768));
			Dimension dim = driver.manage().window().getSize();
			System.out.println(dim);
			driver.manage().window().maximize();

		}
		try {
//			propFileName = ".src\\main\\java\\data\\urlData.properties";
//			fileProp = new ReadPropertiesFile(propFileName);
			driver.get("https://www.contentstack.com/docs/");
			System.out.println("Launching URL...");
			Thread.sleep(4000);
		} catch (Exception e) {
			System.out.println("exception found in setup method");
		}
	}

	@Test
	public static void openPage() throws IOException, InterruptedException {
		String url = null;
		String urlrowWise;
		int count = 0;
		File file = new File("MyFile.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String titleContent = null;
		String decriptionContent = null;
		String title = null;
		boolean PageFound = false;
//		String out = null;
		String currentURL = null;
		try {

			List<String> outList = new ArrayList<String>();
			List l = readFileInList(
					".\\src\\main\\java\\data\\urlData.properties");

			Iterator<String> itr = l.iterator();
	
			while (itr.hasNext()) {

				urlrowWise = itr.next();
				try {

					driver.navigate().to(urlrowWise);
					Thread.sleep(3000);
					try {
						PageFound = driver.findElement(By.xpath("//img[@alt='Page Not Found (404)']")).isDisplayed();
					} catch (Exception e) {
						PageFound = false;
					}
					if (PageFound) {
						System.out.println("Page Not Found inside if:" + urlrowWise);
						writeUsingFiles(bw, "Page Not Found if :" + urlrowWise);
//						writeUsingFiles(bw, "Page Not Found");
						writeUsingFiles(bw,
								"--------------------------------------------------------------------------------");
//						System.out.println(urlrowWise);
						PageFound = true;
						count++;
					} else {

//					System.out.println(fileProp.getProperty("url" + i) + ":" + driver.getTitle());
						currentURL = driver.getCurrentUrl();
						title = driver.getTitle();
						titleContent = driver.findElement(By.xpath("//meta[@name='title']")).getAttribute("content");
						decriptionContent = driver.findElement(By.xpath("//meta[@name='description']"))
								.getAttribute("content");

						writeUsingFiles(bw, "URL : " + urlrowWise);
						writeUsingFiles(bw, "Current URL : " + currentURL);
						writeUsingFiles(bw, "Meta Title : " + titleContent);
						writeUsingFiles(bw, "Meta Desc : " + decriptionContent);
						writeUsingFiles(bw, "Header title : " + title);
						writeUsingFiles(bw,
								"--------------------------------------------------------------------------------");
						System.out.println(urlrowWise);
						count++;
					}
				} catch (Exception e) {
					System.out.println("Page Not Found :" + urlrowWise);
					writeUsingFiles(bw, "Page Not Found :" + urlrowWise);
//					writeUsingFiles(bw, "Page Not Found");
					writeUsingFiles(bw,
							"--------------------------------------------------------------------------------");
					System.out.println(urlrowWise);
					count++;
				}
			}

			Thread.sleep(2000);
			driver.close();
		} catch (Exception e) {
			System.out.println("exception found in openPage method : " + url);
//			out= url + "--- title --- "+driver.getTitle()+"---->$titleContent-" + titleContent + "&---->decriptionContent-" + decriptionContent;
//			writeUsingFiles(bw, out);
//			writeUsingFiles("exception found in openPage method : "+url);
		}
		bw.close();
	}

	public static List<String> readFileInList(String fileName) {

		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static void writeUsingFiles(BufferedWriter bw, String data) {
		try {
			bw.write(data);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
