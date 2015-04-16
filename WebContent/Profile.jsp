<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="controller.UserBean" scope="application"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="Profile.css"></link>
		<title>Personal Profile Page - Diet Patcher</title>
	</head>
	<body>
		<div id="container">
			<div id="header">
				<h1>#UserName's Profile</h1>
			</div>
			<div id="main">
				<h2> Information about your account </h2>
				<form class="form-class" id="login" method="POST"> <!-- insert action and method -->
					<div class="form-group">
						User Name <input type="text" class="form-control" id="profileUserName" placeholder="User Name">
						Password<input type="password" class="form-control" id="profilePassword" placeholder="Password">
						Email<input type="email" class="form-control" id="profileEmail" placeholder="E-mail">
					</div>
					<h2> Information about you </h2>
					<div class="form-group">
						Gender <input type="text" class="form-control" id="profileGender" placeholder="M (Male), F (Female), O (Other)"><br>
						Age <input type="number" class="form-control" id="profileAge" placeholder="40">Years old <br>
						Height <input type="number" class="form-control" id="profileHeight" placeholder="170">cm (centimeters)<br>
						Weight <input type="number" class="form-control" id="profileWeight" placeholder="70">kg (kilograms)<br>
						Waist <input type="number" class="form-control" id="profileWaist" placeholder="60">cm<br>
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