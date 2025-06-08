package bluebeast.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bluebeast.pageobjects.CartPage;
import bluebeast.pageobjects.CheckOutPage;
import bluebeast.pageobjects.ConfirmationPage;
import bluebeast.pageobjects.OrderPage;
import bluebeast.pageobjects.ProductCatalogue;
import bluebeast.testcomponents.BaseTest;

public class SubmitOrderTest extends BaseTest{
	
	String productName = "ZARA COAT 3";
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException
	{
		//Launching application -> It will launch in the BaseTest class.
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		//Getting list of the products
		productCatalogue.getProductList();
		//Adding product to the cart
		productCatalogue.addProductToCart(input.get("product"));
		//Clicking on the cart header
		CartPage cartPage = productCatalogue.goToCartPage();
		//Checking if the cart is having the Item we required
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		//Clicking on the checkout button
		CheckOutPage checkOutPage = cartPage.goToCheckout();
		//Handling auto suggestive dropdown
		checkOutPage.selectCountry("ind");
		//Placing the order
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		//Validating if the order is successfully placed or not
		String confirmMsg = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));		
	}
	
	//Checking placed order in the order history
	@Test(dependsOnMethods={"submitOrder"})
	public void orderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("saurabh12342@gmail.com", "Rahul@1725");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\\\src\\\\test\\\\java\\\\bluebeast\\\\data\\\\PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
}




//HashMap<String, String> map = new HashMap<String, String>();
//map.put("email", "saurabh12342@gmail.com");
//map.put("password", "Rahul@1725");
//map.put("product", "ZARA COAT 3");
//
//HashMap<String, String> map1 = new HashMap<String, String>();
//map1.put("email", "gaurav324@gmail.com");
//map1.put("password", "Gaurav324@");
//map1.put("product", "ADIDAS ORIGINAL");
