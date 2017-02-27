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
				LOGGER.log(Level.SEVERE, "FAMILY: US01: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate())+" after current date");
			}
		}
	
		if(Validate.noNulls(fam.getDivorceDate())){
			if(fam.getDivorceDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE,"FAMILY: US01: Divorced "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getDivorceDate())+" after current date");
			}
		}
		
		if(fam.getMarrDate() != null){
			if(fam.getMarrDate().compareTo(fam.getHusband().getBirthDate()) < 0){
				LOGGER.log(Level.SEVERE,"FAMILY: US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after husband's " + "(" + fam.getHusband().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getHusband().getBirthDate()));
			}
			if(fam.getMarrDate().compareTo(fam.getWife().getBirthDate()) < 0){
				LOGGER.log(Level.SEVERE,"FAMILY: US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after wife's " + "(" + fam.getWife().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getWife().getBirthDate()));
			}
		}
		
	}
	public static void validateIndividualDates(Individual indi) throws Exception{
		Date rn = new Date();
		
		if(indi.getBirthDate().compareTo(rn) > 0){
			System.out.println("");
			LOGGER.log(Level.SEVERE,"INDIVIDUAL: US01: Birthday "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getBirthDate())+" occurs in the future");
		}

		if(indi.getDeathDate() != null){
			if(indi.getDeathDate().compareTo(rn) > 0){
			System.out.println("");
			LOGGER.log(Level.SEVERE,"INDIVIDUAL: US01: Death "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getDeathDate())+" occurs in the future");
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
