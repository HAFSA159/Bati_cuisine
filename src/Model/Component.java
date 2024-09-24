package Model;

public abstract class Component {
    protected String id;
    protected String name;
    protected String componentType; // Can be 'MATERIAL' or 'Labor'
    protected double VATRate;

    public abstract double calculerCout();

    // Constructor
    public Component(String id, String nom, String typeComposant, double tauxTVA) {
        this.id = id;
        this.name = nom;
        this.componentType = typeComposant;
        this.VATRate = tauxTVA;
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public double getVATRate() {
        return VATRate;
    }

    public void setVATRate(double VATRate) {
        this.VATRate = VATRate;
    }
}
