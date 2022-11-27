<nav class="navbar navbar-expand-lg navbar-light bg-light">
<div class="container">
  <a class="navbar-brand" href="index.php">E-Commerce Shopping Cart </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav ml-auto">
      <li class="nav-item active">
        <a class="nav-link" href="index.php">Acceuil </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="cart.jsp">Cart<span class="badge badge-danger px-1">${cart_list.size() }</span></a>
      </li>
      <%if(auth !=null){%>
      <li class="nav-item">
        <a class="nav-link" href="checkout.php">Orders</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="logout.php" >Logout</a>
      </li>
      <li class="nav-item">
       <h6> <a class="nav-link" href="logout.php" >Bienvenu ${auth.nom }</a></h6> 
      </li>
      <%}else{ %>
      <li class="nav-item">
        <a class="nav-link" href="login.php" >Login</a>
      </li>
      <%}%>
      
      
   		
     </ul>
  </div>
  </div>
</nav>

<!-- <span class="sr-only">(current)</span> -->