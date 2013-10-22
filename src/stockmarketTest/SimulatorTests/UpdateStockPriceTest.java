/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import stockmarket.sim.Simulator;
import stockmarket.sim.Stock;
import stockmarket.sim.Trade;

/**
 * @author Anne
 *
 */
public class UpdateStockPriceTest {
	Simulator test = new  Simulator();
	ArrayList<Double> oldValues;

	@Before
	public void setUp() {
		oldValues = new ArrayList<Double>();
		
		for (Stock stock : test.stocks){
			oldValues.add(stock.getPrice());
		}
	}

	@Test
	public void allStocksChangedTest() {
		test.updateIndicators(1);
		test.updateStockPrice(1, new ArrayList<Trade>());
		for (int i = 0; i < oldValues.size(); i++){
			assertNotEquals(oldValues.get(i), test.stocks.get(i).getPrice(), .001);
		}
	}
	
	@Test
	public void testIndicatorHisotry(){
		test.updateIndicators(1);
		test.updateStockPrice(1, new ArrayList<Trade>());
		for (int i = 0; i < test.stocks.size(); i++){
			assertEquals(oldValues.get(i), test.stocks.get(i).getPriceHistory().get(0), .001);
		}
	}
	
	@Test
	public void testNormalPriceRange(){
		test.updateIndicators(1);
		test.updateStockPrice(1, new ArrayList<Trade>());
		test.updateIndicators(2);
		test.updateStockPrice(2, new ArrayList<Trade>());
		for (int i = 0; i < test.stocks.size(); i++){
			String messageMax = "price " + test.stocks.get(i).getPrice() 
					+ " is above the max price of " + Simulator.STOCK_MAX_PRICE;
			String messageMin = "price " + test.stocks.get(i).getPrice() 
					+ " is below the min price of " + Simulator.STOCK_MIN_PRICE;
			assertTrue(messageMin, test.stocks.get(i).getPrice() >= Simulator.STOCK_MIN_PRICE);
			assertTrue(messageMax, test.stocks.get(i).getPrice() <= Simulator.STOCK_MAX_PRICE);
		}
	}
	
	@Test
	public void testPopularity(){
		Stock stock = test.stocks.get(0);
		
		double numberShares = ((100000*test.players.size())/stock.getPriceHistory().get(0));
		double regularPrice = test.calculateStockPrice(stock, 0);
		double popPrice = test.calculateStockPrice(stock, (int)numberShares);
		//is very close to 1
		double increase = 1+ (int)numberShares/numberShares;
		assertEquals(regularPrice*increase, popPrice, 2);	
		
		regularPrice = test.calculateStockPrice(stock, 0);
		popPrice = test.calculateStockPrice(stock, -(int)numberShares);
		increase = 1 - (int)numberShares/numberShares;
		assertEquals(regularPrice*increase, popPrice, 2);	
		
	}
	
//	@Test
//	public void printStocks(){
//		for (int i = 1; i < 500; i++){
//			test.updateIndicatorsTrend(i);
//			test.updateStockPrice(i, new ArrayList <Trade>());
//			for (Stock stock : test.stocks){
//				System.out.println(i + " " + stock.getName() + " " + stock.getPrice());
//			}	
//		}
//	}

}
