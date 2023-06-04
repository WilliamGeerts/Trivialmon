package enumeration;

public enum Cursor {
	BASIC_CURSOR("Pokemon Cursor"),
	LUCAS("Lucas"),
	MEW("Mew");
	//Variable
	private String name;

    //Constructeur
    private Cursor(String name) {
    	this.name = name;
    }

    //Getters
    public String getName() {
    	return name;
    }
    
    //ToString
    @Override
    public String toString() {
    	return name;
    }
}
