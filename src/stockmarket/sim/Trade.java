/**
 * 
 */
package stockmarket.sim;

/**
 * This holds the info the Market uses to make a trade 
 *
 */
public class Trade {
	public static final int BUY    = 1;
	public static final int SELL   = -1;
	
	private Stock stock;
	private int quantity;
	private int type;
	
	public Stock getStock() {
		return stock;
	}
	
	public String getStockName() {
		return stock.getName();
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
	
	public double getCostBeforeTransactionFee() {
		return (stock.getPrice() * quantity);
	}	
	
	public Trade (int type, Stock stock, int quantity){
		this.type = type;
		this.stock = stock;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		if (type == BUY){
			return "Trade: [ Buy " + quantity + " shares of "+ stock.getName() 
					+ " at " + stock.getPrice() + "]";
		}
		return "Trade: [ Sell " + quantity + " shares of "+ stock.getName() 
				+ " at " + stock.getPrice() + "]";
	}
	

}
