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
 * Tests the portfolio sell test
 *
 */
public class PortfolioSellTest {

	Stock stock1;
	Stock stock2;
	Portfolio testing;
	
	@Before
	public void setUp(){
		stock1 = new Stock ("ABC", 10.0);
		stock2 = new Stock ("XYZ", 10000.0);
		testing = new Portfolio (10000.0);
		testing.buyStock(stock1, 20);
	}


	@Test
	public void testSellOK() {
		String message = "Portfolio failed to complete a good trade";
		assertTrue(testing.sellStock(stock1, 10));
		assertEquals(10000, testing.getMonetaryValue(), 001);
		assertEquals(message, "Capital: 9900.0"
							 +"\nStocks:" 
							 +"\nABC, 10 Shares" 
							 +"\nMonetary Value: 10000.0"
							 +"\nProfit / Loss: 0.0\n", testing.toString());
	}
	
	@Test
	public void testSellAllRemovesStock() {
		String message = "Portfolio failed to remove stock once it was all sold";
		assertTrue(testing.sellStock(stock1, 20));
		assertEquals(10000, testing.getMonetaryValue(), 001);
		assertFalse(message, testing.stocks.containsKey(stock1));
	}
	
	@Test
	public void testSellTooManyShares() {
		String message = "Did not fail on selling more shares than were owned";
		assertFalse(testing.sellStock(stock1, 30));
		assertEquals(message, "Capital: 9800.0"
							 +"\nStocks:" 
							 +"\nABC, 20 Shares" 
							 +"\nMonetary Value: 10000.0"
							 +"\nProfit / Loss: 0.0\n", testing.toString());
	}
	
	@Test
	public void testSellNotOwnedShares() {
		String message = "Did not fail on selling shares that were not owned";
		assertFalse(testing.sellStock(stock2, 1));
		assertEquals(message, "Capital: 9800.0"
							 +"\nStocks:" 
							 +"\nABC, 20 Shares" 
							 +"\nMonetary Value: 10000.0"
							 +"\nProfit / Loss: 0.0\n", testing.toString());
	}
	

}
