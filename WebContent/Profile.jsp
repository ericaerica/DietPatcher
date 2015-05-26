<!DOCTYPE HTML>
<!-- <jsp:useBean id="uBean" class="model.UserBean" scope="session"/> -->
<!-- <jsp:setProperty name="uBean" property="*"/> -->
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/Profile.css"></link>
		<title>${uBean.username}'s Profile Page - Diet Patcher</title>
		<script type="text/javascript">
			function refresh(){
				var result = document.getElementById("profileWeight").value / ((document.getElementById("profileHeight")
						.value/100)*(document.getElementById("profileHeight").value/100));
				if (result<17.5 || result>30){document.getElementById("BMI").style.color = "#F00";}
				else if (result<19 || result>25){document.getElementById("BMI").style.color = "#fA0";}
				else{document.getElementById("BMI").style.color = "#090";}
				document.getElementById("BMI").innerHTML = (result).toFixed(2)


				var result1 = document.getElementById("profileWaist").value / (document.getElementById("profileHeight")
						 .value * sqrt(document.getElementById("height").value));
				document.getElementById("BAI").innerHTML = result1;
			}

			
		</script>
	</head>
	<body>
		<div id="header">
			<img src="resources/DietPatcherIco.png" />
			<b>Diet Patcher</b>
			<a href=""> Logout </a> 
			<a> / </a>
			<a href="">Meal Planner </a>
		</div>
		<div id="container">
			<div id="title">
				<h1>${uBean.username}'s Profile</h1>
			</div>
			<div id="main">
				<h2> Information about your account </h2>
				<form class="form-class" id="Profile" action="ProfileServlet" method="GET">
				
					<div class="form-group" id="accountInfo">
						<div class="input-group">
					      <div class="input-group-addon">User Name</div>
					      <input type="text" class="form-control" name="profileUserName" id="profileUserName" value="${uBean.username}" >
					    </div>
 
 						<div class="input-group">
					      <div class="input-group-addon">Password</div>
					      <input type="text" class="form-control" name="profilePassword" id="profilePassword" value="${uBean.password}">
					    </div>

					    <div class="input-group">
					      <div class="input-group-addon">Email</div>
					      <input type="email" class="form-control" name="profileEmail" id="profileEmail" value="${uBean.email}" >
					    </div>
					</div>
					
					<h2> Information about you </h2>
					<div class="form-group" id="userInfo">
						 
						 <div class="input-group">
					      <div class="input-group-addon">Gender</div>
					      <input type="text" class="form-control" name="profileGender" id="profileGender" value="${uBean.gender}" placeholder="M (Male), F (Female), O (Other)"><br>
					    </div>
						<div class="input-group">
					      <div class="input-group-addon">Age</div>
					      <input type="text" min="0" class="form-control" name="profileAge" id="profileAge" value="${uBean.age}" placeholder="40">
					      <div class="input-group-addon">y/o</div>
					    </div>
						<div class="input-group">
					      <div class="input-group-addon">Height</div>
					      <input type="text" min="0" class="form-control" name="profileHeight" id="profileHeight" value="${uBean.height}" placeholder="170">
					      <div class="input-group-addon">cm</div>
					    </div>
					    <div class="input-group">
					      <div class="input-group-addon">Weight</div>
					      <input type="text" min="0" class="form-control" name="profileWeight" id="profileWeight" value="${uBean.weight}" placeholder="70">
					      <div class="input-group-addon">kg</div>
					    </div>
					    <div class="input-group">
					      <div class="input-group-addon">Waist</div>
					      <input type="text" min="0" class="form-control" name="profileWaist" id="profileWaist" value="${uBean.waist}" placeholder="60">
					      <div class="input-group-addon">cm</div>
					    </div>
					</div>
					
					<h2> Choose your tags </h2>
					<div class="form-group" id="tags">
					    <div class="row">
						    <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Vegan">
							      </span>
							      <input type="text" class="form-control" value="Vegan" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
							  <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Lacto-ovo Vegetarian">
							      </span>
							      <input type="text" class="form-control" value="Lacto-ovo Vegetarian" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
						</div>
						<div class="row">
						    <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Frutarian">
							      </span>
							      <input type="text" class="form-control" value="Frutarian" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
							  <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Raw Vegan">
							      </span>
							      <input type="text" class="form-control" value="Raw Vegan" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
						</div>
						<div class="row">
						    <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Raw Till 4 Vegan">
							      </span>
							      <input type="text" class="form-control" value="Raw Till 4 Vegan" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
							  <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Paleo">
							      </span>
							      <input type="text" class="form-control" value="Paleo" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
						</div>
						<div class="row">
						    <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Pescetarian">
							      </span>
							      <input type="text" class="form-control" value="Pescetarian" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
							  <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Pollotarian">
							      </span>
							      <input type="text" class="form-control" value="Pollotarian" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
						</div>
						<div class="row">
						    <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Mediterranean">
							      </span>
							      <input type="text" class="form-control" value="Mediterranean" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
							  <div class="col-lg-6">
							    <div class="input-group">
							      <span class="input-group-addon">
							        <input type="checkbox" name="tag" value="Omnivore">
							      </span>
							      <input type="text" class="form-control" value="Omnivore" disabled >
							    </div><!-- /input-group -->
							  </div><!-- /.col-lg-6 -->
						</div>
					</div>
					
					<h2> Some useful information 
						<button type="button" class="btn btn-default" aria-label="Left Align" onClick="refresh();">
	  						<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
						</button>
					</h2>
					Your BMI (body mass index)= <span id="BMI"></span><br><br>
					Your BAI (body adiposity index)= <span id="BAI"></span><br>
					<br><br>
					<button type="submit" class="btn btn-primary">Save Changes</button>
				</form>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>