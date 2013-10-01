/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import stockmarket.sim.EconomicIndicator;
import stockmarket.sim.Simulator;

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
		test.createIndicators();
		
		 oldValues = new ArrayList<Double>();
		for (EconomicIndicator indicator : test.indicators){
			oldValues.add(indicator.getValue());
		}
		test.updateIndicators(1);
	}

	@Test
	public void allIndicatorsChangedTest() {
		for (int i = 0; i < oldValues.size(); i++){
			assertNotEquals(oldValues.get(i), test.indicators.get(i).getValue(), .0001);
		}
	}
	
	@Test
	public void testIndicatorHisotry(){
		for (int i = 0; i < test.indicators.size(); i++){
			assertEquals(oldValues.get(i), test.indicators.get(i).getHistory().get(0), .001);
		}
	}
	
	@Test
	public void testNormalPriceRange(){
		test.updateIndicators(2);
		System.out.println(test.indicators);
		String messageMax = "Indicator price is too large";
		String messageMin = "Indicator price is below 0";
		for (int i = 0; i < test.indicators.size(); i++){
			assertTrue(messageMin, test.indicators.get(i).getValue() > 0.0);
			assertTrue(messageMax, test.indicators.get(i).getValue() < 25);
		}
	}

}
