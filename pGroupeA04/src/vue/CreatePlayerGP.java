package vue;

import java.util.ArrayList;
import java.util.List;

import enumeration.Pawn;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import modele.Player;

public class CreatePlayerGP extends GridPane {

	private Label lblPlayer, lblP1, lblP2, lblP3, lblP4;
	private static TextField txtPlayer1;
	private static TextField txtPlayer2;
	private static TextField txtPlayer3;
	private static TextField txtPlayer4;
	private ComboBox<String> cPlayer;
	private ComboBox<String> cIcon1, cIcon2, cIcon3, cIcon4;
	private Button btnBack, btnStart, btnProfil1, btnGuest1, btnProfil2, btnGuest2, btnProfil3, btnGuest3, btnProfil4, btnGuest4;
	private ImageView ivImage1, ivImage2, ivImage3, ivImage4;
	private HBox hbSprite1, hbSprite2, hbSprite3, hbSprite4; 
	
	public CreatePlayerGP() {
		this.getStylesheets().add("/ressources/css/createplayer.css");
		//Affichage du grillage
		vue.MainMenuGP.createLine(this, false);
		
		List<Label> listLabelPlayer = getListPlayer();
		List<TextField> listTextFieldPseudo = getListTextFieldPseudo();
		List<Button> listButtonProfil = getListButtonProfil();
		List<Button> listButtonGuest = getListButtonGuest();
		List<ComboBox<String>> listCombo = getListCombo();
		List<ImageView> listImageView = getListImageView();
		List<HBox> listSprite = getListSprite();
		
		//Ajouter les éléments au liste et les rendre invisible
		for(int i=0;i<=3;i++) {
			this.add(listLabelPlayer.get(i), 10+24*i, 15, 40, 10);
			this.add(listTextFieldPseudo.get(i), 7+24*i, 35, 14, 10);
			this.add(listButtonProfil.get(i), 3+24*i, 25, 9, 10);
			this.add(listButtonGuest.get(i), 13+24*i, 25, 9, 10);
			this.add(listCombo.get(i), 3+24*i, 65, 19, 10);
			this.add(listImageView.get(i), 3+i*24, 35, 20, 10);
			this.add(listSprite.get(i), 7+i*24, 46, 11, 20);
		}
		
		//Ajouter l'événement de souris sur les sprites
		for (HBox sprite : listSprite) {
			int indexOf = listSprite.indexOf(sprite);
			
			sprite.setOnMouseEntered(event -> {
				((ImageView) sprite.getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + listCombo.get(indexOf).getSelectionModel().getSelectedItem() + ".gif"));
			});
			
			sprite.setOnMouseExited(event -> {
				((ImageView) sprite.getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + listCombo.get(indexOf).getSelectionModel().getSelectedItem() + "_Face.gif"));
			});
		}
		
		//Déterminer le nombre de joueur de base
		getPlayer(getcPlayer().getSelectionModel().getSelectedItem());
		
		for (Node node : this.getChildren()) {
            if (node instanceof Button) {
                (node).addEventHandler(ActionEvent.ACTION, e -> onClicked(e));
            }
        }
		
		//Mettre tous les profils sur Guest + ajouter la Factory aux combobox
		setGuest();
		setFactory();
		
		//Ajouts des éléments
		this.add(getLblPlayer(), 3, 1, 40, 10);
		this.add(getcPlayer(), 12, 1, 40, 10);
		this.add(getBtnBack(), 1, 91, 15, 8);
		this.add(getBtnStart(), 84, 91, 15, 8);
	}
	
	private void onClicked(ActionEvent e) {
		List<Button> listButtonProfil = getListButtonProfil();
		List<Button> listButtonGuest = getListButtonGuest();
		List<TextField> listText = getListTextFieldPseudo();
		List<Label> listLabel = getListPlayer();
		
		for (Button button : listButtonProfil) {
			if(button.equals(e.getTarget())) {
				listText.get(listButtonProfil.indexOf(button)).setText("");
				listText.get(listButtonProfil.indexOf(button)).setDisable(false);
				button.setId("IsSelected");
				listButtonGuest.get(listButtonProfil.indexOf(button)).setId("");
			}
		}
		for (Button button : listButtonGuest) {
			if(button.equals(e.getTarget())) {
				listText.get(listButtonGuest.indexOf(button)).setText(listLabel.get(listButtonGuest.indexOf(button)).getText());
				listText.get(listButtonGuest.indexOf(button)).setDisable(true);
				button.setId("IsSelected");
				listButtonProfil.get(listButtonGuest.indexOf(button)).setId("");
			}
		}
	}

	//GETTERS
	//ComboBox
	public ComboBox<String> getcPlayer() {
		if(cPlayer == null) {
			cPlayer = new ComboBox<String>();
			cPlayer.getItems().addAll("2 players", "3 players", "4 players");
			cPlayer.setValue(cPlayer.getItems().get(0));
			getPlayer(cPlayer.getSelectionModel().getSelectedItem());
			cPlayer.setOnAction(event -> {getPlayer(cPlayer.getSelectionModel().getSelectedItem());});
		}
		return cPlayer;
	}
	private ComboBox<String> getCIcon1() {
		if(cIcon1 == null) {
			cIcon1 = new ComboBox<>();
			cIcon1.setMaxWidth(Double.MAX_VALUE);
			for (Pawn pawn : Pawn.values()) {cIcon1.getItems().addAll(pawn.getNom());}
			cIcon1.setValue(cIcon1.getItems().get(0));
			cIcon1.setOnHiding(event -> {
				getIvImage1().setImage(new Image("ressources/images/icons/" + cIcon1.getSelectionModel().getSelectedItem() + ".png"));
				((ImageView) getHbSprite1().getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + cIcon1.getSelectionModel().getSelectedItem() + "_Face.gif"));})
			;}
		return cIcon1;
	}
	private ComboBox<String> getCIcon2() {
		if(cIcon2 == null) {
			cIcon2 = new ComboBox<>();
			cIcon2.setMaxWidth(Double.MAX_VALUE);
			for (Pawn pawn : Pawn.values()) {cIcon2.getItems().addAll(pawn.getNom());}
			cIcon2.setValue(cIcon1.getItems().get(0));
			cIcon2.setOnHiding(event -> {
				getIvImage2().setImage(new Image("ressources/images/icons/" + cIcon2.getSelectionModel().getSelectedItem() + ".png"));
				((ImageView) getHbSprite2().getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + cIcon2.getSelectionModel().getSelectedItem() + "_Face.gif"));
			});
		}
		return cIcon2;
	}
	private ComboBox<String> getCIcon3() {
		if(cIcon3 == null) {
			cIcon3 = new ComboBox<>();
			cIcon3.setMaxWidth(Double.MAX_VALUE);
			for (Pawn pawn : Pawn.values()) {cIcon3.getItems().addAll(pawn.getNom());}
			cIcon3.setValue(cIcon1.getItems().get(0));
			cIcon3.setOnHiding(event -> {
				getIvImage3().setImage(new Image("ressources/images/icons/" + cIcon3.getSelectionModel().getSelectedItem() + ".png"));
				((ImageView) getHbSprite3().getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + cIcon3.getSelectionModel().getSelectedItem() + "_Face.gif"));
			});
		}
		return cIcon3;
	}
	private ComboBox<String> getCIcon4() {
		if(cIcon4 == null) {
			cIcon4 = new ComboBox<>();
			cIcon4.setMaxWidth(Double.MAX_VALUE);
			for (Pawn pawn : Pawn.values()) {cIcon4.getItems().addAll(pawn.getNom());}
			cIcon4.setValue(cIcon1.getItems().get(0));
			cIcon4.setOnHiding(event -> {
				getIvImage4().setImage(new Image("ressources/images/icons/" + cIcon4.getSelectionModel().getSelectedItem() + ".png"));
				((ImageView) getHbSprite4().getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + cIcon4.getSelectionModel().getSelectedItem() + "_Face.gif"));
			});
		}
		return cIcon4;
	}
	//Label
	public Label getLblPlayer() {
		if(lblPlayer == null) {lblPlayer = new Label("Number of players : "); lblPlayer.setId("PlayerNumber");}
		return lblPlayer;
	}
	public Label getLblP1() {
		if(lblP1 == null) {lblP1 = new Label("Player 1");}
		return lblP1;
	}
	public Label getLblP2() {
		if(lblP2 == null) {lblP2 = new Label("Player 2");}
		return lblP2;
	}
	public Label getLblP3() {
		if(lblP3 == null) {lblP3 = new Label("Player 3");}
		return lblP3;
	}
	public Label getLblP4() {
		if(lblP4 == null) {lblP4 = new Label("Player 4");}
		return lblP4;
	}
	//TextField
	public static TextField getTxtPlayer1() {
		if (txtPlayer1 == null) {txtPlayer1 = new TextField(); txtPlayer1.setPromptText("Pseudo");}
		return txtPlayer1;
	}
	public static TextField getTxtPlayer2() {
		if (txtPlayer2 == null) {txtPlayer2 = new TextField(); txtPlayer2.setPromptText("Pseudo");}
		return txtPlayer2;
	}
	public static TextField getTxtPlayer3() {
		if (txtPlayer3 == null) {txtPlayer3 = new TextField(); txtPlayer3.setPromptText("Pseudo");}
		return txtPlayer3;
	}
	public static TextField getTxtPlayer4() {
		if (txtPlayer4 == null) {txtPlayer4 = new TextField(); txtPlayer4.setPromptText("Pseudo");}
		return txtPlayer4;
	}
	//Button
	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setId("btn");
			btnBack.setOnAction(event -> {
				setGuest();
				getPlayer("2");
				getcPlayer().setValue(getcPlayer().getItems().get(0));
				MainMenuGP mmgp = (MainMenuGP) CreatePlayerGP.this.getParent().getParent();
				mmgp.setRoot(0);
			});
		}
		return btnBack;
	}
	public Button getBtnStart() {
		if(btnStart == null) {
			btnStart = new Button("Start");
			btnStart.setId("btn");
			btnStart.setOnAction(event -> {
				//Déclaration & Initialisation de la liste contenant les pseudos
				List<TextField> listText = getListTextFieldPseudo();
				boolean empty = true;
				
				//Vérifier si tous les joueurs ont mit un pseudo
				for (TextField textField : listText) {
					if(textField.getText().isEmpty()) {
						System.out.println("You need to give a pseudo to all players !");
						empty = false;
					}
				}
				//Si tous les joueurs ont un pseudo => Lance la partie
				if(empty) {
					//Récupérer le nombre de joueur
					int nbPlayerInt = Integer.parseInt(cPlayer.getSelectionModel().getSelectedItem().split(" ")[0]);
					
					//Récupérer les listes utiles
					List<TextField> pseudos = getListTextFieldPseudo();
					List<ImageView> icons = getListImageView();
					
					//Créer la liste de joueurs
					List<Player> players = new ArrayList<>();
					for(int i = 0; i<nbPlayerInt;i++) {
						players.add(new Player(pseudos.get(i).getText(), icons.get(i).getImage()));
					}
					
					//Passage à la vue du plateau
					MainMenuGP mmgp = (MainMenuGP) CreatePlayerGP.this.getParent().getParent();
					
					mmgp.getMainMenu().getChildren().add(mmgp.getBoard(players));
					mmgp.setRoot(6);
					
					//Reset des paramètres
					getPlayer("2");
				}
			});
		}
		return btnStart;
	}
	//Player 1
	public Button getBtnProfil1() {
		if(btnProfil1 == null) {btnProfil1 = new Button("Profil");
			}
		return btnProfil1;
	}
	public Button getBtnGuest1() {
		if(btnGuest1 == null) {btnGuest1 = new Button("Guest");}
		return btnGuest1;
	}
	//Player 2
	public Button getBtnProfil2() {
		if(btnProfil2 == null) {btnProfil2 = new Button("Profil");}		
		return btnProfil2;
	}
	public Button getBtnGuest2() {
		if(btnGuest2 == null) {btnGuest2 = new Button("Guest");}
		return btnGuest2;
	}
	//Player 3
	public Button getBtnProfil3() {
		if(btnProfil3 == null) {btnProfil3 = new Button("Profil");}
		return btnProfil3;
	}
	public Button getBtnGuest3() {
		if(btnGuest3 == null) {btnGuest3 = new Button("Guest");}
		return btnGuest3;
	}
	//Player 4
	public Button getBtnProfil4() {
		if(btnProfil4 == null) {btnProfil4 = new Button("Profil");}
		return btnProfil4;
	}
	public Button getBtnGuest4() {
		if(btnGuest4 == null) {btnGuest4 = new Button("Guest");}
		return btnGuest4;
	}
	//ImageView
	public ImageView getIvImage1() {
		if(ivImage1 == null) {ivImage1 = new ImageView(new Image("ressources/images/icons/Bulbasaur.png")); ivImage1.setFitWidth(50); ivImage1.setFitHeight(50);}
		return ivImage1;
	}
	public ImageView getIvImage2() {
		if(ivImage2 == null) {ivImage2 = new ImageView(new Image("ressources/images/icons/Bulbasaur.png")); ivImage2.setFitWidth(50); ivImage2.setFitHeight(50);}
		return ivImage2;
	}
	public ImageView getIvImage3() {
		if(ivImage3 == null) {ivImage3 = new ImageView(new Image("ressources/images/icons/Bulbasaur.png")); ivImage3.setFitWidth(50); ivImage3.setFitHeight(50);}
		return ivImage3;
	}
	public ImageView getIvImage4() {
		if(ivImage4 == null) {ivImage4 = new ImageView(new Image("ressources/images/icons/Bulbasaur.png")); ivImage4.setFitWidth(50); ivImage4.setFitHeight(50);}
		return ivImage4;
	}
	public HBox getHbSprite1() {
		if(hbSprite1 == null) {
			hbSprite1 = new HBox();
			ImageView ivSprite1 = new ImageView(new Image("ressources/images/sprites/Bulbasaur.gif"));
			ivSprite1.setFitWidth(100);
			ivSprite1.setFitHeight(100);
			hbSprite1.getChildren().add(ivSprite1);
		}
		return hbSprite1;
	}
	public HBox getHbSprite2() {
		if(hbSprite2 == null) {
			hbSprite2 = new HBox();
			ImageView ivSprite2 = new ImageView(new Image("ressources/images/sprites/Bulbasaur.gif"));
			ivSprite2.setFitWidth(100);
			ivSprite2.setFitHeight(100);
			hbSprite2.getChildren().add(ivSprite2);
		}
		return hbSprite2;
	}
	public HBox getHbSprite3() {
		if(hbSprite3 == null) {
			hbSprite3 = new HBox();
			ImageView ivSprite3 = new ImageView(new Image("ressources/images/sprites/Bulbasaur.gif"));
			ivSprite3.setFitWidth(100);
			ivSprite3.setFitHeight(100);
			hbSprite3.getChildren().add(ivSprite3);
		}
		return hbSprite3;
	}
	public HBox getHbSprite4() {
		if(hbSprite4 == null) {
			hbSprite4 = new HBox();
			ImageView ivSprite4 = new ImageView(new Image("ressources/images/sprites/Bulbasaur.gif"));
			ivSprite4.setFitWidth(100);
			ivSprite4.setFitHeight(100);
			hbSprite4.getChildren().add(ivSprite4);
		}
		return hbSprite4;
	}
	
	//Méthodes utile
	//Récupérer la liste des labels
	public List<Label> getListPlayer() {
		List<Label> listLabel = new ArrayList<>();
		listLabel.add(getLblP1());
		listLabel.add(getLblP2());
		listLabel.add(getLblP3());
		listLabel.add(getLblP4());
		
		return listLabel;
	}
	//Récupérer la liste des textfields
	public List<TextField> getListTextFieldPseudo() {
		List<TextField> listTextField = new ArrayList<>();
		listTextField.add(getTxtPlayer1());
		listTextField.add(getTxtPlayer2());
		listTextField.add(getTxtPlayer3());
		listTextField.add(getTxtPlayer4());
		
		return listTextField;
	}
	//Récupérer la liste des buttons (profil)
	public List<Button> getListButtonProfil() {
		List<Button> listButtonProfil = new ArrayList<>();
		listButtonProfil.add(getBtnProfil1());
		listButtonProfil.add(getBtnProfil2());
		listButtonProfil.add(getBtnProfil3());
		listButtonProfil.add(getBtnProfil4());
			
		return listButtonProfil;
	}
	//Récupérer la liste des buttons (profil)
	public List<Button> getListButtonGuest() {
		List<Button> listButtonGuest = new ArrayList<>();
		listButtonGuest.add(getBtnGuest1());
		listButtonGuest.add(getBtnGuest2());
		listButtonGuest.add(getBtnGuest3());
		listButtonGuest.add(getBtnGuest4());
				
		return listButtonGuest;
	}
	//Récupérer la liste des combobox
	public List<ComboBox<String>> getListCombo() {
		List<ComboBox<String>> listCombo = new ArrayList<>();
		listCombo.add(getCIcon1());
		listCombo.add(getCIcon2());
		listCombo.add(getCIcon3());
		listCombo.add(getCIcon4());
				
		return listCombo;
	}
	//Récupérer la liste des images view
	public List<ImageView> getListImageView() {
		List<ImageView> listImage = new ArrayList<>();
		listImage.add(getIvImage1());
		listImage.add(getIvImage2());
		listImage.add(getIvImage3());
		listImage.add(getIvImage4());
				
		return listImage;
	}
	//Récupérer la liste des images view
	public List<HBox> getListSprite() {
		List<HBox> listSprite = new ArrayList<>();
		listSprite.add(getHbSprite1());
		listSprite.add(getHbSprite2());
		listSprite.add(getHbSprite3());
		listSprite.add(getHbSprite4());
					
		return listSprite;
	}
	public void getPlayer(String nbPlayer) {
		//Récupérer le nombre de joueur
		int nbPlayerInt = Integer.parseInt(nbPlayer.split(" ")[0]);
		
		//Réglage de la ComboBox
		getcPlayer().setValue(cPlayer.getItems().get(nbPlayerInt - 2));
		
		//Changer le background
		switch(nbPlayerInt)
		{
			case 2 : this.setId("CreatePlayerP2"); break;
			case 3 : this.setId("CreatePlayerP3"); break;
			case 4 : this.setId("CreatePlayerP4"); break;
		}
		
		//Récupérer les listes des différents Nodes (Button, TextField, Label, ...)
		List<Label> listLabel = getListPlayer();
		List<TextField> listText = getListTextFieldPseudo();
		List<Button> listButtonPseudo = getListButtonProfil();
		List<Button> listButtonGuest = getListButtonGuest();
		List<ComboBox<String>> listCombo = getListCombo();
		List<ImageView> listImage = getListImageView();
		List<HBox> listSprite = getListSprite();
		
		//Masquer tous les éléments non désirés
		for (int i=0;i<=3;i++) {
			listLabel.get(i).setVisible(false);
			listText.get(i).setVisible(false);
			listButtonPseudo.get(i).setVisible(false);
			listButtonGuest.get(i).setVisible(false);
			listCombo.get(i).setVisible(false);
			listImage.get(i).setVisible(false);
			listSprite.get(i).setVisible(false);
		}
		
		//Afficher tous les éléments désirés
		for(int i = 0;i<nbPlayerInt;i++) {
			listLabel.get(i).setVisible(true);
			listText.get(i).setVisible(true);
			listButtonPseudo.get(i).setVisible(true);
			listButtonGuest.get(i).setVisible(true);
			listCombo.get(i).setVisible(true);
			listImage.get(i).setVisible(true);
			listSprite.get(i).setVisible(true);
			listText.get(i).setFocusTraversable(false);
		}
	}
	//Remettre tous les profils sur "Guest"
	public void setGuest() {
		List<Button> listButtonGuest = getListButtonGuest();
		List<Button> listButtonProfil = getListButtonProfil();
		List<TextField> listText = getListTextFieldPseudo();
		List<Label> listLabel = getListPlayer();
		List<ImageView> listImage = getListImageView();
		List<HBox> listSprite = getListSprite();
		List<ComboBox<String>> listCombo = getListCombo();
		
		for (Button button : listButtonGuest) {
			listText.get(listButtonGuest.indexOf(button)).setText(listLabel.get(listButtonGuest.indexOf(button)).getText());
			listText.get(listButtonGuest.indexOf(button)).setDisable(true);
			button.setId("IsSelected");
			button.setFocusTraversable(false);
			listButtonProfil.get(listButtonGuest.indexOf(button)).setId("");
			listButtonProfil.get(listButtonGuest.indexOf(button)).setFocusTraversable(false);
			listImage.get(listButtonGuest.indexOf(button)).setImage(new Image("ressources/images/icons/Bulbasaur.png"));
			((ImageView) listSprite.get(listButtonGuest.indexOf(button)).getChildren().get(0)).setImage(new Image("ressources/images/sprites/Bulbasaur_Face.gif"));
			listCombo.get(listButtonGuest.indexOf(button)).setValue(listCombo.get(listButtonGuest.indexOf(button)).getItems().get(0));
		}
	}    
	public void setFactory() {
		List<ComboBox<String>> list = getListCombo();
		List<ImageView> listImage = getListImageView();
		List<HBox> listSprite = getListSprite();
		
		int index = 0;
		for (ImageView image : listImage) 
		{
			list.get(index).setCellFactory(lv -> {
			    ListCell<String> cell = new ListCell<String>() {
			        @Override
			        protected void updateItem(String item, boolean empty) {
			            super.updateItem(item, empty);
			            setText(item);
			        }
			    };
			    cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
			        if (cell.isEmpty()) {
			        	image.setImage(null);
			        } 
			        else {
			        	int indexof = listImage.indexOf(image);
			        	image.setImage(new Image("ressources/images/icons/" + cell.getItem() + ".png"));
			        	((ImageView) listSprite.get(indexof).getChildren().get(0)).setImage(new Image("ressources/images/sprites/" + cell.getItem() + "_Face.gif"));
			        }
			    });
			    return cell ;
			});
			//Incrémentation de l'index
			index++;
		}	
	}
}