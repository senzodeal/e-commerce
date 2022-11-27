<%@ include file="includes/header.jsp"%>
  <div class="container col-md-4 col-md-offset=2">
    <p></p>
   
  </div>


    <div>
      <div class="bg-light p-5 rounded">
        <div class="col-sm-8 mx-auto">
          <div class="panel panel-primary">
			   <div class="panel-heading">Modifier un produit</div>
			  	 <div class="panel-body">
				 	<form action="updateproduit.php" method="post">
						
						<div class="form-row">
						<div class="form-group col-md-6">
						      <label for="inputEmail4">ID</label>
						      <input type="text" name="id" value="${produit.id }" class="form-control" id="nom">
						    </div>
						    <div class="form-group col-md-6">
						      <label for="inputEmail4">nom</label>
						      <input type="text" name="nom" value="${produit.nom }" class="form-control" id="nom">
						    </div>
						    <div class="form-group col-md-6">
						      <label for="inputPassword4">Designation</label>
						      <input type="text" name="designation" value="${produit.designation}" class="form-control" id="designation">
						    </div>
						  </div>
						  <div class="form-row">
						    <div class="form-group col-md-2">
						    <label for="inputAddress">Prix</label>
						    <input type="text" name="prix" value="${produit.prix }" class="form-control" id="prix" >
						  </div>
						  </div>
						  <div class="form-row">
						    <div class="form-group col-md-2">
						    <label for="inputAddress">Quantité</label>
						    <input type="text" name="quantite" value="${produit.quantite }" class="form-control" id="quantite" >
						  </div>
						  </div>
						  <div class="form-row">
						    <div class="form-group col-md-6">
						    <label for="inputAddress2">Image</label>
						    <input type="file" class="form-control" id="image" >
						  </div>
						  </div>
						  <div class="form-row">
						    <div class="form-group col-md-6">
						      <label for="date">Date</label>
						      <input type="datetime-local" name="date" class="form-control" id="date">
						    </div>					    
						  </div>
						  		<br/>			   
					   <button type="submit" class="btn btn-primary">Modifier</button>
					   
		   			</form>
	   			</div>
  		 </div><!-- fin de la recherche -->
  		 <br/>
		<table class="table">
		  <thead class="thead-dark">
		    <tr>
		      <th scope="col">ID</th>
		      <th scope="col">NOM</th>
		      <th scope="col">DESIGNATION</th>
		      <th scope="col">PRIX</th>
		       <th scope="col">QUANTITE</th>
		      <th scope="col">DATE</th>
		      <th scope="col">DATE DE MISE A JOUR</th>
		    </tr>
		  </thead>
		  <tbody>
		   
		    <c:forEach items="${model.produits }" var="p">
		     <tr> 
		    <td>${p.id }</td>
		      <td>${p.nom }</td>
		      <td>${p.designation }</td>
		      <td>${p.prix }</td>
		      <td>${p.quantite }</td>
		      <td>${p.date }</td>
		      <td>${p.date_update }</td>
		     </tr> 
		    </c:forEach>      
		      
		  </tbody>
		</table>

</p>
        </div>
      </div>
    </div>
  </div>
</main>
    <script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>