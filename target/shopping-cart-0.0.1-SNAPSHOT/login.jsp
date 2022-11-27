<%@page import="com.enzo.dao.ProduitDaoImp"%>
<%@page import="com.enzo.dao.IProduitDao"%>
<%@page import="cn.metier.entites.Cart"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<title>Login</title>
<%@ include file="includes/header.jsp" %>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="container">
<div class="card  card-w-50 mx-auto md-5">
<div class="card-header text-center">User login</div>
<div class="card-body">
<form action="login.php" method="post">

<div class="form-group">
<label>Email address</label>
<input class="form-control" type="email" name="login-email" placeholder="Enter your email">
</div>
<div class="form-group">
<label>Password</label>
<input class="form-control" type="password" name="login-password" placeholder="Enter your password">
</div>
<div class="text-center">
<button class="btn btn-primary" type="submit" >Login</button>
</div>

</form>
</div>
</div>
</div> 

<%@ include file="includes/footer.jsp" %>
</body>
</html>