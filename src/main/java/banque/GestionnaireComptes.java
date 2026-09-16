package banque;

import exceptions.CompteDejaExistantException;
import exceptions.CompteInconnuException;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireComptes {
    private List<CompteBancaire> comptes = new ArrayList<>();

    public void ajouterCompte(CompteBancaire compte) {
        // Vérifie si l'IBAN existe déjà
        for (CompteBancaire c : comptes) {
            if (c.getIban().equals(compte.getIban())) {
                throw new CompteDejaExistantException("L'IBAN " + compte.getIban() + " existe déjà.");
            }
        }
        comptes.add(compte);
    }

    public CompteBancaire rechercherCompte(String iban) {
        for (CompteBancaire c : comptes) {
            if (c.getIban().equals(iban)) {
                return c;
            }
        }
        throw new CompteInconnuException("Aucun compte trouvé avec l'IBAN : " + iban);
    }

    public void virement(String ibanSource, String ibanDestination, double montant) {
        CompteBancaire source = rechercherCompte(ibanSource);
        CompteBancaire destination = rechercherCompte(ibanDestination);

        // L'atomicité : le retrait lève une exception s'il échoue, empêchant le dépôt de s'exécuter
        source.retirer(montant);
        destination.deposer(montant);
    }

    public double soldeTotal() {
        double total = 0;
        for (CompteBancaire c : comptes) {
            total += c.getSolde();
        }
        return total;
    }

    public List<CompteBancaire> listeComptesEnDecouvert() {
        List<CompteBancaire> enDecouvert = new ArrayList<>();
        for (CompteBancaire c : comptes) {
            if (c.estEnDecouvert()) {
                enDecouvert.add(c);
            }
        }
        return enDecouvert;
    }
}