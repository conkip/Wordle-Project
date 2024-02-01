/**
 * This will create all of the unit testing that is needed 
 * for the Guess class.
 * 
 * Date: 4/25/2023
 * 
 * @author Luke Laurie, Connor Kippes, Sean Eddy Zachary Hansen 
 */

package src.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.model.Dictionary;
import src.model.Guess;
import src.model.Word;
import src.view.WordleGUI;

class GuessTest {
	private Guess curGuess;
	private Word curWord = Word.getInstance(5);
	private ArrayList<String> word;
	

	@Test
	void testEmptyGuess() {
		curGuess = new Guess(5);
		assertEquals(0, curGuess.getGuessNumber());
		assertEquals("[]", curGuess.getCurGuess().toString());
	}

	@Test
	void testAddLetter() {
		curGuess = new Guess(5);
		curGuess.addLetter("a");  
		curGuess.addLetter("b");
		curGuess.addLetter("c");
		assertEquals("[a, b, c]", curGuess.getCurGuess().toString());
		curGuess.addLetter("d");
		curGuess.addLetter("e");
		assertEquals("[a, b, c, d, e]", curGuess.getCurGuess().toString());
		curGuess.addLetter("f");
		assertEquals("[a, b, c, d, e]", curGuess.getCurGuess().toString());
	}

	@Test
	void testRemoveLetter() {
		curGuess = new Guess(5);
		curGuess.addLetter("a");
		curGuess.addLetter("b");
		curGuess.addLetter("c");
		curGuess.removeLetter();
		curGuess.removeLetter();
		assertEquals("[a]", curGuess.getCurGuess().toString());
		curGuess.removeLetter();
		curGuess.removeLetter();
		assertEquals("[]", curGuess.getCurGuess().toString());
	}

	@Test
	void testCanGuessOne() {
		curGuess = new Guess(5);
		curGuess.addLetter("t");
		curGuess.addLetter("e");
		curGuess.addLetter("y");
		curGuess.addLetter("t");
		assertFalse(curGuess.canGuess(curGuess.getCurGuess()));
	}

	@Test
	void testCanGuessTwo() {
		curGuess = new Guess(5);
		curGuess.addLetter("b");
		curGuess.addLetter("u");
		curGuess.addLetter("i");
		curGuess.addLetter("l");
		curGuess.addLetter("d");
		assertTrue(curGuess.canGuess(curGuess.getCurGuess()));
	}

	@Test
	void testCanGuessThree() {
		curGuess = new Guess(5);
		curGuess.addLetter("a");
		curGuess.addLetter("b");
		curGuess.addLetter("c");
		curGuess.addLetter("d");
		curGuess.addLetter("e");
		assertFalse(curGuess.canGuess(curGuess.getCurGuess()));
	}

	@Test
	void testguessNumber() {
		curGuess = new Guess(5);
		curGuess.addLetter("b");
		curGuess.addLetter("u");
		curGuess.addLetter("i");
		curGuess.addLetter("l");
		curGuess.addLetter("d");
		curGuess.makeGuess(curGuess.getCurGuess(), 5);
		curGuess.makeGuess(curGuess.getCurGuess(), 5);
		assertEquals(2, curGuess.getGuessNumber());
	}

	@Test
	void testMakeGuessOne() {
		curGuess = new Guess(5);
		curGuess.setColSize(5);
		curGuess.setNonContrastMode();
		curGuess.setNonDarkMode();
		Word curWord = Word.getInstance(5);
		ArrayList<String> wordInfo = curWord.getCurWord(5, 5);
		ArrayList<String> placements = curGuess.makeGuess(wordInfo, 5);
		assertEquals("Green", placements.get(0));
		assertEquals("Green", placements.get(1));
		assertEquals("Green", placements.get(2));
		assertEquals("Green", placements.get(3));
		assertEquals("Green", placements.get(4));
	}

	@Test
	void testMakeGuessTwo() {
		curGuess = new Guess(5);
		setData("think", "thank");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Green", placements.get(0));
		assertEquals("Green", placements.get(1));
		assertEquals("Dark Gray", placements.get(2));
		assertEquals("Green", placements.get(3));
		assertEquals("Green", placements.get(4));
	}

	@Test
	void testMakeGuessThree() {
		curGuess = new Guess(5);
		setData("smart", "thank");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Yellow", placements.get(0));
		assertEquals("Dark Gray", placements.get(1));
		assertEquals("Green", placements.get(2));
		assertEquals("Dark Gray", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
	}

	@Test
	void testContrastColor() {
		curGuess = new Guess(5);
		curGuess.setContrastMode();
		setData("smart", "thank");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Blue", placements.get(0));
		assertEquals("Dark Gray", placements.get(1));
		assertEquals("Orange", placements.get(2));
		assertEquals("Dark Gray", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
	}

	@Test
	void testDarkMode() {
		curGuess = new Guess(5);
		curGuess.setDarkMode();
		setData("smart", "thank");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Yellow", placements.get(0));
		assertEquals("dm Dark Gray", placements.get(1));
		assertEquals("Green", placements.get(2));
		assertEquals("dm Dark Gray", placements.get(3));
		assertEquals("dm Dark Gray", placements.get(4));
	}

	@Test
	void testWrongPlace1() {
		curGuess = new Guess(5);
		setData("poked", "green");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Dark Gray", placements.get(0));
		assertEquals("Dark Gray", placements.get(1));
		assertEquals("Dark Gray", placements.get(2));
		assertEquals("Green", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
	}

	@Test
	void testWrongPlace2() {
		curGuess = new Guess(5);
		setData("bedew", "green");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Dark Gray", placements.get(0));
		assertEquals("Dark Gray", placements.get(1));
		assertEquals("Yellow", placements.get(2));
		assertEquals("Green", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
	}
	@Test
	void testWrongPlace3() {
		curGuess = new Guess(5);
		setData("jazzy", "pzazz");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Dark Gray", placements.get(0));
		assertEquals("Yellow", placements.get(1));
		assertEquals("Yellow", placements.get(2));
		assertEquals("Green", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
	}
	
	@Test
	void testWrongPlace4() {
		curGuess = new Guess(5);
		setData("squiz", "tazza");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals("Dark Gray", placements.get(0));
		assertEquals("Dark Gray", placements.get(1));
		assertEquals("Yellow", placements.get(2));
		assertEquals("Dark Gray", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
	}
	
	@Test
	void testWrongPlace5() {
		curGuess = new Guess(7);
		setData("fuzzbox", "haboobs");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		System.out.println(placements);
		assertEquals("Dark Gray", placements.get(0));
		assertEquals("Dark Gray", placements.get(1));
		assertEquals("Yellow", placements.get(2));
		assertEquals("Yellow", placements.get(3));
		assertEquals("Dark Gray", placements.get(4));
		assertEquals("Dark Gray", placements.get(5));
		assertEquals("Dark Gray", placements.get(6));
	}
	
	@Test
	void testGuessColors() {
		curGuess = new Guess(5);
		setData("hello", "solid");
		ArrayList<String> placements = curGuess.makeGuess(word, 5);
		assertEquals(placements, curGuess.getCurGuessColors());
		curGuess.setDarkMode();
		ArrayList<String> placements2 = curGuess.makeGuess(word, 5);
		assertEquals(placements2, curGuess.getCurGuessColors());
	}

	@Test
	void testInvalidWord() {
		curGuess = new Guess(5);
		ArrayList<String> invalidGuess = new ArrayList<>();
		invalidGuess.add("h");
		invalidGuess.add("i");
		invalidGuess.add("i");
		ArrayList<String> placements = curGuess.makeGuess(invalidGuess, 5);
		assertEquals(null, placements);
	}

	void setData(String correctWord, String guessedWord) {
		// sets custom word
		ArrayList<String> correctWordList = new ArrayList<>();
		word = new ArrayList<>();
		for (int i = 0; i < correctWord.length(); i++) {
			String charOne = Character.toString(correctWord.charAt(i));
			String charTwo = Character.toString(guessedWord.charAt(i));
			correctWordList.add(charOne);
			word.add(charTwo);
		}
		curWord.setNewWord(correctWordList);
		curGuess.setWord(curWord);
	}
}