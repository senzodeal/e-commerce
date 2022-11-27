<%@page import="com.enzo.dao.ProduitDaoImp"%>
<%@page import="com.enzo.dao.IProduitDao"%>
<%@page import="java.util.List"%>
<%@page import="cn.metier.entites.Cart"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="cn.metier.entites.User"%>
<%
User auth= (User)request.getSession().getAttribute("auth"); 
if(auth !=null){
	request.setAttribute("auth", auth);	
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
<title>Insert title here</title>
<%@ include file="includes/header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<%@ include file="includes/navbar.jsp"%>
	<div class="container">
	<div class="card-header my-3">All products	</div>
	<div class="row">
	<c:forEach items="${model.produits }" var="p">
		<div class="col-md-3 my-3">		
			<div class="card w-100" style="width: 18rem;">
			  <img class="card-img-top" src="product-image/${p.image }" alt="Card image cap">
			  <div class="card-body">
			    <h6 class="card-title">${p.name }</h5>
			    <h5 class="card-title">$ ${p.price}</h5>
			    <h6 class="card-title">Category: ${p.category }</h5>
			    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
			     <div class="mt-3 d-flex justify-content-between">
			    <a href="cart.php?id=${p.id}" class="btn btn-primary btn-dark">Add to cart</a>
			     <a href="orders-now.php?quantity=1&id=${p.id}" class="btn btn-primary">Buy now</a>
			    </div>
			    
			  </div>
			</div>
		</div>	
		    </c:forEach>
			
			
		
	</div>
	</div>

<%@ include file="includes/footer.jsp"%>
</body>
</html>