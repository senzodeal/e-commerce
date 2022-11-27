package cn.metier.model;

import java.util.ArrayList;
import java.util.List;

import cn.metier.entites.Cart;
import cn.metier.entites.Produit;

public class ProduitModel {
	private String motCle;
	private List<Produit> produits = new ArrayList<>();
	private List<Cart> cart = new ArrayList<>();

	public String getMotCle() {
		return motCle;
	}

	public void setMotCle(String motCle) {
		this.motCle = motCle;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

}
