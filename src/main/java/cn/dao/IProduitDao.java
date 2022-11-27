package cn.dao;

import java.util.ArrayList;
import java.util.List;

import cn.metier.entites.Cart;
import cn.metier.entites.Produit;

public interface IProduitDao {
	public Produit save(Produit p);

	public List<Produit> produitsmc(String mc);

	public List<Cart> getCartProducts(ArrayList<Cart> cl);

	public List<Produit> getProduits();

	public Produit getProduit(int id);

	public Produit update(Produit p);

	public void deleteProduit(int id);

	public double getTotalCartPrice(ArrayList<Cart> cartList);
}
