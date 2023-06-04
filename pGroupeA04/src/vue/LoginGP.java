package vue;

import org.mindrot.jbcrypt.BCrypt;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class LoginGP extends GridPane
{
	//Création des différents Label (Pour afficher ce qu'on attends de l'utilisateur)
	private Label lblLogin, lblPassword, lblVerification;
	
	//Création des différents TextField (Pour stocker les valeurs entrer par l'utilisateur)
	private TextField txtLogin;
	private PasswordField pwdPaseword;
	
	//Création du Button pour envoyer les différentes informations.
	private Button btnOk, btnBack;
	
	//Identifiant
	private String user = "admin", pwd = "helha";
	
	public LoginGP()
	{
		//Affichage du grillage
		vue.MainMenuGP.createLine(this, false);
		
		//AJOUTS DES ÉlÉMENTS
		this.add(getLblLogin(), 30, 40, 20, 5);
		this.add(getTxtLogin(), 50, 40, 20, 5);
		this.add(getLblPassword(), 30, 50, 20, 5);
		this.add(getPwdPaseword(), 50, 50, 20, 5);
		this.add(getLblVerification(), 44, 57, 20, 5);
		this.add(getBtnOk(), 43, 65, 16, 8);
		this.add(getBtnBack(), 1, 91, 15, 8);
	}
	
	//GETTERS
	public Label getLblPassword() {
		if(lblPassword == null)
			lblPassword = new Label("Password : ");
		return lblPassword;
	}
	public Label getLblLogin() {
		if(lblLogin == null)
			lblLogin = new Label("Login : ");
		return lblLogin;
	}
	public TextField getTxtLogin() {
		if(txtLogin == null)
			txtLogin = new TextField();
		return txtLogin;
	}
	public PasswordField getPwdPaseword() {
		if(pwdPaseword == null) {
			pwdPaseword = new PasswordField();
			pwdPaseword.setOnKeyPressed(event -> {if (event.getCode().equals(KeyCode.ENTER)) {getBtnOk().fire();}});
		}
		return pwdPaseword;
	}
	public Label getLblVerification() {
		if(lblVerification == null)
			lblVerification = new Label("");
		return lblVerification;
	}
	public Button getBtnOk() {
		if(btnOk == null)
			btnOk = new Button("Valider");
			btnOk.setOnAction(event -> {		
				//Génération du mot de passe hashé
				String hashedCheck = BCrypt.hashpw(getPwdPaseword().getText(), BCrypt.gensalt(10));

				//Vérification du mot de passe
				if (BCrypt.checkpw(pwd, hashedCheck) && user.equals(getTxtLogin().getText())){
					lblVerification.setText("");
					pwdPaseword.clear();
					txtLogin.clear();
					
					//Changement de page
					MainMenuGP mmgp = (MainMenuGP) LoginGP.this.getParent().getParent();
					mmgp.setRoot(1);
				}
				else {
					lblVerification.setText("Identifiants incorrects !");
					lblVerification.setTextFill(Color.RED);
				}
		});
		return btnOk;
	}
	public Button getBtnBack() {
		if(btnBack == null) {
			btnBack = new Button("Back");
			btnBack.setId("btn");
			btnBack.setOnAction(event -> {
				MainMenuGP mmgp = (MainMenuGP) LoginGP.this.getParent().getParent();
				mmgp.setRoot(0);
			});
		}
		return btnBack;
	}
}
