package vue;

import application.StartGame;
import enumeration.Cursor;
import enumeration.Resolution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsGP extends GridPane{
	
	//Node
	private Label lblSound, lblFullscreen, lblSoundEffect, lblResolution, lblCursor;
	private CheckBox cbFullscreen;
	private ComboBox<String> cbResolution, cbCursor;
	private Button btnBack, btnQuitGame;
	
	//Static Node
	private Label lblSoundValue, lblSoundEffectValue;
	private CheckBox cbSound, cbSoundEffect;
	private Slider sVolumeSound, sVolumeSoundEffect;
	
	public SettingsGP() {
		//Affichage du grillage
		vue.MainMenuGP.createLine(this, false);
		
		//Label
		this.add(getLbSound(), 30, 25, 15, 10);
		this.add(getLblSoundValue(), 71, 25, 15, 10);
		this.add(getLbSoundEffect(), 30, 35, 15, 10);
		this.add(getLblSoundEffectValue(), 71, 35, 15, 10);
		this.add(getLbFullscreen(), 30, 45, 15, 10);
		this.add(getLblResolution(), 30, 55, 15, 10);
		this.add(getLblCursor(), 30, 65, 15, 10);
		
		//CheckBox
		this.add(getCbSound(), 50, 25, 15, 10);
		this.add(getCbSoundEffect(), 50, 35, 15, 10);
		this.add(getCbFullscreen(), 50, 45, 15, 10);
		
		//ComboBox
		this.add(getCbResolution(), 50, 55, 15, 10);
		this.add(getCbCursor(), 50, 65, 15, 10);
		
		//Slider
		this.add(getSVolumeSound(), 55, 25, 15, 10);
		this.add(getSVolumeSoundEffect(), 55, 35, 15, 10);
		
		//Button
		this.add(getBtnBack(), 1, 91, 15, 8);
		//this.add(getBtnQuitGame(), 83, 88, 15, 10);
	}

	//GETTERS
	//Label
	public Label getLbSound() {
		if(lblSound == null) {lblSound = new Label("Music : ");}
		return lblSound;
	}
	public Label getLbFullscreen() {
		if(lblFullscreen == null) {lblFullscreen = new Label("Fullscreen : ");}
		return lblFullscreen;
	}
	public Label getLbSoundEffect() {
		if(lblSoundEffect == null) {lblSoundEffect = new Label("Sound Effect : ");}
		return lblSoundEffect;
	}
	public Label getLblResolution() {
		if(lblResolution == null) {lblResolution = new Label("Resolution : ");}
		return lblResolution;
	}
	public Label getLblSoundValue() {
		if(lblSoundValue == null) {lblSoundValue = new Label(String.valueOf((int)getSVolumeSound().getValue()));}
		return lblSoundValue;
	}
	public Label getLblSoundEffectValue() {
		if(lblSoundEffectValue == null) {lblSoundEffectValue = new Label(String.valueOf((int)getSVolumeSoundEffect().getValue()));}
		return lblSoundEffectValue;
	}
	public Label getLblCursor() {
		if(lblCursor == null) {lblCursor = new Label("Cursor : ");}
		return lblCursor;
	}
	//CheckBox
	public CheckBox getCbSound() {
		if(cbSound == null) {
			cbSound = new CheckBox();
			cbSound.setSelected(true);
			cbSound.setOnAction(event -> {
				MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
				mmgp.getMainMusic().setVolume(getSVolumeSound().getValue() * 0.01);
				if(!cbSound.isSelected()) {mmgp.getMainMusic().setVolume(0);}
			});
		}
		return cbSound;
	}
	public CheckBox getCbSoundEffect() {
		if(cbSoundEffect == null) {
			cbSoundEffect = new CheckBox();
			cbSoundEffect.setSelected(true);
			cbSoundEffect.setOnAction(event -> {
				MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
				mmgp.getClick().setVolume(getSVolumeSoundEffect().getValue() * 0.01);
				if(!cbSoundEffect.isSelected()) {mmgp.getClick().setVolume(0);}
			});
		}
		return cbSoundEffect;
	}
	public CheckBox getCbFullscreen() {
		if(cbFullscreen == null) {
			cbFullscreen = new CheckBox();
			cbFullscreen.setOnAction(event -> {
				Stage stage = (Stage) cbFullscreen.getScene().getWindow();
				stage.setFullScreen(!stage.isFullScreen());
				
				//Changer la taille du titre
		        vue.MenuGP.getTitle().setFitWidth(stage.getWidth()/1.5);
		        vue.MenuGP.getTitle().setFitHeight(stage.getHeight()/2.);
			});}
		return cbFullscreen;
	}
	//Slider
	public Slider getSVolumeSound() {
		if(sVolumeSound == null) {
			sVolumeSound = new Slider();
			//VOLUME PAR DEFAUT
			sVolumeSound.setValue(0);
			sVolumeSound.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					double newVolume = sVolumeSound.getValue();
					getLblSoundValue().setText(String.valueOf((int) newVolume));
					if(getCbSound().isSelected()) {
						MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
						mmgp.getMainMusic().setVolume(newVolume * 0.01);
					}
				}
			});
		}
		return sVolumeSound;
	}
	public Slider getSVolumeSoundEffect() {
		if(sVolumeSoundEffect == null) {
			sVolumeSoundEffect = new Slider();
			//VOLUME PAR DEFAUT
			sVolumeSoundEffect.setValue(0);
			sVolumeSoundEffect.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					double newVolume = sVolumeSoundEffect.getValue();
					getLblSoundEffectValue().setText(String.valueOf((int) newVolume));
					if(getCbSoundEffect().isSelected()) {
						MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
						mmgp.getClick().setVolume(newVolume * 0.01);
					}
				}
			});
		}
		return sVolumeSoundEffect;
	}
	//Button
	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnBack.setOnAction(event -> {
				MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
				if(mmgp.getBoard() == null) {mmgp.setRoot(0);}
				else {mmgp.setRoot(6);}
			});
		}
		return btnBack;
	}
	public Button getBtnQuitGame() {
		if(btnQuitGame == null) {
			btnQuitGame = new Button("Quit Game");
			btnQuitGame.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnQuitGame.setOnAction(event -> {
				MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
				mmgp.setRoot(0);
			});
		}
		return btnQuitGame;
	}
	//ComboBox
	public ComboBox<String> getCbResolution() {
		if(cbResolution == null) {
			cbResolution = new ComboBox<>();
			//Ajouter la résolution de l'utilisateur
			ObservableList<Screen> screenSizes = Screen.getScreens();
		    screenSizes.forEach(screen -> {cbResolution.getItems().add(((int) screen.getBounds().getWidth() + "x" + (int) screen.getBounds().getHeight()) + " - Your resolution");});
		    
		    String yourResolution = cbResolution.getItems().get(0).split(" -")[0];
		    
		    //Ajouter les autres résolutions
			for(Resolution res : Resolution.values())
			{
				if(!res.toString().equals(yourResolution)) {cbResolution.getItems().add(res.toString());}	
			}
			cbResolution.setValue(cbResolution.getItems().get(1));
			cbResolution.setOnAction(e -> {setSizeScreen();});
		}
		return cbResolution;
	}
	public ComboBox<String> getCbCursor() {
		if(cbCursor == null) {
			cbCursor = new ComboBox<>();
			cbCursor.getItems().add("Basic Cursor");
			cbCursor.setValue(cbCursor.getItems().get(0));
			
			//Ajouter tous les curseursµ
			for(Cursor cur : Cursor.values()) {cbCursor.getItems().add(cur.toString());}
			
			cbCursor.setOnAction((event) -> {setCursor();}); 
		}
		return cbCursor;
	}
	
	
	//Méthodes utiles
	//Changer la résolution de l'écran par rapport au choix de l'utilisateur
	public void setSizeScreen() {
		if(!getCbFullscreen().isSelected()) {
			//Récupérer la hauteur et la largeur demandées
			String[] separetedText = getCbResolution().getSelectionModel().getSelectedItem().split("x");
			String height = separetedText[0];
			String width = separetedText[1].split(" -")[0];
			
			//Récupérer la fenêtre
			Stage stage = (Stage) cbResolution.getScene().getWindow();
			
			//Changer la Hauteur et la Largeur
	        stage.setHeight(Integer.parseInt(width));
	        stage.setWidth(Integer.parseInt(height));
	        
	        //Changer la taille du titre
	        vue.MenuGP.getTitle().setFitWidth(Integer.parseInt(width));
	        vue.MenuGP.getTitle().setFitHeight(Integer.parseInt(height)/3.);
	        
	        //Centrer la fenêtre 
	        stage.centerOnScreen();
		}
		else 
		{
			MainMenuGP mmgp = (MainMenuGP) SettingsGP.this.getParent().getParent();
			mmgp.getEditcard().getAlert(AlertType.WARNING, "You can't change the resolution in Full Screen Mode !");
		}
	}
	//Changer le curseur de l'utilisateur
	public void setCursor() {
		Scene scene = StartGame.getStage().getScene();
		if(!getCbCursor().getSelectionModel().getSelectedItem().equals(getCbCursor().getItems().get(0))) {
			Image cursor = new Image("ressources/images/gameIcon/" + getCbCursor().getSelectionModel().getSelectedItem() + ".gif");
			scene.setCursor(new ImageCursor(cursor));
		} else {scene.setCursor(null);}
	}
}