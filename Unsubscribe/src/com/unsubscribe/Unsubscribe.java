/*
 * Created by Elliot Barnwell Dec 7, 2016
 * 
 */
package com.unsubscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
	public static int maxto=15;
	public static RemoteWebDriver driver;
	static boolean found=false;
	public static String username;
	public static String password;
	public static ArrayList<String> sender= new ArrayList<String>();
	public static String winHandleBefore;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
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
		  String winHandleBefore = driver.getWindowHandle();
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
		 
		   waitforElement("//*[@id=':m0']","xpath",maxto);
		   
		   //MAY HAVE TO USE 21 TABS TO REACH NEXT PAGE BUTTON
		   //waitforElement("//img[@class='amJ T-I-J3']/@src","xpath",maxto);
		   //WebElement nextresultpage= driver.findElement(By.xpath("//img[@class='amJ T-I-J3']/@src"));
		   //nextresultpage.click();
		   for(int i=1; i<50; i++){
		   waitforElement("//*[@id=':lu']/tbody/tr["+Integer.toString(i)+"]","xpath",maxto);
		   WebElement e1= driver.findElement(By.xpath("//*[@id=':lu']/tbody/tr["+Integer.toString(i)+"]/td[5]"));
		  
		   if(checkSender(e1.getText())){
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", e1);
		    }
		   
		   e1.click();
		   //waitforElement("unsubscribe","linkText",maxto);
		   //waitforElement("Unsubscribe","linkText",maxto);
		  try{
			  
		  

			  waitforElement("//div[@class='adn ads']//a[contains(.,'unsubscribe')]","xpath",maxto);
			  WebElement ul= driver.findElement(By.xpath("//div[@class='adn ads']//a[contains(.,'unsubscribe')]"));
		   //WebElement u= driver.findElement(By.linkText("unsubscribe"));
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", ul);
		    }
		   //ul.click();
		   Actions newwin = new Actions(driver);
		   newwin.keyDown(Keys.SHIFT).click(ul).keyUp(Keys.SHIFT).build().perform();
		   driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		   for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		   driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		   driver.close();
		   driver.switchTo().window(winHandleBefore);
		  
		   
		   found=true;
		   
		  }
		  catch(Exception e){
			  e.printStackTrace();
		
		  }
		  
		  
		  try{
			  if(!found){
		   waitforElement("//div[@class='adn ads']//a[contains(.,'click me')]","xpath",maxto);
		   
		   WebElement ub=driver.findElement(By.xpath("//div[@class='adn ads']//a[contains(.,'click me')]"));
		   //html[@class='aAX']/body[@class='aAU']/div[7]/div[@class='nH']/div[@class='nH']/div[@class='nH']/div[@class='no']/div[@class='nH nn']/div[@class='nH']/div[@class='nH']/div[@class='nH ar4 z']/div/div[@class='AO']/div[@id=':4']/div[@id=':2']/div[@class='nH']/div[@class='nH']/div[@class='nH g id']/table[@class='Bs nH iY']/tr/td[@class='Bu']/div[@class='nH if']/div[@class='nH aHU']/div[@class='nH hx']/div[@class='nH'][3]/div[@class='h7  ie nH oy8Mbf']/div[@class='Bk']/div[@class='G3 G2']/div/div[@id=':nd']/div[@class='adn ads']
		   //WebElement ub= driver.findElement(By.xpath(("//button[contains(.,'unsubscribe')]")));
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", ub);
		    }
		   
		   //ub.click();
		   Actions newwin = new Actions(driver);
		   newwin.keyDown(Keys.SHIFT).click(ub).keyUp(Keys.SHIFT).build().perform();
		   driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		   for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		   driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		   driver.close();
		   driver.switchTo().window(winHandleBefore);
		   
		   found=true;
			  }
		   }
		   catch(Exception e){
				  e.printStackTrace();
				 
			  }
		  
		  try{
			  if(!found){
		   waitforElement("//div[@class='adn ads']//a[contains(.,'Unsubscribe')]","xpath",maxto);
		   
		   WebElement ub=driver.findElement(By.xpath("//div[@class='adn ads']//a[contains(.,'Unsubscribe')]"));
		   //html[@class='aAX']/body[@class='aAU']/div[7]/div[@class='nH']/div[@class='nH']/div[@class='nH']/div[@class='no']/div[@class='nH nn']/div[@class='nH']/div[@class='nH']/div[@class='nH ar4 z']/div/div[@class='AO']/div[@id=':4']/div[@id=':2']/div[@class='nH']/div[@class='nH']/div[@class='nH g id']/table[@class='Bs nH iY']/tr/td[@class='Bu']/div[@class='nH if']/div[@class='nH aHU']/div[@class='nH hx']/div[@class='nH'][3]/div[@class='h7  ie nH oy8Mbf']/div[@class='Bk']/div[@class='G3 G2']/div/div[@id=':nd']/div[@class='adn ads']
		   //WebElement ub= driver.findElement(By.xpath(("//button[contains(.,'unsubscribe')]")));
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", ub);
		    }
		   
		   //ub.click();
		   Actions newwin = new Actions(driver);
		   newwin.keyDown(Keys.SHIFT).click(ub).keyUp(Keys.SHIFT).build().perform();
		   driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		   for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		   driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		   driver.close();
		   driver.switchTo().window(winHandleBefore);
		   
		   found=true;
			  }
		   }
		   catch(Exception e){
				  e.printStackTrace();
				  
			  }

		
		  
		  driver.navigate().back(); 
		  }
		  
		   
		   
		   
		   ((JavascriptExecutor) driver).executeScript("window.focus();");

		   }
		   
		   //waitforElement("Unsubscribe","linkText",maxto);
		   //e1.click();
		   
		   driver.close();
	}
	
	public static boolean checkSender(String emailmsg){
		boolean sendernotfound=true;
		for(int j=0; j<sender.size();j++){
			
 			if(emailmsg.contains(sender.get(j))){
				sendernotfound=false;
				break;
			}
		}
		
		return sendernotfound;
	}

	public static void waitforElement(String identifier, String method, int timeout){
		 found=false;
		 if(method.equals("name")){ 
			 try{
				 
			 
		 Wait<WebDriver> wait = new WebDriverWait(driver,maxto);
		 wait.until(ExpectedConditions.elementToBeClickable(By.name(identifier)));
		 found=true;
			 }
			 catch(Exception e){
				 e.printStackTrace();
				 
			 }
		 }
		 if(method.equals("id")){ 
			 
			 try{
			 Wait<WebDriver> wait = new WebDriverWait(driver,maxto);
			 wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
			 found=true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
			 }
		 
		 
		 if(method.equals("xpath")){ 
			 try{
			 Wait<WebDriver> wait = new WebDriverWait(driver,maxto);
			 wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
			 found=true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
			 }
		 
		 if(method.equals("linkText")){ 
			 try{
			 Wait<WebDriver> wait = new WebDriverWait(driver,maxto);
			 wait.until(ExpectedConditions.elementToBeClickable(By.linkText(identifier)));
			 found=true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
			 }
		 }
}
