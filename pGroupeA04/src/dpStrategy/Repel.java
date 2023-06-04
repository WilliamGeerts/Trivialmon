package dpStrategy;

import java.util.Random;

import enumeration.Category;
import modele.CardEffect;
import modele.Question;
import vue.FightGP;

public class Repel implements CardEffect {
	public void bonusEffect(FightGP fgp) {
		System.out.println("Change the question !");
		Random rand = new Random();
		
		Question questionFight = fgp.getQuestionRand();
		Category categoryFight = questionFight.getCategory();
		String statementFight = questionFight.getStatement();
		
		//Récupérer une question d'une catégorie différente
		while(questionFight.getStatement().equals(statementFight) || !questionFight.getCategory().equals(categoryFight))
		{
			fgp.getQuestionRand(rand.nextInt(6) + 1);
			questionFight =  fgp.getQuestionRand();
		}
		
		//Changer les valeurs de la question et de la réponse
		fgp.getLblAnswer().setText("Correct : " + questionFight.getAnswer());
		fgp.getLblQuestion().setText(questionFight.getStatement());
		fgp.getTxtAnswer().setText("");
		
		//Modifier la question dans le combat
		fgp.setQuestionRand(questionFight);
		
		//Réafficher les informations de la question
		fgp.getInformations();
	}
}
