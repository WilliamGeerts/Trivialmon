package vue;

import application.StartGame;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MenuGP extends GridPane
{
	//ImageView
	private static ImageView title;
	
	//Button
	private Label lblTitle;
	private Button btnPlay, btnQuit, btnSettings, btnCardsEdit , btnRules;
	
	//Configurer le futur Menu
	public MenuGP()
	{
		//Affichage du grillage
		vue.MainMenuGP.createLine(this, false);
		
		//AJOUTS DES ÉLÉMENTS
		this.add(getTitle(), 0, 8, 40, 10);
		this.add(getBtnPlay(), 65, 46, 30, 10);
		this.add(getBtnCardsEdit(), 65, 58, 30, 10);
		this.add(getBtnSettings(), 65, 70, 30, 10);
		this.add(getBtnQuit(), 65, 82, 30, 10);
		
		this.add(getBtnRules(), 89, 1, 10, 8);
	}
	//GETTERS
	public Label getLblTitle() {
		if(lblTitle == null) {
			lblTitle = new Label("");
			lblTitle.setGraphic(getTitle());
		}
		return lblTitle;
	}
	public static ImageView getTitle() {
		if(title == null) {
			Image image = new Image("/ressources/images/backgrounds/title.png");
			title = new ImageView();
			title.setFitHeight(500);
			title.setFitWidth(800);
			title.setImage(image);
		}
		return title;
	}
	public Button getBtnPlay() {
		if(btnPlay == null) {
			btnPlay = new Button("Play");
			btnPlay.setOnAction((ActionEvent event) -> {
				MainMenuGP mmgp = (MainMenuGP) MenuGP.this.getParent().getParent();
				mmgp.setRoot(3);});
			Font customFont = Font.loadFont(getClass().getResourceAsStream("/ressources/fonts/PressStart2P-Regular.ttf"), 12);
			btnPlay.setFont(customFont);
		}
		return btnPlay;
	}
	public Button getBtnQuit() {
		if(btnQuit == null) {
			btnQuit = new Button("Quit");
			btnQuit.setOnAction((ActionEvent event) -> {StartGame.getStage().close();});
		}
		return btnQuit;
	}
	public Button getBtnSettings() {
		if(btnSettings == null) {
			btnSettings = new Button("Settings");
			btnSettings.setOnAction((ActionEvent event) -> {MainMenuGP mmgp = (MainMenuGP) MenuGP.this.getParent().getParent();mmgp.setRoot(2);});
		}
		return btnSettings;
	}
	public Button getBtnCardsEdit() {
		if(btnCardsEdit == null) {
			btnCardsEdit = new Button("Edit");
			btnCardsEdit.setOnAction(event -> {MainMenuGP mmgp = (MainMenuGP) MenuGP.this.getParent().getParent();mmgp.setRoot(4);});
		}
		return btnCardsEdit;
	}
	public Button getBtnRules() {
		if(btnRules == null) {
			btnRules = new Button("Rules");
			btnRules.setOnAction(event -> {MainMenuGP mmgp = (MainMenuGP) MenuGP.this.getParent().getParent();mmgp.setRoot(5);});
		}
		return btnRules;
	}
}