package enumeration;

/* Quand on rajoute une catégorie :
	- Rajouter une image avec le numéro du badge (Exemple : badge7.png (Si c'est la 7 ème catégorie))
	- Rajouter un gif d'enemi avec le nom de la categorie (Exemple : AmongUs.gif (Pour la catégorie AmongUs))
	- Rajouter au minimum une question en rapport avec cette catégorie.
*/
public enum Category {
	
	CYBERSECURITY ("Cybersecurity"),
    ECONOMICS ("Economics"),
    ENVIRONMENT ("Environment"),
    MINDFULNESS ("Mindfulness"),
    SCHOOL ("School"),
    SOCIETY ("Society");
	
	private String category;

	Category(String category) {
		this.setCategory(category);
	}
	
	public void setCategory(String category) {
		this.category = category;
	} 
	
	@Override
	public String toString() {
		return category;
	}
}
