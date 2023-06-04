package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import dpStrategy.Leftovers;
import javafx.application.Application;
import javafx.stage.Stage;
import vue.FightGP;
import vue.MainMenuGP;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;

public class StartGame extends Application {	
	private static Stage stage;
	public void start(Stage primaryStage) {
		try {	
			//STAGE
			stage = primaryStage;
			
			//GRIDPANE
			MainMenuGP mmgp = new MainMenuGP();
			
			//ID
			mmgp.setId("MainMenu");
			
			//SCENE
			Scene mainmenu = new Scene(mmgp, 1280, 720);

			//ASSEMBLAGE
			stage.setScene(mainmenu);
			//stage.setResizable(false);
			stage.setFullScreenExitHint(null);
		    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			stage.setTitle("Trivialmon");
			stage.getIcons().add(new Image("ressources/images/gameIcon/gameIcon.png"));
			stage.show();
			
			Font customFont = Font.loadFont(getClass().getResourceAsStream("file:ressources/fonts/PressStart2P-Regular.ttf"), 4);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Récupérer le stage n'importe où
	public static Stage getStage() {
		return stage;
	}
	
	public static void main(String[] args) {
		launch(args);
		//FightGP gp =  new FightGP(null);
	}
}