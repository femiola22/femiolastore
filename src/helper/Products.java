package helper;

public class Products {

	String name, price, quantity, section;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuatity(String quatity) {
		this.quantity = quatity;
	}

	public Products(String name, String price, String quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public Products(String name, String quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	public Products(String name, String price, String quantity, String section) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.section = section;
	}
	
	
}
