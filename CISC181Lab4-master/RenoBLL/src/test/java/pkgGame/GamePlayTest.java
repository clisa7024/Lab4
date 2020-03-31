package pkgGame;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import pkgCore.Card;
import pkgCore.GamePlay;
import pkgCore.HandPoker;
import pkgCore.Player;
import pkgCore.Rule;
import pkgCore.Table;
import pkgEnum.eGame;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;
import pkgHelper.GamePlayHelper;
import pkgHelper.HandPokerHelper;

public class GamePlayTest {

	@Test
	public void GamePlay_Test1() {
		Table t = new Table("Table 1");
		t.AddPlayerToTable(new Player("Bert"));
		t.AddPlayerToTable(new Player("Joe"));
		t.AddPlayerToTable(new Player("Jim"));
		t.AddPlayerToTable(new Player("Jane"));

		Rule rle = new Rule(eGame.TexasHoldEm);
		GamePlay gp = new GamePlay(t, rle);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(eSuit.HEARTS, eRank.TWO));
		cards.add(new Card(eSuit.HEARTS, eRank.THREE));
		
		Player p1 = new Player("Bert");
		Player p2 = new Player("Joe");
		
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		cards.add(new Card(eSuit.HEARTS, eRank.ACE));
		cards.add(new Card(eSuit.DIAMONDS, eRank.ACE));
		
		ArrayList<Card> commonCards = new ArrayList<Card>();
		commonCards.add(new Card(eSuit.HEARTS, eRank.THREE));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FOUR));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FIVE));
		
		gp = GamePlayHelper.setCommonCards(gp,  commonCards);
		
		HandPoker hp1 = HandPokerHelper.SetHand(cards, new HandPoker());
		HandPoker hp2 = HandPokerHelper.SetHand(p2Cards, new HandPoker());
		
		gp = GamePlayHelper.PutGamePlay(gp, p1.getPlayerID(), hp1);		
		gp = GamePlayHelper.PutGamePlay(gp, p2.getPlayerID(), hp2);
		
		try {
			gp.EvaluateGameHands();
		} catch (HandException e) {
			fail("Evaluate hands failed");
		}

	}
	
	@Test
	public void GetPlayersHand_Test1() {
		//create the table
		Table mytable = new Table("Table 1");
		//add a player to the table
		mytable.AddPlayerToTable(new Player("Jackson"));
		//choose the game type
		Rule rle = new Rule(eGame.TexasHoldEm);
		//add this game type to the table
		GamePlay gp = new GamePlay(mytable, rle);
		//make cards for player 
		ArrayList<Card> Jacksoncards = new ArrayList<Card>();
		Jacksoncards.add(new Card(eSuit.HEARTS, eRank.TWO));
		Jacksoncards.add(new Card(eSuit.HEARTS, eRank.THREE));
		//create an instance of player called Jackson
		Player p1 = new Player("Jackson");
		//make a set of cards that will be common to everyone
		ArrayList<Card> commonCards = new ArrayList<Card>();
		commonCards.add(new Card(eSuit.HEARTS, eRank.THREE));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FOUR));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FIVE));
		//add these common cards to the game
		gp = GamePlayHelper.setCommonCards(gp,  commonCards);
		//set up Jackson's cards
		HandPoker hp1 = HandPokerHelper.SetHand(Jacksoncards, new HandPoker());
		//add Jackson and Jackson's cards to the game
		gp = GamePlayHelper.PutGamePlay(gp, p1.getPlayerID(), hp1);
		assertEquals(Jacksoncards,gp.GetPlayersHand(p1));
	}
	
	@Test
	public void GetPlayersHand_Test2() {
		//create the table
		Table mytable = new Table("Table 1");
		//add a player to the table
		mytable.AddPlayerToTable(new Player("Jackson"));
		//choose the game type
		Rule rle = new Rule(eGame.TexasHoldEm);
		//add this game type to the table
		GamePlay gp = new GamePlay(mytable, rle);
		//create an instance of player called Jackson
		Player p1 = new Player("Jackson");
		//make a set of cards that will be common to everyone
		ArrayList<Card> commonCards = new ArrayList<Card>();
		commonCards.add(new Card(eSuit.HEARTS, eRank.THREE));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FOUR));
		commonCards.add(new Card(eSuit.HEARTS, eRank.FIVE));
		//add these common cards to the game
		gp = GamePlayHelper.setCommonCards(gp,  commonCards);
		//set up Jackson's cards, but make them NULL
		HandPoker hp1 = HandPokerHelper.SetHand(null, new HandPoker());
		//add Jackson and Jackson's cards to the game
		gp = GamePlayHelper.PutGamePlay(gp, p1.getPlayerID(), hp1);
		assertNull(gp.GetPlayersHand(p1));
	}
	
	
}
