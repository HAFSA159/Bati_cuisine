package Model;

import java.time.LocalDate;
import java.util.Objects;

public class Devis {
    private Long id;
    private Projet projet;
    private double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;

    public Devis(Projet projet, LocalDate dateEmission, LocalDate dateValidite) {
        this.projet = projet;
        this.dateEmission = dateEmission;
        this.dateValidite = dateValidite;
        this.accepte = false;
        this.montantEstime = calculerMontantEstime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public double getMontantEstime() {
        return montantEstime;
    }

    public void setMontantEstime(double montantEstime) {
        this.montantEstime = montantEstime;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public boolean isAccepte() {
        return accepte;
    }

    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }

    // Méthode pour calculer le montant estimé
    private double calculerMontantEstime() {
        double totalComposants = projet.getComposants().stream()
                .mapToDouble(Composant::calculerCout)
                .sum();

        return totalComposants * (1 + projet.getMargeBeneficiaire() / 100);
    }

    public boolean estValide() {
        return LocalDate.now().isBefore(dateValidite) || LocalDate.now().isEqual(dateValidite);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Devis devis = (Devis) o;
        return Double.compare(devis.montantEstime, montantEstime) == 0 &&
                accepte == devis.accepte &&
                Objects.equals(id, devis.id) &&
                Objects.equals(projet, devis.projet) &&
                Objects.equals(dateEmission, devis.dateEmission) &&
                Objects.equals(dateValidite, devis.dateValidite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projet, montantEstime, dateEmission, dateValidite, accepte);
    }

    @Override
    public String toString() {
        return "Devis{" +
                "id=" + id +
                ", projet=" + projet.getNomProjet() +
                ", montantEstime=" + montantEstime +
                ", dateEmission=" + dateEmission +
                ", dateValidite=" + dateValidite +
                ", accepte=" + accepte +
                '}';
    }
}