package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.metier.entites.User;

public class UserDaoImp implements IUserDao {

	Connection con;
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;

	public UserDaoImp() {
		this.con = DaoConnection.getConnection();
	}

	@Override
	public User save(User p) {
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO users(name, email, password) VALUES (?,?,?)");

			ps.setString(1, p.getNom());
			ps.setString(2, p.getEmail());
			ps.setString(3, p.getPassword());
			// ps.setString(4, p.getImage());
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
	public List<User> usersMc(String mc) {
		List<User> produits = new ArrayList<>();
		con = DaoConnection.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from users where name like ?");
			ps.setString(1, "%" + mc + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User p = new User();
				p.setId(rs.getInt("id"));
				p.setNom(rs.getString("name"));
				p.setEmail(rs.getString("email"));
				p.setPassword(rs.getString("password"));

				produits.add(p);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return produits;
	}

	@Override
	public User getUser(int id) {
		User p = null;

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p = new User();
				p.setId(rs.getInt("id"));
				p.setNom(rs.getString("name"));
				p.setEmail(rs.getString("email"));
				p.setPassword(rs.getString("password"));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "une erreur est survenu");
		}
		return p;
	}

	@Override
	public User update(User p) {

		try {

			PreparedStatement ps = con.prepareStatement("UPDATE Users SET name=?,email=?,password=? WHERE 1 id=?)");
			ps.setString(1, p.getNom());
			ps.setString(2, p.getEmail());
			ps.setString(3, p.getPassword());
			// ps.setString(4, p.getImage());
			ps.setInt(5, p.getId());
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
	public void deleteProduit(long id) {
		try {

			PreparedStatement ps = con.prepareStatement("DELETE FROM Users WHERE id=?");
			ps.setLong(1, id);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public User getLogin(String email, String password) {
		User p = null;

		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p = new User();
				p.setId(rs.getInt("id"));
				p.setNom(rs.getString("name"));
				p.setEmail("email");
				p.setPassword("password");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "une erreur est survenu");
		}
		return p;
	}

}