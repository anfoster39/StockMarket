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
public class UpdateStockPriceTest {
	Simulator test = new  Simulator();
	ArrayList<Double> oldValues;

	@Before
	public void setUp() {
		test.indicators = new ArrayList<EconomicIndicator> ();
		test.stocks = new ArrayList<Stock>();
		test.calculateStocks = new HashMap <Stock, ArrayList<Double>>();
		test.createIndicators();
		test.createStocks();
		
		 oldValues = new ArrayList<Double>();
		for (Stock stock : test.stocks){
			oldValues.add(stock.getPrice());
		}
		test.updateIndicators(1);
		test.updateStockPrice(1);
	}

	@Test
	public void allIndicatorsChangedTest() {
		for (int i = 0; i < oldValues.size(); i++){
			assertNotEquals(oldValues.get(i), test.stocks.get(i).getPrice(), .001);
		}
	}
	
	@Test
	public void testIndicatorHisotry(){
		for (int i = 0; i < test.stocks.size(); i++){
			assertEquals(oldValues.get(i), test.stocks.get(i).getPriceHistory().get(0), .001);
		}
	}
	
	@Test
	public void testNormalPriceRange(){
		test.updateIndicators(2);
		test.updateStockPrice(2);
		String messageMax = "Stock price is above the max price";
		String messageMin = "Stock price is below the minimum price";
		for (int i = 0; i < test.stocks.size(); i++){
			assertTrue(messageMin, test.stocks.get(i).getPrice() >= Simulator.STOCK_MIN_PRICE);
			assertTrue(messageMax, test.stocks.get(i).getPrice() <= Simulator.STOCK_MAX_PRICE);
		}
	}

}
