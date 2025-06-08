package bluebeast.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import bluebeast.abstractcomponents.AbstractComponents;

public class CheckOutPage extends AbstractComponents{
	
	WebDriver driver;

	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	private By result = By.cssSelector(".ta-results");
	
	@FindBy(css="[placeholder='Select Country']")
	private WebElement country;
	
	@FindBy(css=".action__submit")
	private WebElement submit;
	
	@FindBy(css=".ta-item")
	private List<WebElement> items;
	
	public void selectCountry(String countryName)
	{
		country.sendKeys(countryName); 
		waitForElementToAppear(result);
		items.stream().filter(item -> item.getText().equalsIgnoreCase("india"))
					  .findFirst().ifPresent(WebElement::click);
	}
	
	public ConfirmationPage submitOrder()
	{
		submit.click();
		return new ConfirmationPage(driver);
	}
}

//Actions a = new Actions(driver);
//a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
//driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
