package com.unsubscribe;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Unsubscribe {
	
	public static String filename="Unsubscribeconfig.ini";
	public static int maxto=10;
	public static WebDriver driver;
	static boolean found=false;
	public static String username;
	public static String password;
	public static String sender;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		try {
			Wini ini= new Wini(new File(filename));
			username=ini.get("INFO","username");
			password=ini.get("INFO", "password");
			sender=ini.get("INFO", "sender");
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
		  search.sendKeys("Unsubscribe");
		   
		   WebElement searchb= driver.findElement(By.xpath("//button[@id='gbqfb']"));
		   searchb.click();
		 
		   waitforElement("//*[@id=':m0']","xpath",maxto);
		   
		   for(int i=1; i<50; i++){
			   
		   WebElement e1= driver.findElement(By.xpath("//*[@id=':lu']/tbody/tr["+Integer.toString(i)+"]"));
		  if(e1.getText().contains(sender)){
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", e1);
		    }
		   
		   e1.click();
		   //waitforElement("unsubscribe","linkText",maxto);
		   //waitforElement("Unsubscribe","linkText",maxto);
		   waitforElement("//*[contains(.,'Unsubscribe')]","xpath",maxto);
		   
		   try{
		   WebElement ul=driver.findElement(By.xpath("//div[@class='adn ads']/*[text()[contains(.,'click here')]]"));
		   //
		   System.out.println(ul.getText());
		   ul.click();
		   //need to figure out how to cycle to gmail window
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
		   
		   WebElement u= driver.findElement(By.linkText("unsubscribe"));
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", u);
		    }
		   u.click();
		   
		   waitforElement("//button[contains(.,'unsubscribe')]","xpath",maxto);
		   waitforElement("//button[contains(.,'Unsubscribe')]","xpath",maxto);
		   WebElement ub= driver.findElement(By.xpath(("//button[contains(.,'unsubscribe')]")));
		   if (driver instanceof JavascriptExecutor) {
		        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", ub);
		    }
		   u.click();
		   ub.click();
		   
		   driver.navigate().back();
		   driver.navigate().back();
		   
		  }
		   
		   //System.out.println(e1.getText());
		   //TimeUnit.SECONDS.sleep(1);
		   }
		   
		   //waitforElement("Unsubscribe","linkText",maxto);
		   //e1.click();
		   
		   driver.close();
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
