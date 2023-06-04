package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import modele.Player;


public class VictoryGP extends GridPane {

	private Label lblName;
	private Button btnBack;
	private Player p;
	
	public VictoryGP(Player player) {
		//Mettre le css
		this.setId("Victory");
		this.getStylesheets().add("/ressources/css/victory.css");
		
		//Récupérer le joueur
		this.p = player;
		
		//Création des lignes
		vue.MainMenuGP.createLine(this, false);
		
		//Ajouts
		HBox centered = new HBox(getLblName());
		centered.setAlignment(Pos.CENTER);
		this.add(centered, 0, 80, 100, 20);
		this.add(getBtnBack(), 1, 91, 15, 8);
	}

	//Getters
	public Label getLblName() {
		if(lblName == null) {lblName = new Label("Congrats to " + p.getName() + "\nto have beat the game !"); lblName.setTextAlignment(TextAlignment.CENTER);;}
		return lblName;
	}
	//Button
		public Button getBtnBack() {
			if(btnBack == null) {
				btnBack = new Button("Back");
				btnBack.setId("btn");
				btnBack.setOnAction(event -> {
					MainMenuGP mmgp = (MainMenuGP) VictoryGP.this.getParent().getParent();
					mmgp.getMainMenu().getChildren().remove(6);
					mmgp.setBoard(null);
					mmgp.setRoot(0);
				});
			}
			return btnBack;
		}
}
