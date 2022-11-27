package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.metier.entites.User;

public class UserDao {
	private Connection cn;
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;

	public UserDao() {
		super();
		this.cn = DaoConnection.getConnection();
	}

	public User getLogin(String email, String password) {
		this.query = "SELECT * FROM users WHERE email=? and password=?";
		User p = null;

		try {
			ps = cn.prepareStatement(this.query);
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