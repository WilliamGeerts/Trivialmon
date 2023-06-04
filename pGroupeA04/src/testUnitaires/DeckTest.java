package testUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enumeration.Category;
import modele.Deck;
import modele.Question;
import test.Explorateur;

class DeckTest {

	// Déclaration des variables
	private Deck deck;
	private Question question1;
	private Question question2;
	private List<Question> listQuestion;

	@BeforeEach
	void setUp() throws Exception {
		
		// On instancie un deck avec 2 questions.
		deck = new Deck();
		question1 = new Question("Dennis", Category.SCHOOL, "Est-ce que ça fonctionne ?", "Peut-être");
		question2 = new Question("William", Category.SOCIETY, "Quel est le sport préféré des lycéens japonais ?", "Le baseball");

		listQuestion = (List<Question>) Explorateur.getField(deck, "questions");
	}

	@AfterEach
	void tearDown() throws Exception {
		// On remet toute les valeurs à null
		deck = null;
		question1 = null;
		question2 = null;
		listQuestion = null;
	}

	@Test
	void testAdd() {
		// Ajoute une question et vérifie si celle si est bien ajoutée.
		assertTrue(deck.add(question1));
		assertEquals(1, listQuestion.size());
		assertEquals(question1, listQuestion.get(0));

		// Rajoute la même question pour gerer les doublons
		assertFalse(deck.add(question1));
		assertEquals(1, listQuestion.size());

		// Ajoute une autre question et vérifie si celle si est bien ajoutée
		assertTrue(deck.add(question2));
		assertEquals(2, listQuestion.size());
		assertEquals(question2, listQuestion.get(1));
	}

	@Test
	void testFind() {
		
		//Cherche les questions 1 et 2 (index -1 si elles ne sont pas la)
		assertEquals(-1, deck.find(question1));
		assertEquals(-1, deck.find(question2));

		//Ajoute les questions 1 et 2
		deck.add(question1);
		deck.add(question2);

		//Cherche à nouveau les questions 1 et 2 (Ont maintenant un index)
		assertEquals(0, deck.find(question1));
		assertEquals(1, deck.find(question2));
	}

	@Test
	void testUpdate() {
	    // Ajouter une question dans la liste pour pouvoir la mettre à jour ensuite
	    deck.add(question1);

	    // Créer une nouvelle question avec des valeurs différentes
	    Question question2 = new Question("William", Category.SOCIETY, "Quel est le sport préféré des lycéens japonais ?", "Le baseball");

	    // Mettre à jour la question1 avec la question2
	    assertTrue(deck.update(question2, question1));
	}
	
	@Test
	void testDelete() {
		
		// Verifier si le deck est vide
		assertFalse(deck.delete(null));
		assertFalse(deck.delete(question1));
		
	    // Ajouter de questions au deck
	    deck.add(question1);
		deck.add(question2);

	    // Vérifier que les questions ont bien été ajoutées
	    assertTrue(deck.getQuestions().contains(question1));

	    // Supprimer les questions du deck
	    assertTrue(deck.delete(question1));
	    assertEquals(1, listQuestion.size());
		assertEquals(question2, listQuestion.get(0));
		assertTrue(deck.delete(question2));
		assertEquals(0, listQuestion.size());

	    // Vérifier que les questions ont bien été supprimées
	    assertFalse(deck.getQuestions().contains(question1));
	}
}