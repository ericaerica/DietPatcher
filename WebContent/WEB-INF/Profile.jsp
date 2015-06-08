<!DOCTYPE HTML>
<!-- <jsp:useBean id="uBean" class="model.UserBean" scope="session"/> -->
<!-- <jsp:setProperty name="uBean" property="*"/> -->
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/Profile.css"></link>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<title>${uBean.username}'s Profile Page - Diet Patcher</title>
		<script type="text/javascript">
			$(document).ready(function() {
			var checkboxes = document.getElementsByName('tag');
			var s = "${uBean.tags}";
		  for (var i=0;i<checkboxes.length;i++) {
				if (s.indexOf(checkboxes[i].value) >= 0)
					checkboxes[i].checked = true;
		  }
			});

		

			function refresh(){
				var result = document.getElementById("profileWeight").value / ((document.getElementById("profileHeight")
						.value/100)*(document.getElementById("profileHeight").value/100));
				if (result<17.5 || result>30){document.getElementById("BMI").style.color = "#F00";}
				else if (result<19 || result>25){document.getElementById("BMI").style.color = "#fA0";}
				else{document.getElementById("BMI").style.color = "#090";}
				document.getElementById("BMI").innerHTML = (result).toFixed(2)


				var bai = (document.getElementById("profileWaist").value / Math.pow(((document.getElementById("profileHeight").value)/100),1.5))-18;
				var gender = document.getElementById("profileGender").value;
				var age = document.getElementById("profileAge").value;
				var c = "";
				if(gender=="M"||gender=="m"||gender=="male"||gender=="Male"){
					if(20<=age&&age<40){
						if(bai<8){c="underweight";document.getElementById("BAI").style.color = "#F00";}
						else if(8<=bai&&bai<21){c="healthy";document.getElementById("BAI").style.color = "#090";}
						else if(21<=bai&&bai<26){c="overweight";document.getElementById("BAI").style.color = "#fA0";}
						else if(bai>=26){c="obese";document.getElementById("BAI").style.color = "#F00";}
					}
					else if(40<=age&&age<60){
						if(bai<11){c="underweight";document.getElementById("BAI").style.color = "#F00";}
						else if(11<=bai&&bai<23){c="healthy";document.getElementById("BAI").style.color = "#090";}
						else if(23<=bai&&bai<29){c="overweight";document.getElementById("BAI").style.color = "#fA0";}
						else if(bai>=29){c="obese";document.getElementById("BAI").style.color = "#F00";}
					}
					else if(60<=age&&age<80){
						if(bai<13){c="underweight";document.getElementById("BAI").style.color = "#F00";}
						else if(13<=bai&&bai<25){c="healthy";document.getElementById("BAI").style.color = "#090";}
						else if(25<=bai&&bai<31){c="overweight";document.getElementById("BAI").style.color = "#fA0";}
						else if(bai>=31){c="obese";document.getElementById("BAI").style.color = "#F00";}
					}
				}else if(gender=="F"||gender=="f"||gender=="female"||gender=="Female"){
					if(20<=age&&age<40){
						if(bai<21){c="underweight";document.getElementById("BAI").style.color = "#F00";}
						else if(21<=bai&&bai<33){c="healthy";document.getElementById("BAI").style.color = "#090";}
						else if(33<=bai&&bai<39){c="overweight";document.getElementById("BAI").style.color = "#fA0";}
						else if(bai>=39){c="obese";document.getElementById("BAI").style.color = "#F00";}
					}
					else if(40<=age&&age<60){
						if(bai<23){c="underweight";document.getElementById("BAI").style.color = "#F00";}
						else if(23<=bai&&bai<35){c="healthy";document.getElementById("BAI").style.color = "#090";}
						else if(35<=bai&&bai<41){c="overweight";document.getElementById("BAI").style.color = "#fA0";}
						else if(bai>=41){c="obese";document.getElementById("BAI").style.color = "#F00";}
					}
					else if(60<=age&&age<80){
						if(bai<25){c="underweight";document.getElementById("BAI").style.color = "#F00";}
						else if(25<=bai&&bai<38){c="healthy";document.getElementById("BAI").style.color = "#090";}
						else if(38<=bai&&bai<43){c="overweight";document.getElementById("BAI").style.color = "#fA0";}
						else if(bai>=43){c="obese";document.getElementById("BAI").style.color = "#F00";}
					}
				}
				document.getElementById("BAI").innerHTML = (bai).toFixed(2)+"% of fat ("+c+")";
			};


			function allowOnlyNumbers(){
				$(this).keydown(function (e) {
			        // Allow: backspace, delete, tab, escape, enter and .
			        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
			             // Allow: Ctrl+A, Command+A
			            (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || 
			             // Allow: home, end, left, right, down, up
			            (e.keyCode >= 35 && e.keyCode <= 40)) {
			                 // let it happen, don't do anything
			                 return;
			        }
			        // Ensure that it is a number and stop the keypress
			        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
			            e.preventDefault();
			        }
			    });

			}
		</script>
	</head>
	<body>
		<div id="header">
			<img src="../resources/DietPatcherIco.png" />
			<b>Diet Patcher</b>
			
				<div id="menu" class="btn-group" role="group" aria-label="...">
					<form id="menu"action="Logout" method="GET">
						<button type="submit" class="btn btn-default btn-lg">
						  	<span class="glyphicon glyphicon-off" aria-hidden="true"></span> Logout
						</button>
					</form>
					<form id="menu" action="Redirect" method="GET">
						<input type="hidden" class="form-control" name="page" value="MealPlanner" >
						<button type="submit" class="btn btn-default btn-lg">
							<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Meal Planner 
						</button>
					</form>
				</div>
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
					      <input type="text" class="form-control" id="profileUserName" value="${uBean.username}" disabled><input type="hidden" class="form-control" name="profileUserName" id="profileUserName" value="${uBean.username}" >
					    </div>
 
 						<div class="input-group">
					      <div class="input-group-addon">Password</div>
					      <input type="text" class="form-control" name="profilePassword" id="profilePassword" value="${uBean.password}">
					    </div>

					    <div class="input-group">
					      <div class="input-group-addon">Email</div>
					      <input type="email" class="form-control" id="profileEmail" value="${uBean.email}" disabled >
					      <input type="hidden" class="form-control" name="profileEmail" id="profileEmail" value="${uBean.email}"  >
					    </div>
					</div>
					
					<h2> Information about you </h2>
					<div class="form-group" id="userInfo">
						 
						 <div class="input-group">
					      <div class="input-group-addon">Gender</div>
					      <select class="form-control" name="profileGender" id="profileGender" value="${uBean.gender}">
							  <option value="m">Male</option>
							  <option value="f">Female</option>
							</select>
					    </div>
						<div class="input-group">
					      <div class="input-group-addon">Age</div>
					      <input type="number" min="0" onkeydown="allowOnlyNumbers();" class="form-control" name="profileAge" id="profileAge" value="${uBean.age}" placeholder="40">
					      <div class="input-group-addon">y/o</div>
					    </div>
						<div class="input-group">
					      <div class="input-group-addon">Height</div>
					      <input type="number" min="0" onkeydown="allowOnlyNumbers();" class="form-control" name="profileHeight" id="profileHeight" value="${uBean.height}" placeholder="170">
					      <div class="input-group-addon">cm</div>
					    </div>
					    <div class="input-group">
					      <div class="input-group-addon">Weight</div>
					      <input type="number" min="0" onkeydown="allowOnlyNumbers();" class="form-control" name="profileWeight" id="profileWeight" value="${uBean.weight}" placeholder="70">
					      <div class="input-group-addon">kg</div>
					    </div>
					    <div class="input-group">
					      <div class="input-group-addon">Waist</div>
					      <input type="number" min="0" onkeydown="allowOnlyNumbers();" class="form-control" name="profileWaist" id="profileWaist" value="${uBean.waist}" placeholder="60">
					      <div class="input-group-addon">cm</div>
					    </div>
					</div>
					
					<h2> Choose your tags </h2>
					<div class="form-group" id="tags">
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Vegan"> Vegan
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Lacto-ovo Vegetarian"> Lacto-ovo Vegetarian
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Frutarian"> Frutarian
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Raw Vegan"> Raw Vegan
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Raw Till 4 Vegan"> Raw Till 4 Vegan
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Paleo"> Paleo
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Pescetarian"> Pescetarian
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Pollotarian"> Pollotarian
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Mediterranean"> Mediterranean
									    </label>
									  </div>
							       <div class="checkbox">
									    <label>
									      <input type="checkbox"name="tag" value="Omnivore"> Omnivore
									    </label>
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