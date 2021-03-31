package helper;

public class Cart {
	
	String sn, name, quantity, price;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

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

	public Cart(String sn, String name, String quantity, String price) {
		this.sn = sn;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	

}
