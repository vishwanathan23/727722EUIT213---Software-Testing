package com.ponyo;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;
   @BeforeTest
   public void BeforeTest()
   {
    ExtentSparkReporter sparkReporter = new ExtentSparkReporter("C:\\testreport\\index1.html");
    report = new ExtentReports();
    report.attachReporter(sparkReporter);
         driver = new ChromeDriver();
        WebDriverManager.chromedriver().setup();
        driver.get("https://www.barnesandnoble.com/");
   }
   @SuppressWarnings("resource")
    @Test(priority = 0)
    public void Testcase1() throws Exception
    {
        
        driver.findElement(By.linkText("All")).click();
        driver.findElement(By.linkText("Books")).click();
        Thread.sleep(3000);
        FileInputStream fs = new FileInputStream("C:\\dbankexcel\\dbankexcel.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheet("cc2");
        XSSFRow row = sheet.getRow(0);
        String book_name = row.getCell(0).getStringCellValue();
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys(book_name);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/span/button")).click();
        Thread.sleep(5000);
        test = report.createTest("testing Chetan Bhagat");
        if(driver.getPageSource().contains("Chetan Bhagat"))
            test.log(Status.PASS, "Chetan Bhagat is present ");
        else
            test.log(Status.FAIL, "Chetan Bhagat is not present");
    }
    @AfterTest
    public void AfterTest(){
        driver.quit();
    }
}