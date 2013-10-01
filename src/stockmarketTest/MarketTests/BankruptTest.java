/**
 * 
 */
package stockmarketTest.MarketTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import stockmarket.g0.RandomPlayer;
import stockmarket.sim.Market;
import stockmarket.sim.Player;
import stockmarket.sim.Stock;

/**
 * @author Anne
 *
 */
public class BankruptTest {

	ArrayList<Player> players;
	Market test;

	@Before
	public void setUp() throws Exception {
		players = new ArrayList<Player>();
		players.add(new RandomPlayer());
		players.add(new RandomPlayer());
		players.add(new RandomPlayer());
		test = new Market(players, 100000);
	}

	@Test
	public void noBankruptTest() {
		String message = "Found all bankrupt when none are";
		assertFalse(message, test.allBankrupt());
	}
	
	@Test
	public void someBankruptTest() {
		String message = "Found all bankrupt when only some are";
		test.portfolios.get(players.get(0)).capital = 0.0;
		test.portfolios.get(players.get(0)).stocks = new HashMap <Stock, Integer>();
		assertFalse(message, test.allBankrupt());
	}
	
	@Test
	public void allBankruptTest() {
		String message = "Found not all bankrupt when all are are";
		for(Player player : players){
			test.portfolios.get(player).capital = 0.0;
			test.portfolios.get(player).stocks = new HashMap <Stock, Integer>();
		}
		assertTrue(message, test.allBankrupt());
	}

}
