package com.humanbooster.Business_case_admin.model;

public enum Level {
	DEBUTANT("Débutant"), INTERMEDIAIRE("Intermédiare"), EXPERT("Expert");
	
	private final String displayValue;
	
	private Level(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
