package enumeration;

public enum BonusType {

	LEFTOVERS("Leftovers"),
	MAXREPEL("Max Repel"),
	//METRONOME("Metronome"),
	//NEVERMELTICE("Never Melt Ice"),
	REPEL("Repel");
	//REVIVE("Revive");
	
	//Variable
	private String name;

    //Constructeur
    private BonusType(String name) {this.name = name;}

    //Getters
    public String getName() {return name;}
    
    //ToString
    @Override
    public String toString() {return name;}
}
