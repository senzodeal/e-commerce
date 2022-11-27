<%@ include file="includes/header.jsp"%>
  <div class="container col-md-4 col-md-offset=2">
    <p></p>
   
  </div>


    <div>
      <div class="bg-light p-5 rounded">
        <div class="col-sm-8 mx-auto">
          <div class="panel panel-primary">
			   <div class="panel-heading">Recherche des produits</div>
			  	 <div class="panel-body">
				 	<form action="rechercher.php" method="get">
					   <label>Mot clé</label>
					   <input type="text" name="motcle" value="${model.motCle }"/>
					   <button type="submit" class="btn btn-primary">Chercher</button>
		   			</form>
	   			</div>
  		 </div><!-- fin de la recherche -->
		<table class="table">
		  <thead class="thead-dark">
		    <tr>
		      <th scope="col">ID</th>
		      <th scope="col">NOM</th>
		      <th scope="col">DESIGNATION</th>
		      <th scope="col">PRIX</th>
		       <th scope="col">QUANTITE</th>
		      <th scope="col">METTRE A JOUR</th>
		      <th scope="col">SUPRIMER</th>
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
		      <td><a onclick="return confirm('etes vous sur de vouloir suprimer')" href="deleteproduit.php?id=${p.id}">Suprimer</a>  </td>
		      <td><a href="editproduit.php?id=${p.id}">Mettre a jour</a>  </td>
		     </tr> 
		    </c:forEach>      
		      
		  </tbody>
		</table>

<br>

            <a class="btn btn-primary" href="../components/navbar/" role="button">View navbar docs &raquo;</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</main>
    <script src="js/bootstrap.bundle.min.js"></script>
</body>
</html>
