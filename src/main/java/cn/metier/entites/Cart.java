package cn.metier.entites;

public class Cart extends Produit {
	private int quantity;

	public Cart() {
		super();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
