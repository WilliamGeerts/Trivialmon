package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import enumeration.Category;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import modele.Player;

public class BoardGP extends GridPane
{
	//Nodes
	private Label lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4, lblPlayerTurn;
	private ImageView ivPlayer1, ivPlayer2, ivPlayer3, ivPlayer4, diceImage;
	private StackPane cP1, cP2, cP3, cP4;
	private Button btnDice, btnParam;
	private HBox hbP1, hbP2, hbP3, hbP4, hbP1badge, hbP2badge, hbP3badge, hbP4badge;
    
    //Joueur(s)
    private List<Player> players;
    private Player playerSelected;
    
    //Variables des joueurs
    private List<Label> listLabel;
    private List<ImageView> listImage;
    private List<HBox> listHBoxs;
    private List<HBox> listHBoxsBadge;
    private List<StackPane> listStack;
    
    //Plateau
    private final int DICE_VALUE = 12; //MAX 12
    private List<StackPane> listCase = new ArrayList<>();
    private int[] listNumeroCasePlayer = {0, 0, 0, 0};
    //X=26 & Y=53
	private int[][] listeInt =
	{ 
		{42,223, 1}, {68,223, 1}, {94,223, 1}, {120,223, 1}, //Vers la droite
		{120, 170, 1},{120, 117, 1}, {120, 64, 1}, //Vers le haut
		{146, 64, 1}, {172, 64, 1}, {198, 64, 1}, {224, 64, 1}, //Vers la droite
		{224, 117, 1}, {224, 170, 1}, {224, 223, 1}, //Vers le bas
		{250, 223, 1}, {276, 223, 1}, {302, 223, 1}, {328, 223, 1}, {354, 223, 1}, {380, 223, 1}, //Vers la droite
		{380, 170, 1}, //Vers le haut
		{406, 170, 1}, {432, 170, 1}, {458, 170, 1}, //Vers la droite
		{458, 223, 1}, {458, 276, 1}, {458, 329, 1}, //Vers le bas
		{432, 329, 1}, {406, 329, 1}, {380, 329, 1}, //Vers la gauche
		{380, 382, 1}, //Vers le bas
		{354, 382, 1}, {328, 382, 1}, {302, 382, 1}, {276, 382, 1}, {250, 382, 1}, {224, 382, 1}, {198, 382, 1}, {172, 382, 1}, {146, 382, 1}, {120, 382, 1}, {94, 382, 1}, {68, 382, 1}, //Vers la gauche
		{68, 329, 1}, //Vers le haut
		{42, 329, 1}, //Vers la gauche
		{42, 276, 1} //Vers le haut
	};
	
	public BoardGP(List<Player> players) {
		//Création des lignes et des colonnes
		createLineThere(this, false);

		//Conserver la résolution de la carte
		this.setScaleX(1);
		this.setScaleY(1);
		
		//Récupération des listes
		listLabel = getListPlayer();
		listImage = getListImageView();
		listHBoxs = getListHBox();
		listStack = getListStack();
		listHBoxsBadge = getListHBoxBadge();
		
		//Récupération de la liste de joueur
		this.players = players;
				
		//Adaptation des nodes par rapport au nombre de joueur
		getPlayer(String.valueOf(players.size()));
	
		//Initialisation du premier joueur
		this.playerSelected = players.get(0);
		
		//Récupération d'un tableau dépendant du nombre de catégorie
		Random rand = new Random();
		int listInt[] = getCategoryNumber();
		
		//Ajouter une catégorie à chaque case
		for (int[] is : listeInt) {
			//Initialiser les valeurs à chaque passage
			int valueIndex = rand.nextInt(listInt.length);
			int value = listInt[valueIndex];
			
			//Créez un nouveau tableau avec une taille réduite
	        int[] nouveauTableau = new int[listInt.length - 1];

	        //Copiez les éléments du tableau d'origine dans le nouveau tableau
	        int j = 0;
	        for (int i = 0; i < listInt.length; i++) {if (i != valueIndex) {nouveauTableau[j] = listInt[i]; j++;}}

	        //Mettez à jour le tableau d'origine avec le nouveau tableau
	        listInt = nouveauTableau;
			
			is[2] = value;
			if(listInt.length == 0) {listInt = getCategoryNumber();}
		}
		
		//Ajouter les cases au plateau
		for(int i=0;i<+ listeInt.length;i++) {
			ImageView badge = new ImageView(new Image("/ressources/images/badges/badge" + listeInt[i][2] + ".png"));
			badge.setFitWidth(35); badge.setFitHeight(35);
			
			StackPane badgeBox = new StackPane(badge);
			badgeBox.setAlignment(Pos.CENTER);
			badgeBox.setLayoutX(listeInt[0][0]);
			badgeBox.setLayoutX(listeInt[0][1]);
			
			this.add(badgeBox, listeInt[i][0], listeInt[i][1], 26, 54);
			listCase.add(badgeBox);
		}
		
		//Ajouts des éléments dans les HBox des joueurs + les pions sur le plateau
		for (int i=0;i<this.players.size();i++) {
			listLabel.get(i).setText(this.players.get(i).getName());
			listImage.get(i).setImage(this.players.get(i).getIcon());
			listImage.get(i).setFitHeight(40);
			listImage.get(i).setFitWidth(40);
			
			ImageView image = new ImageView(this.players.get(i).getIcon());
			image.setFitHeight(40);
			image.setFitWidth(40);
			
			listStack.get(i).getChildren().add(image);
			
			this.add(listStack.get(i), listeInt[0][0], listeInt[0][1], 26, 54);
		}

		//Ajouter tous les pions sur la première case
		for (StackPane pion : listStack) {
			listCase.get(0).getChildren().add(pion);
		}
		
		//Ajouts des éléments
		this.add(listHBoxs.get(0), 0, 0, 195, 20);
		this.add(listHBoxsBadge.get(0), 0, 34, 195, 40);
		this.add(listHBoxs.get(1), 305, 0, 195, 20);
		this.add(listHBoxsBadge.get(1), 305, 34, 195, 40);

		this.add(getBtnDice(), 200, 475, 100, 25);
		this.add(getBtnParam(), 225, 30, 50, 25);
		
		HBox hboxDice = new HBox(getDiceImage());
		hboxDice.setAlignment(Pos.CENTER);
		hboxDice.setStyle("-fx-border-color: none;");
		this.add(hboxDice, 150, 200, 200, 50);

		HBox hboxTurn = new HBox(getLblPlayerTurn());
		hboxTurn.setId("hbTurn");
		hboxTurn.setAlignment(Pos.CENTER);
		hboxTurn.setStyle("-fx-border-color: white;");
		this.add(hboxTurn, 200, 0, 100, 25);
		
		updateBadge();
		for (Node node : this.getChildren()) {
		    if (node instanceof Button || node instanceof CheckBox) {
		        (node).addEventHandler(ActionEvent.ACTION, e -> onClicked(e));
		    }
		}
	}
	
	//Action pour chaque click (Sur chaque Button & CheckBox)
	private void onClicked(ActionEvent e) {
		MainMenuGP mmgp = (MainMenuGP) BoardGP.this.getParent().getParent();
		if(mmgp.getSettings().getCbSoundEffect().isSelected()) {
			int volume = Integer.parseInt(mmgp.getSettings().getLblSoundEffectValue().getText());
			mmgp.click(volume);
		}
	}

	//Regénérer la liste de nombre
	public int[] getCategoryNumber() {
		int[] list = new int[Category.values().length];
		for(int i = 0; i<Category.values().length;i++) {list[i] = i + 1;}
		
		return list;
	}
	
	//GETTERS
	//Label
	public List<Player> getPlayers() {
		return players;
	}
	public Label getLblPlayer1() {
		if(lblPlayer1 == null) {lblPlayer1 = new Label("Player");}
		return lblPlayer1;
	}
	public Label getLblPlayer2() {
		if(lblPlayer2 == null) {lblPlayer2 = new Label("Player");}
		return lblPlayer2;
	}
	public Label getLblPlayer3() {
		if(lblPlayer3 == null) {lblPlayer3 = new Label("Player");}
		return lblPlayer3;
	}
	public Label getLblPlayer4() {
		if(lblPlayer4 == null) {lblPlayer4 = new Label("Player");}
		return lblPlayer4;
	}
	//ImageView
	public ImageView getIvPlayer1() {
		if(ivPlayer1 == null) {ivPlayer1 = new ImageView();}
		return ivPlayer1;
	}
	public ImageView getIvPlayer2() {
		if(ivPlayer2 == null) {ivPlayer2 = new ImageView();}
		return ivPlayer2;
	}
	public ImageView getIvPlayer3() {
		if(ivPlayer3 == null) {ivPlayer3 = new ImageView();}
		return ivPlayer3;
	}
	public ImageView getIvPlayer4() {
		if(ivPlayer4 == null) {ivPlayer4 = new ImageView();}
		return ivPlayer4;
	}
	
	public Label getLblPlayerTurn() {
		if(lblPlayerTurn == null) {
			lblPlayerTurn = new Label(playerSelected.getName() + "'s turn");
			lblPlayerTurn.setId("playerTurn");
		}
		return lblPlayerTurn;
	}

	public ImageView getDiceImage() {
		if(diceImage == null) {
			diceImage = new ImageView();
			diceImage.toFront();
			diceImage.setPreserveRatio(true);
			diceImage.setSmooth(true);
			diceImage.setCache(true);
			diceImage.setScaleX(1);
			diceImage.setScaleY(1);
			diceImage.setVisible(false);
		}
		
		return diceImage;
	}
	//StackPane
	public StackPane getCP1() {
		if(cP1 == null) {Circle c = new Circle(10, 10, 25);c.setFill(Color.rgb(142, 198, 250));cP1 = new StackPane(c);}
		return cP1;
	}
	public StackPane getCP2() {
		if(cP2 == null) {Circle c = new Circle(10, 10, 25);c.setFill(Color.rgb(147, 201, 129));cP2 = new StackPane(c);}
		return cP2;
	}
	public StackPane getCP3() {
		if(cP3 == null) {Circle c = new Circle(10, 10, 25);c.setFill(Color.rgb(232, 170, 116));cP3 = new StackPane(c);}
		return cP3;
	}
	public StackPane getCP4() {
		if(cP4 == null) {Circle c = new Circle(10, 10, 25);c.setFill(Color.rgb(204, 98, 188));cP4 = new StackPane(c);}
		return cP4;
	}
	//Button
	public Button getBtnDice() {
		if(btnDice == null) {
			btnDice = new Button("Roll Dice");
			btnDice.setOnAction(new EventHandler<ActionEvent>() 
			{
				//Initialisation des variables
				Random random = new Random();
				StackPane firstCase, pawn;
				List<StackPane> previousCase = new ArrayList<>();
				int dice, indexNow;

				//Lancement du dé
				public void handle(ActionEvent event) 
				{
					//Rendre les deux autres boutons inutilisables
					final double MOVE_SPEED = 0.5;
					btnDice.setDisable(true);
					getBtnParam().setDisable(true);
					
					//Création des TimeLine
					Timeline rollTL = new Timeline();
					rollTL.setCycleCount(1);
					Timeline moveTL = new Timeline();
					moveTL.setCycleCount(1);
					
					//Définition des délais entre chaque itération
			        Duration rollD = Duration.seconds(0.1);
			        Duration moveD = Duration.seconds(MOVE_SPEED);
			        
			        //Définition des délais de pauses
			        PauseTransition rollPT = new PauseTransition(Duration.seconds(1));
			        PauseTransition movePT = new PauseTransition(Duration.seconds(1));
			        
	            	//Inscription en console
	                System.out.println("En cours de lancé ..");
	                
			        //Crée un EventHandler pour l'action à effectuer
			        EventHandler<ActionEvent> rollEH = new EventHandler<ActionEvent>() {
			            public void handle(ActionEvent event) {
			            	//Affichage du dé
			            	diceImage.setVisible(true);
			            	
			            	//Changement de la valeur du dé & de l'image de celui-ci
	                    	dice = random.nextInt(DICE_VALUE) + 1;
	                    	getDiceImage().setImage(new Image("/ressources/images/dice/dice" + dice + ".jpg"));
			            }
			        };
			        EventHandler<ActionEvent> moveEH = new EventHandler<ActionEvent>() {
			            public void handle(ActionEvent event) {
							//Incrémentation du numéro de la case du joueur
							listNumeroCasePlayer[indexNow] += 1;
							
							//Reset de celle-ci si il revient au début du plateau 
							if(listNumeroCasePlayer[indexNow] >= 46) {listNumeroCasePlayer[indexNow] = 0;}
							
							//Si la case de destination contient un pion, on passe au dessus
							while(listCase.get(listNumeroCasePlayer[indexNow]).getChildren().size() > 1) {
								listNumeroCasePlayer[indexNow] += 1;
								
								//Reset de celle-ci si il revient au début du plateau 
								if(listNumeroCasePlayer[indexNow] >= 46) {listNumeroCasePlayer[indexNow] = 0;}
							}
							
							//Placer le pion au premier plan
					        firstCase.toFront();
							
							TranslateTransition pawnTT = new TranslateTransition(Duration.seconds(MOVE_SPEED - 0.1), pawn);
							pawnTT.setToX(listCase.get(listNumeroCasePlayer[indexNow]).getLayoutX() - firstCase.getLayoutX());
							pawnTT.setToY(listCase.get(listNumeroCasePlayer[indexNow]).getLayoutY() - firstCase.getLayoutY());

							//Ajouter la case à une liste pour reset le "ToFront"
							previousCase.add(firstCase);
							
							pawnTT.setOnFinished(action -> {
								//Suppression du pion
								firstCase.getChildren().remove(pawn);

								//Ajouts dans la nouvelle case
								listCase.get(listNumeroCasePlayer[indexNow]).getChildren().addAll(pawn);
								
								//Réinitialiser les positions du pion
								pawn.setTranslateX(0);
								pawn.setTranslateY(0);
								
								for (StackPane stackPane : previousCase) {stackPane.toBack();}
								previousCase.clear();
							});
							
							//Changement de la case qui contient le pion
							firstCase = listCase.get(listNumeroCasePlayer[indexNow]); 
							
							pawnTT.play();
			            }
			        };
			        
			        //Ajoute les KeyFrames à la Timeline avec le délai spécifié
			        for (int i = 0; i < 15; i++) {
			            KeyFrame keyFrame = new KeyFrame(rollD.multiply(i + 1), rollEH);
			            rollTL.getKeyFrames().add(keyFrame);
			        }
			        
			        //Ajouter les actions à la fin des autres
			        rollTL.setOnFinished(finishedEvent -> {
						for (int i = 0; i < dice; i++) {
				            KeyFrame keyFrame = new KeyFrame(moveD.multiply(i + 1), moveEH);
				            moveTL.getKeyFrames().add(keyFrame);
				        }
						
			        	rollPT.play();
			        });
			        rollPT.setOnFinished(finishedEvent -> {
			        	//Désaffichage du dé
		            	diceImage.setVisible(false);
		            	
		            	//CALCULS
		            	//Index du joueur en cours
						indexNow = players.indexOf(playerSelected);
							
						//Obtenir la nouvelle case du joueur
						int indiceNewCase = dice + listNumeroCasePlayer[indexNow];
						
						//Reset de cellle-ci si il revient au début du plateau
						if(indiceNewCase >= 46) {indiceNewCase = indiceNewCase - 46;}
							
						//Informations
						//System.out.println("Dé : " + dice);
						//System.out.println("Numéro case     : " + listNumeroCasePlayer[indexNow]);
						//System.out.println("Numéro new Case : " + indiceNewCase);
						//System.out.println("Case actuelle : " + listCase.get(listNumeroCasePlayer[indexNow]));
						//System.out.println("Case cible    : " + listCase.get(indiceNewCase));
						//System.out.println("Objet case    : " + listCase.get(listNumeroCasePlayer[indexNow]).getChildren());
						//System.out.println("Stack Pion    : " + listCase.get(listNumeroCasePlayer[indexNow]).getChildren().get(1));
							
						//Récupération des éléments (Possition pions déjà présent)			
						firstCase = listCase.get(listNumeroCasePlayer[indexNow]);      	  
						pawn = (StackPane) listCase.get(listNumeroCasePlayer[indexNow]).getChildren().get(1); 
						
			        	moveTL.play();
			        });
			        moveTL.setOnFinished(finishedEvent -> {
			        	movePT.play();
			        });
			        movePT.setOnFinished(finishedEvent -> {
			        	//Ajouts & Afficher le combat
						MainMenuGP mmgp = (MainMenuGP) BoardGP.this.getParent().getParent();
						int indiceCategory = listeInt[listNumeroCasePlayer[indexNow]][2];
						mmgp.getMainMenu().getChildren().add(mmgp.getFight(playerSelected, indiceCategory));
						mmgp.setRoot(7);
						
						btnDice.setDisable(false);
						getBtnParam().setDisable(false);
						
						//Passage du joueurs sélectionner au joueur suivant
						int innerIndiceAfter = 0;
						if(indexNow < (players.size() - 1)) {innerIndiceAfter = indexNow + 1;}
						else if(indexNow == (players.size() - 1)) {innerIndiceAfter = 0;}
									
						playerSelected = players.get(innerIndiceAfter);
						getLblPlayerTurn().setText(playerSelected.getName() + "'s turn");
			        });
			        
			        //Démarre la première TimeLine
			        rollTL.play();
				}
			});
		}
		return btnDice;
	}
	public Button getBtnParam() {
		if(btnParam == null) {
			btnParam = new Button("Settings");
			btnParam.setOnAction((ActionEvent event) -> {
				MainMenuGP mmgp = (MainMenuGP) BoardGP.this.getParent().getParent();
				mmgp.setRoot(2)
			;});
		}
		return btnParam;
	}
	//HBox
	public HBox getHboxP1() {
		if(hbP1 == null) {hbP1 = new HBox(getIvPlayer1(), getLblPlayer1());}
		return hbP1;
	}
	public HBox getHboxP2() {
		if(hbP2 == null) {hbP2 = new HBox(getLblPlayer2(), getIvPlayer2());}
		return hbP2;
	}
	public HBox getHboxP3() {
		if(hbP3 == null) {hbP3 = new HBox(getIvPlayer3(), getLblPlayer3());}
		return hbP3;
	}
	public HBox getHboxP4() {
		if(hbP4 == null) {hbP4 = new HBox(getLblPlayer4(), getIvPlayer4());}
		return hbP4;
	}
	public HBox getHboxP1Badge() {
		if(hbP1badge == null) {hbP1badge = new HBox();}
		return hbP1badge;
	}
	public HBox getHboxP2Badge() {
		if(hbP2badge == null) {hbP2badge = new HBox();}
		return hbP2badge;
	}
	public HBox getHboxP3Badge() {
		if(hbP3badge == null) {hbP3badge = new HBox();}
		return hbP3badge;
	}
	public HBox getHboxP4Badge() {
		if(hbP4badge == null) {hbP4badge = new HBox();}
		return hbP4badge;
	}
	//List
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public List<Label> getListPlayer() {
		List<Label> listLabel = new ArrayList<>();
		listLabel.add(getLblPlayer1());
		listLabel.add(getLblPlayer2());
		listLabel.add(getLblPlayer3());
		listLabel.add(getLblPlayer4());
		
		return listLabel;
	}
	public List<ImageView> getListImageView() {
		List<ImageView> listImage = new ArrayList<>();
		listImage.add(getIvPlayer1());
		listImage.add(getIvPlayer2());
		listImage.add(getIvPlayer3());
		listImage.add(getIvPlayer4());
				
		return listImage;
	}
	public List<StackPane> getListStack() {
		List<StackPane> listStack = new ArrayList<>();
		listStack.add(getCP1());
		listStack.add(getCP2());
		listStack.add(getCP3());
		listStack.add(getCP4());
				
		return listStack;
	}
	public List<HBox> getListHBox() {
		List<HBox> listHbox = new ArrayList<>();
		listHbox.add(getHboxP1());
		listHbox.add(getHboxP2());
		listHbox.add(getHboxP3());
		listHbox.add(getHboxP4());
		listHbox.get(0).setAlignment(Pos.CENTER_LEFT);
		listHbox.get(1).setAlignment(Pos.CENTER_RIGHT);
		listHbox.get(2).setAlignment(Pos.CENTER_LEFT);
		listHbox.get(3).setAlignment(Pos.CENTER_RIGHT);
		listHbox.get(0).setId("P1");
		listHbox.get(1).setId("P2");
		listHbox.get(2).setId("P3");
		listHbox.get(3).setId("P4");
		for (HBox hBox : listHbox) {
			hBox.setSpacing(20);
			hBox.setPadding(new Insets(5));
		}
		
		return listHbox;
	}
	public List<HBox> getListHBoxBadge() {
		List<HBox> listHbox = new ArrayList<>();
		listHbox.add(getHboxP1Badge());
		listHbox.add(getHboxP2Badge());
		listHbox.add(getHboxP3Badge());
		listHbox.add(getHboxP4Badge());
		listHbox.get(0).setAlignment(Pos.CENTER_LEFT);
		listHbox.get(1).setAlignment(Pos.CENTER_RIGHT);
		listHbox.get(2).setAlignment(Pos.CENTER_LEFT);
		listHbox.get(3).setAlignment(Pos.CENTER_RIGHT);
		listHbox.get(0).setId("Badge");
		listHbox.get(1).setId("Badge");
		listHbox.get(2).setId("Badge");
		listHbox.get(3).setId("Badge");
		for (HBox hBox : listHbox) {
			hBox.setSpacing(20);
			hBox.setPadding(new Insets(5));
		}
		
		return listHbox;
	}
	public void getPlayer(String nbPlayer) {
		//Récupérer le nombre de joueur
		int nbPlayerInt = Integer.parseInt(nbPlayer.split(" ")[0]);
		
		//Supprimer les éléments non désirés
		for(int i=3;i>=(nbPlayerInt);i--) {
			listLabel.remove(i);
			listImage.remove(i);
			listHBoxs.remove(i);
			listStack.remove(i);
			listHBoxsBadge.remove(i);
		}
		
		if(nbPlayerInt == 3) {this.add(listHBoxs.get(2), 0, 475, 195, 20);this.add(listHBoxsBadge.get(2), 0, 421, 195, 40);}
		if(nbPlayerInt == 4) {this.add(listHBoxs.get(2), 0, 475, 195, 20);this.add(listHBoxsBadge.get(2), 0, 421, 195, 40);this.add(listHBoxs.get(3), 305, 475, 195, 20);this.add(listHBoxsBadge.get(3), 305, 421, 195, 40);}
	}
	public void updateBadge() {
		//Récupérer le nombre de joueur
		String nbPlayer = String.valueOf(players.size());
		int nbPlayerInt = Integer.parseInt(nbPlayer.split(" ")[0]);
		
		//Récupérer les listes des différents Nodes (Button, TextField, Label, ...)
		List<HBox> listHBoxsBadge = getListHBoxBadge();
				
		for(int i=0;i<nbPlayerInt;i++) {
			listHBoxsBadge.get(i).getChildren().clear();
			
			if(i%2 == 0) {listHBoxsBadge.get(i).getChildren().add(new Label("Badges " + players.get(i).getBadges().size() + "/" + Category.values().length + " :"));}

			for (String badge : players.get(i).getBadges()) {
				List<Category> badges = new ArrayList<>();
				for (Category cat : Category.values()) {
					badges.add(cat);
				}
				int indexOf = badges.indexOf(Category.valueOf(badge.toUpperCase())) + 1;
				ImageView Ivbadge = new ImageView(new Image("/ressources/images/badges/badge" + indexOf + ".png"));
				Ivbadge.setSmooth(true);
				Ivbadge.setCache(true);
				Ivbadge.setScaleX(1);
				Ivbadge.setScaleY(1);
				Ivbadge.setFitHeight(35);
				Ivbadge.setFitWidth(35);
				listHBoxsBadge.get(i).getChildren().add(Ivbadge);
			}
			if(i%2 != 0) {listHBoxsBadge.get(i).getChildren().add(new Label(": " + "Badges " + players.get(i).getBadges().size() + "/" + Category.values().length));}
		}
	}
	public void createLineThere(GridPane gp, Boolean affichageLigne) {
		gp.setGridLinesVisible(affichageLigne);
		gp.setPadding(new Insets(25));
		final int numCol = 500;
		final int numRown = 500;
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
}