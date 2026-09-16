package banque;

import exceptions.MontantInvalideException;
import exceptions.SoldeInsuffisantException;

public class CompteBancaire {
    // L'IBAN est final comme demandé
    private final String iban;
    private String titulaire;
    private double solde;
    private double decouvertAutorise;

    // Constructeur principal
    public CompteBancaire(String iban, String titulaire, double soldeInitial, double decouvertAutorise) {
        this.iban = iban;
        this.titulaire = titulaire;
        this.solde = soldeInitial;
        this.decouvertAutorise = decouvertAutorise;
    }

    // Constructeur alternatif (découvert autorisé par défaut à 0)
    public CompteBancaire(String iban, String titulaire, double soldeInitial) {
        this(iban, titulaire, soldeInitial, 0.0);
    }

    public void deposer(double montant) {
        if (montant <= 0) {
            throw new MontantInvalideException("Le montant déposé doit être strictement positif.");
        }
        this.solde += montant;
    }

    public void retirer(double montant) {
        if (montant <= 0) {
            throw new MontantInvalideException("Le montant retiré doit être strictement positif.");
        }
        // Le solde peut descendre jusqu'à -decouvertAutorise
        if (this.solde - montant < -this.decouvertAutorise) {
            throw new SoldeInsuffisantException("Opération refusée : dépassement du découvert autorisé.");
        }
        this.solde -= montant;
    }

    public double calculerInterets(double taux) {
        if (taux < 0) {
            throw new IllegalArgumentException("Le taux ne peut pas être négatif.");
        }
        // Si le solde est positif, on retourne les intérêts sans modifier le solde
        if (this.solde > 0) {
            return this.solde * taux;
        }
        return 0.0;
    }

    public boolean estEnDecouvert() {
        return this.solde < 0;
    }

    // Getters
    public String getIban() {
        return iban;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public double getSolde() {
        return solde;
    }
}