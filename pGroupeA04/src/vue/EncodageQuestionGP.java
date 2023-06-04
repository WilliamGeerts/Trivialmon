package vue;

import enumeration.Category;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.Deck;
import modele.Question;

public class EncodageQuestionGP extends GridPane 
{
	private Label lblAuthor, lblStatement, lblAnswer, lblCategory;
	private TextField txtAuthor, txtStatement, txtAnswer;
	private ComboBox<String> cbCategory;
	private Button btnValid, btnBack;
	
	//Constructeur
	public EncodageQuestionGP() {
		vue.MainMenuGP.createLine(this, false);
		this.getStylesheets().add("/ressources/css/encodage.css");
		
		//Ajouts des éléments
		this.add(getLblCategory(), 30, 30, 20, 10);
		this.add(getLblAuthor(), 30, 40, 20, 10);
		this.add(getLblStatement(), 30, 50, 20, 10);
		this.add(getLblAnswer(), 30, 60, 20, 10);
		
		this.add(getCbCategory(), 40, 30, 20, 10);
		this.add(getTxtAuthor(), 40, 40, 20, 10);
		this.add(getTxtStatement(), 40, 50, 40, 10);
		this.add(getTxtAnswer(), 40, 60, 20, 10);
		
		this.add(getBtnValid(), 84, 91, 15, 8);
		this.add(getBtnBack(), 1, 91, 15, 8);
	}
	
	
	//Label
	public Label getLblAuthor() {
		if(lblAuthor == null) {lblAuthor = new Label("Author :");}
		return lblAuthor;
	}
	public Label getLblStatement() {
		if(lblStatement == null) {lblStatement = new Label("Statement :");}
		return lblStatement;
	}
	public Label getLblAnswer() {
		if(lblAnswer == null) {lblAnswer = new Label("Answer :");}
		return lblAnswer;
	}
	public Label getLblCategory() {
		if(lblCategory == null) {lblCategory = new Label("Category :");}
		return lblCategory;
	}
	//TextField
	public TextField getTxtAuthor() {
		if(txtAuthor == null) {txtAuthor = new TextField();}
		return txtAuthor;
	}
	public TextField getTxtStatement() {
		if(txtStatement == null) {txtStatement = new TextField();}
		return txtStatement;
	}
	public TextField getTxtAnswer() {
		if(txtAnswer == null) {txtAnswer = new TextField();}
		return txtAnswer;
	}
	//ComboBox
	public ComboBox<String> getCbCategory() {
		if(cbCategory == null) {
			cbCategory = new ComboBox<String>();
			for(Category mod : Category.values()){cbCategory.getItems().add(mod.toString());}
		}
		return cbCategory;
	}
	//Button
	public Button getBtnValid() {
		if(btnValid == null) {
			btnValid = new Button("Valid");
			btnValid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnValid.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event)
				{
					//Récupérer les parent
					MainMenuGP mmgp = (MainMenuGP) EncodageQuestionGP.this.getParent().getParent();
					
					//Récupérer les inforamtions
					String author = getTxtAuthor().getText();
					String answer = getTxtAnswer().getText();
					String statement = getTxtStatement().getText();
					
					if(getCbCategory().getSelectionModel().getSelectedItem() != null && !author.isEmpty() && !answer.isEmpty() && !statement.isEmpty())
					{
						String category = getCbCategory().getSelectionModel().getSelectedItem();
						Deck d = mmgp.getEditcard().getDeck();
						
						//Création de la question
						Question q = new Question(author, Category.valueOf(category.toUpperCase()), statement, answer);
						
						//Ajouter la question aux liste
						if(d.add(q)) {
							mmgp.getEditcard().getTvQuestions().getItems().add(q);
							d.add(q);
							
							//Message de confirmation
							mmgp.getEditcard().getAlert(AlertType.INFORMATION, "Your question has been added succesfully !");
						}
						
						else {
							//Message de confirmation
							mmgp.getEditcard().getAlert(AlertType.WARNING, "Your question is already in the deck !");
						}
						
						
						
						//Changement du nombre de question
						((Label) mmgp.getEditcard().getHbQuestionsNumber().getChildren().get(0)).setText("Questions : " + d.getQuestions().size());
						
						//Retour en arrière
						mmgp.getMainMenu().getChildren().remove(EncodageQuestionGP.this);
						mmgp.getEditcard().setVisible(true);
					}
					else {mmgp.getEditcard().getAlert(AlertType.WARNING, "You need to complete all fields !");}
				}
			});
		}
		return btnValid;
	}
	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnBack.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent event)
				{
					//Passage à la vue du plateau
					MainMenuGP mmgp = (MainMenuGP) EncodageQuestionGP.this.getParent().getParent();
					mmgp.getMainMenu().getChildren().remove(EncodageQuestionGP.this);
					mmgp.setRoot(1);
				}
			});
		}
		return btnBack;
	}
}
