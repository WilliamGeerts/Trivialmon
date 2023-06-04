package vue;

import java.nio.file.Paths;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import modele.Player;

public class MainMenuGP extends BorderPane 
{
	private StackPane mainMenu;
	
	private MenuGP menu;
	private EditCardGP editcard;
	private LoginGP login;
	private SettingsGP settings;
	private CreatePlayerGP createplayer;
	public BoardGP board;
	public FightGP fight;
	public EncodageQuestionGP encodage;
	public RulesGP rules;
	
	//MediaPlayer
	private MediaPlayer mainMusic, click;
	
	public MainMenuGP() {
		this.setCenter(getMainMenu());
		
		//Cacher tous les autres Panes.
		hideall();
		
		//Rendre le Menu visible
		getMenu().setVisible(true);
		
		//Ajouter le Sound Effect sur tous les boutons de tous les Panes
		for (Node paneStack : getMainMenu().getChildren()) {
				GridPane pane = (GridPane) paneStack;
				for (Node node : pane.getChildren()) {
		            if (node instanceof Button || node instanceof CheckBox) {
		                (node).addEventHandler(ActionEvent.ACTION, e -> onClicked(e));
		            }
		        }
		}
	}

	//Action pour chaque click (Sur chaque Button & CheckBox)
	private void onClicked(ActionEvent e) {
		if(getSettings().getCbSoundEffect().isSelected()) {
			int volume = Integer.parseInt(getSettings().getLblSoundEffectValue().getText());
			click(volume);
		}
	}

	//HIDE ALL
	public void hideall() {
		for(Node node : getMainMenu().getChildren()) {
			node.setVisible(false);
		}
	}

	//GETTERS
	public StackPane getMainMenu() {
		if(mainMenu == null) {
			mainMenu = new StackPane();
			mainMenu.getChildren().addAll(getMenu(), getEditcard(), getSettings(), getCreatePlayerGP(), getLogin(), getRules());
			//Lancement de la musique au démarrage
			int volume = Integer.parseInt(getSettings().getLblSoundValue().getText());
			music(volume);
		}
		return mainMenu;
	}
	public MenuGP getMenu() {
		if(menu == null) {
			menu = new MenuGP();
			menu.getStylesheets().add("/ressources/css/menu.css");
			menu.setId("Menu");
		}
		return menu;
	}
	public EditCardGP getEditcard() {
		if(editcard == null) {
			editcard = new EditCardGP();
			editcard.getStylesheets().add("/ressources/css/editcard.css");
			editcard.setId("EditCard");
		}
		return editcard;
	}
	public LoginGP getLogin() {
		if(login == null) {
			login = new LoginGP();
			login.getStylesheets().add("/ressources/css/login.css");
			login.setId("Login");
		}
		return login;
	}
	public SettingsGP getSettings() {
		if(settings == null) {
			settings = new SettingsGP();
			settings.getStylesheets().add("/ressources/css/settings.css");
			settings.setId("Settings");
		}
		return settings;
	}
	public RulesGP getRules() {
		if(rules == null) {
			rules = new RulesGP();
			rules.getStylesheets().add("/ressources/css/rules.css");
			rules.setId("Rules");
		}
		return rules;
	}
	public CreatePlayerGP getCreatePlayerGP() {
		if(createplayer == null) {
			createplayer = new CreatePlayerGP();
			createplayer.getStylesheets().add("/ressources/css/createplayer.css");
			createplayer.setId("CreatePlayerP2");
		}
		return createplayer;
	}
	public BoardGP getBoard(List<Player> players) {
		if(board == null) {
			board = new BoardGP(players);
			board.getStylesheets().add("/ressources/css/board.css");
			board.setId("Board");
		}
		return board;
	}
	public BoardGP getBoard() {
		return board;
	}
	public FightGP getFight(Player playerSelected, int indiceCategory) {
		fight = new FightGP(playerSelected, indiceCategory);
		fight.getStylesheets().add("/ressources/css/fight.css");
		fight.setId("Fight");
		return fight;
	}
	public EncodageQuestionGP getEncodage() {
		encodage = new EncodageQuestionGP();
		encodage.getStylesheets().add("/ressources/css/encodage.css");
		encodage.setId("Encodage");
		
		return encodage;
	}
	
	public MediaPlayer getMainMusic() {return mainMusic;}
	public MediaPlayer getClick() {return click;}
	
	//SETTERS
	public void setBoard(BoardGP board) {
		this.board = board;
	}
	public void setRoot(int index) {
		//Cacher tous les panes
		hideall();
		
		//Aficher celui qui nous intéresse
		getMainMenu().getChildren().get(index).setVisible(true);
	}
	
	
	//Méthodes utiles
	public void music(int volume) {
		String music = "src/ressources/sounds/musics/MainMusic.mp3";
		Media media = new Media(Paths.get(music).toUri().toString());
		mainMusic = new MediaPlayer(media);
		mainMusic.setVolume(volume * 0.01);
		mainMusic.play();
		mainMusic.setOnEndOfMedia(new Runnable() {public void run() {mainMusic.seek(Duration.ZERO);}});
	}
	public void click(int volume) {
		String music = "src/ressources/sounds/effects/Click.mp3";
		Media media = new Media(Paths.get(music).toUri().toString());
		click = new MediaPlayer(media);
		click.setVolume(volume * 0.01);
		click.play();
	}
	public static void createLine(GridPane gp, Boolean affichageLigne) {
		gp.setGridLinesVisible(affichageLigne);
		final int numCol = 100;
		final int numRown = 100;
		for(int i=0 ; i < numCol; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0/numCol);
			gp.getColumnConstraints().add(colConst);
		}
		for(int i=0 ; i < numRown; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0/numRown);
			gp.getRowConstraints().add(rowConst);
		}
	}
	public void addSoundEffect() {
		//Ajouter le Sound Effect sur tous les boutons de tous les Panes
			for (Node paneStack : getMainMenu().getChildren()) {
				GridPane pane = (GridPane) paneStack;
				for (Node node : pane.getChildren()) {
				    if (node instanceof Button || node instanceof CheckBox) {
				        (node).addEventHandler(ActionEvent.ACTION, e -> onClicked(e));
				    }
				}
			}
	}
}