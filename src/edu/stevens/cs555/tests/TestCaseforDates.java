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
