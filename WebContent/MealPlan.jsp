<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="model.UserBean" scope="application"/>
<jsp:setProperty name="uBean" property="*"/>
<html>
<head>
	<!-- CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/datepicker.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/MealPlan.css" />
	<!-- JS -->
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/datepicker.js"></script>
	<script type="text/javascript" src="js/eye.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/layout.js?ver=1.0.2"></script>
	<script src="js/jquery.1.9.1.min.js"></script>
	<script src="js/myfunctions.js"></script>
	<script type="text/javascript">
	function sendAjax() {
 
    // get inputs
    
    var string = $('#autocomplete').val();
    if(string.length >2){
        $("option").remove();
        $.ajax({
            url: "${pageContext.request.contextPath}/MealPlanServlet",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(string),
            contentType: 'application/json',
            mimeType: 'application/json',
            
            success: function (data) {
                var setOfSuggestions = "";
                
                $.each(data, function (index, sugg) {
                    setOfSuggestions+= '<tr onClick="focused(this)" class=""><td>'+sugg+'</td></tr>';
                });
                document.getElementById('food_suggestions').innerHTML = setOfSuggestions;

            },
            error:function(data,status,er) {
                alert("error: "+data+" status: "+status+" er:"+er);
            }
        });
    }
}

function focused(c){
	 $(c).addClass("selected").siblings().removeClass('selected'); 
}



$('.ok').on('click', function(e){
    document.getElementById('tbody').innerHTML += "<tr><td>"+$("#food_suggestions td.selected").html()+"</td><tr>"+$("#amount").html()+"</td></tr>"
   
});
</script>

				<title>Personal Profile Page - Diet Patcher</title>
			</head>
			<body>

				<div id="adder" class="panel panel-primary">
					<input type="text" name="food" id="autocomplete" class="form-control" placeholder="Enter Food name" onkeyup="sendAjax()" >
					<div id="food_suggestions_div">
						<table class="table table-hover" id="food_suggestions">

						</table>
					</div>
					<input id="amount" type="number" placeholder="100"></input>g
									<br><br>
									<a class="btn btn-info ok" href="#header" role="button">Add!</a><br>
				</div>




				<div id="header">
					<img src="resources/DietPatcherIco.png" />
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
								<a class="btn btn-info" id="addFood" role="button" href="#adder">Choose a food to add to your meal plan!</a>
								<!--
								<div id="dishAdd">Choose a dish to add to your meal plan!<br>
									<select>
										<option value="null"></option>
										<option value="Pasta al pomodoro">Pasta al pomodoro</option>
										<option value="Torta salata xyz">Torta salata xyz</option>
										<option value="Spuntino">Spuntino</option>
									</select><br> <br>
									<a class="btn btn-info" href="#" role="button">Add!</a><br>
								</div>
							-->
							</div>
							
							<table class="table">
								<caption></caption>
								<thead>
									<tr>
										<th>What</th>
										<th>How much</th>
									</tr>
								</thead>
								<tbody id="tbody">

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