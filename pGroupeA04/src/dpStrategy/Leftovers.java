package dpStrategy;

import modele.CardEffect;
import vue.FightGP;

/**
 * Stratégie concrète de CardEffect
 */
public class Leftovers implements CardEffect{
	/**
	 * Affiche les 2 premiers caractères de la réponse 
	 */
	public void bonusEffect(FightGP fgp) 
	{
		String newValue = String.valueOf(fgp.getQuestionRand().getAnswer().charAt(0)) + 
				String.valueOf(fgp.getQuestionRand().getAnswer().charAt(1));
		fgp.getTxtAnswer().setText(newValue);
	}
}
