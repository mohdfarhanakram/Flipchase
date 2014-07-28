package com.jabong.android.model.response.error.login;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Validate {

	private ArrayList<String> email;
	private ArrayList<String> password;
	@SerializedName("is_confirmed")
	private ArrayList<String> isConfirmed;

	public ArrayList<String> getEmail() {
		return email;
	}

	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}

	public ArrayList<String> getPassword() {
		return password;
	}

	public void setPassword(ArrayList<String> password) {
		this.password = password;
	}

	public ArrayList<String> getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(ArrayList<String> isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

}
