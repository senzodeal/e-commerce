package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.metier.entites.Cart;
import cn.metier.entites.Produit;

public class ProduitDaoImp implements IProduitDao {
	private Connection con = DaoConnection.getConnection();
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;

	@Override
	public Produit save(Produit p) {
		try {
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO products(name, category, price, image) VALUES (?,?,?,?)");

			ps.setString(1, p.getName());
			ps.setString(2, p.getCategory());
			ps.setDouble(3, p.getPrice());
			ps.setString(4, p.getImage());
			// Insertiondu produit dans la table produit
			ps.executeUpdate();
			// Recuperation de l'id du produit qui vient d'etre insere
			PreparedStatement ps2 = con.prepareStatement("select MAX(ID) as max_id from products");
			ResultSet rs = ps2.executeQuery();
			if (rs.next()) {
				p.setId(rs.getInt("max_id")); // Definir l'id du produit insere

			}
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.getId());
		return p;
	}

	@Override
	public List<Produit> produitsmc(String mc) {
		List<Produit> produits = new ArrayList<>();
		con = DaoConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM products where category like ?");
			ps.setString(1, "%" + mc + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Produit p = new Produit();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setCategory(rs.getString("category"));
				p.setPrice(rs.getDouble("price"));
				p.setImage(rs.getString("image"));

				produits.add(p);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return produits;
	}

	@Override
	public List<Produit> getProduits() {
		List<Produit> produits = new ArrayList<>();
		con = DaoConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM products");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Produit p = new Produit();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setCategory(rs.getString("category"));
				p.setPrice(rs.getDouble("price"));
				p.setImage(rs.getString("image"));

				produits.add(p);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return produits;
	}

	@Override
	public Produit getProduit(int id) {
		Produit p = null;

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE id=?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setCategory(rs.getString("category"));
				p.setPrice(rs.getDouble("price"));
				p.setImage(rs.getString("image"));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "une erreur est survenu");
		}
		return p;
	}

	@Override
	public Produit update(Produit p) {

		try {

			PreparedStatement ps = con
					.prepareStatement("UPDATE produits SET name=?,category=?,price=?,image=? WHERE 1 id=?)");
			ps.setString(1, p.getName());
			ps.setString(2, p.getCategory());
			ps.setDouble(3, p.getPrice());
			ps.setString(4, p.getImage());
			ps.setLong(5, p.getId());
			// Insertiondu produit dans la table produit
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.getId());
		return p;
	}

	@Override
	public void deleteProduit(int id) {
		try {

			PreparedStatement ps = con.prepareStatement("DELETE FROM produits WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public List<Cart> getCartProducts(ArrayList<Cart> cl) {
		List<Cart> produits = new ArrayList<>();
		con = DaoConnection.getConnection();
		try {
			if (cl.size() > 0) {

				for (Cart c : cl) {
					PreparedStatement ps = con.prepareStatement("SELECT * FROM products where id=?");
					ps.setInt(1, c.getId());
					ResultSet rs = ps.executeQuery();

					while (rs.next()) {
						Cart p = new Cart();
						p.setId(rs.getInt("id"));
						p.setName(rs.getString("name"));
						p.setCategory(rs.getString("category"));
						p.setPrice(rs.getDouble("price") * c.getQuantity());
						p.setImage(rs.getString("image"));
						p.setQuantity(c.getQuantity());

						produits.add(p);
					}
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return produits;
	}

	@Override
	public double getTotalCartPrice(ArrayList<Cart> cartList) {
		double sum = 0;

		try {
			if (cartList.size() > 0) {
				for (Cart c : cartList) {
					query = "select price from products where id=?";
					ps = this.con.prepareStatement(query);
					ps.setInt(1, c.getId());
					rs = ps.executeQuery();
					while (rs.next()) {
						sum += rs.getDouble("price") * c.getQuantity();

					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sum;
	}

}
