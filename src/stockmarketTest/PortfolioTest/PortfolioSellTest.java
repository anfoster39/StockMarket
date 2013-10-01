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
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp(){
		stock1 = new Stock ("ABC", 10.0);
		stock2 = new Stock ("XYZ", 10000.0);
		testing = new Portfolio (10000.0);
		testing.buyStock(stock1, 20);
	}


	@Test
	public void testSellOK() {
		assertTrue(testing.sellStock(stock1, 10));
		assertEquals(10000, testing.monetaryValue(), 001);
		assertEquals(testing.toString(), "Capital: 9900.0"
										+"\nStocks:" 
										+"\nABC, 10 Shares" 
										+"\nMonetary Value: 10000.0"
										+"\nProfit / Loss: 10000.0\n");
	}
	
	@Test
	public void testSellTooManyShares() {
		assertFalse(testing.sellStock(stock1, 30));
		assertEquals(testing.toString(), "Capital: 9800.0"
										+"\nStocks:" 
										+"\nABC, 20 Shares" 
										+"\nMonetary Value: 10000.0"
										+"\nProfit / Loss: 10000.0\n");
	}
	
	@Test
	public void testSellNotOwnedShares() {
		assertFalse(testing.sellStock(stock2, 1));
		assertEquals(testing.toString(),  "Capital: 9800.0"
										+"\nStocks:" 
										+"\nABC, 20 Shares" 
										+"\nMonetary Value: 10000.0"
										+"\nProfit / Loss: 10000.0\n");
	}
	

}
