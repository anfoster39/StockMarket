/**
 * 
 */
package stockmarket.sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.Math;


/**
 * this class generates the economic indicators and stock price
 *   each round. It will first create the test data for the player to learn by
 *   generating a number of rounds of economic indicators and stock prices.
 *   It will then go into live mode where it notifies the Market it is a new 
 *   round and give it the indicators whereafter the market will get the player
 *   trades. The Simulator will then update the price of each stock based on a 
 *   secret formula based on the economic indicators. This continues until the 
 *   the round limit is hit or Player goes bankrupt. At the end of the game the 
 *   Simulator prints out the score.
 *
 */
public class Simulator {

    private static Random           random;
	private double 					STARTING_CAPITAL				=100000;
	private double					TRANSACTIONFEE					= 5;
	public int 						NUM_STOCKS						=10;
    private static final int        MAX_ROUNDS     					=500;
	private static final int		TRAINING_ROUNDS					=25;
	private static final double		PROB_NO_INFLUANCE	  		 	=.2;
	private static final double		STRENGTH_OF_TRENDS	  		 	=.5;
	private static final double		RANDOM_STOCK_VARIATIONLIMIT 	=.2;
	public static final int			STOCK_MAX_PRICE					= 500;
	public static final int			STOCK_MIN_PRICE					= 10;

	private GameConfig config;
	public HashMap <Stock, ArrayList<Double>> calculateStocks; 
	public ArrayList<EconomicIndicator> indicators;
	public ArrayList<Double> indicatorsMax;
	public ArrayList<Double> indicatorsMin;
	public ArrayList<Stock> stocks;
	public ArrayList<Player> players;
	public ArrayList<String> stockNames;
	public Market market;
	
	public Simulator(String filename){
		random = new Random();
		stockNames = new ArrayList<String>();
		config = new GameConfig(filename);
		
		indicators = new ArrayList<EconomicIndicator> ();
		stocks = new ArrayList<Stock>();
		players = new ArrayList<Player>();
		calculateStocks = new HashMap <Stock, ArrayList<Double>>();
		indicatorsMin = new ArrayList<Double> ();
		indicatorsMax = new ArrayList<Double> ();
		
		
		createIndicators();
		createStocks();
		initializePlayers();
		
		market = new Market(players, STARTING_CAPITAL, TRANSACTIONFEE);
		
		run();
	}
	
	//for testing
	public Simulator(){
		random = new Random();
		stockNames = new ArrayList<String>();
		
		indicators = new ArrayList<EconomicIndicator> ();
		stocks = new ArrayList<Stock>();
		players = new ArrayList<Player>();
		calculateStocks = new HashMap <Stock, ArrayList<Double>>();
		indicatorsMin = new ArrayList<Double> ();
		indicatorsMax = new ArrayList<Double> ();
		
		
		createIndicators();
		createStocks();
		initializePlayer();
		
		market = new Market(players, STARTING_CAPITAL, TRANSACTIONFEE);
		
	}
	
	public static final void main(String[] args)
	{
		if (args.length < 1)
		{
			System.out.println("Quitting, no players given");
			System.exit(-1);
		}
		Simulator game = new Simulator(args[0]);
	}
	
	
	public void run(){
		
		int round = 0;

		for (round = 0; round < TRAINING_ROUNDS; round++){
			updateIndicatorsTrend(round);
			updateStockPrice(round, null);
		}
		
		market.trainPlayers(indicators, stocks);

		
		while (!market.allBankrupt() && round <= MAX_ROUNDS){
			updateIndicatorsTrend(round);
			ArrayList<Trade> marketForces = market.newRound(round, indicators, stocks);
			updateStockPrice(round, marketForces);
			market.printPorfolios();
			round++;
		}
		
		printResults();
		
	}
	
	/**
	 * 
	 */
	private void printResults() {
		market.printPorfolios();
	}

	/**
	 * 
	 */
	private void initializePlayer() {
		players.add(new stockmarket.g0.RandomPlayer());
		
	}
	
	/**
	 * 
	 */
	private void initializePlayers() {
		try {
			players.add(config.availablePlayers.get(0).newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void createIndicators(){
		indicators.add(new EconomicIndicator("Unemployment", 5));
		indicatorsMin.add((double) 2);
		indicatorsMax.add((double) 10);
		indicators.add(new EconomicIndicator("GDP", 13.263));
		indicatorsMin.add((double) 5);
		indicatorsMax.add((double) 21);
		indicators.add(new EconomicIndicator("Inflation", 2.01));
		indicatorsMin.add((double) -2);
		indicatorsMax.add((double) 10);
		indicators.add(new EconomicIndicator("Exports", 3.52));
		indicatorsMin.add((double) .5);
		indicatorsMax.add((double) 8);
		indicators.add(new EconomicIndicator("Imports", 6.93));
		indicatorsMin.add((double) 2);
		indicatorsMax.add((double) 10);
	}

	/**
	 * Creates the formula that determines each stocks's price
	 * 
	 */
	public void createStocks(){
		//does not check case where all coefficients are 0 
		for (int i = 0; i < NUM_STOCKS; i++){
			stocks.add(new Stock (createStockName(), getRandomBetween(STOCK_MIN_PRICE, STOCK_MAX_PRICE)));
			ArrayList<Double> formula;
			do{
				formula = new ArrayList<Double>();
				for (int k = 0; k < indicators.size(); k++){
					formula.add(generateCoefficient());
				}	
			} while (!formulaOK(i, formula));
			calculateStocks.put(stocks.get(i), formula);
		}
	}
	
	/**
	 * @param i
	 * @return
	 */
	private boolean formulaOK(int stockIndex, ArrayList<Double> formula) {
		double calcPrice = 0;
		//does the current calculation return an unusable price?
		for (int i = 0; i < indicators.size(); i++){
			calcPrice += (indicators.get(i).getValue()) * formula.get(i);
		}
		if(calcPrice > STOCK_MAX_PRICE || calcPrice < STOCK_MIN_PRICE) return false;
		
		//Will all indicators very high result in an unusable price?
		calcPrice = 0;
		for (int i = 0; i < indicators.size(); i++){
			calcPrice += indicatorsMin.get(i) * formula.get(i);
		}
		if(calcPrice > STOCK_MAX_PRICE || calcPrice < STOCK_MIN_PRICE) return false;
		
		//Will all indicators very low result in an unusable price?
		calcPrice = 0;
		for (int i = 0; i < indicators.size(); i++){
			calcPrice += indicatorsMax.get(i) * formula.get(i);
		}
		if(calcPrice > STOCK_MAX_PRICE || calcPrice < STOCK_MIN_PRICE) return false;
		
		return true;
	}

	/**
	 * Generates 3 letter stock names and checks for duplicate names 
	 */
	public String createStockName(){
		String name = "" + (char)('A' + Math.abs(random.nextInt() % 25)) +					
						   (char)('A' + Math.abs(random.nextInt() % 25)) + 
						   (char)('A' + Math.abs(random.nextInt() % 25));
		if(!stockNames.contains(name)){
			stockNames.add(name);
			return name;
		}
		else return createStockName();
	}
	
	/**
	 * Creates the coefficient for a stock equation
	 * All coefficients are between -1 and 1 and have a
	 * certain percentage change of being 0
	 * @return
	 */
	public double generateCoefficient(){
		if (random.nextDouble() < PROB_NO_INFLUANCE) return 0.0; 
		int coefficient = 1; 
		if(random.nextBoolean()) coefficient = -1;
		return (coefficient * random.nextDouble()); 
	}
	
	/**
	 * Economic indicators need to have trends (if going up, more
              likely to go up)
                - if previous was negative, then a large % chance the next one is negative
                    - if the amount going up is small a smaller chance of staying the same direction
	 * @param round
	 */
	public void updateIndicators(int round){
		for(EconomicIndicator indicator: indicators){
			indicator.updateValue(round, indicator.getValue()
					*(1 + getRandomBetween(-.01, .01)) );
		}
	}
		
	/**
	 * Economic indicators need to have trends (if going up, more likely to go up)
                - if previous was negative, then a large % chance the next one is negative
                    - if the amount going up is small a smaller chance of staying the same direction
	 * @param round
	 */
	public void updateIndicatorsTrend (int round){
		for(int i = 0; i < indicators.size(); i++){
			int direction = 1;
			double change = .5;
			double incriment = (indicatorsMax.get(i) - indicatorsMin.get(i)) / 100;
			
			if (round > 2){
				 change = (1 - (indicators.get(i).getHistory().get(round-1) 
							/ indicators.get(i).getHistory().get(round-2)));
				
				 if (change < 0){
					direction = -1;
				}
			 
				//The random double from the last round
				double percentageChange = normalizeRandom(0, incriment, Math.abs(change));
//				System.out.println("normalized " + percentageChange + " total:  " + (percentageChange + STRENGTH_OF_TRENDS) + " direction " + direction);
				if (random.nextDouble() > (percentageChange + STRENGTH_OF_TRENDS)){
					direction *= -1;
				}
			}
			
				//to stay in range
				if (indicators.get(i).getValue() >= indicatorsMax.get(i)*(1-incriment)){
					direction = -1;
				}
				if (indicators.get(i).getValue() <= indicatorsMin.get(i)*(1+incriment)){
					direction = 1;
				}	
				
			double update = 1 + (direction*getRandomBetween(0, incriment));
			double newPrice = indicators.get(i).getValue()* update;
			indicators.get(i).updateValue(round, (newPrice));
		}
	}

	
	/**
	 * Updates each stock price based on the economic indicators and the popularity of the 
	 * stock
	 * @param round The current round
	 */
	public void updateStockPrice(int round, ArrayList<Trade> marketTrades){
		Hashtable<Stock, Integer> marketPressures = consolidateMarketPressures(marketTrades);
		for (Stock stock: stocks){
			int popularity = 1;
			if (marketPressures.contains(stock)){
				popularity = marketPressures.get(stock);
			}
			stock.updatePrice(round, calculateStockPrice(stock, popularity));
		}
	}
	
	/**
	 * Calculates the new stock price given the current indicators, the formula, and the popularity 
	 * Goes through the stock's formula to get the new base price, plus a small random amount.
	 * The stock will also increase based on how much of the stock was bought or sold as a percentage
	 * of the total stock on the market, calculated by how many shares would be in circulation if 
	 * every player bought all of the stocks they could at the beginning. 
	 * @param stock To update
	 * @return the new price of the stock
	 */
	public double calculateStockPrice(Stock stock, int popularity){
		double newPrice = 0;
		newPrice += RANDOM_STOCK_VARIATIONLIMIT * random.nextDouble();
		
		//change from indicators
		for (int i = 0; i < indicators.size(); i++){
			newPrice += indicators.get(i).getValue() * calculateStocks.get(stock).get(i);
		}
		//market adjustment based on how much the stock has been bought or sold
		double sharesAvabilble = getMarketSize() /stock.getPrice();
		newPrice *= 1 + (popularity / sharesAvabilble);
		return newPrice;
	}

	
	/**
	 * Consolidates all trades to see how much of a stock is bought or sold
	 * @param trades The list of all trades done that round. 
	 * @return A Map with all the socks and the final number of buys and sells 
	 */
	private Hashtable<Stock, Integer> consolidateMarketPressures (ArrayList<Trade> trades){
		Hashtable<Stock, Integer> pressurs = new Hashtable<Stock, Integer>();
		if (trades == null){
			return pressurs;
		}
		for (Trade trade : trades){
			Stock stock = trade.getStock();
			if (pressurs.contains(stock)){
				pressurs.put(stock, pressurs.get(stock) + (trade.getType()*trade.getQuantity()));
			}
			else {
				pressurs.put(stock, (trade.getType()*trade.getQuantity()));
			}
		}
		return pressurs;
	}
	
	/**
	 * Used to get the size of the market to calculate the number of shares for a certain stock.
	 * @return
	 */
	private double getMarketSize(){
		double size =0;
		for (Player player : players){
			size += market.getPortfolio(player).getMonetaryValue();
		}
		return size;
	}
	
	
	public double getRandomBetween(double lowerBound, double upperBound){
		return (random.nextDouble()*(upperBound-lowerBound)) + lowerBound; 
	}
	
	public double normalizeRandom(double lowerBound, double upperBound, double value){
		return (value / (upperBound-lowerBound)) - lowerBound;
	}
	
		
}
