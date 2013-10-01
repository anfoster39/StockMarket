/**
 * 
 */
package stockmarketTest.PortfolioTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stockmarket.sim.Portfolio;
import stockmarket.sim.Stock;

/**
 * @author Anne
 *
 */
public class ProfitTest {

	Stock stock1;
	Stock stock2;
	Portfolio testing;
	
	@Before
	public void setUp(){
		stock1 = new Stock ("ABC", 1.0);
		testing = new Portfolio (100.0);
		testing.buyStock(stock1, 20);
	}

	@Test
	public void test() {
		String valueMessage = "Monetary Value not correct";
		String message = "Profit calcualtion not correct";
		assertEquals(valueMessage, 100, testing.getMonetaryValue(), .001);
		stock1.updatePrice(2, 2.0);
		assertEquals(valueMessage, 120, testing.getMonetaryValue(), .001);
		assertEquals(message, 20, testing.getProfit(), .01);
	}

}
