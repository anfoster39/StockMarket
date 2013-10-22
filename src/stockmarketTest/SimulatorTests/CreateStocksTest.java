/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import stockmarket.sim.Simulator;
import stockmarket.sim.Stock;

/**
 * @author Anne
 *
 */
public class CreateStocksTest {
	Simulator test;

	@Before
	public void setUp(){
		 test = new  Simulator();
	}

	@Test
	public void CorrectNumberCreatedTest() {
		String message = "created " + test.stocks.size() + ", but should have created " + test.NUM_STOCKS;
		assertEquals(message, test.NUM_STOCKS, test.stocks.size());
	}
	
	@Test
	public void initialStockPriceInRangeTest() {
		test.calculateStocks = new HashMap<Stock, ArrayList<Double>>();
		test.stocks = new ArrayList<Stock>();
		test.createStocks();
		String message = "createStocks did not create an initial price between the stock min and max price";
		test.NUM_STOCKS = 100;
		test.createStocks();
		for (int i = 0; i < test.NUM_STOCKS; i++ ){
			Stock created = test.stocks.get(i);
			assertTrue(message, (created.getPrice() >= Simulator.STOCK_MIN_PRICE) 
							 && (created.getPrice() <= Simulator.STOCK_MAX_PRICE));
		}
		
	}
	
	@Test
	public void stockCoefficientsInRangeTest() {
		String message = "createStocks did not create the corrct number of coefficients to calculate price";
		String message2 = "coefficents are not in the correct range";
		test.NUM_STOCKS = 100;
		test.createStocks();
		for (int i = 0; i < test.NUM_STOCKS; i++ ){
			ArrayList<Double> formula = test.calculateStocks.get(test.stocks.get(0));
			assertEquals(message, test.indicators.size(), formula.size());
			for(Double coefficient : formula){
				assertTrue (message2, coefficient >= -1 && coefficient <= 1);
			}	
		}	
	}
	
	@Test
	public void stockFormulaProducePriceInRange() {
		test.NUM_STOCKS = 100;
		test.createStocks();
		assertEquals(5, test.indicators.size());
		for(Stock stock : test.stocks){
			double price = test.calculateStockPrice(stock, 1);
			String lowMessage = "stock price " + price + " was smaller then the min of " + Simulator.STOCK_MIN_PRICE;
			String highMessage = "stock price " + price + " was larger then the max of " + Simulator.STOCK_MAX_PRICE;
			//System.out.println("" + stock + "price: " + price);
			assertTrue(lowMessage,  price >= Simulator.STOCK_MIN_PRICE); 
			assertTrue(highMessage, price <= Simulator.STOCK_MAX_PRICE);
		}
	}
	

}
