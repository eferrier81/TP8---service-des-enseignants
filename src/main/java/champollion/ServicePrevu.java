package champollion;

public class ServicePrevu {
    private int heuresCM;
    private int heuresTD;
    private int heuresTP;

    // Ajoute les heures pour chaque type d'enseignement
    public void ajouterHeures(int cm, int td, int tp) {
        this.heuresCM += cm;
        this.heuresTD += td;
        this.heuresTP += tp;
    }

    // Calcule le nombre d'heures équivalentes en TD
    public int heuresEquivalentTD() {
        // Par exemple, 1 CM = 1.5 heures TD, 1 TD = 1 heure TD, 1 TP = 0.75 heure TD
        return (int) (heuresCM * 1.5 + heuresTD * 1.0 + heuresTP * 0.75);
    }

    // Retourne le volume d'heures pour un type d'intervention spécifique (CM, TD, TP)
    public int getVolume(TypeIntervention type) {
        return switch (type) {
            case CM -> heuresCM;
            case TD -> heuresTD;
            case TP -> heuresTP;
        };
    }
}
