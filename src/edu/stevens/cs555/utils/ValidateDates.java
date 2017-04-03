package edu.stevens.cs555.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import edu.stevens.cs555.Family;
import edu.stevens.cs555.Individual;

public class ValidateDates {
	
	private static final Logger LOGGER = SingletonLogger.getInstance();
	public ValidateDates (){
		
	}
	
	 
	public void validateFamilyDates(Family fam) throws Exception{
		
		//US01
		isMarrBeforeCurrent(fam);
		isDivBeforeCurrent(fam);
		//US02
		isMarrAfterBirth(fam);
		//US04
		isMarriageBeforeDivorce(fam);
		//US05
		isMarriageBeforeDeath(fam);
		//US06
		isDivorceBeforeDeath(fam);
		//US08
		isChildBirthBeforeParentsMarr(fam);
		//US09
		isChildBirthBeforeParentDeath(fam);
		//US10
		isMarriageAfter14(fam);
		
	}
	
	//US01
	public  boolean isMarrBeforeCurrent(Family fam){
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE, "ERROR: FAMILY: US01: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate())+" after current date");
				return false;
			}
		} return true;
	}
	
	//US01
	public  boolean isDivBeforeCurrent(Family fam){
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getDivorceDate())){
			if(fam.getDivorceDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US01: Divorced " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getDivorceDate()) + " after current date");
				return false;
			}
		}
		return true;
	}
	
	//US02
	public  boolean isMarrAfterBirth(Family fam){
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getHusband().getBirthDate().compareTo(fam.getMarrDate()) > 0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after husband's " + "(" + fam.getHusband().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getHusband().getBirthDate()));
				return false;
			}
			if(fam.getWife().getBirthDate().compareTo(fam.getMarrDate()) > 0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after wife's " + "(" + fam.getWife().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getWife().getBirthDate()));
				return false;
			}
		} return true;
	}
	
	//US04
	public  boolean isMarriageBeforeDivorce(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(fam.getMarrDate(),fam.getDivorceDate())){
			if(fam.getMarrDate().compareTo(fam.getDivorceDate())>0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US04: "+fam.getId() +": Divorce "+ dt.format(fam.getDivorceDate())+" before Marriage " + dt.format(fam.getMarrDate())+ " of Spouses");
				return false;
			}
		}else if(Validate.noNulls(fam.getDivorceDate()) && Validate.allNulls(fam.getMarrDate())){
			LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US04: "+fam.getId() +": Marriage should happen before Divorce " + dt.format(fam.getDivorceDate())+ " of Spouses");
			return false;
		}
		return true;
	}
	
	//US05
	public  boolean isMarriageBeforeDeath(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(fam.getMarrDate())){
			if(Validate.noNulls(fam.getHusband().getDeathDate())){
				if(fam.getHusband().getDeathDate().compareTo(fam.getMarrDate()) < 0){
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US05: " + fam.getId() + ": Married "+ dt.format(fam.getMarrDate())+" after husband's (" + fam.getHusband().getId() + ") death on " + dt.format(fam.getHusband().getDeathDate()));
					return false;
				}
			}
			
			if(Validate.noNulls(fam.getWife().getDeathDate())){
				if(fam.getWife().getDeathDate().compareTo(fam.getMarrDate()) < 0){
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US05: " + fam.getId() + ": Married "+ dt.format(fam.getMarrDate())+" after wife's (" + fam.getWife().getId() + ") death on " + dt.format(fam.getWife().getDeathDate()));
					return false;
				}
			}
		}
		return true;
	}
	
	//US06
	public  boolean isDivorceBeforeDeath(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(fam.getDivorceDate())){
			if(Validate.noNulls(fam.getHusband().getDeathDate())){
				if(fam.getHusband().getDeathDate().compareTo(fam.getDivorceDate()) < 0){
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US06: " + fam.getId() + ": Divorced "+ dt.format(fam.getDivorceDate())+" after husband's (" + fam.getHusband().getId() + ") death on " + dt.format(fam.getHusband().getDeathDate()));
					return false;
				}
			}
			
			if(Validate.noNulls(fam.getWife().getDeathDate())){
				if(fam.getWife().getDeathDate().compareTo(fam.getMarrDate()) < 0){
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY: US05: " + fam.getId() + ": Divored "+ dt.format(fam.getDivorceDate())+" after wife's (" + fam.getWife().getId() + ") death on " + dt.format(fam.getWife().getDeathDate()));
					return false;
				}
			}
		}
		return true;
	}	
	
	//US07
	public  boolean isLessThan150Years(Individual indi){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(indi.getDeathDate())){
			if(Validate.noNulls(indi.getBirthDate())){
				if(indi.getDeathDate().compareTo(indi.getBirthDate())>150){
					LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL: US07: "+indi.getId()+ ": More than 150 years old at death - Birth "+dt.format(indi.getBirthDate()+ ": Death "+dt.format(indi.getDeathDate())));
					return false;
				}
				
			}
			
		}else{
			if(Validate.noNulls(indi.getBirthDate())){
				Date currentDate = new Date();
				if(currentDate.compareTo(indi.getBirthDate())>150){
					LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL: US07: "+indi.getId()+ ": More than 150 years old - Birth "+dt.format(indi.getBirthDate()));
					return false;
				}
			}
		}
		
		return true;
	}
	
	//US08
	public  boolean isChildBirthBeforeParentsMarr(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(fam.getChildren())){
			for(Individual child : fam.getChildren()){
				if(Validate.noNulls(fam.getMarrDate())){
					if(Validate.noNulls(fam.getDivorceDate())){
						Calendar nineMonthsAhead = Calendar.getInstance();
						nineMonthsAhead.setTime(fam.getDivorceDate());
						nineMonthsAhead.add(Calendar.MONTH,9 );
						long diff = nineMonthsAhead.getTimeInMillis() - child.getBirthDate().getTime();
						if(diff<0){
							LOGGER.log(Level.SEVERE,"ANOMALY: FAMILY: US08: "+fam.getId()+": Child "+child.getId()+"born " +dt.format(child.getBirthDate())+" after divorce on"+dt.format(fam.getDivorceDate()));
							return false;
						}
					}else{
						if(child.getBirthDate().compareTo(fam.getMarrDate())<0){
							LOGGER.log(Level.SEVERE,"ANOMALY: FAMILY: US08: "+fam.getId()+": Child "+child.getId()+"born " +dt.format(child.getBirthDate())+" before marriage on"+dt.format(fam.getMarrDate()));
							return false;
						}
					}
					
				}else{
					LOGGER.log(Level.SEVERE,"ANOMALY: FAMILY: US08: "+fam.getId()+": Child "+child.getId()+"born " +dt.format(child.getBirthDate())+" without marriage.");
					return false;
				}
			}
		}
		
		return true;	
	}
	
	//US09
	public boolean isChildBirthBeforeParentDeath(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		long dtotal = 0;
		if(Validate.noNulls(fam.getChildren())){
			for(int i = 0; i < fam.getChildren().size() - 1; i++){
				if(Validate.noNulls(fam.getWife().getDeathDate())){
					if(fam.getChildren().get(i).getBirthDate().compareTo(fam.getWife().getDeathDate()) > 0){
						LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL: US09: Birthdate of " + fam.getChildren().get(i).getName() + " (" + fam.getChildren().get(i).getId() +
								") (" + dt.format(fam.getChildren().get(i).getBirthDate()) + ") occurs after death of mother (" + fam.getWife().getId() + ") (" + fam.getWife().getDeathDate() + ")");
						return false;
					}
				}
				
				if(Validate.noNulls(fam.getHusband().getDeathDate())){
					dtotal = fam.getChildren().get(i).getBirthDate().getTime() - fam.getHusband().getDeathDate().getTime();
					dtotal = dtotal / (24 * 60 * 60 * 1000);
					if(dtotal > 274){
						LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL: US09: Birthdate of " + fam.getChildren().get(i).getName() + " (" + fam.getChildren().get(i).getId() +
								") (" + dt.format(fam.getChildren().get(i).getBirthDate()) + ") occurs after 9 months after death of father (" + fam.getHusband().getId() + ") (" + fam.getHusband().getDeathDate() + ")");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//US10
	public boolean isMarriageAfter14(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		long dtotal1 = 0;
		long dtotal2 = 0;
		if(Validate.noNulls(fam.getHusband().getBirthDate(), fam.getWife().getBirthDate(), fam.getMarrDate())){
			dtotal1 = fam.getMarrDate().getTime() - fam.getHusband().getBirthDate().getTime();
			dtotal2 = fam.getMarrDate().getTime() - fam.getWife().getBirthDate().getTime();
			dtotal1 = dtotal1 / (24 * 60 * 60 * 1000);
			dtotal2 = dtotal2 / (24 * 60 * 60 * 1000);
			if(dtotal1 < 5110){
				LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL: US10: Marriage date of Family (" + fam.getId() + ") (" + dt.format(fam.getMarrDate()) + 
						") must be 14 years after Husband's (" + fam.getHusband().getId() + ") birthdate (" + dt.format(fam.getHusband().getBirthDate()) + ")");
				return false;
			}
			if(dtotal2 < 5110){
				LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL: US10: Marriage date of Family (" + fam.getId() + ") (" + dt.format(fam.getMarrDate()) + 
						") must be 14 years after Wife's (" + fam.getWife().getId() + ") birthdate (" + dt.format(fam.getWife().getBirthDate()) + ")");
				return false;
			}
		}
		return true;
	}
	
	public  void validateIndividualDates(Individual indi) throws Exception{
		//US01
		isDeathBeforeCurrent(indi);
		isBirthBeforeCurrent(indi);
		//US03
		isBirthBeforeDeath(indi);
		//US07
		isLessThan150Years(indi);
	}
	
	//US01
	public  boolean isDeathBeforeCurrent(Individual indi){
		Date rn = new Date();
		
		if(Validate.noNulls(indi.getDeathDate())){
			if(indi.getDeathDate().compareTo(rn) > 0){
			LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL: US01: "+indi.getId() +":  Death "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getDeathDate())+" occurs in the future");
			return false;
			}
		} return true;
	}
	
	//US01
	public  boolean isBirthBeforeCurrent(Individual indi){
		Date rn = new Date();
		
		if(indi.getBirthDate().compareTo(rn) > 0){
			LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL: US01: "+indi.getId() +":  Birthday "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getBirthDate())+" occurs in the future");
			return false;
		} return true;
	}
	
	//US03
	public  boolean isBirthBeforeDeath(Individual indi){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		
		if(Validate.allNulls(indi.getBirthDate())){
			LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL: US03: "+indi.getId() +": Birthday is mandatory for any person to be alive.");
			return false;
		}
		if(Validate.noNulls(indi.getDeathDate())){
			if(indi.getBirthDate().compareTo(indi.getDeathDate())>0){
				LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL: US03: "+indi.getId() +": Death "+ dt.format(indi.getDeathDate())+" before born " + dt.format(indi.getBirthDate()));
				return false;
			}
		}
		return true;	
	}
	
	// End of US
	
	public  void validateIndividualAndFamily(HashMap<String,Individual> individuals,HashMap<String,Family> families){
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

class SingletonLogger {
	private static Logger instance = null;
	   protected  SingletonLogger() {
		// Exists only to defeat instantiation.
	} 
	   public static Logger getInstance() {
	      if(instance == null) {
	         instance = Logger.getLogger(ValidateDates.class.getName());
	         instance.setUseParentHandlers(false);
	 		
	 		MyFormatter formatter = new MyFormatter();
	 		ConsoleHandler handler = new ConsoleHandler();
	 		handler.setFormatter(formatter);
	 		
	 		instance.addHandler(handler);
	         
	      }
	      return instance;
	   }
	
	
}

class MyFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(record.getLevel()).append("] - ");
        builder.append(formatMessage(record));
        builder.append("\n");
		return builder.toString();
		}
	
	public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }
	
}

