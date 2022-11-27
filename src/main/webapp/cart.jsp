<%@page import="java.text.DecimalFormat"%>
<%@page import="com.mysql.cj.result.BigDecimalValueFactory"%>
<%@page import="com.enzo.dao.IProduitDao"%>
<%@page import="com.enzo.dao.ProduitDaoImp"%>
<%@page import="cn.metier.entites.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="cn.metier.entites.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
DecimalFormat dcf=new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);

}
ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
List<Cart> cartProducts = null;
if (cart_list != null) {
	IProduitDao metier = new ProduitDaoImp();
	cartProducts = metier.getCartProducts(cart_list);
	double total = metier.getTotalCartPrice(cart_list);
	request.setAttribute("cart_list", cart_list);
	request.setAttribute("total", total);

} else {
	System.out.println("Le panier est vide !!!");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>
<%@ include file="includes/header.jsp"%>
<style type="text/css">
.table tbody td {
	vertical-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 25px;
}
</style>
</head>
<body>
	<%@ include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="d-flex py-3">
			<h3>Total price : $ ${(total>0)?dcf.format(total):0 }</h3>
			<a href="checkout.php" class="mx-3 btn btn-primary">Check outs</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (cart_list != null) {
					for (Cart c : cartProducts) {
				%>
				<tr>
					<td><%=c.getName()%></td>
					<td><%=c.getCategory()%></td>
					<td><%=dcf.format(c.getPrice())%></td>
					<td>
						<form action="orders-now.php" method="post" class="form-inline">
							<input type="hidden" name="id" value="<%=c.getId()%>"
								class="form-input">
							<div class="form-group d-flex justify-content-between">
								<a class="btn btn-sm btn-incre"
									href="quantity.php?action=dec&id=<%=c.getId()%>"><i
									class="fas fa-minus-square"></i></a> <input type="text"
									name="quantity" class="form-control" value="<%=c.getQuantity() %>"
									readonly="readonly"> <a class="btn btn-sm btn-incre"
									href="quantity.php?action=inc&id=<%=c.getId()%>"><i
									class="fas fa-plus-square"></i></a>
							</div>
							<button type="submit" class="btn btn-primary btn-sm">Buy</button>
						</form>
					</td>
					<td><a href="remove_cart.php?id=<%=c.getId() %>" class="btn btn-sm btn-danger">Remove</a></td>
				</tr>


				<%
				}
				}
				%>

			</tbody>
		</table>
	</div>
	<%@ include file="includes/footer.jsp"%>
</body>
</html>