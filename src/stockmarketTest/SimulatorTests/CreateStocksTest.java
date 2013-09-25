/**
 * 
 */
package stockmarketTest.SimulatorTests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stockmarket.Simulator;

/**
 * @author Anne
 *
 */
public class CreateStocksTest {
Simulator test = new  Simulator();
Random random = new Random();

	@Test
	public void test() {
		for(int i = 0; i < 100; i++){
			System.out.println(random.nextInt() % 10);
		}
	}

}
