package helper;


public class Sales {
	String name, quantity, price, date, time, cashier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public Sales(String name, String quantity, String price, String date, String time, String cashier) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
		this.time = time;
		this.cashier = cashier;
	}
	
	
	

}
