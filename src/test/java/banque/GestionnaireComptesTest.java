package banque;

import exceptions.CompteDejaExistantException;
import exceptions.CompteInconnuException;
import exceptions.SoldeInsuffisantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestionnaireComptesTest {
    private GestionnaireComptes gestionnaire;
    private CompteBancaire compte1;
    private CompteBancaire compte2;

    @BeforeEach
    void setUp() {
        gestionnaire = new GestionnaireComptes();
        compte1 = new CompteBancaire("FR1", "Alice", 100.0, 50.0);
        compte2 = new CompteBancaire("FR2", "Bob", 50.0, 0.0);
        gestionnaire.ajouterCompte(compte1);
        gestionnaire.ajouterCompte(compte2);
    }

    @Test
    void testAjoutCompteExistant() {
        CompteBancaire doublon = new CompteBancaire("FR1", "Charlie", 200.0);
        assertThrows(CompteDejaExistantException.class, () -> gestionnaire.ajouterCompte(doublon));
    }

    @Test
    void testRechercheCompteInconnu() {
        assertThrows(CompteInconnuException.class, () -> gestionnaire.rechercherCompte("FR999"));
    }

    @Test
    void testVirementReussi() {
        gestionnaire.virement("FR1", "FR2", 30.0);
        assertEquals(70.0, gestionnaire.rechercherCompte("FR1").getSolde());
        assertEquals(80.0, gestionnaire.rechercherCompte("FR2").getSolde());
    }

    @Test
    void testVirementEchoueAtomique() {
        // Le compte 1 n'a pas assez (100 de solde + 50 de découvert = 150 max)
        assertThrows(SoldeInsuffisantException.class, () -> gestionnaire.virement("FR1", "FR2", 200.0));
        
        // Vérification de l'atomicité : aucun solde n'a dû bouger
        assertEquals(100.0, gestionnaire.rechercherCompte("FR1").getSolde(), "Le retrait a été annulé");
        assertEquals(50.0, gestionnaire.rechercherCompte("FR2").getSolde(), "Le dépôt n'a pas eu lieu");
    }
}