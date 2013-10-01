/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import stockmarket.sim.Simulator;

/**
 * @author Anne
 *
 */
public class CreateStockNameTest {
	Simulator test = new Simulator();

	@Test
	public void testCorrectCharactors() {
		String name = test.createStockName();
		assertTrue(name.charAt(0) >= 65 && name.charAt(0) <= 90);
		assertTrue(name.charAt(1) >= 65 && name.charAt(0) <= 90);
		assertTrue(name.charAt(2) >= 65 && name.charAt(0) <= 90);
	}
	
	@Test
	public void testNoDuplictes() {
		for (int i = 0; i < 1000; i++){
			test.createStockName();
		}
		HashSet<String> testSet = new HashSet<String>(test.stockNames);
		assertTrue(test.stockNames.size() == testSet.size());		
	}

	
	
}
