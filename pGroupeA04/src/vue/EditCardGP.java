package vue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.StartGame;
import enumeration.Category;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.Deck;
import modele.Persistance;
import modele.Question;

public class EditCardGP extends GridPane
{
	//Création des différents attributs
	private HBox HbQuestionsNumber;
	private Button btnAdd, btnErased, btnBack;
	private TableView<Question> tvQuestions;
	private MenuBar mbMenu;
	private Menu menuFile;
	private MenuItem exports;
	private MenuItem imports;
	private Deck deck, deckWaiting;
	
	public EditCardGP()
	{
		//Instanciation du deck
		this.deck = Persistance.read("src/ressources/datas/dataQuestion.json");
		this.deckWaiting = Persistance.read("src/ressources/datas/dataQuestion.json");
		
		//Affichage du grillage
		vue.MainMenuGP.createLine(this, false);
		
		//Label
		this.add(getHbQuestionsNumber(), 82, 14, 15, 10);
		
		//Boutons
		this.add(getBtnAdd(), 82, 38, 15, 8);
		this.add(getBtnErased(), 82, 50, 15, 8);
		this.add(getBtnBack(), 1, 91, 15, 8);
		
		//Affichage des questions
		this.add(getMbMenu(), 0, 0, 100, 2);
		this.add(getTvQuestions(), 3, 10, 75, 65);
	}
	
	//GETTERS
	public HBox getHbQuestionsNumber() {
		if(HbQuestionsNumber == null){HbQuestionsNumber = new HBox(new Label("Question : 0")); HbQuestionsNumber.setId("NumberQ");}
		return HbQuestionsNumber;
	}
	
	@SuppressWarnings("unchecked")
	public TableView<Question> getTvQuestions(){
		if(tvQuestions == null) {
			//Paramètres de la TV
			tvQuestions = new TableView<>();
			tvQuestions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			tvQuestions.setItems(FXCollections.observableArrayList(deck.getQuestions()));
			tvQuestions.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvQuestions.setEditable(true);
			
			//Création des différents colonnes
			TableColumn<Question, String> colCategory = new TableColumn<>("Category");
			colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
			
			TableColumn<Question, String> colAuthor = new TableColumn<>("Author");
			colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
			
			TableColumn<Question, String> colStatement = new TableColumn<>("Statement");
			colStatement.setCellValueFactory(new PropertyValueFactory<>("statement"));
			
			TableColumn<Question, String> colAnswer = new TableColumn<>("Answer");
			colAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
			
			//Ajouts de l'édit au double clique.
			colAuthor.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
			colAuthor.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question,String>>() 
			{
				public void handle(CellEditEvent<Question, String> event) 
				{
					//Initialisation des données nécessaires
					Question newQuestion = event.getRowValue().clone();
					Question oldQuestion = event.getRowValue();
	
					//Préparation du modèle
					newQuestion.setAuthor(event.getNewValue());
					
					if(deck.updateAttribut(newQuestion, oldQuestion))
					{
						//Modifier la vue
						event.getTableView().getItems().get(event.getTablePosition().getRow()).setAuthor(event.getNewValue());
					}
					
					//Raffraichissement de la TV
					tvQuestions.refresh();
				}
			});
			
			colStatement.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
			colStatement.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question,String>>() 
			{
				public void handle(CellEditEvent<Question, String> event) 
				{
					//Initialisation des données nécessaires
					Question newQuestion = event.getRowValue().clone();
					Question oldQuestion = event.getRowValue();
	
					//Préparation du modèle
					newQuestion.setStatement(event.getNewValue());
					
					//Modifier le modèle
					if(deck.update(newQuestion, oldQuestion))
					{
						//Modifier la vue
						event.getTableView().getItems().get(event.getTablePosition().getRow()).setStatement(event.getNewValue());
					}
					
					//Raffraichissement de la TV
					tvQuestions.refresh();
				}
			});
			
			colAnswer.setCellFactory(TextFieldTableCell.<Question>forTableColumn());
			colAnswer.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Question,String>>() 
			{
				public void handle(CellEditEvent<Question, String> event) 
				{
					//Initialisation des données nécessaires
					Question newQuestion = event.getRowValue().clone();
					Question oldQuestion = event.getRowValue();
	
					//Préparation du modèle
					newQuestion.setAnswer(event.getNewValue());
					
					//Modifier le modèle
					if(deck.updateAttribut(newQuestion, oldQuestion))
					{
						//Modifier la vue
						event.getTableView().getItems().get(event.getTablePosition().getRow()).setAnswer(event.getNewValue());
					}
					
					//Raffraichissement de la TV
					tvQuestions.refresh();
				}
			});
			
			tvQuestions.getColumns().addAll(colCategory, colAuthor, colStatement, colAnswer);
			
			//Initialisation du nombre de questions
			((Label) getHbQuestionsNumber().getChildren().get(0)).setText("Questions : " + deck.getQuestions().size());
		}
		return tvQuestions;
	}

	public Button getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new Button("Add");
			btnAdd.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnAdd.setOnAction((ActionEvent event) -> {
				//Passage à la vue du plateau
				MainMenuGP mmgp = (MainMenuGP) EditCardGP.this.getParent().getParent();
				mmgp.getMainMenu().getChildren().add(mmgp.getEncodage());
				mmgp.setRoot(6);
			});
			
		}
		return btnAdd;
	}
	public Button getBtnErased() {
		if(btnErased == null) {
			btnErased = new Button("Erased");
			btnErased.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnErased.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					
					if(!getTvQuestions().getSelectionModel().getSelectedCells().isEmpty())
					{
						//Création de la liste de questions à supprimer
						List<Question> questions = new ArrayList<>();
						questions.addAll(getTvQuestions().getSelectionModel().getSelectedItems());		

						//Supprimer les questions ciblées
						for (Question q : questions) {
							deck.delete(q);
							getTvQuestions().getItems().remove(q);
						}
						
						//Changer le nombre de question
						((Label) getHbQuestionsNumber().getChildren().get(0)).setText("Questions : " + deck.getQuestions().size());
						
						getAlert(AlertType.INFORMATION, "Question(s) have been deleted succesfully !");
					}
					else{getAlert(AlertType.WARNING, "You need to select at least one question !");}
				}
			});
		}
		return btnErased;
	}
	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			btnBack.setOnAction(event -> {	
				if(!deck.equals(deckWaiting))
				{
					//Création de l'alert (PAS LA FONCTION CAR J'AI BESOIN DE LA RÉCUPÉRER CETTE FOIS)
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			        alert.setTitle("Confirmation");
			        alert.setHeaderText(null);
			        alert.setContentText("Do you want to save the JSON file ?");
					
					//Afficher l'alerte et attendre la réponse de l'utilisateur
			        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
			        
			        //Vérifier la réponse de l'utilisateur
			        if (result == ButtonType.OK) {
			        	Persistance.write("src/ressources/datas/dataQuestion.json", deck);
			        	this.deck = Persistance.read("src/ressources/datas/dataQuestion.json");
			        	this.deckWaiting = Persistance.read("src/ressources/datas/dataQuestion.json");
			        }
			        else {
			        	setDeck(deckWaiting);
			        	getTvQuestions().setItems(FXCollections.observableArrayList(deck.getQuestions()));
			        }
			        
			        //À faire dans les deux cas
			        ((Label) getHbQuestionsNumber().getChildren().get(0)).setText("Questions : " + deck.getQuestions().size());
		        	getTvQuestions().refresh();
				}
				
				//Revenir au Menu (0)
				MainMenuGP mmgp = (MainMenuGP) EditCardGP.this.getParent().getParent();
				mmgp.setRoot(0);
		});}
		return btnBack;
	}
	public MenuBar getMbMenu() {
		if(mbMenu == null) {mbMenu = new MenuBar();mbMenu.getMenus().addAll(getMenuFile());}
		return mbMenu;
	}
	public Menu getMenuFile() {
		if(menuFile == null) {menuFile = new Menu("File");menuFile.getItems().addAll(getExports(), getImports());}
		return menuFile;
	}
	public MenuItem getExports() {
		if(exports == null) {
			exports = new MenuItem("Export");
			exports.setOnAction((ActionEvent t) -> {
				FileChooser fc = new FileChooser();
				File fileSave = fc.showSaveDialog(null);
					
				//Enregistrement du deck
				Persistance.write(fileSave.getPath(), deck);
			});
		}
		return exports;
	}

	public MenuItem getImports() {
		if(imports == null) {
			imports = new MenuItem("Import");
			imports.setOnAction((ActionEvent t) -> {
				FileChooser fc = new FileChooser();
				File fileOpen = fc.showOpenDialog(null);
				if(fileOpen != null)
				{
					//Variables
					int added = 0;
					Boolean isOk = true;
					Boolean haveCat = false;
					
					//Récupération du fichier à lire
					Deck deckToImport = Persistance.read(fileOpen.getPath());
					
					//Ajouts des questions au vrai Deck
					for(Question q : deckToImport.getQuestions()){
						//Réinitialisation des variables
						isOk = true;
						haveCat = false;
						
						//Si la catégorie de la question est null => Au revoir
						if(q.getCategory() == null) {isOk = false;}
						
						//Si la catégorie de la question n'est pas présente dans notre jeu => Au revoir
						for(Category cat : Category.values()) {
							if(cat.equals(q.getCategory())){haveCat = true;}
						}
						
						//Si la phrase de la question n'est pas encore présente + la catégorie est correct => Bonjour
						if(isOk && haveCat) {
							if(deck.add(q)){getTvQuestions().getItems().add(q); added += 1;}
						}
					}
					
					//Alert au client
					MainMenuGP mmgp = (MainMenuGP) EditCardGP.this.getParent().getParent();
					
					System.out.println("Added = " + added);
					System.out.println("All   = " + deckToImport.getQuestions().size());
					
					if(added == 0) {mmgp.getEditcard().getAlert(AlertType.WARNING, "All the questions are already in the deck !");}
					else if(added == deckToImport.getQuestions().size()) {mmgp.getEditcard().getAlert(AlertType.CONFIRMATION, "All your questions have been added succesfully !");}
					else {mmgp.getEditcard().getAlert(AlertType.CONFIRMATION, added + "/" + deckToImport.getQuestions().size() + " questions have been added succesfully ! \nOthers are already in the deck or are not at the good format !");}
					
					//Raffraichissement de la TV
					getTvQuestions().refresh();
					
					//Changer le nombre de question
					((Label) getHbQuestionsNumber().getChildren().get(0)).setText("Questions : " + deck.getQuestions().size());
				}
			});
		}
		return imports;
	}
	
	//Méthode utiles	
	//Créer une alerte
	public void getAlert(AlertType alert, String message) {
		Stage stage = StartGame.getStage();
		Alert a = new Alert(AlertType.NONE);
		a.setAlertType(alert);
		a.setHeaderText(null);
		a.setContentText(message);
		a.initOwner(stage);
		a.show();
	}
	
	//GET DECK
	public Deck getDeck() {
		return deck;
	}
	//SET DECK
	public void setDeck(Deck newDeck) {
		this.deck = newDeck;
	}
	
	 
}