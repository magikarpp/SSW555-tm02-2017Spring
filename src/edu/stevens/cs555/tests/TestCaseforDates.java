package edu.stevens.cs555.tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import edu.stevens.cs555.Family;
import edu.stevens.cs555.Individual;
import edu.stevens.cs555.utils.Validate;
import edu.stevens.cs555.utils.ValidateDates;

public class TestCaseforDates {

	Family fam = new Family();
	Individual indo = new Individual();
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd");
	
	public TestCaseforDates(){
		fam.setId("F20");
		indo.setId("I12");
	}
	
	
	@Test
	public void testDateNotNull() {
		fam.setDivorceDate(new Date());
		assertTrue(Validate.noNulls(fam.getDivorceDate()));
	}
	
	@Test
	public void testDateNull(){
		assertFalse(Validate.noNulls(fam.getDivorceDate()));
	}
	
	//USO1 TestCase
	@Test
	public void testBirthAfterCurrentDate(){
		String birth = "3048-NOV-11";
		
		try{
			indo.setBirthDate(dt.parse(birth));
		} catch (ParseException e){
			e.printStackTrace();
		}
		
		assertFalse(ValidateDates.isBirthBeforeCurrent(indo));
	}
	
	@Test
	public void testDeathAfterCurrentDate(){
		String death = "3048-NOV-11";
		
		try{
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e){
			e.printStackTrace();
		}
		
		assertFalse(ValidateDates.isDeathBeforeCurrent(indo));
	}
	
	@Test
	public void testMarrAfterCurrentDate(){
		String marriage = "3048-NOV-11";
		
		try{
			fam.setMarrDate(dt.parse(marriage));
		} catch (ParseException e){
			e.printStackTrace();
		}
		
		assertFalse(ValidateDates.isMarrBeforeCurrent(fam));
	}
	
	@Test
	public void testDivAfterCurrentDate(){
		String divorce = "3048-NOV-11";
		
		try{
			fam.setMarrDate(dt.parse(divorce));
		} catch (ParseException e){
			e.printStackTrace();
		}
		
		assertFalse(ValidateDates.isDivBeforeCurrent(fam));
	}
	
	@Test
	public void testBirthMarriageSameDay(){
		String birth="2010-DEC-16";
		String marriage ="2010-DEC-16";
		try {
			fam.getHusband().setBirthDate(dt.parse(birth));
			fam.getWife().setBirthDate(dt.parse(birth));
			fam.setMarrDate(dt.parse(marriage));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(ValidateDates.isMarrAfterBirth(fam));
	}
	
	@Test
	public void testBirthAfterMarriage(){
		String birth="2010-DEC-20";
		String marriage ="2010-DEC-16";
		try {
			fam.getHusband().setBirthDate(dt.parse(birth));
			fam.getWife().setBirthDate(dt.parse(birth));
			fam.setMarrDate(dt.parse(marriage));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(ValidateDates.isMarrAfterBirth(fam));
	}
	
	//USO3 TestCase
	@Test
	public void testBirthDeathSameDay(){
		String birth="2016-OCT-25";
		String death ="2016-OCT-25";
		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(ValidateDates.isBirthBeforeDeath(indo));
		
	}
	@Test
	public void testBirthAfterDeath(){
		String birth="2016-OCT-26";
		String death ="2016-OCT-25";
		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(ValidateDates.isBirthBeforeDeath(indo));
		
	}
	@Test
	public void testBirthBeforeDeath(){
		String birth="1989-SEP-13";
		String death ="2016-OCT-25";
		try {
			indo.setBirthDate(dt.parse(birth));
			indo.setDeathDate(dt.parse(death));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(ValidateDates.isBirthBeforeDeath(indo));
		
	}
	//US04 TestCase
	@Test
	public void testDivorceBeforeMarr(){
		String marriage="2016-AUG-10";
		String divorce ="2016-JAN-13";
		try{
			fam.setMarrDate(dt.parse(marriage));
			fam.setDivorceDate(dt.parse(divorce));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		assertFalse(ValidateDates.isMarriageBeforeDivorce(fam));
	}
	@Test
	public void testNoMarrDivor(){
		String divorce ="2016-JAN-13";
		try{
			fam.setDivorceDate(dt.parse(divorce));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		assertFalse(ValidateDates.isMarriageBeforeDivorce(fam));
	}
	
	@Test
	public void testMarrBeforeDivorce(){
		
		String marriage="2016-AUG-10";
		String divorce ="2017-JAN-24";
		try{
			fam.setMarrDate(dt.parse(marriage));
			fam.setDivorceDate(dt.parse(divorce));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		assertTrue(ValidateDates.isMarriageBeforeDivorce(fam));
	}
	
	//US05 TestCase
	@Test
	public void testMarrBeforeDeath(){
		String marriage= "2016-AUG-10";
		String death1 = "2014-JAN-24";
		String death2 = "2018-FEB-18";
		
		try{
			fam.setMarrDate(dt.parse(marriage));
			fam.getHusband().setDeathDate(dt.parse(death1));
			fam.getWife().setDeathDate(dt.parse(death2));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		assertFalse(ValidateDates.isMarriageBeforeDeath(fam));
	}
	
	//US06 TestCase
	@Test
	public void testDivBeforeDeath(){
		String divorce= "2016-AUG-10";
		String death1 = "2014-JAN-24";
		String death2 = "2018-FEB-18";
		
		try{
			fam.setDivorceDate(dt.parse(divorce));
			fam.getHusband().setDeathDate(dt.parse(death1));
			fam.getWife().setDeathDate(dt.parse(death2));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		assertFalse(ValidateDates.isDivorceBeforeDeath(fam));
	}
	
}
