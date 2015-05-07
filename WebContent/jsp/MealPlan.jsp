<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="controller.UserBean" scope="application"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
<head>
	<!-- CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="../css/datepicker.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="../css/MealPlan.css" />
	<!-- JS -->
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/datepicker.js"></script>
	<script type="text/javascript" src="../js/eye.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../js/layout.js?ver=1.0.2"></script>
	<script type="text/javascript">



	function dtval(d,e) {

		var pK = e ? e.which : window.event.keyCode;
		if (pK == 8) {d.value = substr(0,d.value.length-1); return;}
		var dt = d.value;
		var da = dt.split('/');
		for (var a = 0; a < da.length; a++) {if (da[a] != +da[a]) da[a] = da[a].substr(0,da[a].length-1);}
			if (da[0] > 12) {da[1] = da[0].substr(da[0].length-1,1);da[0] = '0'+da[0].substr(0,da[0].length-1);}
		if (da[1] > 31) {da[2] = da[1].substr(da[1].length-1,1);da[1] = '0'+da[1].substr(0,da[1].length-1);}
		if (da[2] > 9999) da[1] = da[2].substr(0,da[2].length-1);
		dt = da.join('/');
		if (dt.length == 2  || dt.length == 5) dt += '/';
		d.value = dt;

		var today = new Date();
		var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();
if(dd<10) {dd='0'+dd} 
	if(mm<10) {mm='0'+mm} 
		today = mm + '/' + dd + '/'+yyyy;
	if (dt.length == 0) dt += today;
	d.value = dt;
}
</script>

<script type="text/javascript">

$('#inputDate').DatePicker({
	format:"Y/m/d",
	date: $('#inputDate').val(),
	current: $('#inputDate').val(),
	starts: 1,
	position: 'r',
	onBeforeShow: function(){
		$('#inputDate').DatePickerSetDate($('#inputDate').val(), true);
	},
	onChange: function(formated, dates){
		$('#inputDate').val(formated);
		$('#inputDate').DatePickerHide();
	}
});


function dtval(d,e) {
	var pK = e ? e.which : window.event.keyCode;
	if (pK == 8) {d.value = substr(0,d.value.length-1); return;}
	var dt = d.value;
	var da = dt.split('/');
	for (var a = 0; a < da.length; a++) {if (da[a] != +da[a]) da[a] = da[a].substr(0,da[a].length-1);}
		if (da[0] > 12) {da[1] = da[0].substr(da[0].length-1,1);da[0] = '0'+da[0].substr(0,da[0].length-1);}
	if (da[1] > 31) {da[2] = da[1].substr(da[1].length-1,1);da[1] = '0'+da[1].substr(0,da[1].length-1);}
	if (da[2] > 9999) da[1] = da[2].substr(0,da[2].length-1);
	dt = da.join('/');
	if (dt.length == 2 || dt.length == 5) dt += '/';
	d.value = dt;

	var today = new Date();
	var dd = today.getDate();
				var mm = today.getMonth()+1; //January is 0!
				var yyyy = today.getFullYear();

				if(dd<10) {dd='0'+dd} 
					if(mm<10) {mm='0'+mm} 
						today = mm+'/'+dd+'/'+yyyy;
					if(d.value ==""){d.value=today;}
				}
				</script>
				<title>Personal Profile Page - Diet Patcher</title>
			</head>
			<body>
				<div id="header">
					<img src="../resources/DietPatcherIco.png" />
					<b>Diet Patcher</b>
					<a href=""> Logout </a> 
					<a> / </a>
					<a href="">Profile </a>
				</div>
				<div id="container">
					<div id="title">
						<h2>Meal Plan for the day 		
							<input class="inputDate" id="inputDate" placeholder="mm/dd/yyyy" size="10" maxlength="10" onkeyup="dtval(this,event)" onfocus="dtval(this,event)"/>	
						</h2>
					</div>

					<div id="meal_plan_table" class="panel panel-primary">
						<div class="panel-heading">Contents table</div>
						<div class="panel-body">
							<div id="addModule">
								<div id="foodAdd">Choose a food to add to your meal plan!<br>
									<select>
										<option value="null"> </option>
										<option value="Apple">Apple</option>
										<option value="Orange">Orange</option>
										<option value="Bread">Bread</option>
										<option value="Honey">Honey</option>
									</select> 
									<input type="number"placeholder="100"></input>
									<select>
										<option value="null"> </option>
										<option value="g">g</option>
										<option value="kg">kg</option>
										<option value="lbs">lbs</option>
										<option value="st">st</option>
									</select> 
									<br><br>
									<a class="btn btn-info" href="#" role="button">Add!</a><br>
								</div>
								<div id="dishAdd">Choose a dish to add to your meal plan!<br>
									<select>
										<option value="null"></option>
										<option value="Pasta al pomodoro">Pasta al pomodoro</option>
										<option value="Torta salata xyz">Torta salata xyz</option>
										<option value="Spuntino">Spuntino</option>
									</select><br> <br>
									<a class="btn btn-info" href="#" role="button">Add!</a><br>
								</div>
							</div>
							
							<table class="table">
								<caption></caption>
								<thead>
									<tr>
										<th>What</th>
										<th>How much</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>Honey</td>
										<td>100g</td>
									</tr>
									<tr>
										<td>Apple</td>
										<td>100g</td>
									</tr>
									<tr>
										<td>Orange</td>
										<td>100g</td>
									</tr>
									<tr>
										<td>Cookie</td>
										<td>100g</td>
									</tr>
									<tr>
										<td>Pineapple</td>
										<td>100g</td>
									</tr>

								</tbody>

							</table>
						</div>
					</div>
					<div id="recommender" class="panel panel-primary">
						<div class="panel-heading">Recommendations</div>
						<div class="panel-body">
							Here are some awesome Recommendations to Patch your diet! <br>
							Please select a nutrient you lack in: 
							<select>
								<option value="null"></option>
								<option value="">Vitamin C</option>
								<option value="">Magnesium</option>
							</select><br><br>
							In the past you have eaten <span><b>Banana</b></span>, you could eat that again!  <a class="btn btn-info" href="#" role="button">More</a> <br><br>
							Other people with the tag <span><b>Vegan</b></span> ave eaten <span><b>Potassium pills</b></span>!  <a class="btn btn-info" href="#" role="button">More</a> <br><br>
							If you want something new, you could try <span><b>Beans</b></span>!  <a class="btn btn-info" href="#" role="button">More</a> <br><br>
						</div>
					</div>
					<div id="stats" class="panel panel-primary">
						<div class="panel-heading">Statistics</div>
						<div class="panel-body">
							<div class="nutrientStat">
								Potassium:<div class="progress"><div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 45%">45%</div></div>
							</div>
							<div class="nutrientStat">
								Calcium:<div class="progress"><div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 30%">30%</div></div>
							</div>
							<div class="nutrientStat">
								Vitamin A:<div class="progress"><div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">60%</div></div>
							</div>
							<div class="nutrientStat">
								Vitamin C:<div class="progress"><div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">100%</div></div>
							</div>
						</div>
					</div>

					<div id="main">


					</div>
					<div id="footer">
					</div>
				</div>
			</body>
			</html>