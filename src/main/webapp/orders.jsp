<%@page import="java.text.DecimalFormat"%>
<%@page import="cn.metier.entites.Order"%>
<%@page import="com.enzo.dao.OrderDao"%>
<%@page import="com.enzo.dao.ProduitDaoImp"%>
<%@page import="cn.metier.entites.Cart"%>
<%@page import="com.enzo.dao.IProduitDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="cn.metier.entites.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
DecimalFormat dcf=new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);
User auth= (User)request.getSession().getAttribute("auth");
List<Order> od=null;
if(auth !=null){
	request.setAttribute("auth", auth);	
	od=new OrderDao().userOrder(auth.getId());
out.print(od);
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
List<Cart> cartProducts = null;
if (cart_list != null) {
	IProduitDao metier = new ProduitDaoImp();
	cartProducts = metier.getCartProducts(cart_list);
	request.setAttribute("lc", cartProducts);

}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Orders</title>
<%@ include file="includes/header.jsp" %>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

	<div class="container">
		<div class="card-header py-3"> All orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (od != null) {
					for (Order o : od) {
				%>
				<tr>
					<td><%=o.getO_date() %></td>
					<td><%=o.getName() %></td>
					<td><%=o.getCategory() %></td>
					<td><%=o.getO_quantity()%></td>					
					<td><%=o.getPrice() %></td>
					<td>
					</td>
					<td><a href="cancel_order.php?id=<%=o.getOid() %>" class="btn btn-sm btn-danger">Cancel</a></td>
				</tr>
				<%
				}
				}
				%>
<%@ include file="includes/footer.jsp" %>
</body>
</html>