<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="controller.UserBean" scope="application"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="LoginForm.css"></link>
		<title>Diet Patcher - Personal Profile Page</title>
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
						<input type="text" class="form-control" id="profile" placeholder="User Name">
						<input type="password" class="form-control" id="inputPassword" placeholder="Password">
						<input type="email" class="form-control" id="inputPassword" placeholder="Password">
					</div>
					<h2> Information about you </h2>
					
					<h2> Some useful information </h2>


					<button type="submit" class="btn btn-primary">Save Changes</button>
				</form>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>