package enumeration;

public enum Pawn {
    BULBASAUR("Bulbasaur"),
	CHARMANDER("Charmander"),
	SQUIRTLE("Squirtle"),
	PIKACHU("Pikachu"),
	MACHOP("Machop"),
	SLOWPOKE("Slowpoke"),
	FARFETCH("Farfetch'd"),
	CUBONE("Cubone"),
	MAGIKARP("Magikarp"),
	EEVEE("Eevee"),
	SNORLAX("Snorlax"),
	MEWTWO("MewTwo");

    private String nom;

    private Pawn(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
