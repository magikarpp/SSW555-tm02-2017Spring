package edu.stevens.cs555;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stevens.cs555.utils.ValidateDates;

public class GedComParser {	
	
	// individuals and families represent the collection of Individual and Family read from GEDCOM File. 
	private static HashMap<String,Individual> individuals = new HashMap<String,Individual>();
	private static HashMap<String,Family> families = new HashMap<String,Family>();
	
	public static void main(String[] args) throws Exception {
		//Check for the gedcom file address passed as argument.
		if(args.length!=1){
			System.out.println("Please include the absolute file path as the first and only command line. Format: GedComParser < Ged File > ");
			System.exit(0);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

			String sCurrentLine;
			// Gets the tags from tags.txt which are important to capture from the file.
			Set<String> eligibleTags = getEligibleTags();
			Properties props = new Properties();
			
			while ((sCurrentLine = br.readLine()) != null) {
				
				// Reading each line and forming REGEX to match pattern and extract information from line. 
				String REGEX = "(^\\d+)\\s(\\W?\\w+\\W?)\\s?(.*)";
				Pattern p = Pattern.compile(REGEX);
				Matcher m = p.matcher(sCurrentLine); //get a matcher object
				
				if(m.find()){
				
		            String level = m.group(1);
		            //System.out.println("level:\t" + level);
		            
		            String tagName = m.group(2).trim();
		            // Individual and Family comprise of different properties. 
		            //Fetching its value and associating with appropriate object. 
		            if(m.group(3)!=null){
			            if(m.group(3).equals("INDI")||m.group(3).equals("FAM")) {
			            	tagName = m.group(3);
			            	if(props.size()>0){
			            		if(props.containsKey("INDI")){
			            			Individual indi = getIndividual(props);
			            			individuals.put(props.getProperty("INDI"), indi);
			            			
			            		}else if(props.containsKey("FAM")){
			            			Family fam = getFamily(props);
			            			families.put(props.getProperty("FAM"), fam);
			            		}
			            		props.clear();
			            	}
			            	props.put(tagName,m.group(2));
			            }else{
			            	if(tagName.equals("CHIL")){
			            		if(props.get("CHIL")!=null){
			            			String children = props.get("CHIL").toString();
			            			children += ","+ m.group(3);
			            			props.put("CHIL", children);
			            			
			            		}else{
			            			props.put(tagName, m.group(3));
			            		}
			            	}else{
			            		if(eligibleTags.contains(tagName))
					            	props.put(tagName, m.group(3));
			            	}
			            	
			            }
		            }
		            
		            if(tagName.equals("BIRT")||tagName.equals("DEAT")||tagName.equals("DIV")||tagName.equals("MARR")){
		            	sCurrentLine = br.readLine();
		            	
						Matcher match = p.matcher(sCurrentLine);
						if(match.find()){
							props.put(tagName,match.group(3) );
						}	
		            	
		            }
		            
//		    		tagName = eligibleTags.contains(tagName) ? tagName : "Invalid tag";
//		            System.out.println("tag:\t" + tagName);
//		            System.out.println();
				}
				
		        
			}
			// Logic is to save Individual and family & its properties after next such object arrives
			// so the last object still remains to be added as after that we will not encounter object.
			// adding the last object to the list.
			if(props.size()>0){
				if(props.containsKey("FAM")){
        			Family fam = getFamily(props);
        			families.put(props.getProperty("FAM"), fam);
        		}
        		props.clear();
			}
			
			getDisplay();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
		
	private static Family getFamily(Properties props) {
		Family fam = new Family();
		ArrayList<Individual> children = new ArrayList<>();
		Enumeration<?> e = props.propertyNames();
	    while (e.hasMoreElements()) {
	        String key = (String) e.nextElement();
	        String value = props.getProperty(key);
	        switch(key){
	        case "FAM": 
	        	fam.setId(value);
	        	break;
	        
	        case "MARR":
	        	try{
		        	DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		        	Date marrDate = (Date)formatter.parse(value);
		        	fam.setMarrDate(marrDate);
	        	}catch(ParseException ex){
	        		ex.printStackTrace();
	        	}
	        	break;
	        case "DIV":
	        	try{
		        	DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		        	Date divDate = (Date)formatter.parse(value);
		        	fam.setDivorceDate(divDate);
	        	}catch(ParseException ex){
	        		ex.printStackTrace();
	        	}
	        	break;
	        case "HUSB":
	        	Individual husb= individuals.get(value);
	        	fam.setHusband(husb);
	        	break;
	        case "WIFE":
	        	Individual wife= individuals.get(value);
	        	fam.setWife(wife);
	        	break;
	        case "CHIL":
	        	String [] chil = value.split(",");
	        	for(String ch : chil){
	        		Individual child = individuals.get(ch);
		        	children.add(child);
	        	}
	        	fam.setChildren(children);
	        	break;
	        		
	        }
	    }
		return fam;
	}

	private static Individual getIndividual(Properties props) {
		Individual indi = new Individual();
		Enumeration<?> e = props.propertyNames();
	    while (e.hasMoreElements()) {
	        String key = (String) e.nextElement();
	        String value = props.getProperty(key);
	        switch(key){
	        case "INDI": 
	        	indi.setId(value);
	        	break;
	        case "NAME":
	        	indi.setName(value);
	        	break;
	        case "SEX":
	        	indi.setGender(value);
	        	break;	
	        case "FAMS":
	        	indi.setSpouse(value);
	        	break;
	        case "FAMC":
	        	indi.setChild(value);
	        	break;
	        case "DEAT":
	        	try{
		        	DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		        	Date deathDate = (Date)formatter.parse(value);
		        	indi.setDeathDate(deathDate);
		        	indi.setAlive(false);
	        	}catch(ParseException ex){
	        		ex.printStackTrace();
	        	}
	        	break;
	        case "BIRT":
	        	try{
		        	DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		        	Date birthDate = (Date)formatter.parse(value);
		        	indi.setBirthDate(birthDate);
		        	indi.setAge(getAge(birthDate));
	        	}catch(ParseException ex){
	        		ex.printStackTrace();
	        	}
	        	break;	
	        }
//	        System.out.println("Key : " + key + ", Value : " + value);
	    }
		return indi;
	}
	
	private static int getAge(Date birthDate) {
		Date dt1 = new Date();
		long diff = dt1.getTime()- birthDate.getTime();	
		long d = (1000*60*60*24);
		long expectedDays=diff/d;
		long years = (long) Math.floor(expectedDays/365.25);
		return (int) years;

	}
	private static Set<String> getEligibleTags() throws Exception {
		Set<String> tags = new HashSet<>();
		Scanner i = new Scanner(new File("./CLASSPATH/tags.txt"));
		while(i.hasNextLine()) {
			tags.add(i.nextLine());
		}
		i.close();
		return tags;
	}
	
	//updated display
	private static void getDisplay(){
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Individuals");
		Field [] fields = Individual.class.getDeclaredFields();
		for(Field field : fields){
			System.out.print(field.getName()+"\t\t\t");
		}
		for(Individual indi :individuals.values()){
			//indi.checkDates();
			String indent = "";
			System.out.println();
			System.out.print(indi.getId()+"\t\t");
			int total = (int) Math.ceil((double)16/indi.getName().length());
			for(int i = 0; i < total; i++){
				indent += "\t";
			}
			System.out.print(indi.getName()+"\t"+indent);
			indent = "";
			System.out.print(indi.getGender()+"\t\t\t");
			System.out.print(formattedDate.format(indi.getBirthDate())+"\t\t\t");
			System.out.print(indi.getAge()+"\t\t\t");
			System.out.print(indi.isAlive()+"\t\t\t");
			if(indi.getDeathDate()==null){
				System.out.print("NA \t\t\t");
			}else{
				System.out.print(formattedDate.format(indi.getDeathDate())+"\t\t");
			}
			System.out.print(indi.getChild()+"\t\t\t");
			System.out.print(indi.getSpouse()+"\t");
			
			
		}
		System.out.println();
		System.out.println("Families");
		Field [] famfields = Family.class.getDeclaredFields();
		
		for(Field field : famfields){
			if(field.getName().equals("Husband")){
				System.out.print("HusbandID \t\t HusbandName \t\t\t");
			}else if(field.getName().equals("Wife")){
				System.out.print("WifeID \t\t WifeName \t\t\t");
			}else if(field.getName().equals("Divorced")){
				System.out.print("Divorced \t\t");
			}else if(field.getName().equals("ID")){
				System.out.print("ID \t\t");
			}else
			System.out.print(field.getName()+"\t\t\t");
			
		}
		
		for(Family fam :families.values()){
			//fam.checkDates();
			String indent = "";
			System.out.println();
			System.out.print(fam.getId()+"\t\t");
			if(fam.getMarrDate()==null){
				System.out.print("NA \t\t\t");
			}else{
				System.out.print(formattedDate.format(fam.getMarrDate())+"\t\t");
			}
			if(fam.getDivorceDate()==null){
				System.out.print("NA \t\t\t");
			}else{
				System.out.print(formattedDate.format(fam.getDivorceDate())+"\t\t");
			}
			Individual husb = fam.getHusband();
			int total = (int) Math.ceil((double)16/husb.getName().length());
			for(int i = 0; i < total; i++){
				indent += "\t";
			}
			System.out.print(husb.getId()+"\t\t\t"+husb.getName()+"\t"+indent);
			indent = "";
			Individual wife = fam.getWife();
			total = (int) Math.ceil((double)16/wife.getName().length());
			for(int i = 0; i < total; i++){
				indent += "\t";
			}
			System.out.print(wife.getId()+"\t\t"+wife.getName()+"\t"+indent);
			indent = "";
			if(fam.getChildren()!=null){
				List<Individual> children = fam.getChildren();
				int count =0;
				for(Individual child :children ){
					
					System.out.print(child.getId());
					if(count<children.size()-1){
						System.out.print(",");
					}
					count++;
				}
			}else{
				System.out.print("NA \t");
			}
		}
		System.out.println();
		System.out.println();
		ValidateDates validator = new ValidateDates();
		validator.validateIndividualAndFamily(individuals, families);
	}

}