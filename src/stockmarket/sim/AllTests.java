/**
 * 
 */
package stockmarket.sim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import stockmarketTest.PortfolioTest.allPortfolioTests;
import stockmarketTest.SimulatorTests.AllSimulatorTests;


@RunWith(Suite.class)
@SuiteClasses({AllSimulatorTests.class, allPortfolioTests.class})

public class AllTests {


}
