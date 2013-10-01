/**
 * 
 */
package stockmarketTest.SimulatorTests;


import java.util.Random;

import org.junit.Test;

import stockmarket.sim.Simulator;

/**
 * @author Anne
 *
 */
public class TestSim {
	Simulator test = new  Simulator();
	Random random = new Random();

		@Test
		public void test() {
			test.run();
		}

}

