<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="controller.UserBean" scope="application"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="../css/Profile.css"></link>
		<title>Personal Profile Page - Diet Patcher</title>
	</head>
	<body>
		<div id="header">
			<img src="../resources/DietPatcherIco.png" />
			<b>Diet Patcher</b>
			<a href=""> Logout </a> 
			<a> / </a>
			<a href="">Meal Planner </a>
		</div>
		<div id="container">
			<div id="title">
				<h1>#UserName's Profile</h1>
			</div>
			<div id="main">
				<h2> Information about your account </h2>
				<form class="form-class" id="login" method="POST"> <!-- insert action and method -->
					<div class="form-group" id="accountInfo">

						<div class="input-group">
					      <div class="input-group-addon">User Name</div>
					      <input type="text" class="form-control" id="profileUserName" placeholder="PatchedDieter69">
					    </div>
 
 						<div class="input-group">
					      <div class="input-group-addon">Password</div>
					      <input type="password" class="form-control" id="profilePassword" placeholder="$uP3R5EkR3T_P4$$w0rD">
					    </div>

					    <div class="input-group">
					      <div class="input-group-addon">Email</div>
					      <input type="email" class="form-control" id="profileEmail" placeholder="your@email.here">
					    </div>

						
						
					</div>
					<h2> Information about you </h2>
					<div class="form-group" id="userInfo">
						 
						 <div class="input-group">
					      <div class="input-group-addon">Gender</div>
					      <input type="text" class="form-control" id="profileGender" placeholder="M (Male), F (Female), O (Other)"><br>
					    </div>
						<div class="input-group">
					      <div class="input-group-addon">Age</div>
					      <input type="number" min="0" class="form-control" id="profileAge" placeholder="40">
					      <div class="input-group-addon">y/o</div>
					    </div>
						<div class="input-group">
					      <div class="input-group-addon">Height</div>
					      <input type="number" min="0" class="form-control" id="profileHeight" placeholder="170">
					      <div class="input-group-addon">cm</div>
					    </div>
					    <div class="input-group">
					      <div class="input-group-addon">Weight</div>
					      <input type="number" min="0" class="form-control" id="profileWeight" placeholder="70">
					      <div class="input-group-addon">kg</div>
					    </div>
					    <div class="input-group">
					      <div class="input-group-addon">Waist</div>
					      <input type="number" min="0" class="form-control" id="profileWaist" placeholder="60">
					      <div class="input-group-addon">cm</div>
					    </div>

					</div>
					<h2> Some useful information </h2>
							BMI <span id="BMI"></span><br>
							BAS <span id="BAS"></span><br>
					<button type="submit" class="btn btn-primary">Save Changes</button>
				</form>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>