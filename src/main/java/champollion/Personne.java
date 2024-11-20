package champollion;

import lombok.Getter;

@Getter
public class Personne {
    private final String nom;
    private final String email;

    public Personne(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

}
