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
		test.updateIndicatorsTrend(1);
	}

	@Test
	public void allIndicatorsChangedTest() {
		for (int i = 0; i < oldValues.size(); i++){
			assertNotEquals(oldValues.get(i), test.indicators.get(i).getValue(), .0001);
		}
	}
	
//	@Test
//	public void updateIndicators() {
//		test.updateIndicatorsTrend(1);
//		test.updateIndicatorsTrend(2);
//		for (int i = 2; i < 500; i++){
//			test.updateIndicatorsTrend(i);
//			for (EconomicIndicator indicator : test.indicators){
//				System.out.println(i + " " + indicator);
//			}	
//		}	
//	}
	
	
	@Test
	public void testTrends() {
		test.updateIndicatorsTrend(1);
		test.updateIndicatorsTrend(2);
		for (int i = 3; i < 1000; i++){
			test.updateIndicatorsTrend(i);
			for (int j = 0; j < test.indicators.size(); j++){
				//look at the change the time before the current change
				double incriment = (test.indicatorsMax.get(j) - test.indicatorsMin.get(j)) / 100;
				double previousChange = (1 - (test.indicators.get(j).getHistory().get(i-1) 
						/ test.indicators.get(j).getHistory().get(i-2)));
				int oldDirection = 1;
				if (previousChange < 1){
					oldDirection = -1;
				}
				//if that change is more then .5, then there should not be a change in trend
				double normaizedPreviousChange = Math.abs(previousChange) / incriment; 
				if (normaizedPreviousChange > .5){
					double currentChange = (1 - (test.indicators.get(j).getHistory().get(i) 
							/ test.indicators.get(j).getHistory().get(i-1)));
					int newDirection = 1;
					if (currentChange < 1){
						newDirection = -1;
					}
					assertTrue(newDirection == oldDirection);
					
				}
			}

		}
	}
	
	@Test
	public void updateInRange(){
		for (int i = 0; i < 1000; i++){
			test.updateIndicatorsTrend(i);
			for (int j = 0; j < test.indicators.size(); j++){
				assertTrue(("round " + i + " indicator is below the minimum by " + (test.indicatorsMin.get(j) - test.indicators.get(j).getValue())), 
						test.indicators.get(j).getValue() >= test.indicatorsMin.get(j));
				assertTrue(("round " + i + " indicator is above the maximum by " + (test.indicators.get(j).getValue()-test.indicatorsMax.get(j))), 
						test.indicators.get(j).getValue() <= test.indicatorsMax.get(j));
				
			}	
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
	
	@Test
	public void testRangeConversion(){
		for (int i = 0; i < 1000; i++){
			Double randomDouble = test.getRandomBetween(0, .8);
			assertTrue(randomDouble >= 0.0 && randomDouble <= 8);
		}
	}

}
