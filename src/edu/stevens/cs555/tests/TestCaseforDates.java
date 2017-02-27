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
	public void testDatesAfterCurrentDate(){
		String birth = "3048-NOV-11";
		String death = "3048-NOV-11";
		String marriage = "3048-NOV-11";
		String divorce = "3048-NOV-11";
		
		try{
			indo.setBirthDate(dt.parse(birth));
			indo.setBirthDate(dt.parse(death));
			fam.setMarrDate(dt.parse(marriage));
			fam.setDivorceDate(dt.parse(divorce));
		} catch (ParseException e){
			e.printStackTrace();
		}
		
		assertFalse(ValidateDates.isMarrBeforeCurrent(fam));
		assertFalse(ValidateDates.isDivBeforeCurrent(fam));
		assertFalse(ValidateDates.isBirthBeforeCurrent(indo));
		assertFalse(ValidateDates.isDeathBeforeCurrent(indo));
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

}
