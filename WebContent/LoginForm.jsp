<!DOCTYPE HTML>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="LoginForm.css"></link>
		<title>Login</title>
	</head>
	<body>
		
		<div id="header">
		
		</div>
	
	
		<div id="main">
			<form class="form-class" id="login"> <!-- insert action and method -->
			<h1 align="center">Login</h1>
				<div class="form-group">
					<label for="inputUserNameLogin">User name: </label>
					<input type="text" class="form-control" id="inputUserNameLogin" placeholder="Write here the user name">
				</div>
				
				<div class="form-group">
					<label for="inputPassword">Password: </label>
					<input type="password" class="form-control" id="inputPassword" placeholder="Write here the password">
				</div>
	
				<button type="submit" class="btn btn-primary">Login</button>
			</form>
			
			
			
			<form class="form-class" id="subscribe"> <!-- insert action and method -->
			<h1 align="center">Subscribe</h1>
				<div class="form-group">
					<label for="inputEmail">Email address: </label>
					<input type="email" class="form-control" id="inputEmail" placeholder="Write your Email">
				</div>
				<div class="form-group">
					<label for="inputUserNameReg">User name: </label>
					<input type="text" class="form-control" id="inputUserNameReg" placeholder="Write here the user name">
				</div>
				<div class="form-group">
					<label for="inputPassword">Password: </label>
					<input type="password" class="form-control" id="inputPassword" placeholder="Write here the password">
				</div>
				<div class="form-group">
					<label for="inputPasswordAgain">Confirm password: </label>
					<input type="password" class="form-control" id="inputPasswordAgain" placeholder="write here the password again">
				</div>
				<button type="submit" class="btn btn-primary">Subscribe</button>
			</form>
		</div>
	</body>
</html>