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
	public void initialStockPriceInRangeTest() {
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
		String highMessage = "createStocks created a formula that made stock price above the max";
		String lowMessage = "createStocks created a formula that made stock price below the min";
		test.NUM_STOCKS = 100;
		test.createStocks();
		for (int i = 0; i < test.NUM_STOCKS; i++){
			for(Stock stock : test.stocks){
				double price = test.updateStockPrice(stock);
				//System.out.println("" + stock + "price: " + price);
				assertTrue(lowMessage,  price >= Simulator.STOCK_MIN_PRICE); 
				assertTrue(highMessage, price <= Simulator.STOCK_MAX_PRICE);
			}
		}
	}
	

}
