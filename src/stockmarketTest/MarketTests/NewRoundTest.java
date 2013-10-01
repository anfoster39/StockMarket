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
import stockmarket.sim.Trade;

/**
 * @author Anne
 *
 */
public class NewRoundTest {
	ArrayList<Player> players;
	Market test;
	Trade testTrade;

	@Before
	public void setUp() throws Exception {
		players = new ArrayList<Player>();
		players.add(new RandomPlayer());
		test = new Market(players, 100000);
	}

	@Test
	public void setUpPlayersTest() {
		String nullMessage = "Players is null";
		assertNotNull(nullMessage, test.players);
		for (Player player : test.players){
			assertNotNull(nullMessage, player);
		}
		String nullNameMessage = "Players name is null";
		for (Player player : test.players){
			assertNotNull(nullNameMessage, player.getName());
		}
	}
	
	@Test
	public void setUpPortfoliosTest() {
		String nullMessage = "Portfolio is not initiated";
		String sizeMessage = "Different number of portfolios then players";
		assertNotNull(nullMessage, test.portfolios);
		for (Player player : test.portfolios.keySet()){
			assertNotNull(nullMessage, test.portfolios.get(player));
		}
		assertEquals(sizeMessage, test.players.size(), test.portfolios.size());
	}

}
