package model;

import java.util.ArrayList;

public class UserBean {

	private String email="";
	private String username="";
	private String password="";
	private String gender="";
	private int age=0;
	private double height=0;
	private double weight=0;
	private double waist=0;
	private int id=0;
	private ArrayList<String> tags = new ArrayList<String>();
	
	public void setUserBeanParameters(String email, String username, String password,
			String gender, int age, double height, double weight, double waist) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.waist = waist;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String usernameLogin) {
		this.username = usernameLogin;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWaist() {
		return waist;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
}

