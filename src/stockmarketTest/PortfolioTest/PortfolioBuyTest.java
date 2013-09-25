/**
 * 
 */
package stockmarketTest.PortfolioTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stockmarket.Portfolio;
import stockmarket.Stock;

/**
 * Tests the portfolio class buy method
 *
 */
public class PortfolioBuyTest {
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
	}


	@Test
	public void testBuyOK() {
		assertTrue(testing.buyStock(stock1, 10));
		assertEquals(10000, testing.monetaryValue(), 001);
		assertEquals(testing.toString(), "Capital: 9900.0"+
										"\nStocks:" + 
										"\nABC, 10 Shares" + 
										"\nValue: 10000.0");
	}
	
	@Test
	public void testBuyTooMuch() {
		assertFalse(testing.buyStock(stock2, 10));
		assertEquals(10000, testing.monetaryValue(), 001);
		assertEquals(testing.toString(), "Capital: 10000.0"+
										"\nStocks:" + 
										"\nValue: 10000.0");
	}

}
