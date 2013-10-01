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
 * Tests the portfolio class buy method
 *
 */
public class PortfolioBuyTest {
	Stock stock1;
	Stock stock2;
	Stock stock3;
	Portfolio testing;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp(){
		stock1 = new Stock ("ABC", 10.0);
		stock2 = new Stock ("XYZ", 10000.0);
		stock3 = new Stock ("MNO", 10.0);
		testing = new Portfolio (10000.0);
	}


	@Test
	public void testBuyOK() {
		assertTrue(testing.buyStock(stock1, 10));
		assertEquals(10000, testing.monetaryValue(), 001);
		assertEquals(testing.toString(), "Capital: 9900.0"
										+"\nStocks:" 
										+"\nABC, 10 Shares" 
										+"\nMonetary Value: 10000.0"
										+"\nProfit / Loss: 10000.0\n");
	}
	
	@Test
	public void testBuyNotEnoughMoney() {
		assertFalse(testing.buyStock(stock2, 10));
		assertEquals(10000, testing.monetaryValue(), 001);
		assertEquals(testing.toString(), "Capital: 10000.0"
										+"\nStocks:"  
										+"\nMonetary Value: 10000.0"
										+"\nProfit / Loss: 10000.0\n");
	}
	


}
