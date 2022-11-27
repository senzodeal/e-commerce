package cn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cn.dao.DaoConnection;
import cn.dao.IProduitDao;
import cn.dao.OrderDao;
import cn.dao.ProduitDaoImp;
import cn.dao.UserDao;
import cn.metier.entites.Cart;
import cn.metier.entites.Order;
import cn.metier.entites.Produit;
import cn.metier.entites.User;
import cn.metier.model.ProduitModel;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 1000, // 1 GB
		maxRequestSize = 1024 * 1024 * 1000) // 1 GB
/**
 * Servlet implementation class ControleurServlet
 */
@WebServlet(name = "cs", urlPatterns = { "/index", "*.php" })
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IProduitDao metier;
	ProduitModel model = null;
	HttpSession session;
	RequestDispatcher dispacher = null;

	@Override
	public void init() throws ServletException {
		metier = new ProduitDaoImp();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
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
		} else if (path.equals("/cart.php")) {
			addtocart(request, response);

		} else if (path.equals("/quantity.php")) {
			getIncrementDecrement(request, response);

		} else if (path.equals("/remove_cart.php")) {
			removeProductCart(request, response);

		} else if (path.equals("/orders-now.php")) {
			getOrders_now(request, response);

		} else if (path.equals("/checkout.php")) {
			getCheckout(request, response);

		} else if (path.equals("/login.php")) {
			getLogin(request, response);

		} else if (path.equals("/logout.php")) {
			getLogout(request, response);
		} else if (path.equals("/rechercher.php")) {
			rechercherproduit(request, response);

		} else if (path.equals("/saisiproduit.php")) {
			request.getRequestDispatcher("saisi.jsp").forward(request, response);
		} else if (path.equals("/saveproduit.php") && (request.getMethod().equals("POST"))) {
			saveproduit(request, response);
		} else if (path.equals("/editproduit.php")) {
			editproduit(request, response);
		} else if (path.equals("/updateproduit.php") && (request.getMethod().equals("POST"))) {
			getUpdateProduit(request, response);

		} else if (path.equals("/deleteproduit.php")) {
			deleteproduit(request, response);
		}

		else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		// request.getRequestDispatcher("produit.jsp").forward(request, response);
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

	private void getIncrementDecrement(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html, charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String action = request.getParameter("action");
			int id = Integer.parseInt(request.getParameter("id"));
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");

			if (action != null && id >= 1) {
				if (action.equals("inc")) {
					for (Cart c : cart_list) {
						if (c.getId() == id) {
							int quantity = c.getQuantity();
							quantity++;
							c.setQuantity(quantity);
							dispacher = request.getRequestDispatcher("cart.jsp");
						}
					}
				}
				if (action.equals("dec")) {
					for (Cart c : cart_list) {
						if (c.getId() == id && c.getQuantity() > 1) {
							int quantity = c.getQuantity();
							quantity--;
							c.setQuantity(quantity);
							break;
						}
					}
					dispacher = request.getRequestDispatcher("cart.jsp");

				}
			} else {
				dispacher = request.getRequestDispatcher("cart.jsp");

			}
			dispacher.forward(request, response);
		}
	}

	private void removeProductCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html, charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String id = request.getParameter("id");

			if (id != null) {
				ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");
				if (cart_list != null) {
					for (Cart c : cart_list) {
						if (c.getId() == Integer.parseInt(id)) {
							cart_list.remove(cart_list.indexOf(c));
							break;
						}
					}
				}
			} else {
				dispacher = request.getRequestDispatcher("cart.jsp");

			}
			dispacher.forward(request, response);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		dispacher.forward(request, response);
	}

	private void deleteproduit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		metier.deleteProduit(id);
		// request.getRequestDispatcher("produit.jsp").forward(request, response);
		response.sendRedirect("rechercher.php?motcle=");
	}

	private void getUpdateProduit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("nom");
		String Designation = request.getParameter("designation");
		double price = Double.parseDouble(request.getParameter("prix"));
		String image = request.getParameter("quantite");

		Produit p = new Produit();
		p.setId(id);
		p.setName(name);
		p.setCategory(Designation);
		p.setPrice(price);
		p.setImage(image);

		Produit d = metier.update(p);
		request.setAttribute("produit", d);

		request.getRequestDispatcher("confirmation.jsp").forward(request, response);
	}

	private void getLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getMethod().equals("POST")) {
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");

			User u = new User();
			UserDao ud = new UserDao();
			u = ud.getLogin(email, password);

			if (u != null) {
				request.getSession().setAttribute("auth", u);
				request.getRequestDispatcher("index.jsp").forward(request, response);
				// System.out.println("ok :"+ request.getSession().getAttribute("auth"));
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);

			}
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	private void getLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("auth") != null) {
			request.getSession().removeAttribute("auth");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private void rechercherproduit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String motcle = request.getParameter("motcle");
		ProduitModel model = new ProduitModel();
		model.setMotCle(motcle);
		List<Produit> produits = metier.produitsmc("%" + motcle + "%");
		model.setProduits(produits);
		request.setAttribute("model", model);
		request.getRequestDispatcher("produit.jsp").forward(request, response);
	}

	private void editproduit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Produit p = metier.getProduit(id);
		request.setAttribute("produit", p);
		request.getRequestDispatcher("editeproduit.jsp").forward(request, response);
	}

	private void saveproduit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("nom");
		String category = request.getParameter("designation");
		double price = Double.parseDouble(request.getParameter("prix"));
		String image = request.getParameter("quantite");

		Produit p = new Produit();
		p.setName(name);
		p.setCategory(category);
		p.setPrice(price);
		p.setImage(image);

		Produit d = metier.save(p);
		request.setAttribute("produit", d);
		request.getRequestDispatcher("confirmation.jsp").forward(request, response);
	}

	private void getOrders_now(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html, charset=UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt = new java.util.Date();
		try (PrintWriter out = response.getWriter()) {
			try {
				User auth = (User) request.getSession().getAttribute("auth");
				if (auth != null) {
					int id = Integer.parseInt(request.getParameter("id"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));

					if (quantity <= 0) {
						quantity = 1;
					}
					Order od = new Order();
					od.setProductId(id);
					od.setUserId(auth.getId());
					od.setO_quantity(quantity);
					od.setO_date(sdf.format(dt));

					OrderDao odd = new OrderDao();
					boolean result = odd.insertOrder(od);

					if (result) {
						ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");
						if (cart_list != null) {
							for (Cart c : cart_list) {
								if (c.getId() == id) {
									cart_list.remove(cart_list.indexOf(c));
									break;
								}
							}
						}
						response.sendRedirect("orders.jsp");
					} else {
						out.print("order failed");
					}

				} else {
					response.sendRedirect("login.jsp");
				}

			} catch (Exception e) {
				out.print(e.getMessage());
			}
		}
	}

	private void getCheckout(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// response.setContentType("text/html, charset=UTF-8");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt = new java.util.Date();
		try (PrintWriter out = response.getWriter()) {
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart_list");
			int id = Integer.parseInt(request.getParameter("id"));
			User auth = (User) request.getSession().getAttribute("auth");
			if (cart_list != null && auth != null) {
				for (Cart c : cart_list) {
					Order order = new Order();
					order.setOid(c.getId());
					order.setUserId(auth.getId());
					order.setO_quantity(c.getQuantity());
					order.setO_date(sdf.format(dt));
					
					 out.print("Contenu order: !!!" + order);
					  OrderDao oDao=new OrderDao(); boolean result = oDao.insertOrder(order);
					  
					  if (!result) break;
					  
					  }
						
						  out.print("Contenu de la liste: !!!"); cart_list.clear();
						  request.getRequestDispatcher("orders.jsp").forward(request, response);
						  //response.sendRedirect(); } else {
						  
						  if(auth==null) {response.sendRedirect("login.jsp");
						  
						  }else {response.sendRedirect("orders.jsp"); }
						 
					 

				}
			}
		}
	

	private void addtocart(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html, charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			ArrayList<Cart> cl = new ArrayList<>();

			int id = Integer.parseInt(request.getParameter("id"));
			Cart cm = new Cart();
			cm.setId(id);
			cm.setQuantity(1);

			HttpSession session = request.getSession();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");

			if (cart_list == null) {
				cl.add(cm);
				session.setAttribute("cart_list", cl);
				dispacher = request.getRequestDispatcher("index.php");
				out.print("session create and added the list");
			} else {
				cl = cart_list;
				boolean exist = false;
				for (Cart c : cart_list) {
					if (c.getId() == id) {
						exist = true;
						dispacher = request.getRequestDispatcher("cart.jsp");
					}

				}
				if (!exist) {
					cl.add(cm);
					out.println("Product added");
					response.sendRedirect("index.php");
				}
			}

			List<Cart> cartProducts = null;
			if (cart_list != null) {
				IProduitDao metier = new ProduitDaoImp();
				cartProducts = metier.getCartProducts(cart_list);

				request.setAttribute("cartProducts", cartProducts);
				dispacher = request.getRequestDispatcher("cart.jsp");
			}
			dispacher.forward(request, response);

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

	}

	private static java.sql.Date getDate(String date) {
		/**
		 * String dt=date.substring(0,10); LocalDateTime dtm = LocalDateTime.now(); long
		 * dtL=Long.parseLong((dtm.toString()).substring(0,10)); String
		 * dtS=String.valueOf(dtL); int dtI=Integer.parseInt(dtS);
		 * System.out.println("\nPlease enter the date of the appointment, format:
		 * yyyy/mm/dd"+" "+dtI);
		 **/
		Date apptDay = new Date(2022 - 9 - 18);
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date sqlDate = null;

		try {

			apptDay = (Date) df.parse(date);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + "Please enter a valid date! Format is yyyy/mm/dd"
					+ (date.toString()).substring(0, 10));
		}

		// sqlDate = Date(apptDay.getTime());
		return sqlDate;
	}

	private java.sql.Date farmatDate() {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	private Date convertToDate(String receivedDate) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = (Date) formatter.parse(receivedDate);
		return date;
	}

	protected void downlaodFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int BUFFER_SIZE = 1024 * 100;
		final String UPLOAD_DIR = "resources";
		String fileName = null;

		/**
		 * *** Get The Absolute Path Of The File To Be Downloaded ****
		 */
		fileName = request.getParameter("fileName");
		if (fileName == null || fileName.equals("")) {
			/**
			 * *** Set Response Content Type ****
			 */
			response.setContentType("text/html");

			/**
			 * *** Print The Response ****
			 */
			response.getWriter().println("<h3>File " + fileName + " Is Not Present .....!</h3>");
		} else {
			String applicationPath = getServletContext().getRealPath("");
			String downloadPath = applicationPath + File.separator + UPLOAD_DIR;
			String filePath = downloadPath + File.separator + fileName;
			System.out.println(fileName);
			System.out.println(filePath);
			System.out.println("fileName:" + fileName);
			System.out.println("filePath :" + filePath);
			File file = new File(filePath);
			OutputStream outStream = null;
			FileInputStream inputStream = null;

			if (file.exists()) {

				/**
				 * ** Setting The Content Attributes For The Response Object ***
				 */
				String mimeType = "application/octet-stream";
				response.setContentType(mimeType);

				/**
				 * ** Setting The Headers For The Response Object ***
				 */
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
				response.setHeader(headerKey, headerValue);

				try {

					/**
					 * ** Get The Output Stream Of The Response ***
					 */
					outStream = response.getOutputStream();
					inputStream = new FileInputStream(file);
					byte[] buffer = new byte[BUFFER_SIZE];
					int bytesRead = -1;

					/**
					 * ** Write Each Byte Of Data Read From The Input Stream Write Each Byte Of Data
					 * Read From The Input Stream Into The Output Stream ***
					 */
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}
				} catch (IOException ioExObj) {
					System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}

					outStream.flush();
					if (outStream != null) {
						outStream.close();
					}
				}
			} else {

				/**
				 * *** Set Response Content Type ****
				 */
				response.setContentType("text/html");

				/**
				 * *** Print The Response ****
				 */
				response.getWriter().println("<h3>File " + fileName + " Is Not Present .....!</h3>");
			}

		}
	}

	protected void uploadFiles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		Connection con = null;
		PreparedStatement ps = null;
		HttpSession session = null;
		response.setContentType("text/plain;charset=UTF-8");
		try {
			out = response.getWriter();
			session = request.getSession(false);
			String folderName = "resources";
			String uploadPath = request.getServletContext().getRealPath("") /** + File.separator **/
					+ folderName;// for netbeans use this code
			System.out.println("Uploaded Path: " + uploadPath);
			// String uploadPath = request.getServletContext().getRealPath("") +
			// folderName;//for eclipse use this code
			File dir = new File(uploadPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			Part filePart = request.getPart("file");// Textbox value of name file.
			String firstName = request.getParameter("firstname");// Textbox value of name firstname.
			String lastName = request.getParameter("lastname");// Textbox value of name lastname.
			String fileName = filePart.getSubmittedFileName();
			String path = folderName + File.separator + fileName;
			Timestamp added_date = new Timestamp(System.currentTimeMillis());
			System.out.println("fileName: " + fileName);
			System.out.println("Path: " + uploadPath);
			System.out.println("Name: " + firstName);
			InputStream is = filePart.getInputStream();
			Files.copy(is, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);

			try {
				con = DaoConnection.getConnection();
				System.out.println("connection done");
				String sql = "insert into employee(firstname,lastname,filename,path,added_date) values(?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, fileName);
				ps.setString(4, path);
				ps.setTimestamp(5, added_date);
				int status = ps.executeUpdate();
				if (status > 0) {
					session.setAttribute("fileName", fileName);
					String msg = "" + fileName + " File uploaded successfully...";
					request.setAttribute("msg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("/success.jsp");
					rd.forward(request, response);
					System.out.println("File uploaded successfully...");
					System.out.println("Uploaded Path: " + uploadPath);
				}
			} catch (SQLException e) {
				out.println("Exception: " + e);
				System.out.println("Exception1: " + e);
			}

		} catch (IOException | ServletException e) {
			out.println("Exception: " + e);
			System.out.println("Exception2: " + e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
