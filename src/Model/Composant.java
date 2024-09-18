package Model;

import java.util.Objects;

public abstract class Composant {
    protected Long id;
    protected String nom;
    protected String typeComposant;
    protected double tauxTVA;

    public Composant(String nom, String typeComposant, double tauxTVA) {
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.tauxTVA = tauxTVA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    // Méthode abstraite pour calculer le coût
    public abstract double calculerCout();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Composant composant = (Composant) o;
        return Double.compare(composant.tauxTVA, tauxTVA) == 0 &&
                Objects.equals(id, composant.id) &&
                Objects.equals(nom, composant.nom) &&
                Objects.equals(typeComposant, composant.typeComposant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, typeComposant, tauxTVA);
    }

    @Override
    public String toString() {
        return "Composant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", typeComposant='" + typeComposant + '\'' +
                ", tauxTVA=" + tauxTVA +
                '}';
    }
}