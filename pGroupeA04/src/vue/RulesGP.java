package vue;



import enumeration.Category;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class RulesGP extends GridPane{
	
	//Node
	private Button btnBack;
	private Label lblRules, lblTitle;
	private int categoryNumber;
	
	public RulesGP() {
		//Affichage du grillage
		vue.MainMenuGP.createLine(this, false);
		
		//Initialisation des valeurs
		categoryNumber = Category.values().length;
		
		//Ajout texte
		this.add(getLblTitle(), 48, 24, 10, 10);
		this.add(getLblRules(), 0,0,100,110);
		this.setAlignment(Pos.CENTER);
		GridPane.setHalignment(getLblRules(), HPos.CENTER);
		GridPane.setValignment(getLblRules(), VPos.CENTER);
				
		//Ajout button
		this.add(getBtnBack(), 1, 91, 15, 8);
		
		//Initialisation des variables
		int i = 1;
		
		//Création de la HBox (Et réglage)
		HBox badgeBox = new HBox();
		
		for(Category cat : Category.values()) { 
			//Création des nodes
			ImageView badge = new ImageView(new Image("/ressources/images/badges/badge" + i + ".png"));
			Label categ = new Label(cat.toString());  
			
			//Réglage des ImageView
			badge.setFitWidth(35); badge.setFitHeight(35);
			
			//Ajouts du badge dans la HBox
			badgeBox.getChildren().addAll(badge,categ);
			
			//Augmentation des variables utiles
			i +=1;
		}
		
		this.add(badgeBox, 20, 65, 60, 10); 
	}
	public Label getLblTitle() {
		if(lblTitle == null) {lblTitle = new Label("Rules :"); lblTitle.setId("Title");}
		return lblTitle;
	}
	public Label getLblRules() {
		if(lblRules == null) {
			lblRules = new Label("Each player will have to roll the dice in turn. The pawn will advance the number of squares the die displays.\n"
	                + "\r\n"
	                + "The player will arrive on a box with one of the " + categoryNumber + " categories of questions in the game. A question will be displayed with the category displayed on the box as a theme. \r\n"
	                + "\r\n"
	                + "If the player answers this question correctly, he will get a badge. \r\n"
	                + "However if he answers this question correctly but he already has a badge of this category, he will get a random bonus usable on his next round among these: \r\n"
	                + "\r\n"
	                + "Leftovers: gives the first 2 letters of the answer\r\n"
	                + "Repel: Change the question\r\n"
	                + "MaxRepel: Change category and question \r\n"
	                + "\r\n"
	                + "To win the game, the player will need to get the " + categoryNumber + " badges corresponding to the " + categoryNumber + " categories of questions.\n\n"
	                + "There's all of icons for each categories : \r\n");
			lblRules.setAlignment(Pos.CENTER);
			lblRules.setTextAlignment(TextAlignment.CENTER);
			lblRules.setWrapText(true);

		}
		return lblRules;
	}

	//Button
	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnBack.setOnAction(event -> {
				MainMenuGP mmgp = (MainMenuGP) RulesGP.this.getParent().getParent();
				if(mmgp.getBoard() == null) {mmgp.setRoot(0);}
				else {mmgp.setRoot(5);}
			});
		}
		return btnBack;
	}

}
