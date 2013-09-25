/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stockmarket.sim.Simulator;

/**
 * @author Anne
 *
 */
public class CreateStocksTest {
Simulator test = new  Simulator();
Random random = new Random();

	@Test
	public void test() {
		test.run();
	}

}
