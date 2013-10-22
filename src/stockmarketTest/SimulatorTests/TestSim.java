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
	Simulator test;

		@Test
		public void test() {
			test = new  Simulator();
			test.run();
		}

}

