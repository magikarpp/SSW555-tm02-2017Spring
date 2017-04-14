package edu.stevens.cs555.tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import edu.stevens.cs555.Family;
import edu.stevens.cs555.Individual;
import edu.stevens.cs555.utils.Validate;
import edu.stevens.cs555.utils.ValidateDates;

public class TestCaseforDates {

	Family fam = null;
	Individual hus = null;
	Individual wif = null;
	Individual child1 = null;
	Individual child2 = null;
	Individual child3 = null;
	ArrayList<Individual> children = new ArrayList<Individual>();
	Individual indo = null;
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
	final ValidateDates validator = new ValidateDates();

	public TestCaseforDates() {
		fam = new Family();
		hus = new Individual();
		wif = new Individual();
		child1 = new Individual();
		child2 = new Individual();
		child3 = new Individual();
		child1.setName("Test Child");
		children.add(child1);
		children.add(child2);
		children.add(child3);
		indo = new Individual();

		fam.setId("F20");
		hus.setId("I1");
		wif.setId("I2");
		child1.setId("I3");
		child2.setId("I5");
		child3.setId("I6");
		indo.setId("I4");

	}

	@Test
	public void testDateNotNull() {
		fam.setDivorceDate(new Date());
		assertTrue(Validate.noNulls(fam.getDivorceDate()));
	}

	@Test
	public void testDateNull() {
		assertFalse(Validate.noNulls(fam.getDivorceDate()));
	}

	// USO1 TestCase
	@Test
	public void testBirthAfterCurrentDate() {
		String birth = "3048-NOV-11";

		try {
			indo.setBirthDate(dt.parse(birth));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isBirthBeforeCurrent(indo));
	}

	@Test
	public void testDeathAfterCurrentDate() {
		String death = "3048-NOV-11";

		try {
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isDeathBeforeCurrent(indo));
	}

	@Test
	public void testMarrAfterCurrentDate() {
		String marriage = "3048-NOV-11";

		try {
			fam.setMarrDate(dt.parse(marriage));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isMarrBeforeCurrent(fam));
	}

	@Test
	public void testDivAfterCurrentDate() {
		String divorce = "3048-NOV-11";

		try {
			fam.setDivorceDate(dt.parse(divorce));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isDivBeforeCurrent(fam));
	}

	@Test
	public void testBirthMarriageSameDay() {
		String birth = "2010-DEC-16";
		String marriage = "2010-DEC-16";
		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.getHusband().setBirthDate(dt.parse(birth));
			fam.getWife().setBirthDate(dt.parse(birth));
			fam.setMarrDate(dt.parse(marriage));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(validator.isMarrAfterBirth(fam));
	}

	@Test
	public void testBirthAfterMarriage() {
		String birth = "2010-DEC-20";
		String marriage = "2010-DEC-16";
		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.getHusband().setBirthDate(dt.parse(birth));
			fam.getWife().setBirthDate(dt.parse(birth));
			fam.setMarrDate(dt.parse(marriage));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(validator.isMarrAfterBirth(fam));
	}

	// USO3 TestCase
	@Test
	public void testBirthDeathSameDay() {
		String birth = "2016-OCT-25";
		String death = "2016-OCT-25";
		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(validator.isBirthBeforeDeath(indo));

	}

	@Test
	public void testBirthAfterDeath() {
		String birth = "2016-OCT-26";
		String death = "2016-OCT-25";
		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(validator.isBirthBeforeDeath(indo));

	}

	@Test
	public void testBirthBeforeDeath() {
		String birth = "1989-SEP-13";
		String death = "2016-OCT-25";
		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(validator.isBirthBeforeDeath(indo));

	}

	// US04 TestCase
	@Test
	public void testDivorceBeforeMarr() {
		String marriage = "2016-AUG-10";
		String divorce = "2016-JAN-13";
		try {
			fam.setMarrDate(dt.parse(marriage));
			fam.setDivorceDate(dt.parse(divorce));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertFalse(validator.isMarriageBeforeDivorce(fam));
	}

	@Test
	public void testNoMarrDivor() {
		String divorce = "2016-JAN-13";
		try {
			fam.setDivorceDate(dt.parse(divorce));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertFalse(validator.isMarriageBeforeDivorce(fam));
	}

	@Test
	public void testMarrBeforeDivorce() {

		String marriage = "2016-AUG-10";
		String divorce = "2017-JAN-24";
		try {
			fam.setMarrDate(dt.parse(marriage));
			fam.setDivorceDate(dt.parse(divorce));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertTrue(validator.isMarriageBeforeDivorce(fam));
	}

	// US05 TestCase
	@Test
	public void testMarrBeforeDeath() {
		String marriage = "2016-AUG-10";
		String death1 = "2014-JAN-24";
		String death2 = "2018-FEB-18";

		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setMarrDate(dt.parse(marriage));
			fam.getHusband().setDeathDate(dt.parse(death1));
			fam.getWife().setDeathDate(dt.parse(death2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isMarriageBeforeDeath(fam));
	}

	// US06 TestCase
	@Test
	public void testDivBeforeDeath() {
		String divorce = "2016-AUG-10";
		String death1 = "2014-JAN-24";
		String death2 = "2018-FEB-18";

		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setDivorceDate(dt.parse(divorce));
			fam.getHusband().setDeathDate(dt.parse(death1));
			fam.getWife().setDeathDate(dt.parse(death2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isDivorceBeforeDeath(fam));
	}

	//US07 TestCase

	@Test
	public void testBirthLessThan150Years() {
		String birth = "1800-OCT-25";

		try {
			indo.setBirthDate(dt.parse(birth));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(validator.isLessThan150Years(indo));
	}
	@Test
	public void testDeathLessThan150Years() {
		String birth = "1800-OCT-25";
		String death = "2000-SEP-13";

		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(validator.isLessThan150Years(indo));
	}
	//US08 Testcase
	@Test
	public void testChildBirthBeforeMarr(){
		String marriage = "2016-AUG-10";
		String childBirth ="2015-AUG-15";
		
		try{
			fam.setMarrDate(dt.parse(marriage));
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setChildren(children);
			fam.getChildren().get(0).setBirthDate(dt.parse(childBirth));
		}catch(ParseException e){
			e.printStackTrace();
		}
		assertFalse(validator.isChildBirthBeforeParentsMarr(fam));
	}

	// US09 TestCase
	@Test
	
	public void testChildBirthBeforeParentDeath(){
		String birth = "2018-FEB-20";
		String death1 = "2018-JAN-24";
		String death2 = "2018-FEB-19";

		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setChildren(children);
			fam.getChildren().get(0).setBirthDate(dt.parse(birth));
			fam.getHusband().setDeathDate(dt.parse(death1));
			fam.getWife().setDeathDate(dt.parse(death2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		assertFalse(validator.isChildBirthBeforeParentDeath(fam));
	}

	// US10 TestCase
	@Test
	public void testMarriagebefore14() {
		String marr = "2012-FEB-18";
		String birth1 = "2018-JAN-24";
		String birth2 = "2018-FEB-19";

		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setMarrDate(dt.parse(marr));
			fam.getHusband().setBirthDate(dt.parse(birth1));
			fam.getWife().setBirthDate(dt.parse(birth2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		assertFalse(validator.isMarriageAfter14(fam));
	}
	//US11 Testcase
	@Test
	public void testBigamyWithoutDivorce(){
		HashMap<String,Family> families =new HashMap<>();
		String firstMarr ="2010-Apr-24";
		String secondMarr ="2013-Feb-14";
		
		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setMarrDate(dt.parse(firstMarr));
			
			Family fam2 = new Family();
			fam2.setId("I6");
			fam2.setHusband(hus);
			fam2.setWife(indo);
			fam2.setMarrDate(dt.parse(secondMarr));
			families.put(fam.getId(), fam);
			families.put(fam2.getId(), fam2);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertFalse(validator.isBigamy(families));
	}
	@Test
	public void testBigamyWithDivorce(){
		HashMap<String,Family> families =new HashMap<>();
		String firstMarr ="2010-Aug-15";
		String secondMarr ="2013-May-14";
		String firstDivorce ="2015-Jul-18";
		
		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setMarrDate(dt.parse(firstMarr));
			fam.setDivorceDate(dt.parse(firstDivorce));
			
			Family fam2 = new Family();
			fam2.setId("I6");
			fam2.setHusband(hus);
			fam2.setWife(indo);
			fam2.setMarrDate(dt.parse(secondMarr));
			families.put(fam.getId(), fam);
			families.put(fam2.getId(), fam2);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertFalse(validator.isBigamy(families));
	}
	@Test
	public void testBigamyWithManyMarriages(){
		HashMap<String,Family> families =new HashMap<>();
		String firstMarr ="2010-Jan-2";
		String secondMarr ="2013-Oct-14";
		String firstDivorce ="2015-Mar-25";
		String thirdMarr ="2016-Sep-26";
		
		try {
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setMarrDate(dt.parse(firstMarr));
			fam.setDivorceDate(dt.parse(firstDivorce));
			
			Family fam2 = new Family();
			fam2.setId("I6");
			fam2.setHusband(hus);
			fam2.setWife(indo);
			fam2.setMarrDate(dt.parse(secondMarr));
			
			Family fam3 = new Family();
			fam3.setId("I7");
			fam3.setHusband(hus);
			Individual wife = new Individual();
			wife.setId("I27");
			fam3.setWife(wife);
			fam3.setMarrDate(dt.parse(thirdMarr));
			
			families.put(fam.getId(), fam);
			families.put(fam2.getId(), fam2);
			families.put(fam3.getId(), fam3);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		assertFalse(validator.isBigamy(families));
	}
	@Test
	public void testParentsNotTooOld(){
		String motherBirthDate ="1916-SEP-19";
		String fatherBirthDate = "1910-JUL-18";
		String childBirthdate ="2010-MAY-04";
		
		
		try{
			fam.setHusband(hus);
			fam.setWife(wif);
			fam.setChildren(children);
			child1.setBirthDate(dt.parse(childBirthdate));
			
			
			hus.setBirthDate(dt.parse(fatherBirthDate));
			wif.setBirthDate(dt.parse(motherBirthDate));
		
		}catch(Exception e){
			e.printStackTrace();
		}
		assertFalse(validator.isParentsTooOlder(fam));
	}
	
	//US13
	@Test
	public void TestSiblingAgeSpacing(){
		String s1 ="1990-SEP-19";
		String s2 = "1990-SEP-28";
		String s3 ="1889-MAY-04";
		
		try{
			fam.setChildren(children);
			child1.setBirthDate(dt.parse(s1));
			child2.setBirthDate(dt.parse(s2));
			child3.setBirthDate(dt.parse(s3));
		}catch(Exception e){
			e.printStackTrace();
		}
		assertFalse(validator.isSiblingsAgeApart(fam));
	}
	
	//US14
	@Test
	public void Test5SiblingAge(){
		String s1 ="1990-SEP-19";
		try{
			children.add(child1);
			children.add(child2);
			children.add(child3);
			fam.setChildren(children);
			child1.setBirthDate(dt.parse(s1));
			child2.setBirthDate(dt.parse(s1));
			child3.setBirthDate(dt.parse(s1));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		assertFalse(validator.isLessThan5SameBirths(fam));
	}
}
