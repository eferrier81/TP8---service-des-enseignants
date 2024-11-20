package champollion;

import lombok.Getter;

@Getter
public class Salle {
    private final String intitule;
    private final int capacite;

    public Salle(String intitule, int capacite) {
        this.intitule = intitule;
        this.capacite = capacite;
    }
}