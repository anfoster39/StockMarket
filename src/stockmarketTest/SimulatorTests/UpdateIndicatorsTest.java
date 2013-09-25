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
public class UpdateIndicatorsTest {
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
		for (EconomicIndicator indicator : test.indicators){
			oldValues.add(indicator.getValue());
		}
		test.updateIndicators(1);
	}

	@Test
	public void allIndicatorsChangedTest() {
		for (int i = 0; i < oldValues.size(); i++){
			assertNotEquals(oldValues.get(i), test.indicators.get(i).getValue(), .001);
		}
	}
	
	@Test
	public void testIndicatorHisotry(){
		for (int i = 0; i < test.indicators.size(); i++){
			assertEquals(oldValues.get(i), test.indicators.get(i).getHistory().get(0), .001);
		}
	}

}
