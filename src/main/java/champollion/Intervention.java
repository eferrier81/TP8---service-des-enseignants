package champollion;

import lombok.Getter;

import java.util.Date;

@Getter
public class Intervention {
    private final Date debut;
    private final int duree;
    private final boolean annulee;
    private final int heureDebut;
    private final UE ue;
    private final Salle salle;
    private final TypeIntervention type;

    public Intervention(Date debut, int duree, int heureDebut, UE ue, Salle salle, TypeIntervention type) {
        this.debut = debut;
        this.duree = duree;
        this.heureDebut = heureDebut;
        this.ue = ue;
        this.salle = salle;
        this.type = type;
        this.annulee = false;
    }
}
