package Model;

import java.util.Objects;

public class Materiau extends Composant {
    private double coutUnitaire;
    private double quantite;
    private double coutTransport;
    private double coefficientQualite;

    // Constructeur
    public Materiau(String nom, double tauxTVA, double coutUnitaire, double quantite,
                    double coutTransport, double coefficientQualite) {
        super(nom, "Matériel", tauxTVA);
        this.coutUnitaire = coutUnitaire;
        this.quantite = quantite;
        this.coutTransport = coutTransport;
        this.coefficientQualite = coefficientQualite;
    }

    // Getters et Setters
    public double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getCoutTransport() {
        return coutTransport;
    }

    public void setCoutTransport(double coutTransport) {
        this.coutTransport = coutTransport;
    }

    public double getCoefficientQualite() {
        return coefficientQualite;
    }

    public void setCoefficientQualite(double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }

    @Override
    public double calculerCout() {
        double coutHorsTVA = (coutUnitaire * quantite * coefficientQualite) + coutTransport;
        return coutHorsTVA * (1 + tauxTVA / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Materiau materiau = (Materiau) o;
        return Double.compare(materiau.coutUnitaire, coutUnitaire) == 0 &&
                Double.compare(materiau.quantite, quantite) == 0 &&
                Double.compare(materiau.coutTransport, coutTransport) == 0 &&
                Double.compare(materiau.coefficientQualite, coefficientQualite) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coutUnitaire, quantite, coutTransport, coefficientQualite);
    }

    @Override
    public String toString() {
        return "Materiau{" +
                "coutUnitaire=" + coutUnitaire +
                ", quantite=" + quantite +
                ", coutTransport=" + coutTransport +
                ", coefficientQualite=" + coefficientQualite +
                "} " + super.toString();
    }
}