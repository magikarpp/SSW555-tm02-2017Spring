package edu.stevens.cs555.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import edu.stevens.cs555.Family;
import edu.stevens.cs555.Individual;

public class ValidateDates {
	
	 private static final Logger LOGGER = Logger.getLogger(ValidateDates.class.getName());
	 
	 
	public static void validateFamilyDates(Family fam) throws Exception{

		isMarrBeforeCurrent(fam);
		isDivBeforeCurrent(fam);
		isMarrAfterBirth(fam);
		isMarriageBeforeDivorce(fam);
		
	}
	
	//US01
	public static boolean isMarrBeforeCurrent(Family fam){
		
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE, "FAMILY: US01: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate())+" after current date");
				return false;
			}
		} return true;
	}
	
	//US01
	public static boolean isDivBeforeCurrent(Family fam){
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getDivorceDate())){
			if(fam.getDivorceDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE,"FAMILY: US01: Divorced "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getDivorceDate())+" after current date");
				return false;
			}
		} return true;
	}
	
	//US02
	public static boolean isMarrAfterBirth(Family fam){
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(fam.getHusband().getBirthDate()) < 1){
				LOGGER.log(Level.SEVERE,"FAMILY: US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after husband's " + "(" + fam.getHusband().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getHusband().getBirthDate()));
				return false;
			}
			if(fam.getMarrDate().compareTo(fam.getWife().getBirthDate()) < 1){
				LOGGER.log(Level.SEVERE,"FAMILY: US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after wife's " + "(" + fam.getWife().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getWife().getBirthDate()));
				return false;
			}
		} return true;
	}
	//US04
		public static boolean isMarriageBeforeDivorce(Family fam){
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
			if(Validate.noNulls(fam.getMarrDate(),fam.getDivorceDate())){
				if(fam.getMarrDate().compareTo(fam.getDivorceDate())>0){
					LOGGER.log(Level.SEVERE,"FAMILY: US04: "+fam.getId() +": Divorce "+ dt.format(fam.getDivorceDate())+" before Marriage " + dt.format(fam.getMarrDate())+ " of Spouses");
					return false;
				}
			}else if(Validate.noNulls(fam.getDivorceDate())&& Validate.allNulls(fam.getMarrDate())){
				LOGGER.log(Level.SEVERE,"FAMILY: US04: "+fam.getId() +": Marriage should happen before Divorce " + dt.format(fam.getDivorceDate())+ " of Spouses");
				return false;
			}
			return true;
		}
	
	
	public static void validateIndividualDates(Individual indi) throws Exception{
		
		isDeathBeforeCurrent(indi);
		isBirthBeforeCurrent(indi);
		isBirthBeforeDeath(indi);
	}
	
	//US01
	public static boolean isDeathBeforeCurrent(Individual indi){
		Date rn = new Date();
		
		if(Validate.noNulls(indi.getDeathDate())){
			if(indi.getDeathDate().compareTo(rn) > 0){
			LOGGER.log(Level.SEVERE,"INDIVIDUAL: US01: "+indi.getId() +":  Death "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getDeathDate())+" occurs in the future");
			return false;
			}
		} return true;
	}
	
	//US01
	public static boolean isBirthBeforeCurrent(Individual indi){
		Date rn = new Date();
		
		if(indi.getBirthDate().compareTo(rn) > 0){
			LOGGER.log(Level.SEVERE,"INDIVIDUAL: US01: "+indi.getId() +":  Birthday "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getBirthDate())+" occurs in the future");
			return false;
		} return true;
	}
	
	//US03
	public static boolean isBirthBeforeDeath(Individual indi){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		
		if(Validate.allNulls(indi.getBirthDate())){
			LOGGER.log(Level.SEVERE,"INDIVIDUAL: US03: "+indi.getId() +": Birthday is mandatory for any person to be alive.");
			return false;
		}
		if(Validate.noNulls(indi.getDeathDate())){
			if(indi.getBirthDate().compareTo(indi.getDeathDate())>0){
				LOGGER.log(Level.SEVERE,"INDIVIDUAL: US03: "+indi.getId() +": Death "+ dt.format(indi.getDeathDate())+" before born " + dt.format(indi.getBirthDate()));
				return false;
			}
		}
		return true;	
	}
	
	
	public static void validateIndividualAndFamily(HashMap<String,Individual> individuals,HashMap<String,Family> families){
		try{
		for(Individual indi :individuals.values()){
			
			validateIndividualDates(indi);
			}
		for(Family fam : families.values()){
			
			validateFamilyDates(fam);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
class MyFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		return record.getLevel() + ":" + record.getMessage();
	}
	
}
