package edu.stevens.cs555.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.stevens.cs555.Family;
import edu.stevens.cs555.Individual;

public class ValidateDates {
	
	 private static final Logger LOGGER = Logger.getLogger(ValidateDates.class.getName());
	 
	public static void validateFamilyDates(Family fam) throws Exception{
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE, "Married is past current date.");
			}
		}
	
		if(Validate.noNulls(fam.getDivorceDate())){
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
	public static void validateIndividualDates(Individual indi) throws Exception{
		Date rn = new Date();
		
		if(indi.getBirthDate().compareTo(rn) > 0){
			System.out.println("");
			LOGGER.log(Level.SEVERE,"Birthday is past current date.");
		}

		if(indi.getDeathDate() != null){
			if(indi.getDeathDate().compareTo(rn) > 0){
			System.out.println("");
			LOGGER.log(Level.SEVERE,"Death is past current date.");
			}
		}
		isBirthBeforeDeath(indi);
	}
	public static boolean isBirthBeforeDeath(Individual indi){
		if(indi.getBirthDate().compareTo(indi.getDeathDate())>0){
			LOGGER.log(Level.SEVERE,"INDIVIDUAL: US03: DIED "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getDeathDate())+" before born");
			return false;
		}
		return true;	
	}
}
