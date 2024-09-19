package DAO;

import Model.Projet;
import Model.EtatProjet;
import Utilitaire.DatabaseConnection;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjetDAO {

    public void createProjet(Projet projet) throws SQLException {
        if (projet == null) {
            throw new IllegalArgumentException("Projet object cannot be null.");
        }

        String query = "INSERT INTO projet (id, nomprojet, surface, margebeneficiaire, couttotal, etatprojet) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, projet.getId());
            stmt.setString(2, projet.getNomProjet());
            stmt.setDouble(3, projet.getSurface());
            stmt.setDouble(4, projet.getMargeBeneficiaire());
            stmt.setDouble(5, projet.getCoutTotal());
            stmt.setString(6, projet.getEtatProjet().name());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            throw e;
        }
    }



    // Read
    public Projet getProjetById(String id) throws SQLException {
        String query = "SELECT * FROM projet WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Projet(
                        rs.getString("id"),
                        rs.getString("nom_projet"),
                        rs.getDouble("surface"),
                        rs.getDouble("marge_beneficiaire"),
                        rs.getDouble("cout_total"),
                        EtatProjet.valueOf(rs.getString("etat_projet"))
                );
            }
        }
        return null;
    }

    // Update
    public void updateProjet(Projet projet) throws SQLException {
        String query = "UPDATE projet SET nomprojet = ?, surface = ?, margebeneficiaire = ?, couttotal = ?, etatprojet = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, projet.getNomProjet());
            stmt.setDouble(2, projet.getSurface());
            stmt.setDouble(3, projet.getMargeBeneficiaire());
            stmt.setDouble(4, projet.getCoutTotal());
            stmt.setString(5, projet.getEtatProjet().toString());
            stmt.setString(6, projet.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteProjet(String id) throws SQLException {
        String query = "DELETE FROM projet WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    // Read all projects
    public List<Projet> getAllProjets() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT * FROM projet";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Projet projet = new Projet(
                        rs.getString("id"),
                        rs.getString("nomprojet"),
                        rs.getDouble("surface"),
                        rs.getDouble("margebeneficiaire"),
                        rs.getDouble("couttotal"),
                        EtatProjet.valueOf(rs.getString("etatprojet"))
                );
                projets.add(projet);
            }
        }
        return projets;
    }

}
