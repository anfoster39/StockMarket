/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import stockmarket.sim.Simulator;

/**
 * @author Anne
 *
 */
public class SimulatorGenerateCoefficentTest {
	Simulator test = new  Simulator();

	
	@Test
	public void testCoefficientRange() {
		for (int i = 0; i < 100; i++){
			double coefficient = test.generateCoefficient();
			assertTrue((coefficient <= 10) && (coefficient >=-10));
		}
	}
		
}
