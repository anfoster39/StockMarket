/**
 * 
 */
package stockmarketTest.MarketTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import stockmarket.g0.RandomPlayer;
import stockmarket.sim.Market;
import stockmarket.sim.Player;

/**
 * @author Anne
 *
 */
public class AllBankruptTest {

	ArrayList<Player> players;
	Market test;

	@Before
	public void setUp() throws Exception {
		players = new ArrayList<Player>();
		players.add(new RandomPlayer());
		test = new Market(players, 100000);
	}

	@Test
	public void BuyTest() {
		fail("Not yet implemented");
	}

}
