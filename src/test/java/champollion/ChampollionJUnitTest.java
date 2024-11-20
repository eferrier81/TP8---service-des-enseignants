package champollion;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ChampollionJUnitTest {

	@Test
	public void testAjouteEnseignement() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		// Vérification des heures prévues
		assertEquals(38, enseignant.heuresPrevues());
	}

	@Test
	public void testAjouteInterventionDansLesLimites() throws Exception {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		Intervention intervention = new Intervention(new Date(), 5, 9, ue, new Salle("101", 50), TypeIntervention.TD);
		enseignant.ajouteIntervention(intervention);

		// Vérification que l'intervention a bien été ajoutée
		assertEquals(15, enseignant.resteAPlanifier(ue, TypeIntervention.TD)); // 20 - 5 = 15 heures restantes
	}

	@Test
	public void testAjouteInterventionExcedantLesHeuresPrevues() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		try {
			Intervention intervention = new Intervention(new Date(), 25, 9, ue, new Salle("101", 50), TypeIntervention.TD);
			enseignant.ajouteIntervention(intervention);
			fail("Exception attendue : dépassement des heures prévues");
		} catch (Exception e) {
			assertEquals("Heures planifiées dépassent les heures prévues pour ce type d'intervention.", e.getMessage());
		}
	}

	@Test
	public void testHeuresPrevuesPourUE() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		// Vérification des heures prévues pour l'UE
		assertEquals(38, enseignant.heuresPrevuesPourUE(ue));
	}

	@Test
	public void testEnSousService() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		// Vérification du sous-service
		assertTrue(enseignant.enSousService()); // Les heures sont bien inférieures à 192
	}

	@Test
	public void testResteAPlanifierSansIntervention() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		// Vérification que le nombre d'heures restantes est égal à celui initialement prévu
		assertEquals(20, enseignant.resteAPlanifier(ue, TypeIntervention.TD)); // 20 - 0 = 20 heures restantes
	}

	@Test
	public void testHeuresPrevuesSansEnseignement() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");

		// Vérification qu'aucune heure n'est prévue si aucun enseignement n'est ajouté
		assertEquals(0, enseignant.heuresPrevues());
	}

	@Test
	public void testAjouteMultipleInterventionsDeMemeType() throws Exception {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		// Ajouter plusieurs interventions du même type (TD) pour cette UE
		Intervention inter1 = new Intervention(new Date(), 5, 9, ue, new Salle("101", 50), TypeIntervention.TD);
		Intervention inter2 = new Intervention(new Date(), 5, 10, ue, new Salle("102", 60), TypeIntervention.TD);
		enseignant.ajouteIntervention(inter1);
		enseignant.ajouteIntervention(inter2);

		// Vérification des heures restantes pour le type TD
		assertEquals(10, enseignant.resteAPlanifier(ue, TypeIntervention.TD)); // 20 - 5 - 5 = 10 heures restantes
	}

	@Test
	public void testAjouteInterventionsAvecTypesDifferents() throws Exception {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);

		// Ajouter une intervention de type TD
		Intervention inter1 = new Intervention(new Date(), 5, 9, ue, new Salle("101", 50), TypeIntervention.TD);
		enseignant.ajouteIntervention(inter1);

		// Ajouter une intervention de type TP
		Intervention inter2 = new Intervention(new Date(), 3, 10, ue, new Salle("102", 60), TypeIntervention.TP);
		enseignant.ajouteIntervention(inter2);

		// Vérification des heures restantes pour le type TD (20 - 5 = 15)
		assertEquals(15, enseignant.resteAPlanifier(ue, TypeIntervention.TD));

		// Vérification des heures restantes pour le type TP (5 - 3 = 2)
		assertEquals(2, enseignant.resteAPlanifier(ue, TypeIntervention.TP));
	}

	@Test
	public void testPasDInterventionPourLeType() {
		Enseignant enseignant = new Enseignant("Jean Dupont", "jean.dupont@example.com");
		UE ue = new UE("Mathématiques", 10, 20, 5); // 10 heures CM, 20 heures TD, 5 heures TP
		enseignant.ajouteEnseignement(ue, 10, 20, 5);


	}

	@Test
	public void testEqualsPourUE() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5);
		UE ue2 = new UE("Mathématiques", 10, 20, 5); // Même intitulé, même nombre d'heures
		UE ue3 = new UE("Physique", 10, 20, 5); // Différent intitulé

		// Vérification de l'égalité
		assertEquals(ue1, ue2);  // Les deux UE devraient être égales
		assertNotEquals(ue1, ue3); // Les UE avec des intitulés différents ne devraient pas être égales
	}

	@Test
	public void testEqualsWithNull() {
		UE ue = new UE("Mathématiques", 10, 20, 5); // Initialisation de l'UE
        assertNotEquals(null, ue);  // L'égalité avec null doit retourner false
	}

	@Test
	public void testEqualsWithDifferentClass() {
		UE ue = new UE("Mathématiques", 10, 20, 5); // Initialisation de l'UE
		Object obj = new Object();  // Objet d'une classe différente

        assertNotEquals(ue, obj);  // L'égalité avec un objet d'une classe différente doit retourner false
	}

	@Test
	public void testEqualsWithDifferentValues() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5); // Initialisation de l'UE
		UE ue2 = new UE("Mathématiques", 10, 30, 5); // Une autre instance avec des heuresTD différentes

        assertNotEquals(ue1, ue2);  // Les deux UE ne doivent pas être égales car les heuresTD sont différentes
	}

	@Test
	public void testEqualsWithCompletelyDifferentValues() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5);  // Initialisation de l'UE
		UE ue2 = new UE("Mathématiques", 15, 25, 10); // Une autre instance avec des valeurs complètement différentes

        assertNotEquals(ue1, ue2);  // Les deux UE ne doivent pas être égales car les heures sont différentes
	}

	@Test
	public void testEqualsWithDifferentHeuresTD() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5); // Initialisation de l'UE
		UE ue2 = new UE("Mathématiques", 10, 25, 5); // Une autre instance avec heuresTD différentes

        assertNotEquals(ue1, ue2);  // Les deux UE ne doivent pas être égales car les heuresTD sont différentes
	}

	@Test
	public void testEqualsWithDifferentHeuresTP() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5); // Initialisation de l'UE
		UE ue2 = new UE("Mathématiques", 10, 20, 10); // Une autre instance avec heuresTP différentes

        assertNotEquals(ue1, ue2);  // Les deux UE ne doivent pas être égales car les heuresTP sont différentes
	}

	@Test
	public void testEqualsWithSameClassButDifferentAttributes() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5);
		UE ue2 = new UE("Physique", 10, 20, 5);  // Même classe mais attributs différents
        assertNotEquals(ue1, ue2);  // Vérifie que les deux UE ne sont pas égales
	}

	@Test
	public void testEqualsWithSameAttributes() {
		UE ue1 = new UE("Mathématiques", 10, 20, 5);
		UE ue2 = new UE("Mathématiques", 10, 20, 5);  // Même classe et mêmes attributs
        assertEquals(ue1, ue2);  // Vérifie que les deux UE sont égales
	}

	@Test
	public void testEqualsWithDifferentType() {
		UE ue = new UE("Mathématiques", 10, 20, 5);
		Object nonUEObject = new Object();  // Un objet d'une classe totalement différente
        assertNotEquals(ue, nonUEObject);  // Cas où l'objet comparé est d'un type complètement différent
	}

}