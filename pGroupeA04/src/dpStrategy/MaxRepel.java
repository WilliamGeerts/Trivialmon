package dpStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import enumeration.Category;
import javafx.scene.image.Image;
import modele.CardEffect;
import modele.Question;
import vue.FightGP;

public class MaxRepel implements CardEffect {
	public void bonusEffect(FightGP fgp) {
		System.out.println("Change the question and the category");
		Random rand = new Random();
		
		Question questionFight = fgp.getQuestionRand();
		Category categoryFight = questionFight.getCategory();
		
		System.out.println(questionFight);
		System.out.println(categoryFight);
		System.out.println(questionFight.getCategory());
		
		//Récupérer une question d'une catégorie différente
		while(questionFight.getCategory().equals(categoryFight))
		{
			fgp.getQuestionRand(rand.nextInt(6) + 1);
			questionFight =  fgp.getQuestionRand();
		}
		
		//Variables utiles
		//Catégories
		List<Category> badges = new ArrayList<>();
		for (Category cat : Category.values()) {
			badges.add(cat);
		}
		int indexOf = badges.indexOf(questionFight.getCategory()) + 1;
		
		//Changer les valeurs de la catégorie, du badge, du sprite adverse, de la question et de la réponse
		fgp.getLblCategoryName().setText(questionFight.getCategory().toString());
		fgp.getIvBadge().setImage(new Image("/ressources/images/badges/badge" + indexOf + ".png"));
		fgp.getEnemySprite().setImage(new Image("ressources/images/enemySprites/" + questionFight.getCategory().toString() + ".gif"));
		fgp.getLblAnswer().setText("Correct : " + questionFight.getAnswer());
		fgp.getLblQuestion().setText(questionFight.getStatement());
		fgp.getTxtAnswer().setText("");
		
		//Modifier la question dans le combat
		fgp.setQuestionRand(questionFight);
		System.out.println(questionFight);
		
		//Réafficher les informations de la question
		fgp.getInformations();
	}
}
