package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Projet {
    private Long id;
    private String nomProjet;
    private Client client;
    private double margeBeneficiaire;
    private double coutTotal;
    private EtatProjet etatProjet;
    private List<Composant> composants;

    public enum EtatProjet {
        EN_COURS, TERMINE, ANNULE
    }

    public Projet(String nomProjet, Client client, double margeBeneficiaire) {
        this.nomProjet = nomProjet;
        this.client = client;
        this.margeBeneficiaire = margeBeneficiaire;
        this.etatProjet = EtatProjet.EN_COURS;
        this.composants = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getMargeBeneficiaire() {
        return margeBeneficiaire;
    }

    public void setMargeBeneficiaire(double margeBeneficiaire) {
        this.margeBeneficiaire = margeBeneficiaire;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public EtatProjet getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }

    public List<Composant> getComposants() {
        return composants;
    }

    public void ajouterComposant(Composant composant) {
        this.composants.add(composant);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projet projet = (Projet) o;
        return Double.compare(projet.margeBeneficiaire, margeBeneficiaire) == 0 &&
                Double.compare(projet.coutTotal, coutTotal) == 0 &&
                Objects.equals(id, projet.id) &&
                Objects.equals(nomProjet, projet.nomProjet) &&
                Objects.equals(client, projet.client) &&
                etatProjet == projet.etatProjet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomProjet, client, margeBeneficiaire, coutTotal, etatProjet);
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nomProjet='" + nomProjet + '\'' +
                ", client=" + client +
                ", margeBeneficiaire=" + margeBeneficiaire +
                ", coutTotal=" + coutTotal +
                ", etatProjet=" + etatProjet +
                ", composants=" + composants +
                '}';
    }
}