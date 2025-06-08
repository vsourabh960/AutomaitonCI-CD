package bluebeast.stepDefinition;

import java.io.IOException;

import org.testng.Assert;

import bluebeast.pageobjects.CartPage;
import bluebeast.pageobjects.CheckOutPage;
import bluebeast.pageobjects.ConfirmationPage;
import bluebeast.pageobjects.LandingPage;
import bluebeast.pageobjects.ProductCatalogue;
import bluebeast.testcomponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest{
	
	LandingPage landingPage;
	ProductCatalogue productCatalogue;
	ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void i_landed_on_ecommerce_page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username,password);
	}
	
	@When("^add product (.+) to the cart$")
	public void add_product_to_the_cart(String productName) throws InterruptedException
	{
		productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^checkout (.+) and submit order$")
	public void checkout_and_submit_order(String productName)
	{
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckOutPage checkOutPage = cartPage.goToCheckout();
		checkOutPage.selectCountry("ind");
		confirmationPage = checkOutPage.submitOrder();
	}
	
	@Then("{string} message is displayed on confirmationpage")
	public void message_displayed_on_confirmationPage(String cnfrmMessage)
	{
		String confirmMsg = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase(cnfrmMessage));
		driver.quit();
	}
	
	@Then("{string} message is displayed")
    public void validateErrorMessage(String strArg1) {
		Assert.assertEquals(strArg1, landingPage.getErrorMessage());
		driver.quit();
    }

}
