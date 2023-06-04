package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

import dpStrategy.Leftovers;
import dpStrategy.MaxRepel;
import dpStrategy.Metronome;
import dpStrategy.NeverMeltIce;
import dpStrategy.Repel;
import dpStrategy.Revive;
import enumeration.BonusType;
import enumeration.Category;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import modele.CardEffect;
import modele.Deck;
import modele.Persistance;
import modele.Player;
import modele.Question;
import modele.Timer;

public class FightGP extends GridPane {
	private Label lblCategoryName, lblPlayerName, lblQuestion, lblAnswer;
	private TextField txtAnswer;
	private Player playerSelected;
	private Deck deck;
	private Question questionRand;
	private Timer timer;
	private ImageView ivBadge, ivIcon, sprite, enemySprite;
	private Button bonus0, bonus1, bonus2, bonus3, btnSubmit, btnDemo;
	private CardEffect cardEffect;

	public FightGP(Player playerSelected, int indiceCategory) {
		//Mettre le css
		this.setId("Fight");
		this.getStylesheets().add("/ressources/css/fight.css");
		
		//Récupérer le joueur
		if(playerSelected != null){this.playerSelected = playerSelected;}
		else {this.playerSelected = new Player("Wouilliam", new Image("ressources/images/icons/Bulbasaur.png"));}
		
		//Récupérer le deck et une question au hasard en fonction de la catégorie de la case
		getQuestionRand(indiceCategory);
		
		//Création des lignes
		MainMenuGP.createLine(this, false);	
		
		//Création des HBox + Modification de ceux-ci
		HBox categoryBadge = new HBox(getIvBadge(), getLblCategoryName());
		HBox playerIcon = new HBox(getIvIcon(), getLblPlayerName());
		
		categoryBadge.setSpacing(10);
		playerIcon.setSpacing(10);
		
		//Ajouts des HBox
		this.add(categoryBadge, 0, 12, 40, 10);
		this.add(playerIcon, 60, 54, 40, 10);
		this.add(getTimer(), 95, 87);
		this.add(getEnemySprite(), 65, 15, 20, 28);
		this.add(getSprite(), 18, 49, 25, 25);
		
		//Question
		this.add(getLblQuestion(), 4, 78, 40, 30);
		this.add(getTxtAnswer(), 50, 85, 30, 5);
		this.add(getLblAnswer(), 50, 80, 50, 5);
		this.add(getBtnSubmit(), 82, 85, 10, 5);
		this.add(getBtnDemo(), 90, 78, 8, 5);
				
		//Afficher les solutions dans la console 
		getInformations();
				
		//Boutons bonus
		List<Button> listButton = getListButtonBonus();
		for (Button button : listButton) {if(button.getText().equals("No bonus")) {button.setDisable(true);}}
		for (Button button : listButton) {
			this.add(button, 50 + 11 * listButton.indexOf(button), 92,10,6);
			
			String bonusH = button.getText();
			Tooltip information = null;
			
			switch(bonusH) {
				case "Leftovers": information = new Tooltip("Add the first 2 letters !");break;
				case "Repel": information = new Tooltip("Change the question !");break;
				case "Max Repel": information = new Tooltip("Change the category and the question !");break;
				case "Metronome": information = new Tooltip("Reset the time !");break;
				case "Revive": information = new Tooltip("2 try to answer the question !");break;
				case "Never Melt Ice": information = new Tooltip("Stop the time !");break;
			}
			
			button.setTooltip(information);
			
			//Méthode pour le DP
			button.setOnAction(event ->{
				String bonusHere = button.getText();
				if(!bonusHere.equals("No bonus")) {
					switch(bonusHere) {
						case "Leftovers": this.cardEffect = new Leftovers();break;
						case "Repel": this.cardEffect = new Repel();break;
						case "Max Repel": this.cardEffect = new MaxRepel();break;
						case "Metronome": this.cardEffect = new Metronome();break;
						case "Revive": this.cardEffect = new Revive();break;
						case "Never Melt Ice": this.cardEffect = new NeverMeltIce();break;
					}
					
					this.setBonus(cardEffect);
					this.bonusEffect();
					
					//Enlève le bonus de l'inventaire lorsqu'il est utilisé 
					playerSelected.getInventory().remove(listButton.indexOf(button));
					
					for (Button button2 : listButton) {button2.setText("No bonus");}
					for (int i=0;i<playerSelected.getInventory().size();i++) {getListButtonBonus().get(i).setText(playerSelected.getInventory().get(i).toString());}
					for (Button button2 : listButton) 
					{
						if(button2.getText().equals("No bonus")) {button2.setDisable(true);}
						else{button2.setDisable(false);}
						
						String newBonusHere = button2.getText();
						Tooltip newInformation = null;
						
						//Changer le tooltip
						switch(newBonusHere) {
							case "Leftovers": newInformation = new Tooltip("Add the first 2 letters !");break;
							case "Repel": newInformation = new Tooltip("Change the question !");break;
							case "Max Repel": newInformation = new Tooltip("Change the category and the question !");break;
							case "Metronome": newInformation = new Tooltip("Reset the time !");break;
							case "Revive": newInformation = new Tooltip("Can responde 2 times the question !");break;
							case "Never Melt Ice": newInformation = new Tooltip("Stop the time !");break;
						}
						
						//Changer le tooltip
						button2.setTooltip(newInformation);
					}
				}
			});
		}
		
		for (Node node : this.getChildren()) {
		    if (node instanceof Button || node instanceof CheckBox) {
		        (node).addEventHandler(ActionEvent.ACTION, e -> onClicked(e));
		    }
		}
	}
	//Récupérer la question
	public Question getQuestionRand() {return questionRand;}
	public void setQuestionRand(Question newQuestionRand) {questionRand = newQuestionRand;}
	
	//Récupérer le joueur séléctionner
	public Player getSelectedPlayer() {return playerSelected;}
	
	//Action pour chaque click (Sur chaque Button & CheckBox)
	private void onClicked(ActionEvent e) {
		MainMenuGP mmgp = (MainMenuGP) FightGP.this.getParent().getParent();
		if(mmgp.getSettings().getCbSoundEffect().isSelected()) {
			int volume = Integer.parseInt(mmgp.getSettings().getLblSoundEffectValue().getText());
			mmgp.click(volume);
		}
	}
	
	//Getters
	public Label getLblCategoryName() {
		if(lblCategoryName == null) {lblCategoryName = new Label(questionRand.getCategory().toString());}
		return lblCategoryName;
	}
	public Label getLblPlayerName() {
		if(lblPlayerName == null) {lblPlayerName = new Label(playerSelected.getName());}
		return lblPlayerName;
	}
	public Label getLblQuestion() {
		if(lblQuestion == null) {lblQuestion = new Label(questionRand.getStatement()); lblQuestion.setWrapText(true); lblQuestion.setId("Title");}
		return lblQuestion;
	}
	public Label getLblAnswer() {
		if(lblAnswer == null) {lblAnswer = new Label("Correct Answer : " + questionRand.getAnswer()); lblAnswer.setWrapText(true); lblAnswer.setVisible(false); lblAnswer.setStyle("-fx-font-size : 15px; -fx-text-fill: red;");}
		return lblAnswer;
	}

	public TextField getTxtAnswer() {
		if(txtAnswer == null) {
			txtAnswer = new TextField();
			txtAnswer.setOnKeyPressed(event -> {if (event.getCode().equals(KeyCode.ENTER)) {getBtnSubmit().fire();}});
		}
		return txtAnswer;
	}
	public Button getBtnSubmit() {
		if(btnSubmit == null) {
			btnSubmit = new Button("Submit");
			btnSubmit.setOnAction(event -> {
				//IDs
				btnSubmit.setId("IsSelected");
			
				//Récupérer la liste de badge du joueur
				List<String> badges = playerSelected.getBadges();

				//Bonne réponse
				double similarity = findSimilarity(getTxtAnswer().getText().toLowerCase().trim(), questionRand.getAnswer().toLowerCase().trim());
				if(similarity > 0.67 || similarity == 1.0) {
					btnSubmit.setId("RightQuestion");	

					if(!badges.contains(questionRand.getCategory().toString())){
						//Si le joueur n'as pas le badge de la catégorie
						badges.add(questionRand.getCategory().toString());
						MainMenuGP mmgp = (MainMenuGP) FightGP.this.getParent().getParent();
						mmgp.getBoard(null).updateBadge();
					}
					else {
						//Si le joueur a le badge de la catégorie
						//Création de la liste des bonus pour les ajouts aléatoires
						BonusType[] types = BonusType.values();
						System.out.println(types);
						
						//Ajout d'une carte bonus dans l'inventaire si le joueur a déjà le badge
						Random random = new Random();
						int index = random.nextInt(types.length);
						
						if (playerSelected.getInventory().size()<4) {playerSelected.getInventory().add(types[index]);}	
					}
					getLblAnswer().setStyle("-fx-font-size : 15px; -fx-text-fill: green;");
				}
				
				getLblAnswer().setVisible(true);
				
				//Désactiver les éléments
				btnSubmit.setDisable(true);
				getTxtAnswer().setEditable(false);
				
				List<Button> listButton = getListButtonBonus();
				for (Button b : listButton) {b.setDisable(true);}
				
				//Quitter le combat
				leftFight();
			});
		}
		return btnSubmit;
	}
	public ImageView getIvBadge() {
		if(ivBadge == null) {
			List<Category> badges = new ArrayList<>();
			for (Category cat : Category.values()) {
				badges.add(cat);
			}
			int indexOf = badges.indexOf(questionRand.getCategory()) + 1;
			ivBadge = new ImageView(new Image("/ressources/images/badges/badge" + indexOf + ".png"));
			ivBadge.setFitHeight(40);
			ivBadge.setFitWidth(40);
		}
		return ivBadge;
	}
	public ImageView getIvIcon() {
		if(ivIcon == null) {ivIcon = new ImageView(playerSelected.getIcon()); ivIcon.setFitHeight(50); ivIcon.setFitWidth(50);}
		return ivIcon;
	}
	
	public ImageView getSprite() {
		if(sprite == null) {
			//Reprends uniquement le nom du gif dans l'url
			String[] gifName = playerSelected.getIcon().getUrl().split("/");
			String Name = gifName[gifName.length - 1].replace(".png", "");
			sprite = new ImageView(new Image("ressources/images/sprites/" + Name + ".gif"));
			sprite.setFitHeight(200);
			sprite.setFitWidth(200);
		}
		return sprite;
	}
	

	public ImageView getEnemySprite() {
		if(enemySprite==null) {
			String catName = questionRand.getCategory().toString();
			enemySprite = new ImageView(new Image("ressources/images/enemySprites/" + catName + ".gif"));
			enemySprite.setFitHeight(270);
			enemySprite.setFitWidth(270);
		}
		return enemySprite;
	}
	
	public void setBonus(CardEffect ce) {
		this.cardEffect = ce;
	}

	public void bonusEffect() {
		getCardEffect().bonusEffect(this);
	}
	
	//Méthode utiles
	//Récupérer une question random dans le deck
	public void getQuestionRand(int indiceCategory) {
		Random rand = new Random();
		this.deck = Persistance.read("src/ressources/datas/dataQuestion.json");
		
		boolean isNotOk = true;
		while(isNotOk)
		{
			questionRand = deck.getQuestions().get(rand.nextInt(deck.getQuestions().size()));
			if(questionRand.getCategory().equals(Category.values()[indiceCategory-1])){isNotOk = false;}
		}
	}
	//Action à effectuer avant de quitter le "Combat"
	public void leftFight() {
		KeyFrame kf = new KeyFrame(Duration.seconds(2),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//Retour sur le plateau
						MainMenuGP mmgp = (MainMenuGP) FightGP.this.getParent().getParent();
						mmgp.getMainMenu().getChildren().remove(7);
						mmgp.setRoot(6);
						
						//Vérification, si le joueur a les x badges	 et gagne la partie
						if(playerSelected.getBadges().size() == Category.values().length) {
							mmgp.getMainMenu().getChildren().add(new VictoryGP(playerSelected));
							mmgp.getMainMenu().getChildren().remove(6);
							mmgp.setRoot(6);
						}
					}
				});
		Timeline tl = new Timeline(kf);
		tl.setCycleCount(1);
		tl.play();
	}
	//Récupérer les réponses vrais et fausses pour les afficher dans la console
	public void getInformations() {
		//Affichage du numéro de la question dans la liste
		int indexQuestion = deck.getQuestions().indexOf(questionRand) + 1;
		System.out.println("\033[0;31m" + "Question n°" + indexQuestion + "/" + deck.getQuestions().size() + " :" + "\033[0m");
		
		//Affichage de la question & de l'autheur
		System.out.println(questionRand.getStatement() + " - " + questionRand.getAuthor());
		
		//Affichage des réponses
		System.out.println("\033[0;32m" + "Correct   - " + questionRand.getAnswer() + "\033[0m");
		
		System.out.println("Bonus : " + playerSelected.getInventory());
		System.out.println("Badge(s) : " + playerSelected.getBadges());
		System.out.println("----------------------------------------------------------------------------------------");
	}

	public CardEffect getCardEffect() {
		return cardEffect;
	}

	public Timer getTimer() {
    	if (timer == null) {timer = new Timer(this, 30);}
		return timer;
	}
	
	//Boutons pour le DP
	public Button getBonus0() {
		if(bonus0==null) {
			if(playerSelected.getInventory().size()>0) {bonus0 = new Button(playerSelected.getInventory().get(0).toString());}
			else {bonus0 = new Button("No bonus");}
		}
		return bonus0;
	}
	
	public Button getBonus1() {
		if(bonus1==null) {
			if(playerSelected.getInventory().size()>1) {bonus1 = new Button(playerSelected.getInventory().get(1).toString());}
			else {bonus1 = new Button("No bonus");}
		}
		return bonus1;
	}
	
	public Button getBonus2() {
		if(bonus2==null) {
			if(playerSelected.getInventory().size()>2) {bonus2 = new Button(playerSelected.getInventory().get(2).toString());}
			else {bonus2 = new Button("No bonus");}
		}
		return bonus2;
	}
	public Button getBonus3() {
		if(bonus3==null) {
			if(playerSelected.getInventory().size()>3) {bonus3 = new Button(playerSelected.getInventory().get(3).toString());}
			else {bonus3 = new Button("No bonus");}
		}
		return bonus3;
	}
	public Button getBtnDemo() {
		if(btnDemo == null) {
			btnDemo = new Button("Demo");
			btnDemo.setOnAction(event -> {		
				getLblAnswer().setVisible(true);
			});
		}
		return btnDemo;
	}
	
	//Récupérer la liste des boutons bonus
	public List<Button> getListButtonBonus() {
		List<Button> listButton = new ArrayList<>();
		listButton.add(getBonus0());
		listButton.add(getBonus1());
		listButton.add(getBonus2());
		listButton.add(getBonus3());
		
		return listButton;
	}
	
	//Méthodes pour le pourcentage d'erreurs
	public static int getLevenshteinDistance(String X, String Y)
    {
        int m = X.length();
        int n = Y.length();

        int[][] T = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }
	
    
    public static double findSimilarity(String x, String y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            // optionally ignore case if needed
            return (maxLength - getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }
        
}
