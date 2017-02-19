import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GedComParser {	
	public static void main(String[] args) throws Exception {
		if(args.length!=1){
			System.out.println("Please include the absolute file path as the first and only command line. Format: GedComParser < Ged File > ");
			System.exit(0);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

			String sCurrentLine;
			
			Set<String> eligibleTags = getEligibleTags();
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println("line:\t" + sCurrentLine);
				
				String REGEX = "(^\\d+)\\s(\\W?\\w+\\W?)\\s?(.*)";
				Pattern p = Pattern.compile(REGEX);
				Matcher m = p.matcher(sCurrentLine); //get a matcher object
				int count = 0;
				while(m.find()){
					count++;
				
		            String level = m.group(1);
		            System.out.println("level:\t" + level);
		            
		            String tagName = m.group(2).trim();
		            if(m.group(3)!=null){
			            if(m.group(3).equals("INDI")||m.group(3).equals("FAM")) {
			            	tagName = m.group(3);
			            }
		            }
		    		tagName = eligibleTags.contains(tagName) ? tagName : "Invalid tag";
		            System.out.println("tag:\t" + tagName);
		            System.out.println();
				}
		        
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End of parsing");
	
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
}