<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="controller.UserBean" scope="application"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="Profile.css"></link>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/datepicker.css">
		<script type="text/javascript" src="js/bootstrap-datepicker.js"></script>
		
		<title>Personal Profile Page - Diet Patcher</title>
	</head>
	<body>
		<div id="header">
			<img src="DietPatcherIco.png" />
			<b>Diet Patcher</b>
			<a href=""> Logout </a> 
			<a> / </a>
			<a href="">Profile </a>
		</div>
		<div id="container">
			<div id="title">
				
				<div class="well">
					  <div class="input-append date" id="dp3" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
						<input class="span2" size="16" type="text" value="12-02-2012" readonly>
						<span class="add-on"><i class="icon-calendar"></i></span>
					  </div>
		        </div>
				<h1>Meal Plan for the day </h1>
			</div>
			<div id="main">


			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>