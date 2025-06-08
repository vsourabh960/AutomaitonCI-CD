package bluebeast.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import bluebeast.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		
		/*  options.setAcceptInsecureCerts(true); tells ChromeDriver to accept insecure certificates,
            allowing it to bypass security warnings and proceed with the automation.  */
//		ChromeOptions options = new ChromeOptions();
//		options.setAcceptInsecureCerts(true);
//		WebDriver driver = new ChromeDriver(options);
		
		String productName = "ZARA COAT 3";
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("saurabh12342@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Rahul@1725");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b"))
						  .getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//ng-animating
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating"))); --> Slowing the process(performance issue)
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//Checking if the cart is having the Item we required
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		//Clicking on the checkout button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//Placing the order and handling auto suggestive dropdown
		
//		Actions a = new Actions(driver);
//		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
//		driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
		
		driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("ind");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		List<WebElement> items = driver.findElements(By.cssSelector(".ta-item"));
		items.stream().filter(item -> item.getText().equalsIgnoreCase("india"))
					  .findFirst().ifPresent(WebElement::click);
		
		//Placing the order
		driver.findElement(By.cssSelector(".btnn.action__submit")).click();
		
		//Validating if the order is successfully placed or not
		String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
		
		//closing the browser
		driver.quit();
	}
}
