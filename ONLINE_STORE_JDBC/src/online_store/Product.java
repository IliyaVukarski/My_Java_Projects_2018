package online_store;

public class Product {
	private String productName;
	private double productPrice;
	private double productPriceWithTax;
	
	public Product() {
		
	}
	
	public Product(String productName, double productPrice,
			double productPriceWithTax) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPriceWithTax = productPriceWithTax;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getProductPriceWithTax() {
		return productPriceWithTax;
	}

	public void setProductPriceWithTax(double productPriceWithTax) {
		this.productPriceWithTax = productPriceWithTax;
	}
}