package edu.stevens.cs555.utils;

import java.util.Date;

import edu.stevens.cs555.Family;

public class ValidateDates {
	
	
	public void validateFamilyDates(Family fam) throws Exception{
		Date rn = new Date();
		
		if(!Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(rn) > 0){
				System.out.println("");
				throw new java.lang.Exception("Married is past current date.");
			}
		}
	
		if(!Validate.noNulls(fam.getDivorceDate())){
			if(fam.getDivorceDate().compareTo(rn) > 0){
				System.out.println("");
				throw new java.lang.Exception("Divorced is past current date.");
			}
		}
		
		if(fam.getMarrDate() != null){
			if(fam.getMarrDate().compareTo(fam.getHusband().getBirthDate()) < 0){
				System.out.println("");
				throw new java.lang.Exception("Birthdate of Husband is after Marriage");
			}
			if(fam.getMarrDate().compareTo(fam.getWife().getBirthDate()) < 0){
				System.out.println("");
				throw new java.lang.Exception("Birthdate of Wife is after Marriage");
			}
		}
	
	}

}
