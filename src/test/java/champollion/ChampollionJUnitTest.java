	package champollion;

	import org.junit.jupiter.api.Test;

	import java.util.Date;

	import static org.junit.jupiter.api.Assertions.*;

	public class ChampollionJUnitTest {

		@Test
		public void testConstructeurEnseignant() {
			// Création d'un enseignant avec des valeurs spécifiques
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");

			// Vérification que le nom est bien initialisé
			assertEquals("Dupont", enseignant.getNom());

			// Vérification que l'email est bien initialisé
			assertEquals("dupont@example.com", enseignant.getEmail());

			// Vérification que les heures prévues sont initialement à 0
			assertEquals(0, enseignant.heuresPrevues());
		}

		@Test
		public void testAjouteEnseignement() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue = new UE("Mathématiques", 10, 15, 5);

			enseignant.ajouteEnseignement(ue, 10, 15, 5);

			// Calcul attendu des heures
			int heuresAttendues = (int) (10 * 1.5 + 15 * 1.0 + 5 * 0.75);
			assertEquals(heuresAttendues, enseignant.heuresPrevues());
		}

		@Test
		public void testHeuresPrevuesPourUE() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue1 = new UE("Mathématiques", 10, 15, 5);
			UE ue2 = new UE("Informatique", 20, 10, 10);

			enseignant.ajouteEnseignement(ue1, 10, 15, 5);
			enseignant.ajouteEnseignement(ue2, 20, 10, 10);

			// Vérifie les heures pour ue1
			int heuresUE1 = (int) (10 * 1.5 + 15 * 1.0 + 5 * 0.75);
			assertEquals(heuresUE1, enseignant.heuresPrevuesPourUE(ue1));

			// Vérifie les heures pour ue2
			int heuresUE2 = (int) (20 * 1.5 + 10 * 1.0 + 10 * 0.75);
			assertEquals(heuresUE2, enseignant.heuresPrevuesPourUE(ue2));
		}

		@Test
		public void testAjouteIntervention() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue = new UE("Mathématiques", 10, 15, 5);
			Salle salle = new Salle("Salle A", 50);

			// Ajout des heures prévues pour l'UE
			enseignant.ajouteEnseignement(ue, 10, 15, 5);

			// Ajout de l'intervention
			Intervention inter = new Intervention(new Date(), 5, 8, ue, salle, TypeIntervention.TD);

			// Vérifie qu'aucune exception n'est levée lors de l'ajout de l'intervention
			assertDoesNotThrow(() -> enseignant.ajouteIntervention(inter));

			// Vérifie qu'il reste bien 10 heures à planifier pour ce type d'intervention
			// Après l'ajout de l'intervention de 5 heures, il devrait rester 10 heures de TD
			assertEquals(10, enseignant.resteAPlanifier(ue, TypeIntervention.TD));
		}

		@Test
		public void testResteAPlanifier() throws Exception {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue = new UE("Mathématiques", 10, 15, 5);
			Salle salle = new Salle("Salle A", 50);

			enseignant.ajouteEnseignement(ue, 10, 15, 5);

			// Ajout d'une première intervention de 5h
			Intervention inter1 = new Intervention(new Date(), 5, 8, ue, salle, TypeIntervention.TD);
			enseignant.ajouteIntervention(inter1);

			// Vérification qu'il reste 10h de TD à planifier
			assertEquals(10, enseignant.resteAPlanifier(ue, TypeIntervention.TD));
		}

		@Test
		public void testEnSousService() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue = new UE("Mathématiques", 10, 15, 5);

			enseignant.ajouteEnseignement(ue, 10, 15, 5);

			// Vérifie qu'il est en sous-service
			assertTrue(enseignant.enSousService());

			// Ajoute suffisamment d'heures pour ne pas être en sous-service
			enseignant.ajouteEnseignement(new UE("Informatique", 50, 50, 50), 50, 50, 50);

			// Vérifie qu'il n'est plus en sous-service
			assertFalse(enseignant.enSousService());
		}

		@Test
		public void testHeuresPrevues() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ueMath = new UE("Mathématiques", 10, 15, 5);
			UE ueInfo = new UE("Informatique", 8, 12, 4);

			enseignant.ajouteEnseignement(ueMath, 10, 15, 5);
			enseignant.ajouteEnseignement(ueInfo, 8, 12, 4);

			int heuresPrevues = enseignant.heuresPrevues();
			assertEquals(60, heuresPrevues);
		}

		@Test
		public void testAjouteInterventionException() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue = new UE("Mathématiques", 10, 15, 5);  // 10h de CM, 15h de TD, 5h de TP
			Salle salle = new Salle("Salle A", 50);

			// Ajoute les heures prévues pour l'UE
			enseignant.ajouteEnseignement(ue, 10, 15, 5);

			// Ajoute une intervention de 6h de TD
			Intervention inter1 = new Intervention(new Date(), 6, 8, ue, salle, TypeIntervention.TD);

			// Première intervention, pas de problème ici
			assertDoesNotThrow(() -> enseignant.ajouteIntervention(inter1));

			// Ajoute une autre intervention de 10h de TD, ce qui dépasse le volume prévu (15h)
			Intervention inter2 = new Intervention(new Date(), 10, 8, ue, salle, TypeIntervention.TD);

			// Vérification que l'exception est bien levée lors de l'ajout de l'intervention qui dépasse les heures prévues
			assertThrows(Exception.class, () -> enseignant.ajouteIntervention(inter2));
		}

		@Test
		public void testHeuresPrevuesPourUEInexistante() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ueInexistante = new UE("Biologie", 10, 10, 5);

			// Vérifie qu'aucune heure n'est prévue pour une UE inexistante
			assertEquals(0, enseignant.heuresPrevuesPourUE(ueInexistante));
		}

		@Test
		public void testAjouteInterventionHeuresTropLongues() {
			Enseignant enseignant = new Enseignant("Dupont", "dupont@example.com");
			UE ue = new UE("Mathématiques", 10, 15, 5);
			Salle salle = new Salle("Salle A", 50);

			// Ajoute l'enseignement avec des heures initiales
			enseignant.ajouteEnseignement(ue, 10, 15, 5);

			// Essaie d'ajouter une intervention de 20 heures de TD (ce qui dépasse les heures disponibles)
			Intervention inter = new Intervention(new Date(), 20, 8, ue, salle, TypeIntervention.TD);

			// Vérifie que l'exception est levée pour heures trop longues
			assertThrows(Exception.class, () -> enseignant.ajouteIntervention(inter));
		}

	}