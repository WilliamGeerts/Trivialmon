package modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Pour importer et expoter toutes les questions du jeu dans un fichier Json
 *
 */
public class Persistance
{
	
	//Écrire dans un fichier
	/**
	 * Pour sauvegarder les questions du jeu dans un fichier json
	 * @param file nom du fichier où l'on souhaite sauvegarder les questions
	 * @param instance les questions que l'on souhaite mettre dans le fichier
	 */
	 public static void write(String file, Deck instance)
	 {
		//Déclaration & Initialisation des variabl
		 Gson gson = new GsonBuilder().setPrettyPrinting().create();
		 String json = gson.toJson(instance);
		 
		//Try-Catch pour écrire dans le fichier
		 try
		 {
			 //Initialisation du fichier
			 File f = new File("src/ressources/datas/dataQuestion.json");
			 
			 //Écriture des données sous format GSON
			 PrintWriter PW = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			 PW.print(json);
			 
			 //Fermeture du graveur
			 PW.close();
		 }
		 catch (IOException e){e.printStackTrace();}
	 }
	
	//Sauvegarder un fichier
	 /**
	  * Importer une instance de Deck grâce à un fichier json
	  * @param file le nom du fichier que l'on souhaite utiliser
	  * @return renvoie une instance Deck
	  */
	public static Deck read(String file)
    {
		//Déclaration & Initialisation des variables
        String json = "";
        Deck instance;
        //"src/ressources/datas/dataQuestion.json"
        File f = new File(file);

        //Try-Catch pour lire le fichier et le convertir au format GSON
        try
        {
        	//Ouverture de la lecture
        	BufferedReader FR = new BufferedReader(new FileReader(f));
        	
        	//Déclaration & Initialisation de la première ligne
        	String line = null;
        	
        	//Lire toutes les lignes
        	while ((line = FR.readLine()) != null) {json += line;}

        	//Création du GSON
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        	instance = gson.fromJson(json, Deck.class);
        	
        	//Fermeture du lecteur
        	FR.close();
        	
        	//Retour de l'instance "Deck"
        	return instance;
        }
        catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException e){e.printStackTrace();}
        catch (IllegalStateException e) {e.printStackTrace();}
        
        //Si ça se passe mal, on renvoie "null"
        return null;
    }
}
