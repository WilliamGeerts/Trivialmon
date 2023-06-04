package modele;

import java.util.ArrayList;
import java.util.List;

import enumeration.BonusType;
import javafx.scene.image.Image;

/**
 * Classe qui créer un joueur, pour ensuite l'ajouter dans une liste de joueurs qui partiperont
 * à la partie, sert à le déterminer et lui ajouter un inventaire de badges et un de bonus
 */

public class Player {
	/**
	 * Le nom (pseudo) du joueur
	 */
	private String name;
	/**
	 * L'image du pion du joueur
	 */
	private Image icon;
	/**
	 * La liste des badges (morceaux de fromages)
	 */
	private List<String> badges;
	/**
	 * La liste de cartes bonus du joueur
	 */
	private List<BonusType> inventory;
	
	/**
	 * Crée une instance de la classe Player
	 * @param name, le nom du joueur
	 * @param icon, l'image du joueur
	 */
	//Constructeur
	public Player(String name, Image icon) {
		this.name = name;
		this.icon = icon;
		this.badges = new ArrayList<>();
		this.inventory = new ArrayList<>();
	}

	//Getters
	/**
	 * Renvoie le nom du joueur
	 * @return Le nom du joueur
	 */
	public String getName() {return name;}
	/**
	 * Renvoie l'image du joueur
	 * @return l'image du joueur
	 */
	public Image getIcon() {return icon;}
	/**
	 * Renvoie la liste des badges du joueur
	 * @return la liste des badges du joueur
	 */
	public List<String> getBadges() {return badges;}
	/**
	 * Renvoie la liste des bonus du joueur
	 * @return la liste des bonus du joueur
	 */
	public List<BonusType> getInventory() {return inventory;}

	//Setters
	/**
	 * Modifie le nom du joueur
	 * @param name, le nouveau nom du joueur
	 */
	public void setName(String name) {this.name = name;}
	/**
	 * Modifie l'image du joueur
	 * @param icon, la nouvelle image du joueur
	 */
	public void setIcon(Image icon) {this.icon = icon;}
	/**
	 * Modifie la liste de badge du joueur
	 * @param badges, la nouvelle liste de badge du joueur
	 */
	public void setBadges(List<String> badges) {this.badges = badges;}
	/**
	 * Modifie la liste de bonus du joueur
	 * @param inventory, la nouvelle liste de badge du joueur
	 */
	public void setInventory(List<BonusType> inventory) {this.inventory = inventory;}

	@Override
	public String toString() {return name +  " " + icon + " " + badges;}


	
	
}
