package eCommerceLive;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BookOrderTest {

	WebDriver driver = null;
	Properties obj;

	@BeforeTest
	public void setUp() throws Exception {
		FileInputStream objfile = null;

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Reading data from properties file
		obj = new Properties();
		objfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");
		obj.load(objfile);

	}

	@BeforeMethod
	public void login() {

		// Navigate to URL
		driver.get(obj.getProperty("url"));
		// Click on Login button
		driver.findElement(By.xpath(obj.getProperty("login"))).click();
		// Validate “Welcome, Please Sign In!” message
		WebElement titlevalidation = driver.findElement(By.xpath(obj.getProperty("validateMassage")));
		// Validate “Welcome, Please Sign In!” message
		if (titlevalidation.isDisplayed()) {
			System.out.println("Title is Displayed");
		} else {
			System.out.println("Title is Not Displayed");
		}
		String ExpectedText = "Welcome, Please Sign In!";
		String ActualText = titlevalidation.getText();
		Assert.assertEquals(ActualText, ExpectedText);
		System.out.println("The actual and expected result is same.the title value is:" + " " + ActualText);

		// Log in with given credentials
		WebElement email = driver.findElement(By.xpath(obj.getProperty("loginEmail")));
		email.sendKeys(obj.getProperty("email"));

		WebElement password = driver.findElement(By.xpath(obj.getProperty("loginPassWord")));
		password.sendKeys(obj.getProperty("passWord"));

		driver.findElement(By.xpath(obj.getProperty("loginClick"))).click();
	}

	@Test
	public void verifyUserId() {
		// Validate the user account ID on top right
		WebElement IDvalidation = driver.findElement(By.xpath(obj.getProperty("validateID")));
		if (IDvalidation.isDisplayed()) {
			System.out.println("ID is Displayed");
		} else {
			System.out.println("ID is Not Displayed");
		}
		String ExpectedID = "atest@gmail.com";
		String ActualID = IDvalidation.getText();
		Assert.assertEquals(ActualID, ExpectedID);
		System.out.println("The actual and expected result is same.the title value is:" + " " + ActualID);
		clearCart();
		selectBook();
		getPrice();
		addCart();
		validateShoppingCartMsg();
		billingAddress();
		shippingAddress();
		shippingMethod();
		paymentMethod();
		paymentInfo();
		confirmOrder();
		validateOrderMsg();
	}

	public void clearCart() {
		// Clear the shopping cart

		WebElement shoppingcart = driver.findElement(By.xpath(obj.getProperty("shoppingCart")));
		Actions action = new Actions(driver);
		action.moveToElement(shoppingcart).perform();
		System.out.println(shoppingcart.getText());
		WebElement gotocart = driver.findElement(By.xpath(obj.getProperty("goToCart")));
		action.click(gotocart).perform();
		// click check box for update shopping cart
		driver.findElement(By.xpath(obj.getProperty("checkBox"))).click();

		driver.findElement(By.xpath(obj.getProperty("updateShoppingCart"))).click();

	}

	public void selectBook() {
		driver.findElement(By.xpath(obj.getProperty("booksElement"))).click();

		// Select a book from the list displayed

		driver.findElement(By.xpath(obj.getProperty("healthBook"))).click();
	}

	public void getPrice() {
		// Get the price details and enter the quantity (more than one)
		WebElement price = driver.findElement(By.xpath(obj.getProperty("priceOfBook")));
		System.out.println(price.getText());

		WebElement qty = driver.findElement(By.xpath(obj.getProperty("changeQty")));

		qty.clear();
		qty.sendKeys("10");
	}

	public void addCart() {
		// Click on “Add to cart”
		driver.findElement(By.xpath(obj.getProperty("addToCart"))).click();
	}

	public void validateShoppingCartMsg() {

		// Validate “The product has been added to shopping cart” message

		WebElement contentvalidation = driver.findElement(By.xpath(obj.getProperty("validateContent")));
		if (contentvalidation.isDisplayed()) {
			System.out.println("contentvalidation is Displayed");
		} else {
			System.out.println("contentvalidation is Not Displayed");
		}
		String Expectedcontent = "The product has been added to your shopping cart";
		String Actualcontent = contentvalidation.getText();
		Assert.assertEquals(Actualcontent, Expectedcontent);
		System.out.println("The actual and expected result is same.the content value is:" + " " + Actualcontent);
		// Click on “shopping cart” on top right and validate the “Sub-Total” Price for
		// selected book.
		driver.findElement(By.xpath(obj.getProperty("clickShopcart"))).click();

		WebElement subtotal = driver.findElement(By.xpath(obj.getProperty("subTotal")));
		System.out.println(subtotal.getText());

		// System.out.println(price.getText()==subtotal.getText());

		// Click on “Check-out”
		driver.findElement(By.xpath(obj.getProperty("termsOfService"))).click();

		driver.findElement(By.xpath(obj.getProperty("checkOut"))).click();
	}

	public void billingAddress() {
		// Select “New Address” From “Billing Address” drop down.
		WebElement billaddress = driver.findElement(By.xpath(obj.getProperty("bullingAddress")));
		Select select = new Select(billaddress);
		select.selectByIndex(10);
		// Fill all mandatory fields in “Billing Address” and click “Continue”
		driver.findElement(By.xpath(obj.getProperty("clickcontinue"))).click();
	}

	public void shippingAddress() {
		// Select the “Shipping Address” as same as “Billing Address” from “Shipping
		// Address” drop down and click on “Continue”.
		WebElement shipaddress = driver.findElement(By.xpath(obj.getProperty("shippingAddress")));
		Select select = new Select(shipaddress);
		select.selectByIndex(10);
		driver.findElement(By.xpath(obj.getProperty("continue_3"))).click();
	}

	public void shippingMethod() {
		// Select the shipping method as “Next Day Air” and click on “Continue”
		driver.findElement(By.xpath(obj.getProperty("clickRadiobt"))).click();
		driver.findElement(By.xpath(obj.getProperty("continue_4"))).click();
	}

	public void paymentMethod() {
		// Choose the payment method as COD (Cash on delivery) and click on “Continue”
		driver.findElement(By.xpath(obj.getProperty("codRadioBt"))).click();
		driver.findElement(By.xpath(obj.getProperty("continue_5"))).click();
	}

	public void paymentInfo() {
		// Validate the message “You will pay by COD” and click on “Continue”
		WebElement codvalidate = driver.findElement(By.xpath(obj.getProperty("codMessage")));

		if (codvalidate.isDisplayed()) {
			System.out.println("codvalidate is Displayed");
		} else {
			System.out.println("codvalidate is Not Displayed");
		}
		String expectemsg = "You will pay by COD";
		String actualmsg = codvalidate.getText();
		Assert.assertEquals(actualmsg, expectemsg);
		System.out.println("The actual and expected result is same.the content value is:" + " " + actualmsg);

		driver.findElement(By.xpath(obj.getProperty("continue_6"))).click();
	}

	public void confirmOrder() {
		// Click on “Confirm Order”
		driver.findElement(By.xpath(obj.getProperty("continue_7"))).click();
	}

	public void validateOrderMsg() {
		// Validate the message “Your order has been successfully processed!” and print
		// the Order number
		WebElement ordersuccessmsg = driver.findElement(By.xpath(obj.getProperty("orderSuccessMsg")));

		if (ordersuccessmsg.isDisplayed()) {
			System.out.println("ordersuccessmsg is Displayed");
		} else {
			System.out.println("ordersuccessmsg is Not Displayed");
		}
		String expectesuccessmsg = "Your order has been successfully processed!";
		String actualsuccessmsg = ordersuccessmsg.getText();
		Assert.assertEquals(actualsuccessmsg, expectesuccessmsg);
		System.out.println("The actual and expected result is same.the content value is:" + " " + actualsuccessmsg);

		// Print the Order number

		WebElement ordernum = driver.findElement(By.xpath(obj.getProperty("orderNumber")));
		System.out.println(ordernum.getText());

		// Click on “Continue” and log out from the application
		driver.findElement(By.xpath(obj.getProperty("continueBt"))).click();
	}

	@AfterMethod
	public void logOut() {
		driver.findElement(By.xpath(obj.getProperty("logout"))).click();
	}

	@AfterTest
	public void closeBrowser() {

		driver.quit();
	}

}
