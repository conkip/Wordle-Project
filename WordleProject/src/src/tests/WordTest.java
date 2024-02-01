/**
 * This will create all of the unit testing that is needed 
 * for the Word class.
 * Author: Luke Laurie, Connor Kippes, Sean Eddy Zachary Hansen 
 * Date: 4/25/2023
 */

package src.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import src.model.Dictionary;
import src.model.Word;

class WordTest {
	private Word curWord;

	@Test
	void testGetCurDay() {
		curWord = Word.getInstance(5);
		assertEquals(LocalDate.now().toString(), curWord.getCurDate());
	}
  
	@Test
	void testGetCurWord() {
		curWord = Word.getInstance(5);
		assertEquals(5, curWord.getCurWord(5, 5).size());
	}

	@Test
	void testNewDayOne() {
		curWord = Word.getInstance(5);
		curWord.setNewDay();
		curWord.updateTime();
		assertFalse(curWord.checkNewDate());
	}

	@Test
	void testNewDayTwo() {
		curWord = Word.getInstance(5);
		curWord.setPastDate();
		assertTrue(curWord.checkNewDate());
		curWord.updateTime();
	}
	
	@Test
	void testSetWord() {
		curWord = Word.getInstance(4);
		ArrayList<String> word = new ArrayList<>();
		word.add("b");
		word.add("u");
		word.add("n");
		word.add("t");
		curWord.setNewWord(word);
		assertEquals(word, curWord.getCurWord(4, 5));
	}
  
	@Test
	void testNewDay() {
		curWord = Word.getInstance(5);
		assertEquals(5, curWord.getCurWord(5, 5).size());
	}
	
	@Test
	void testNullDate() {
		curWord = Word.getInstance(5);
		curWord.setCustomValues(null, null);
		curWord.saveDate();
		curWord.saveWord();
		curWord.readDate();
		curWord.readWord(); 
		assertNotEquals(null, curWord.getCurDate());
		assertNotEquals(null, curWord.getCurWord(5, 5));
	}
	
	@Test
	void testOtherFeatures() {
		curWord = Word.getInstance(5);
		curWord.getDict(); 
		curWord.setColSize(4);
		assertEquals(4, curWord.getColSize());
	}
	
	@Test
	void other()
	{
		curWord = Word.getInstance(4);
		curWord.getDict();
	}
}