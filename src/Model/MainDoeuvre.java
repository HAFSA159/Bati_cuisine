package Model;

import java.util.Objects;

public class MainDoeuvre extends Composant {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public MainDoeuvre(String nom, double tauxTVA, double tauxHoraire,
                       double heuresTravail, double productiviteOuvrier) {
        super(nom, "Main-d'œuvre", tauxTVA);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public double calculerCout() {
        double coutHorsTVA = tauxHoraire * heuresTravail * productiviteOuvrier;
        return coutHorsTVA * (1 + tauxTVA / 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MainDoeuvre that = (MainDoeuvre) o;
        return Double.compare(that.tauxHoraire, tauxHoraire) == 0 &&
                Double.compare(that.heuresTravail, heuresTravail) == 0 &&
                Double.compare(that.productiviteOuvrier, productiviteOuvrier) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tauxHoraire, heuresTravail, productiviteOuvrier);
    }

    @Override
    public String toString() {
        return "MainDoeuvre{" +
                "tauxHoraire=" + tauxHoraire +
                ", heuresTravail=" + heuresTravail +
                ", productiviteOuvrier=" + productiviteOuvrier +
                "} " + super.toString();
    }
}