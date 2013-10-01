/**
 * 
 */
package stockmarket.sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import stockmarketTest.MarketTests.AllMarketTests;
import stockmarketTest.PortfolioTest.AllPortfolioTests;
import stockmarketTest.SimulatorTests.AllSimulatorTests;


@RunWith(Suite.class)
@SuiteClasses({AllSimulatorTests.class, AllPortfolioTests.class, AllMarketTests.class})

public class AllTests {


}
