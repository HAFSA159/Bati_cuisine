package Model;

public class Labor extends Component {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    // Constructor
    public Labor(String id, String nom, double tauxTVA, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(id, nom, "MAINDOEUVRE", tauxTVA);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    // Getters and Setters
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
}
