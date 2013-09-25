/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
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
			assertTrue((coefficient <= 1) && (coefficient >=-1));
		}
		}
		
	@Test
	public void testCoefficient0prob() {
		int count = 0;
		for (int i = 0; i < 100; i++){
			if (test.generateCoefficient() == 0.0){
				count ++;
			}
		}
		assertEquals(20, count, 14); //within 2 standard deviations
	}
}
