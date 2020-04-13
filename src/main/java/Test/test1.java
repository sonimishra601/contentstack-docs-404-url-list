package Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.objectweb.asm.tree.TryCatchBlockNode;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import data.ReadPropertiesFile;

public class test1 {

	public static String propFileName = null;
	public static WebDriver driver;

	public static ReadPropertiesFile fileProp = null;

	@SuppressWarnings("static-access")
	@BeforeTest
	public static void setup() {
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "./src/main/driver/chromedriver.exe"); driver = new ChromeDriver();
		 * driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		 * driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		 * driver.manage().window().maximize(); driver.manage().deleteAllCookies();
		 */

		String type = "chromeHeadless";
		if (type.equalsIgnoreCase("chrome")) {
			// System.setProperty("webdriver.chrome.driver",
			// "./src/main/resources/chromedriver");
			System.setProperty("webdriver.chrome.driver", "./src/main/driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");

			// options.addArguments("--disable-setuid-sandbox");
			// options.addArguments("headless");
			options.addArguments("window-size=1366x768");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			driver.manage().window().setSize(new Dimension(1366, 768));
			Dimension dim = driver.manage().window().getSize();
			System.out.println(dim);
			driver.manage().window().maximize();

		} else if (type.equalsIgnoreCase("chromeHeadless")) {

			// System.setProperty("webdriver.chrome.driver",
			// "./src/main/resources/chromedriver");
			System.setProperty("webdriver.chrome.driver", "./src/main/driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");

			// options.addArguments("--disable-setuid-sandbox");
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
			propFileName = "C:\\Users\\Ashish Davda\\eclipse-workspace\\Testing\\src\\main\\java\\data\\urlData.properties";
			fileProp = new ReadPropertiesFile(propFileName);

//			System.out.println("using prop : " + fileProp.getProperty("url" + 1));
			driver.get("https://built:built1234@staging-www.contentstack.com/docs/");
			Thread.sleep(4000);
		} catch (Exception e) {
			System.out.println("exception found in setup method");
		}
	}

	@Test
	public static void openPage() throws IOException, InterruptedException {
		String url = null;
		File file = new File("MyFile.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String titleContent = null;
		String decriptionContent = null;
		String out = null;
		try {
//			System.out.println(fileProp.getProperty("url") + ":" + driver.getTitle());	
//			System.out.println(driver.findElement(By.xpath("//meta[@name='title']")).getAttribute("content"));

			
			List<String> outList = new ArrayList<String>();
			for (int i = 1; i <= 577; i++) {
				try {
//					System.out.println("--------------------------------------------------------------------------------------");
					url = fileProp.getProperty("url" + i);
					driver.navigate().to(url);
					Thread.sleep(3000);
//					System.out.println(fileProp.getProperty("url" + i) + ":" + driver.getTitle());
					titleContent = driver.findElement(By.xpath("//meta[@name='title']")).getAttribute("content");
					decriptionContent = driver.findElement(By.xpath("//meta[@name='description']"))
							.getAttribute("content");
					System.out
							.println(url +"--- title --- "+driver.getTitle()+ "$titleContent-" + titleContent + "&decriptionContent-" + decriptionContent);
//					writeUsingFiles(url+"$titleContent-"+titleContent+"&decriptionContent-"+decriptionContent); 
					out = url + "--- title --- "+driver.getTitle()+"---->$titleContent-" + titleContent + "&---->decriptionContent-" + decriptionContent;
					outList.add(out);
					writeUsingFiles(bw, out);
				} catch (Exception e) {
					System.out.println("exception found in openPage for loop " + url);
//					out = "exception found: " + url;
					out= url + "--- title --- "+driver.getTitle()+"---->$titleContent-" + titleContent + "&---->decriptionContent-" + decriptionContent;
					outList.add(out);
					writeUsingFiles(bw, out);
				}
			}
			System.out.println("size of list : " + outList.size());
			Thread.sleep(2000);
			driver.close();
		} catch (Exception e) {
			System.out.println("exception found in openPage method : " + url);
			out= url + "--- title --- "+driver.getTitle()+"---->$titleContent-" + titleContent + "&---->decriptionContent-" + decriptionContent;
			writeUsingFiles(bw, out);
//			writeUsingFiles("exception found in openPage method : "+url);
		}
		bw.close();
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
