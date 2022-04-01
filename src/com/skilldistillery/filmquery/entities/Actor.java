package com.skilldistillery.filmquery.entities;

import java.util.Objects;

public class Actor {

	private int ID;
	private String firstName;
	private String lastName;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Actor() {
		
	}
	@Override
	public String toString() {
		return "Actor [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	public Actor(int iD, String firstName, String lastName) {
		super();
		ID = iD;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return ID == other.ID;
	}
	
}
