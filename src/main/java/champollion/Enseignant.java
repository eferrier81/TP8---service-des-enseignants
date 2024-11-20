package champollion;

import java.util.*;

public class Enseignant extends Personne {
    private final Map<UE, ServicePrevu> enseignements = new HashMap<>();
    private final List<Intervention> interventionsPlanifiees = new ArrayList<>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    public void ajouteEnseignement(UE ue, int cm, int td, int tp) {
        ServicePrevu service = enseignements.getOrDefault(ue, new ServicePrevu());
        service.ajouterHeures(cm, td, tp);
        enseignements.put(ue, service);
    }

    public int heuresPrevues() {
        return enseignements.values().stream()
                .mapToInt(ServicePrevu::heuresEquivalentTD)
                .sum();
    }

    public int heuresPrevuesPourUE(UE ue) {
        ServicePrevu service = enseignements.get(ue);
        return service != null ? service.heuresEquivalentTD() : 0;
    }

    public void ajouteIntervention(Intervention inter) throws Exception {
        UE ue = inter.getUe();
        TypeIntervention type = inter.getType();

        int planifie = interventionsPlanifiees.stream()
                .filter(i -> i.getUe().equals(ue) && i.getType() == type)
                .mapToInt(Intervention::getDuree)
                .sum();

        int prevu = enseignements.getOrDefault(ue, new ServicePrevu()).getVolume(type);
        if (planifie + inter.getDuree() > prevu) {
            throw new Exception("Heures planifiées dépassent les heures prévues pour ce type d'intervention.");
        }

        interventionsPlanifiees.add(inter);
    }

    public int resteAPlanifier(UE ue, TypeIntervention type) {
        int prevu = enseignements.getOrDefault(ue, new ServicePrevu()).getVolume(type);
        int planifie = interventionsPlanifiees.stream()
                .filter(i -> i.getUe().equals(ue) && i.getType() == type)
                .mapToInt(Intervention::getDuree)
                .sum();
        return prevu - planifie;
    }

    public boolean enSousService() {
        return heuresPrevues() < 192;
    }
}
