/**
 * 
 */
package stockmarket.sim;

/**
 * This holds the info the Market uses to make a trade 
 *
 */
public class Trade {
	public Stock stock;
	public int round;
	public int quantity;
	public int type;
	
	public Trade (int type, Stock stock, int quantity){
		this.type = type;
		this.stock = stock;
		this.quantity = quantity;
	}

}
