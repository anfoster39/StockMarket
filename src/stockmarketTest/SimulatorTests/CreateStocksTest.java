/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

import stockmarket.sim.EconomicIndicator;
import stockmarket.sim.Simulator;
import stockmarket.sim.Stock;

/**
 * @author Anne
 *
 */
public class CreateStocksTest {
Simulator test = new  Simulator();

	@Before
	public void setUp(){
		test.indicators = new ArrayList<EconomicIndicator> ();
		test.stocks = new ArrayList<Stock>();
		test.calculateStocks = new HashMap <Stock, ArrayList<Double>>();
		test.createIndicators();
	}

	@Test
	public void CorrectNumberCreatedTest() {
		String message = "createStocks did not creat the correct number of stocks";
		test.createStocks();
		assertEquals(message, test.NUM_STOCKS, test.stocks.size());
	}
	
	@Test
	public void initialStockPriceOKTest() {
		String message = "createStocks did not create an initial price between the stock min and max price";
		test.NUM_STOCKS = 1;
		test.createStocks();
		Stock created = test.stocks.get(0);
		assertTrue(message, created.getPrice() > Simulator.STOCK_MIN_PRICE && created.getPrice() < Simulator.STOCK_MAX_PRICE);
	}
	
	@Test
	public void stockFormulaOKTest() {
		String message = "createStocks did not creat the corrct number of coefficients to calcualte price";
		String message2 = "coefficents are not in the correct range";
		test.NUM_STOCKS = 1;
		test.createStocks();
		ArrayList<Double> formula = test.calculateStocks.get(test.stocks.get(0));
		assertEquals(message, test.indicators.size(), formula.size());
		for(Double coefficient : formula){
			assertTrue (message2, coefficient >= -1 && coefficient <= 1);
		}
	}
	

}
