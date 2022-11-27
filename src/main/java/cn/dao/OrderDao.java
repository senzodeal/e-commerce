package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.metier.entites.Order;
import cn.metier.entites.Produit;

public class OrderDao {
	private Connection con = DaoConnection.getConnection();
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;

	public OrderDao() {
	}

	public boolean insertOrder(Order o) {
		this.query = "INSERT INTO orders(p_id, u_id, o_quantity, o_date) VALUES (?,?,?,?)";
		boolean insert = false;
		try {

			PreparedStatement ps = con.prepareStatement(this.query);

			ps.setInt(1, o.getProductId());
			ps.setInt(2, o.getUserId());
			ps.setInt(3, o.getO_quantity());
			ps.setString(4, o.getO_date());
			ps.executeUpdate();
			insert = true;

			// System.out.print("Insertion ok !!!"+o.toString());
			ps.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return insert;
	}

	public List<Order> userOrder(int id) {
		List<Order> lo = new ArrayList<>();
		try {
			this.query = "select * from orders where u_id=? oder by orders.o_id desc";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order o = new Order();
				ProduitDaoImp pd = new ProduitDaoImp();
				int p_id = rs.getInt("p_id");

				Produit p = pd.getProduit(p_id);
				o.setOid(rs.getInt("o_id"));
				o.setProductId(id);
				o.setName(p.getName());
				o.setCategory(p.getCategory());
				o.setPrice(p.getPrice() * rs.getInt("o_quantity"));
				o.setO_quantity(rs.getInt("o_quantity"));
				o.setO_date(rs.getString("o_date"));
				lo.add(o);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return lo;
	}
}
