package modele;

import vue.FightGP;
/**
 * L'interface stratégie pour la méthode bonusEffect à redéfinir dans les différentes stratégies concrètes 
 */

public interface CardEffect 
{
	/**
	 * La méthode fera une action différente dans le combat (affichage de la question)
	 * en paramètres en fonction 
	 * de la stratégie concrète choisie dans le combat  
	 * @param fgp : le combat en cours pour pouvoir récupérer les infos du combat 
	 * (questions, sprites, joueur)
	 */
	public void bonusEffect(FightGP fgp);
}
