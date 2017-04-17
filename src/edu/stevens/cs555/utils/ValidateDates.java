package edu.stevens.cs555.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		//US12
		isParentsTooOlder(fam);
		//US13
		isSiblingsAgeApart(fam);
		//US14
		isLessThan5SameBirths(fam);
		//US16
		isMaleSameLastName(fam);
		//US21
		isCorrectGenderRole(fam);
		
	}
	private static long getDifference(long dt1, long dt2){
		long diff;
			diff = dt1-dt2;
		
		return diff;
	}
	public static long year_between(Date dt1,Date dt2){
		long diff = getDifference(dt1.getTime(), dt2.getTime());	
		long d = (1000*60*60*24);
		long expectedDays=diff/d;
		long years = (long) Math.floor(expectedDays/365.25);
		return years;
	}
	
	//US01
	public  boolean isMarrBeforeCurrent(Family fam){
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getMarrDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US01: " + fam.getId() + ": Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate())+" after current date");
				return false;
			}
		} return true;
	}
	
	//US01
	public  boolean isDivBeforeCurrent(Family fam){
		Date rn = new Date();
		
		if(Validate.noNulls(fam.getDivorceDate())){
			if(fam.getDivorceDate().compareTo(rn) > 0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US01: " + fam.getId() + ": Divorced " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getDivorceDate()) + " after current date");
				return false;
			}
		}
		return true;
	}
	
	//US02
	public  boolean isMarrAfterBirth(Family fam){
		
		if(Validate.noNulls(fam.getMarrDate())){
			if(fam.getHusband().getBirthDate().compareTo(fam.getMarrDate()) > 0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US02: " + fam.getId() + ": Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after husband's " + "(" + fam.getHusband().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getHusband().getBirthDate()));
				return false;
			}
			if(fam.getWife().getBirthDate().compareTo(fam.getMarrDate()) > 0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US02: Married "+ new SimpleDateFormat("yyyy-MMM-dd").format(fam.getMarrDate()) + " after wife's " + "(" + fam.getWife().getId() + ") " + "birth on " + new SimpleDateFormat("yyyy-MMM-dd").format(fam.getWife().getBirthDate()));
				return false;
			}
		} return true;
	}
	
	//US04
	public  boolean isMarriageBeforeDivorce(Family fam){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(fam.getMarrDate(),fam.getDivorceDate())){
			if(fam.getMarrDate().compareTo(fam.getDivorceDate())>0){
				LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US04: "+fam.getId() +": Divorce "+ dt.format(fam.getDivorceDate())+" before Marriage " + dt.format(fam.getMarrDate())+ " of Spouses");
				return false;
			}
		}else if(Validate.noNulls(fam.getDivorceDate()) && Validate.allNulls(fam.getMarrDate())){
			LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US04: "+fam.getId() +": Marriage should happen before Divorce " + dt.format(fam.getDivorceDate())+ " of Spouses");
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
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US05: " + fam.getId() + ": Married "+ dt.format(fam.getMarrDate())+" after husband's (" + fam.getHusband().getId() + ") death on " + dt.format(fam.getHusband().getDeathDate()));
					return false;
				}
			}
			
			if(Validate.noNulls(fam.getWife().getDeathDate())){
				if(fam.getWife().getDeathDate().compareTo(fam.getMarrDate()) < 0){
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US05: " + fam.getId() + ": Married "+ dt.format(fam.getMarrDate())+" after wife's (" + fam.getWife().getId() + ") death on " + dt.format(fam.getWife().getDeathDate()));
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
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US06: " + fam.getId() + ": Divorced "+ dt.format(fam.getDivorceDate())+" after husband's (" + fam.getHusband().getId() + ") death on " + dt.format(fam.getHusband().getDeathDate()));
					return false;
				}
			}
			
			if(Validate.noNulls(fam.getWife().getDeathDate())){
				if(fam.getWife().getDeathDate().compareTo(fam.getMarrDate()) < 0){
					LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US06: " + fam.getId() + ": Divored "+ dt.format(fam.getDivorceDate())+" after wife's (" + fam.getWife().getId() + ") death on " + dt.format(fam.getWife().getDeathDate()));
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
				long diff = year_between(indi.getDeathDate(), indi.getBirthDate());
				if(diff>150){
					LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL:\t US07: "+indi.getId()+ ": More than 150 years old at death - Birth date "+dt.format(indi.getBirthDate())+ ": Death date "+ dt.format(indi.getDeathDate()));
					return false;
				}	
			}
			
		}else{
			if(Validate.noNulls(indi.getBirthDate())){
				Date currentDate = new Date();
				long diff = year_between(currentDate, indi.getBirthDate());
				if(diff>150){
					LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL:\t US07: "+indi.getId()+ ": More than 150 years old - Birth date "+dt.format(indi.getBirthDate()));
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
							LOGGER.log(Level.SEVERE,"ANOMALY: FAMILY:\t US08: "+fam.getId()+": Child "+child.getId()+" born " + dt.format(child.getBirthDate())+" after divorce on "+dt.format(fam.getDivorceDate()));
							return false;
						}
					}else{
						if(child.getBirthDate().compareTo(fam.getMarrDate())<0){

							LOGGER.log(Level.SEVERE,"ANOMALY: FAMILY:\t US08: "+fam.getId()+": Child "+child.getId()+" born " +dt.format(child.getBirthDate())+" before marriage on "+dt.format(fam.getMarrDate()));
							return false;
						}
					}
					
				}else{

					LOGGER.log(Level.SEVERE,"ANOMALY: FAMILY:\t US08: "+fam.getId()+": Child "+child.getId()+" born " +dt.format(child.getBirthDate())+" without marriage.");
					return false;
				}
			}
		}
		
		return true;	
	}
	
	//US09
	public boolean isChildBirthBeforeParentDeath(Family fam){
		/*
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		long dtotal = 0;
		if(Validate.noNulls(fam.getChildren())){
			for(int i = 0; i < fam.getChildren().size() - 1; i++){
				if(Validate.noNulls(fam.getWife().getDeathDate())){
					if(fam.getChildren().get(i).getBirthDate().compareTo(fam.getWife().getDeathDate()) > 0){
						LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL:\t US09: Birthdate of " + fam.getChildren().get(i).getName() + " (" + fam.getChildren().get(i).getId() +
								") (" + dt.format(fam.getChildren().get(i).getBirthDate()) + ") occurs after death of mother (" + fam.getWife().getId() + ") (" + fam.getWife().getDeathDate() + ")");
						return false;
					}
				}
				
				if(Validate.noNulls(fam.getHusband().getDeathDate())){
					dtotal = fam.getChildren().get(i).getBirthDate().getTime() - fam.getHusband().getDeathDate().getTime();
					dtotal = dtotal / (24 * 60 * 60 * 1000);
					if(dtotal > 274){
						LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL:\t US09: Birthdate of " + fam.getChildren().get(i).getName() + " (" + fam.getChildren().get(i).getId() +
								") (" + dt.format(fam.getChildren().get(i).getBirthDate()) + ") occurs after 9 months after death of father (" + fam.getHusband().getId() + ") (" + fam.getHusband().getDeathDate() + ")");
						return false;
					}
				}
			}
		}
		return true;
		*/
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		if(Validate.noNulls(fam.getChildren())){
			for(Individual child : fam.getChildren()){
				if(Validate.noNulls(fam.getHusband().getDeathDate())){
					Calendar nineMonthsAhead = Calendar.getInstance();
					nineMonthsAhead.setTime(fam.getHusband().getDeathDate());
					nineMonthsAhead.add(Calendar.MONTH, 9);
					long diff = nineMonthsAhead.getTimeInMillis() - child.getBirthDate().getTime();
					if(diff<0){
						LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL:\t US09: Birthdate of " + child.getName() + " (" + child.getId() +
								") (" + dt.format(child.getBirthDate()) + ") occurs after 9 months after death of father (" + fam.getHusband().getId() + ") (" + dt.format(fam.getHusband().getDeathDate()) + ")");
						return false;
					}
				}
				if(Validate.noNulls(fam.getWife().getDeathDate())){
					if(child.getBirthDate().compareTo(fam.getWife().getDeathDate()) > 0){
						LOGGER.log(Level.SEVERE, "ERROR: INDIVIDUAL:\t US09: Birthdate of " + child.getName() + " (" + child.getId() +
								") (" + dt.format(child.getBirthDate()) + ") occurs after death of mother (" + fam.getWife().getId() + ") (" + dt.format(fam.getWife().getDeathDate()) + ")");
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
				LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US10: " + fam.getId() + ": Marriage date of Family on " + dt.format(fam.getMarrDate()) + 
						" must be 14 years after Husband's (" + fam.getHusband().getId() + ") birthdate on " + dt.format(fam.getHusband().getBirthDate()));
				return false;
			}
			if(dtotal2 < 5110){
				LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US10: " + fam.getId() + ": Marriage date of Family on " + dt.format(fam.getMarrDate()) + 
						" must be 14 years after Wife's (" + fam.getWife().getId() + ") birthdate on " + dt.format(fam.getWife().getBirthDate()));
				return false;
			}
		}
		return true;
	}
	
	//US12 Parents not too older
		public boolean isParentsTooOlder(Family fam){
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
			
			Individual mother = fam.getWife();
			Individual father = fam.getHusband(); 
			boolean flag = true;
			if(Validate.noNulls(fam.getChildren())){
				List<Individual> children = fam.getChildren();
				for(Individual child : children){
					long yearsMotherOlder = year_between(child.getBirthDate(), mother.getBirthDate()) ;
					long yearsFatherOlder = year_between(child.getBirthDate(), father.getBirthDate());
					
					if(yearsMotherOlder > 60){
						LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US12: "+fam.getId()+": Mother is more than 60 of age "+yearsMotherOlder+ " older Date :"+dt.format(mother.getBirthDate())+" then child  "+ child.getId()+" born on "+ dt.format(child.getBirthDate()));
						flag = false;
					}
					if(yearsFatherOlder > 80){
						LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US12: "+fam.getId()+": Father is more than 80 of age  "+yearsFatherOlder+ " older Date :"+dt.format(father.getBirthDate())+" then child  "+ child.getId()+" born on "+ dt.format(child.getBirthDate()));
						flag= false;
					}
				}
			
			}
			
			return flag;
		}
		
		//US13
		public boolean isSiblingsAgeApart(Family fam){
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
			if(Validate.noNulls(fam.getChildren())){
				if(fam.getChildren().size() >= 2){
					for(Individual child1 : fam.getChildren()){
						for(Individual child2 : fam.getChildren()){
							if(child1 != child2){
								if(child1.getBirthDate().compareTo(child2.getBirthDate()) < 0){
									Calendar eightMonthsAhead = Calendar.getInstance();
									eightMonthsAhead.setTime(child1.getBirthDate());
									eightMonthsAhead.add(Calendar.MONTH, 8);
									long diff1 = eightMonthsAhead.getTimeInMillis() - child2.getBirthDate().getTime();
									Calendar twoDaysAhead = Calendar.getInstance();
									twoDaysAhead.setTime(child1.getBirthDate());
									twoDaysAhead.add(Calendar.DAY_OF_YEAR, 2);
									long diff2 = twoDaysAhead.getTimeInMillis() - child2.getBirthDate().getTime();
									if(diff1 > 0){
										if(diff2 < 0){
											LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US13: " + fam.getId() + ": Sibiling (" + child1.getId() + " and " + child2.getId() + ") are born within 8 months and more than 2 days of each other.");
											return false;
										}
									}
								}
							}
						}
					}
				}
			}
			return true;
		}
		
		//US14
		public boolean isLessThan5SameBirths(Family fam){
			HashMap<Date, Integer> dupes = new HashMap<Date, Integer>();
			if(Validate.noNulls(fam.getChildren())){
				if(fam.getChildren().size() >= 5){
					for(Individual child : fam.getChildren()){
						Date temp = child.getBirthDate();
						if(dupes.containsKey(temp)){
							dupes.put(temp, dupes.get(temp) + 1);
							if(dupes.get(temp) > 5){
								LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US14: " + fam.getId() + ": More than 5 siblings have the same birthday.");
								return false;
							}
						}else{
							dupes.put(temp, 1);
						}
					}
				}
			}
			
			return true;
		}
	//US16
		public boolean isMaleSameLastName(Family fam){
			String lastName;
			Boolean flag = true;
			
			if(Validate.noNulls(fam.getHusband())){
				Individual husb = fam.getHusband();
				if(Validate.noNulls(husb.getName())){
					String [] name =husb.getName().split(" ");
					if(Validate.noNulls(name[1])){
						lastName = name[1];
						if(Validate.noNulls(fam.getChildren())){
							for(Individual indi : fam.getChildren()){
								if(indi.getGender().equals("M")){
									if(Validate.noNulls(indi.getName())){
										String [] childName =indi.getName().split(" ");
										if(Validate.noNulls(childName[1])){
											if(!lastName.equalsIgnoreCase(childName[1])){	
												LOGGER.log(Level.SEVERE,"ERROR: FAMILY:\t US16: "+fam.getId()+": Male Family Member "+indi.getId()+ " "+indi.getName()+ " doesn't have the same last name as "+lastName);
												flag = false;
											}	
										}
									}
								}
							}
						}
					}
				
				}
		
			}
			return flag;
		}
		//US21
		public boolean isCorrectGenderRole(Family fam){
			Boolean flag= true;
			if(Validate.noNulls(fam.getHusband())){
				Individual husb = fam.getHusband();
				if(!husb.getGender().equals("M")){
					LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US21: "+fam.getId()+": Husband "+husb.getId()+" "+husb.getName()+" has gender "+husb.getGender()+" in a family instead of Male 'M'");
					flag = false;
				}
			}
			if(Validate.noNulls(fam.getWife())){
				Individual wife = fam.getWife();
				if(!wife.getGender().equals("F")){
					LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US21: "+fam.getId()+": Wife "+wife.getId()+" "+wife.getName()+" has gender "+wife.getGender()+" in a family instead of Female 'F'");
					flag = false;
				}
			}
			return flag;
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
			LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL:\t US01: "+indi.getId() +":  Death "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getDeathDate())+" occurs in the future");
			return false;
			}
		} return true;
	}
	
	//US01
	public  boolean isBirthBeforeCurrent(Individual indi){
		Date rn = new Date();
		
		if(indi.getBirthDate().compareTo(rn) > 0){
			LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL:\t US01: "+indi.getId() +": Birthday "+ new SimpleDateFormat("yyyy-MMM-dd").format(indi.getBirthDate())+" occurs in the future");
			return false;
		} return true;
	}
	
	//US03
	public  boolean isBirthBeforeDeath(Individual indi){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		
		if(Validate.allNulls(indi.getBirthDate())){
			LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL:\t US03: "+indi.getId() +": Birthday is mandatory for any person to be alive.");
			return false;
		}
		if(Validate.noNulls(indi.getDeathDate())){
			if(indi.getBirthDate().compareTo(indi.getDeathDate())>0){
				LOGGER.log(Level.SEVERE,"ERROR: INDIVIDUAL:\t US03: "+indi.getId() +": Death "+ dt.format(indi.getDeathDate())+" before born " + dt.format(indi.getBirthDate()));
				return false;
			}
		}
		return true;	
	}
	
	//US11 No bigamy
	public boolean isBigamy(HashMap<String,Family> families){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
		HashMap<String,ArrayList<Family>> referenceMap = new HashMap();
		for(Family fam : families.values()){
			if(referenceMap.containsKey(fam.getHusband().getId())||referenceMap.containsKey(fam.getWife().getId())){
				if(referenceMap.containsKey(fam.getHusband().getId())){
					ArrayList<Family> famy = referenceMap.get(fam.getHusband().getId());
					famy.add(fam);	
				}
				if(referenceMap.containsKey(fam.getWife().getId())){
					ArrayList<Family> famy = referenceMap.get(fam.getWife().getId());
					famy.add(fam);	
				}
			}else{
				ArrayList<Family> husFamy = new ArrayList<Family>();
				ArrayList<Family> wifFamy = new ArrayList<Family>();
				
				husFamy.add(fam);
				wifFamy.add(fam);
				
				referenceMap.put(fam.getHusband().getId(), husFamy);
				referenceMap.put(fam.getWife().getId(), wifFamy);
			}
			
		}
		boolean flag= true;
		for(Map.Entry<String, ArrayList<Family>> entry  : referenceMap.entrySet()){
			
			if(entry.getValue().size()>1){
				ArrayList<Family> manyFamily = entry.getValue();
				for(int i=0;i<manyFamily.size();i++){
					Family fam1 = manyFamily.get(i);
					for(int j=i+1;j<manyFamily.size();j++){
						Family fam2 = manyFamily.get(j);
						Family firstMarr = null;
						Family secondMarr = null;
						if(Validate.noNulls(fam1.getMarrDate(),fam2.getMarrDate())){
							if(fam1.getMarrDate().compareTo(fam2.getMarrDate())>0){
								firstMarr = fam2;
								secondMarr = fam1;
							}else if(fam2.getMarrDate().compareTo(fam1.getMarrDate())>0){
								firstMarr = fam1;
								secondMarr = fam2;
							}
						}
						if(Validate.noNulls(firstMarr.getDivorceDate())){
							if(firstMarr.getDivorceDate().compareTo(secondMarr.getMarrDate())>0){
								LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US11: "+entry.getKey() +": Individual has performed illegal mariage on "+dt.format(secondMarr.getMarrDate()) + " before divorce on "+dt.format(firstMarr.getDivorceDate()));
								flag=false;
							}
						}else{
							LOGGER.log(Level.SEVERE, "ERROR: FAMILY:\t US11: "+entry.getKey() +": Individual has performed illegal mariage on "+dt.format(secondMarr.getMarrDate()) + " without taking divorce");
							flag = false;
						}
						
					}
				}
			}
		}
		return flag;
		
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
			isBigamy(families);
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

