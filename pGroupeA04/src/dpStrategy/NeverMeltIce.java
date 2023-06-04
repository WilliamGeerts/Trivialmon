package dpStrategy;

import modele.CardEffect;
import vue.FightGP;

public class NeverMeltIce implements CardEffect {
	public void bonusEffect(FightGP fgp) {
		System.out.println("Time stop !");
		
		fgp.getTimer();
	}
}
