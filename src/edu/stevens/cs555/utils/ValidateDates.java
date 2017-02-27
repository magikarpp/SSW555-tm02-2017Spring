package edu.stevens.cs555.utils;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.stevens.cs555.Family;

public class ValidateDates {
	
	 private static final Logger LOGGER = Logger.getLogger(ValidateDates.class.getName());
	 
	public void validateFamilyDates(Family fam) throws Exception{
		Date rn = new Date();
		
		if(!Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE, "Married is past current date.");
			}
		}
	
		if(!Validate.noNulls(fam.getDivorceDate())){
			if(fam.getDivorceDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE,"Divorced is past current date.");
			}
		}
		
		if(fam.getMarrDate() != null){
			if(fam.getMarrDate().compareTo(fam.getHusband().getBirthDate()) < 0){
				LOGGER.log(Level.SEVERE,"Birthdate of Husband is after Marriage");
			}
			if(fam.getMarrDate().compareTo(fam.getWife().getBirthDate()) < 0){
				LOGGER.log(Level.SEVERE,"Birthdate of Wife is after Marriage");
			}
		}
	
	}

}
