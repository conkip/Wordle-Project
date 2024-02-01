/**
 * This will create all of the unit testing that is needed 
 * for the WordleUser class.
 * 
 * Date: 4/25/2023
 * 
 * @author Luke Laurie, Connor Kippes, Sean Eddy Zachary Hansen
 */

package src.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Currency;

import org.junit.jupiter.api.Test;

import src.model.Guess;
import src.model.WordleUser;

class WordleUserTest {
	private WordleUser curUser;
	ArrayList<ArrayList<String>> curGuess;
	

	void setupGuess() { 
		curGuess = new ArrayList<>();
		ArrayList<String> tempList = new ArrayList<>();
		tempList.add("C");
		tempList.add("R");
		tempList.add("A");
		tempList.add("N");
		tempList.add("E");
		curGuess.add(tempList);
		tempList = new ArrayList<>();
		tempList.add("K");
		tempList.add("I");
		tempList.add("N");
		tempList.add("D");
		tempList.add("S");
		curGuess.add(tempList);
		tempList = new ArrayList<>();
		tempList.add(" ");
		tempList.add(" ");
		tempList.add(" ");
		tempList.add(" ");
		tempList.add(" ");
		curGuess.add(tempList);
		curGuess.add(tempList);
		curGuess.add(tempList);
		curGuess.add(tempList);
	}
	@Test
	void testNoData() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		assertEquals(0, curUser.getGamesPlayed());
		assertEquals(0, curUser.getWinCount());
		assertEquals(0, curUser.getLossCount());
		assertEquals(0, curUser.getWinRate());
		assertEquals(0, curUser.getCurStreak());
		assertEquals(6, curUser.getGuesses(6, 5).size());
	}

	@Test
	void testOneWin() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		curUser.reportGame(true, 6, 5);
		assertEquals(1, curUser.getGamesPlayed());
		assertEquals(1, curUser.getWinCount());
		assertEquals(0, curUser.getLossCount());
		assertEquals(1, curUser.getWinRate());
		assertEquals(1, curUser.getCurStreak());
	}

	@Test
	void testOneLoss() {
		curUser = new WordleUser("email", "password");
		curUser.reportGame(false, 6, 5);
		assertEquals(1, curUser.getGamesPlayed());
		assertEquals(0, curUser.getWinCount());
		assertEquals(1, curUser.getLossCount());
		assertEquals(0, curUser.getWinRate());
		assertEquals(0, curUser.getCurStreak());
	}

	@Test
	void testTwoWinStreak() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		// two win streak
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		assertEquals(2, curUser.getGamesPlayed());
		assertEquals(2, curUser.getWinCount());
		assertEquals(0, curUser.getLossCount());
		assertEquals(1, curUser.getWinRate());
		assertEquals(2, curUser.getCurStreak());
	}

	@Test
	void testWinStreakEnd() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		// win streak ends
		curUser.reportGame(false, 6, 5);
		assertEquals(3, curUser.getGamesPlayed());
		assertEquals(2, curUser.getWinCount());
		assertEquals(1, curUser.getLossCount());
		assertEquals(0, curUser.getCurStreak());
	}

	@Test
	void testWinRate() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(false, 6, 5);
		curUser.reportGame(false, 6, 5);
		curUser.reportGame(true, 6, 5);
		assertEquals(4, curUser.getGamesPlayed());
		assertEquals(2, curUser.getWinCount());
		assertEquals(2, curUser.getLossCount());
		assertEquals(1, curUser.getCurStreak());
		assertEquals(0.5, curUser.getWinRate());
	}

	@Test
	void testMaxStreak() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(false, 6, 5);
		curUser.reportGame(false, 6, 5);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		assertEquals(3, curUser.getMaxStreak());
	}

	@Test
	void testNoGuesses() {
		curUser = new WordleUser("email", "password");
		assertEquals(6, curUser.getGuesses(6, 5).size());
	}

	@Test
	void testguessDistributionOne() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		curUser.reportGame(true, 6, 5);
		assertEquals(1, curUser.getGuessDistribution(6, 5).get(2));
		assertEquals(0, curUser.getGuessDistribution(6, 5).get(1));
	}

	@Test
	void testguessDistributionTwo() {
		curUser = new WordleUser("email", "password");
		setupGuess();
		curUser.setGuesses(curGuess);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		curUser.reportGame(true, 6, 5);
		assertEquals(3, curUser.getGuessDistribution(6, 5).get(2));
		assertEquals(0, curUser.getGuessDistribution(6, 5).get(1));
		assertEquals(0, curUser.getGuessDistribution(6, 5).get(3));
	}

	@Test
	void testLoginInfo() {
		curUser = new WordleUser("email", "password");
		assertEquals("email", curUser.getEmail());
		assertEquals("password", curUser.getPassword()); 
	}
	
	@Test 
	void testColoring() {
		curUser = new WordleUser("email", "password");
		curUser.setDarkMode(true);
		curUser.setContrastMode(false);
		assertTrue(curUser.isDarkMode());
		assertFalse(curUser.isContrastMode());
	}

}