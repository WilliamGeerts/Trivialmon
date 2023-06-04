package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe Deck sert de gestion de listes pour toutes les questions 
 * que l'on rajoute dans une partie
 * 
 * 
 */

public class Deck
{
	
	/**
	 * La liste des questions qu'on ajoute dans un deck
	 */
	//Variables
	private List<Question> questions;

	
	/**
	 * Constructeur du deck avec instanciation d'une liste
	 */
	//CONSTRUCTEUR
	public Deck()
	{
		questions = new ArrayList<>();
	}
	
	/**
	 * Méthode pour ajouter des questions dans la liste du deck
	 * @param question à ajouter dans la liste
	 * @return false si la liste contient déjà la question sinon true
	 */
	//ADD
	public boolean add(Question question)
	{
		if(questions.contains(question))
		{
			return false;
		}
		return questions.add(question.clone());
	}
	
	/**
	 * Méthode pour trouver la question en argument dans la liste du deck
	 * @param question à retrouver dans la liste
	 * @return l'index de la question en argument
	 */
	//FIND
	public int find(Question question)
	{
		return questions.indexOf(question); 
	}
	
	/**
	 * Mettre à jour une question
	 * @param q1 nouvelle question
	 * @param q2 ancienne question
	 * @return false si q1 déjà dans la liste,
	 * false si q1 est null,
	 * sinon true
	 * 
	 */
	//UPDATE
	public boolean update(Question q1, Question q2) {
		//Si la liste contient déjà la question 1 (La nouvelle question)
		if(questions.contains(q1)) {return false;}
		
		//Si la nouvelle question est égal à null
		if(q1 == null) {return false;}
		
		//Si non, on la modifie
		questions.set(questions.indexOf(q2), q1); return true;
	}
	
	/**
	 * Mettre à jour une question sans vérification si le statement est déjà dans le deck
	 * @param q1 nouvelle question
	 * @param q2 ancienne question
	 * @return false si q1 est null,
	 * sinon true
	 * 
	 */
	//UPDATE
	public boolean updateAttribut(Question q1, Question q2) {
		//Si la nouvelle question est égal à null
		if(q1 == null) {return false;}
				
		//Si non, on la modifie
		questions.set(questions.indexOf(q2), q1); return true;
	}	
	
	/**
	 * Supprime une question dans la liste
	 * @param question à supprimer
	 * @return true 
	 */
	//DELETE
	public boolean delete(Question question)
	{
		return questions.remove(question);
	}

	/**
	 * Permet de renvoyer la liste de questions
	 * @return La liste des questions
	 */
	//TOSTRING
	public String toString() {
		return "Deck [questions=" + questions + "]";
	}

	/**
	 * Permet de comparer deux listes de questions
	 * @return true si les deux listes de questions sont identiques et false si elles ne le sont pas
	 */
	//EQUALS
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deck other = (Deck) obj;
		return Objects.equals(questions, other.questions);
	}
	
	/**
	 * Renvoie la liste des questions
	 * @return la liste des questions
	 */
	//GETTER
	public List<Question> getQuestions() {
		ArrayList<Question> questionsClone = new ArrayList<>();
		
		for (Question question : questions) {
			questionsClone.add(question.clone());
		}
		return questionsClone;
	}	

	/**
	 * Modifie la liste des questions
	 * @param qList, la nouvelle liste des questions
	 */
	//SETTER
	public void setQuestions(List<Question> qList) {
		this.questions = qList;
	}
}
