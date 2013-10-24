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
		test.updateIndicatorsTrend(1);
		test.updateStockPrices(1, new ArrayList<Trade>());
		for (int i = 0; i < oldValues.size(); i++){
			assertNotEquals(oldValues.get(i), test.stocks.get(i).getPrice(), .001);
		}
	}
	
	@Test
	public void testIndicatorHisotry(){
		test.updateIndicatorsTrend(1);
		test.updateStockPrices(1, new ArrayList<Trade>());
		for (int i = 0; i < test.stocks.size(); i++){
			assertEquals(oldValues.get(i), test.stocks.get(i).getPriceHistory().get(0), .001);
		}
	}
	
	@Test
	public void testNormalPriceRange(){
		test.stocks = new ArrayList<Stock>();
		test.NUM_STOCKS = 10000;
		test.createStocks();
		for(int round = 0; round < test.MAX_ROUNDS; round++){
			test.updateIndicatorsTrend(round);
			test.updateStockPrices(round, new ArrayList<Trade>());
			for (int i = 0; i < test.stocks.size(); i++){
			String messageMax = "price " + test.stocks.get(i).getPrice() 
					+ " is above the max price of " + Simulator.STOCK_MAX_PRICE;
			String messageMin = "Stock " + i + " price " + test.stocks.get(i).getPrice() 
					+ " is below 0";
			assertTrue(messageMin, test.stocks.get(i).getPrice() >= 0);
			assertTrue(messageMax, test.stocks.get(i).getPrice() <= Simulator.STOCK_MAX_PRICE);
			}
		}
	}
	
	@Test
	public void testPopularity(){
		Stock stock = test.stocks.get(0);
		
		double numberShares = ((100000*test.players.size())/stock.getPriceHistory().get(0));
		double regularPrice = test.calculateStockPrice(stock, 0, test.calculateStocks.get(stock));
		double popPrice = test.calculateStockPrice(stock, (int)numberShares, test.calculateStocks.get(stock));
		//is very close to 1
		double increase = 1+ (int)numberShares/numberShares;
		assertEquals(regularPrice*increase, popPrice, 2);	
		
		regularPrice = test.calculateStockPrice(stock, 0, test.calculateStocks.get(stock));
		popPrice = test.calculateStockPrice(stock, -(int)numberShares, test.calculateStocks.get(stock));
		increase = 1 - (int)numberShares/numberShares;
		assertEquals(regularPrice*increase, popPrice, 2);	
		
	}
	
	@Test
	public void printStocks(){
		for (int i = 1; i < 500; i++){
			test.updateIndicatorsTrend(i, test.indicators);
			test.updateStockPrices(i, new ArrayList <Trade>());
			for (Stock stock : test.stocks){
				System.out.println(i + " " + stock.getName() + " " + stock.getPrice());
			}	
		}
	}

}
