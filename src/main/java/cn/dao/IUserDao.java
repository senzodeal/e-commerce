package cn.dao;

import java.util.List;

import cn.metier.entites.User;

public interface IUserDao {
	public User save(User u);

	public List<User> usersMc(String mc);

	public User getUser(int id);

	public User update(User u);

	public void deleteProduit(long id);

	public User getLogin(String email, String password);
}
