<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="controller.UserBean" scope="session"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="LoginForm.css"></link>
		<title>Login</title>
	</head>
	<body>
		
		<div id="header">
			<img src="DietPatcherIco.png" />
			<b>Diet Patcher</b>
			<form class="form-class" id="login" method="POST"> <!-- insert action and method -->
				<button type="submit" class="btn btn-primary">Login</button>
				<div class="form-group">
					<input type="text" class="form-control" id="inputUserNameLogin" placeholder="User Name">
					<input type="password" class="form-control" id="inputPassword" placeholder="Password">
				</div>
			</form>
		</div>
	
	
		<div id="main">
			<form class="form-class" id="subscribe" method="POST"> <!-- insert action and method -->
			<h2 align="center">Subscribe</h2><br>
				<div class="form-group">
					<label for="inputUserNameReg">User name: </label>
					<input type="text" class="form-control" id="inputUserNameReg" placeholder="Write here the user name">
				</div>
				<div class="form-group">
					<label for="subPassword">Password: </label>
					<input type="password" class="form-control" id="inputPassword" placeholder="Write here the password">
				</div>
				<div class="form-group">
					<label for="inputPasswordAgain">Confirm password: </label>
					<input type="password" class="form-control" id="inputPasswordAgain" placeholder="write here the password again">
				</div>
				<div class="form-group">
					<label for="inputEmail">Email address: </label>
					<input type="email" class="form-control" id="inputEmail" placeholder="Write your Email">
				</div>
				<button type="submit" class="btn btn-primary">Subscribe</button>
			</form>
		</div>
		
		<div id="footer">
				<h4>Diet Patcher</h4>
               Copyright © Erica Tomaselli & Stanko Franz Ovkaric 2015 All rights reserved. 
        </div>
	</body>
</html>