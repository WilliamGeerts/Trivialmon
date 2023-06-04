package dpStrategy;

import modele.CardEffect;
import vue.FightGP;

public class Metronome implements CardEffect{
	public void bonusEffect(FightGP fgp) {
		System.out.println("Reset the timer !");
		fgp.add(fgp.getTimer(), 0, 2, 10, 10);
	}
}
