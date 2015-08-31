# DietPatcher
Authored by Erica Tomaselli & Stanko Franz Ovkaric
Free University of Bozen-Bolzano

##Introduction
**The context.** Recently, many people have started seeking information about food and diets in order to know better what they eat; to this end, the Internet is a great and mostly used resource, thus many websites already exist which provide personalized nutritional information.

**The problem.** We noticed, however, that the level of personalization is not high enough to tell the users how to properly integrate the nutrients in which they lack; in other words, how to ”patch” their diet. For this purpose, we thought that a recommender system would do the work.

**The solution.** With our web application, the user can track his/her own diet through a meal plan on a daily basis; the system analyses the meal plan of a day and provides suggestions on how to improve it based on profile information and food habits.

##Features

****** Legend **********

* Nutrient: nutritional component in foods; this category includes all macronutri
ents (proteins, carbohydrates and fats), micronutrients (vitamins, minerals and
organic acids) and fibers.
* Food: any substance made of nutrients that can be consumed by the user for
nutritional purposes (e.g. any fruit, vegetable or meat, pasta, pizza etc.); this
category includes drinks, condiments and foods that are not usually consumed
alone (e.g. flour, oil).
* Meal plan: combination of foods that the user plans to eat on the selected day.

***** /Legend **********

###Login/Registration page

In the login/registration page the user can:

* read a brief description of the web application.
* register by providing a username, a password and an email address.
* have access through the login to his/her own account by providing username and password.

###Profile page

In the profile page the user can:

* insert personal information: age, gender, height, weight, waist size.
* choose tags (e.g. vegetarian, omnivore, raw vegan) to identify in a user
category with respect to his/her food habits.
* visualize his/her BMI (Body Mass Index) and BAI (Body Adiposity
Index), that the application will calculate based on the personal information.

###Meal plan page

In the meal plan page the user can:

* navigate through the calendar to select the meal plan of a certain day.
* select some food from the DB, choose the amount in grams and add it to
the current meal plan.
* check how nutritious a daily plan is by looking at the percentage of covered
nutrients. These percentages are shown with colored bars that indicate
either ”not enough” (yellow), ”enough” (green) or ”too much” (red). The
gender of the user is considered in the fulfilling of the bars, because males
and females need different nutrient amounts.
* see a list of lacking nutrients and, after one of them is selected, see various
suggestions of food to add to the meal plan.

##Development Process

DietPatcher is a responsive web application written mainly in Java, JavaScript
and HTML. It has been developed between May, 23rd and June, 10th 2015.
Most of the technologies used have been taught during the course of Internet
and Mobile Services. The recommendation algorithms used have been taught
during the course of Recommender Systems and Information Retrieval.

###Technologies

* Java 8
* Apache Tomcat 7
* Java Servlets, Java Beans
* JSP
* CSS3
* Javascript, jQuery
* JSON
* HTML
* PostgreSQL
* Maven
* Trello kanban board
* GitHub versioning system
 
###Dataset

For foods and nutritional values we used the National Nutrient Database for
Standard Reference of the United States Department of Agriculture, which
contains four principal and eight support tables offering the nutritional values
of 8618 different foods.


To complete the nutritional information contained in the database, we con
sulted the Dietary Reference Intake RDA (Recommended Dietary Allowance)
and UL (Tolerable upper intake levels) tables.

##Recommendations

**Disclaimer.** Due to the use of a complex data set, we could not manipulate
much the information about foods without modifying a large part of it. There
fore item-based recommendations are not provided.

**Hybrid system.** For our recommendations we used a hybrid system based
on various strategies: personalized recommendations, user-based collaborative
filtering, knowledge-based recommendations.

**Recommender methods.** The recommender system of the application
consists of three algorithms:

* select the foods containing the greatest amount of a given nutrient among
those already eaten by the user.
* select the foods containing the greatest amount of a given nutrient among
those eaten by users with the same tags as the user.
* select the food containing the greatest amount of a given nutrient among
the whole list of foods in the database.

**Similarity and difference.** Although we do not use any formula to calculate
similarity, the second method provides recommendations based on similarity
between users, which is represented by having the same tags.
Since the user can reuse an item (i.e. food) he/she has previously used, the
first method is also based on similarity, because the food proposed is not new
to the user.

The third method, instead, is based on difference, since the recommended
food is in no way related to any user. Moreover, this food is chosen randomly
between a list of foods having the same amount of the given nutrient, in order
to avoid recommending always the same food.

