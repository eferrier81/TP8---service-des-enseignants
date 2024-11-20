package champollion;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Création des enseignants
        Enseignant bastide = new Enseignant("Rémi Bastide", "Remi.Bastide@irit.fr");
        Enseignant lamine = new Enseignant("Elyes Lamine", "Elyes.Lamine@univ-jfc.fr");

        // Création des UE
        UE uml = new UE("Conception par objets avec UML", 20, 15, 10);
        UE bd = new UE("Bases de données", 10, 20, 15);
        UE web = new UE("Technologies web", 15, 10, 20);

        // Ajout d'enseignements
        bastide.ajouteEnseignement(uml, 12, 20, 20);
        bastide.ajouteEnseignement(web, 8, 20, 30);
        lamine.ajouteEnseignement(bd, 10, 20, 15);
        lamine.ajouteEnseignement(web, 15, 15, 25);

        // Affichage des heures prévues pour chaque enseignant
        System.out.printf("Mr. %s a un total de %d heures prévues%n", bastide.getNom(), bastide.heuresPrevues());
        System.out.printf("Mr. %s a un total de %d heures prévues%n", lamine.getNom(), lamine.heuresPrevues());

        // Heures prévues pour une UE spécifique
        System.out.printf("Mr. %s a un total de %d heures prévues dans l'UE %s%n",
                bastide.getNom(),
                bastide.heuresPrevuesPourUE(uml),
                uml.getIntitule());

        // Création d'une salle
        Salle salle = new Salle("Salle A", 50);

        // Ajout d'interventions
        Intervention inter1 = new Intervention(new Date(), 10, 9, uml, salle, TypeIntervention.TD);
        Intervention inter2 = new Intervention(new Date(), 5, 14, web, salle, TypeIntervention.CM);

        try {
            bastide.ajouteIntervention(inter1);
            bastide.ajouteIntervention(inter2);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'une intervention : " + e.getMessage());
        }

        // Vérification des heures restantes à planifier
        System.out.printf("Il reste %d heures de CM à planifier pour l'UE %s pour Mr. %s%n",
                bastide.resteAPlanifier(uml, TypeIntervention.CM),
                uml.getIntitule(),
                bastide.getNom());

        System.out.printf("Il reste %d heures de TD à planifier pour l'UE %s pour Mr. %s%n",
                bastide.resteAPlanifier(uml, TypeIntervention.TD),
                uml.getIntitule(),
                bastide.getNom());

        // Vérification du mode sous-service
        if (bastide.enSousService()) {
            System.out.printf("Mr. %s est en sous-service.%n", bastide.getNom());
        } else {
            System.out.printf("Mr. %s n'est pas en sous-service.%n", bastide.getNom());
        }

        if (lamine.enSousService()) {
            System.out.printf("Mr. %s est en sous-service.%n", lamine.getNom());
        } else {
            System.out.printf("Mr. %s n'est pas en sous-service.%n", lamine.getNom());
        }
    }
}
