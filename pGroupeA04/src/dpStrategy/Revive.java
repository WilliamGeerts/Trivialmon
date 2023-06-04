package dpStrategy;

import modele.CardEffect;
import vue.FightGP;

public class Revive implements CardEffect {
	public void bonusEffect(FightGP fgp) {
		System.out.println("Respond 2 times at the question");
		
		System.out.println(fgp.getLblPlayerName());
	}
}
