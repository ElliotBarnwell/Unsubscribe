/*
 * Created by Elliot Barnwell Dec 7, 2016
 * 
 */
package com.unsubscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Unsubscribe {

	public static String filename="Unsubscribeconfig.ini";
	public static int maxto=20;
	public static RemoteWebDriver driver;
	static boolean linkfound=false;
	static boolean found;
	public static String username;
	public static String password;
	public static ArrayList<String> sender= new ArrayList<String>();
	public static String winHandleBefore;
	public static boolean nextbuttonfound=false;
	public static String senderName="NA";
	public static int scrnshtcnt=1;


	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebElement row=null;

		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {

			Wini ini= new Wini(new File(filename));
			username=ini.get("INFO","username");
			password=ini.get("INFO", "password");

			for (String retval: ini.get("INFO", "sender").split(",")) {
				if(retval!=null){
					sender.add(retval);
					System.out.println(retval);
				}
			}

			System.out.println(username + password + sender);

		} catch (InvalidFileFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 



		driver= new ChromeDriver();
		driver.get("https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/#identifier");

		driver.manage().window().maximize();
		winHandleBefore = driver.getWindowHandle();
		waitforElement("//*[@id='Email']","xpath",maxto);
		WebElement email=driver.findElement(By.xpath("//*[@id='Email']"));
		email.sendKeys(username);

		WebElement nextb= driver.findElement(By.id("next"));
		nextb.click();

		waitforElement("signIn","id",maxto);
		WebElement pass= driver.findElement(By.id("Passwd"));
		pass.sendKeys(password);

		WebElement submitb= driver.findElement(By.id("signIn"));
		submitb.click();

		waitforElement("//*[@id='gbqfq']","xpath",maxto);
		WebElement search= driver.findElement(By.xpath("//*[@id='gbqfq']"));
		// JavascriptExecutor rightexecutor = (JavascriptExecutor)driver;
		//rightexecutor.executeScript("arguments[0].setAttribute('innerHTML','Unsubscribe')", search);
		search.sendKeys("unsubscribe");

		WebElement searchb= driver.findElement(By.xpath("//button[@id='gbqfb']"));
		searchb.click();

		Thread.sleep(1000);
		Wait<WebDriver> wait1 = new WebDriverWait(driver,maxto);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='F cf zt']")));

		//MAY HAVE TO USE 21 TABS TO REACH NEXT PAGE BUTTON
		//waitforElement("//img[@class='amJ T-I-J3']/@src","xpath",maxto);
		//WebElement nextresultpage= driver.findElement(By.xpath("//img[@class='amJ T-I-J3']/@src"));
		//nextresultpage.click();

		do{
			boolean nextbuttonfound=false;

			for(int i=1; i<50; i++){


				driver.navigate().refresh();
				linkfound=false;
				try{ 
					Wait<WebDriver> wait3 = new WebDriverWait(driver,maxto);
					wait3.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//table[@class='F cf zt'])/tbody/tr["+Integer.toString(i)+"]/td[5]")));


					List<WebElement> tables= new ArrayList<WebElement>();
					tables=driver.findElements(By.xpath("(//table[@class='F cf zt'])/tbody/tr["+Integer.toString(i)+"]/td[5]"));
					Iterator<WebElement> iterator = tables.iterator();

					while (iterator.hasNext()) {
						WebElement tb= null;
						tb= iterator.next();
						if(tb.isDisplayed()){
							System.out.println("Table found");
							row=tb;

							break;
						}

					}

				}


				catch(Exception e)
				{
					e.printStackTrace();

				}

				boolean checkedsender= checkSender(row.getText());

				if(!(row.getText().equals("")) && checkedsender ){


					if (driver instanceof JavascriptExecutor) {
						((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", row);
					}

					String sender = row.getText();
					String[] parts = sender.split(" ");
					senderName = parts[0]; 


					row.click();

					if(!linkfound)
					{
						try{

							waitforElement("//div[@class='adn ads']//a[contains(.,'unsubscribe')]","xpath",10);
							WebElement ul= driver.findElement(By.xpath("//div[@class='adn ads']//*[contains(.,'unsubscribe')]/a"));
							unsubscribe(ul);
						}



						catch(Exception e){
							e.printStackTrace();
						}
					}

					if(!linkfound)
					{
						try{


							waitforElement("//div[@class='adn ads']//a[contains(.,'Unsubscribe')]","xpath",10);
							WebElement ul=driver.findElement(By.xpath("//div[@class='adn ads']//*[contains(.,'Unsubscribe')]/a"));
							unsubscribe(ul);

						}


						catch(Exception e){
							e.printStackTrace();
						}
					}

					if(!linkfound)
					{
						try
						{


							waitforElement("//div[@class='adn ads']//a[contains(.,'click here')]","xpath",10);
							WebElement ul=driver.findElement(By.xpath("//div[@class='adn ads']//*[contains(.,'click here')]/a"));
							unsubscribe(ul);
						}


						catch(Exception e){
							e.printStackTrace();
						}
					}

					if(!linkfound)
					{
						try{

							waitforElement("//div[@class='adn ads']//a[contains(.,'Click here')]","xpath",10);
							WebElement ul=driver.findElement(By.xpath("//div[@class='adn ads']//*[contains(.,'Click here')]/a"));
							unsubscribe(ul);
						}


						catch(Exception e){
							e.printStackTrace();
						}
					}

					if(!linkfound)
					{
						try{

							waitforElement("//div[@class='adn ads']//a[contains(.,'here')]","xpath",10);
							WebElement ul=driver.findElement(By.xpath("//div[@class='adn ads']//*[contains(.,'here')]/a"));
							unsubscribe(ul);
						}


						catch(Exception e){
							e.printStackTrace();
						}
					}


					driver.navigate().back();
					((JavascriptExecutor) driver).executeScript("window.focus();");
				}




				((JavascriptExecutor) driver).executeScript("window.focus();");


			}


			try{ 

				Wait<WebDriver> wait2 = new WebDriverWait(driver,maxto);
				wait2.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@class='T-I J-J5-Ji amD T-I-awG T-I-ax7 T-I-Js-Gs L3'])/img")));
				// waitforElement("(//div[@class='T-I J-J5-Ji amD T-I-awG T-I-ax7 T-I-Js-Gs L3'])[2]/img","xpath",maxto);

				List<WebElement> buttons= new ArrayList<WebElement>(driver.findElements(By.xpath("(//div[@class='T-I J-J5-Ji amD T-I-awG T-I-ax7 T-I-Js-Gs L3'])/img")));
				Iterator<WebElement> iterator = buttons.iterator();
				while (iterator.hasNext()) {
					WebElement nb= iterator.next();
					if(nb.isDisplayed()){
						System.out.println("NEXT BUTTON Clicked!!!");

						if (driver instanceof JavascriptExecutor) {
							((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", nb);
						}
						nb.click();
						nextbuttonfound=true;
						Thread.sleep(1000);
						break;
					}

				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("NEXT BUTTON NOT FOUND :(");
			}


		}
		while(nextbuttonfound);
		//waitforElement("Unsubscribe","linkText",maxto);
		//e1.click();

		driver.close();
	}


	public static void unsubscribe(WebElement ul){
		try{
			if(found){

				//WebElement u= driver.findElement(By.linkText("unsubscribe"));
				if (driver instanceof JavascriptExecutor) {
					((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", ul);
				}
				//ul.click();
				Actions actions = new Actions(driver);
				actions.moveToElement(ul);
				Actions newwin = new Actions(driver);
				newwin.keyDown(Keys.SHIFT).click(ul).keyUp(Keys.SHIFT).build().perform();
				Thread.sleep(2000);
				for(String winHandle : driver.getWindowHandles()){
					driver.switchTo().window(winHandle);
				}
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				// Now you can do whatever you need to do with it, for example copy somewhere

				FileUtils.copyFile(scrFile, new File("Screenshots/"+senderName+" "+Integer.toString(scrnshtcnt)+".png"));
				scrnshtcnt++;
				Thread.sleep(2000);
				driver.close();
				driver.switchTo().window(winHandleBefore);


				linkfound=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}


	}

	public static boolean checkSender(String emailmsg){
		boolean sendernotfound=true;
		for(int j=0; j<sender.size();j++){

			if(!(sender.get(j).equals("none"))){

				if(emailmsg.contains(sender.get(j))){
					sendernotfound=false;
					break;
				}
				else
				{
					sendernotfound=true;
					break;
				}
			}
		}

		return sendernotfound;
	}

	public static void waitforElement(String identifier, String method, int timeout){
		found=false;
		if(method.equals("name")){ 
			try{


				Wait<WebDriver> wait = new WebDriverWait(driver,timeout);
				wait.until(ExpectedConditions.elementToBeClickable(By.name(identifier)));
				found=true;
			}
			catch(Exception e){
				e.printStackTrace();

			}
		}
		if(method.equals("id")){ 

			try{
				Wait<WebDriver> wait = new WebDriverWait(driver,timeout);
				wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
				found=true;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}


		if(method.equals("xpath")){ 
			try{
				Wait<WebDriver> wait = new WebDriverWait(driver,timeout);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
				found=true;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		if(method.equals("linkText")){ 
			try{
				Wait<WebDriver> wait = new WebDriverWait(driver,timeout);
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(identifier)));
				found=true;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
