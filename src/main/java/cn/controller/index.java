package cn.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.dao.IProduitDao;
import cn.dao.ProduitDaoImp;
import cn.metier.entites.Produit;
import cn.metier.model.ProduitModel;

/**
 * Servlet implementation class index
 */
@WebServlet(name = "cs", urlPatterns = { "/index", "*.php" })
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IProduitDao metier;
	ProduitModel model = null;
	HttpSession session;
	RequestDispatcher dispacher = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public index() {
		super();
		metier = new ProduitDaoImp();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		if (path.equals("/")) {
			getProducts(request, response);
		} else if (path.equals("/index.php")) {
			getProducts(request, response);
		} else if (path.equals("/index.php")) {
			dispacher = request.getRequestDispatcher("index.jsp");
			dispacher.forward(request, response);
		}
	}

	private void getProducts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String motcle = request.getParameter("motcle");
		ProduitModel model = new ProduitModel();
		// model.setMotCle(motcle);
		List<Produit> produits = metier.getProduits();

		model.setProduits(produits);
		request.setAttribute("model", model);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
