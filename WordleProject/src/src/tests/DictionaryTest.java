/**
 * This will create all of the unit testing that is needed 
 * for the Dictionary class.
 * Author: Luke Laurie, Connor Kippes, Sean Eddy Zachary Hansen 
 * Date: 4/25/2023
 */

package src.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import src.model.Dictionary;

class DictionaryTest {
	@Test
	void testRandomWord() {
		Dictionary curDict = new Dictionary();
		assertEquals(5, curDict.getRandomWord(5).size());
	}

	@Test
	void testGetRandomWord() {
		Dictionary curDict = new Dictionary();
		assertEquals(6, curDict.getRandomWord(6).size());
	}
	
	@Test
	void testReadFile()
	{
		Dictionary curDict = new Dictionary();
		curDict.readFile("dwnadkwajd");
		curDict.readFile("possible");
	}

	@Test
	void testValidWordOne() {
		Dictionary curDict = new Dictionary();
		ArrayList<String> word1 = new ArrayList<>();
		word1.add("t");
		word1.add("h");
		word1.add("a");
		word1.add("n");
		word1.add("k");
		assertTrue(curDict.isValidWord(word1));
		word1.remove(4);
		word1.remove(3);
		assertFalse(curDict.isValidWord(word1));
		ArrayList<String> word2 = new ArrayList<>();
		word2.add("a");
		word2.add("b");
		word2.add("c");
		word2.add("d");
		word2.add("e");
		assertFalse(curDict.isValidWord(word2));
	}
}