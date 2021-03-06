package edu.stevens.cs555;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Family {
	private String ID;
	private Date Married;
	private Date Divorced;
	private Individual Husband;
	private Individual Wife;
	private ArrayList<Individual> Children;
	
	
	public String getId() {
		return ID;
	}
	public void setId(String id) {
		ID = id;
	}
	public Date getMarrDate() {
		return Married;
	}
	public void setMarrDate(Date marrDate) {
		this.Married = marrDate;
	}
	public Date getDivorceDate() {
		return Divorced;
	}
	public void setDivorceDate(Date divorceDate) {
		this.Divorced = divorceDate;
	}
	public Individual getHusband() {
		return Husband;
	}
	public void setHusband(Individual husband) {
		this.Husband = husband;
	}
	public Individual getWife() {
		return Wife;
	}
	public void setWife(Individual wife) {
		this.Wife = wife;
	}
	public List<Individual> getChildren() {
		return Children;
	}
	public void setChildren(ArrayList<Individual> children) {
		this.Children = children;
	}
	

}
