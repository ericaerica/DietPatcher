<!DOCTYPE HTML>
<jsp:useBean id="uBean" class="model.UserBean" scope="session"/>
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

	$(document).ready(function() {
			dtval(document.getElementById("inputDate"),null);
			});



	function getRSAndStats(){
	var string = $('#inputDate').val();
    if(string.length >2){
        $("#statistics div").remove();
        $.ajax({
            url: "${pageContext.request.contextPath}/MealPlanRSAndStatistics",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(string),
            contentType: 'application/json',
            mimeType: 'application/json',
            
                  
            success: function (data) {
                document.getElementById('statistics').innerHTML = data;

            },
            error:function(data,status,er) {
                alert("error: "+data+" status: "+status+" er:"+er);
            }
        });
    }
}

	function sendAjax(){
		var string = $('#autocomplete').val();
    if(string.length >2){
        $("option").remove();
        $.ajax({
            url: "${pageContext.request.contextPath}/MealPlanFoodChooserServlet",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(string),
            contentType: 'application/json',
            mimeType: 'application/json',
            
            success: function (data) {
                var setOfSuggestions = "";
                
                $.each(data, function (index, sugg) {
                    setOfSuggestions+= '<tr onClick="focused(this)" class=""><td>'+sugg[1]+'<input name="food_name" type="hidden" value="'+sugg[0]+'"></td></tr>';
                });
                document.getElementById('food_suggestions').innerHTML = setOfSuggestions;

            },
            error:function(data,status,er) {
                alert("error: "+data+" status: "+status+" er:"+er);
            }
        });
    }
	}

	function sendDate() {
 
    // get inputs
    
    var string = $('#inputDate').val();
    if(string.length >2){
        $(".table td").remove();
        $.ajax({
            url: "${pageContext.request.contextPath}/MealPlanDateServlet",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(string),
            contentType: 'application/json',
            mimeType: 'application/json',
            
            success: function (data) {
                document.getElementById('tbody').innerHTML = data+"";
                getRSAndStats();
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


function ok(){
	document.getElementById('tbody').innerHTML +=  "<tr>"+document.getElementsByClassName('selected')[0].innerHTML+'<td>'+document.getElementById('amount').value + '<input name="food_amount" type="hidden" value="'+document.getElementById('amount').value+'" readonly></td></tr>';

}
</script>

				<title>Meal Plan Manager - Diet Patcher</title>
			</head>
			<body>
				<div id="black">
					<div id="adder" class="panel panel-primary">
						<input type="text" name="food" id="autocomplete" class="form-control" placeholder="Enter Food name" onkeyup="sendAjax();s" >
						<div id="food_suggestions_div">
							<table class="table table-hover" id="food_suggestions">

							</table>
						</div><div class="input-group">
						<input name="amount" class="form-control" id="amount" type="text" placeholder="100"></input><span class="input-group-addon">g</span></div>
										<br><br>
										<a class="btn btn-info" href="#header" onClick="ok()" role="button">Add!</a>
										<a class="btn btn-default" href="#header" role="button">Cancel</a><br>
					</div>
				</div>
				<div id="header">
					<img src="resources/DietPatcherIco.png" />
					<b>Diet Patcher</b>
					<div id="menu" class="btn-group" role="group" aria-label="...">
						<form id="menu"action="Logout" method="GET">
							<button type="submit" class="btn btn-default btn-lg">
							  	<span class="glyphicon glyphicon-off" aria-hidden="true"></span> Logout
							</button>
						</form>
						<form id="menu" action="Redirect" method="GET">
							<input type="hidden" class="form-control" name="page" value="Profile" >
							<button type="submit" class="btn btn-default btn-lg">
								<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Profile 
							</button>
						</form>
					</div>
					
				</div>
				<div id="container">
					<form class="form-class" id="addMealPlan" action="MealPlanAdderServlet" method="GET">	
					<div id="title">
						<h2>Meal Plan for the day 	
						
							<input class="inputDate" name="inputDate" id="inputDate" placeholder="mm/dd/yyyy" size="10" maxlength="10" onkeyup="dtval(this,event)" onfocus="dtval(this,event)"/>	
							<button type="button" class="btn btn-default" aria-label="Left Align" onClick="sendDate()">
	  							<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
							</button>
						</h2>
					</div>

					<div id="meal_plan_table" class="panel panel-primary">
						<div class="panel-heading">Contents table</div>
						<div class="panel-body">
							<div id="addModule">
								<a class="btn btn-info" id="addFood" role="button" href="#black">Choose a food to add to your meal plan!</a>
							</div>
							
							<table class="table">
								<caption></caption>
								<thead>
									<tr>
										<th>What</th>
										<th>How much (g)</th>
									</tr>
								</thead>
								<tbody id="tbody">

								</tbody>
							</table>
							<button type="submit" class="btn btn-primary">Save Meal Plan</button>
						</div>
					</div>
				</form>
					<div id="recommender_and_statistics" class="panel panel-primary">
						<div class="panel-heading">Recommendations and Statistics
						</div>
						
						<div class="panel-body" id="statistics">
						
						</div>
					</div>
					<div id="main">


					</div>
					<div id="footer">
					</div>
				</div>
			</body>
			</html>