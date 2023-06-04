package modele;

import java.io.Serializable;
import java.util.Objects;

import enumeration.Category;


/**
 * Création de question à implémenter dans un deck
 */

public class Question
{
	//Variables
	/**
	 * Nom de l'auteur de la question
	 */
	private String author;
	/**
	 * Nom de la catégorie de la question
	 */
	private Category category;
	/**
	 * L'intitulé de la question
	 */
	private String statement;
	/**
	 * La réponse correct
	 */
	private String answer;
	
	//CONSTRUCTEUR
	/**
	 * Permet d'instancier une question
	 * @param author, l'auteur de la question
	 * @param category, la catégorie de la question
	 * @param statement, l'intitulé de la question
	 * @param answer, la réponse à la question
	 */
	public Question(String author, Category category, String statement, String answer)
	{
		this.author = author;
		this.category = category;
		this.statement = statement;
		this.answer = answer;
	}
	
	//GETTERS
	/**
	 * Renvoie l'autheur de la question
	 * @return l'autheur de la question
	 */
	public String getAuthor() {return author;}
	/**
	 * Renvoie la catégorie de la question
	 * @return la catégorie de la question
	 */
	public Category getCategory() {return category;}
	/**
	 * Renvoie l'intitulé de la question
	 * @return l'intitulé de la question
	 */
	public String getStatement() {return statement;}
	/**
	 * Renvoie la réponse de la question
	 * @return la réponse de la question
	 */
	public String getAnswer() {return answer;}

	//SETTERS
	/**
	 * Modifie l'autheur de la question
	 * @param auhtor, le nouvelle autheur de la question
	 */
	public void setAuthor(String auhtor) {this.author = auhtor;}
	/**
	 * Modifie la catégorie de la question
	 * @param category, la nouvelle catégorie de la question
	 */
	public void setCategory(Category category) {this.category = category;}
	/**
	 * Modifie l'intitulé de la question
	 * @param statement, le nouvelle intitulé de la question
	 */
	public void setStatement(String statement) {this.statement = statement;}
	/**
	 * Modifie la réponse de la question
	 * @param answer, la nouvelle réponse à la question
	 */
	public void setAnswer(String answer) {this.answer = answer;}

	//HASHCODE
	public int hashCode() {return Objects.hash(statement);}

	//EQUALS
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(statement, other.statement);
	}

	//CLONE
	public Question clone()
	{
		return new Question(author, category, statement, answer);
	}

	//TOSTRING
	public String toString() {
		return "Question [author=" + author + ", category=" + category + ", statement=" + statement + ", answer="
				+ answer + "]";
	}
}
