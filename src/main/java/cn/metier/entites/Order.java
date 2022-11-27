package cn.metier.entites;

public class Order extends Produit {
	private int oid;
	private int productId;
	private int userId;
	private double shipping;
	private int o_quantity;
	private double subtotal;
	private double tax;
	private double total;
	private String o_date;

	public Order() {
		super();
	}

	public Order(int orderId, int productId, int userId, double shipping, int o_quantity, double tax, double total,
			String o_date) {
		super();
		this.oid = orderId;
		this.productId = productId;
		this.userId = userId;
		this.shipping = shipping;
		this.o_quantity = o_quantity;
		this.tax = tax;
		this.total = total;
		this.o_date = o_date;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int orderId) {
		this.oid = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getO_quantity() {
		return o_quantity;
	}

	public void setO_quantity(int o_quantity) {
		this.o_quantity = o_quantity;
	}

	public String getO_date() {
		return o_date;
	}

	public void setO_date(String o_date) {
		this.o_date = o_date;
	}

	public void setShipping(float shipping) {
		this.shipping = shipping;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getSubtotal() {
		return String.format("%.2f", subtotal);
	}

	public String getShipping() {
		return String.format("%.2f", shipping);
	}

	public String getTax() {
		return String.format("%.2f", tax);
	}

	public String getTotal() {
		return String.format("%.2f", total);
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", productId=" + productId + ", userId=" + userId + ", shipping=" + shipping
				+ ", o_quantity=" + o_quantity + ", subtotal=" + subtotal + ", tax=" + tax + ", total=" + total
				+ ", o_date=" + o_date + "]";
	}

}
