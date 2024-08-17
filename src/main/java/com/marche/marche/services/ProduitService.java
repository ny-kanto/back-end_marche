package com.marche.marche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Produit;
import com.marche.marche.modele.ProduitPhotos;
import com.marche.marche.modele.Unite;
import com.marche.marche.repository.ProduitRepository;
import com.marche.marche.repository.UniteRepository;
import com.marche.marche.modele.Categorie;
import com.marche.marche.repository.CategorieRepository;
import com.marche.marche.modele.Personne;
import com.marche.marche.repository.PersonneRepository;
import com.marche.marche.repository.ProduitPhotosRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository pr;

    @Autowired
    private ProduitPhotosRepository ppr;

    @Autowired
    private CategorieRepository cr;

    @Autowired
    private PersonneRepository per;

    @Autowired
    private UniteRepository ur;

    @Autowired
    private DataSource dataSource;

    public Produit saveProduit(Produit produit) {
        return pr.save(produit);
    }

    public List<Produit> getAllProduits() {
        return pr.findAll();
    }

    public Produit getProduit(int id) {
        return pr.findById(id).get();
    }

    public Produit updateProduit(int idProduit, Produit produit, Personne personne) {
        Produit existingProduit = pr.findById(idProduit)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID : " + idProduit));

        if (!existingProduit.getPersonne().equals(personne)) {
            throw new IllegalArgumentException("Le produit avec l'ID : " + idProduit + " n'appartient pas à la personne " + personne.getId());
        }

        existingProduit.setNom(produit.getNom());
        existingProduit.setDescription(produit.getDescription());
        existingProduit.setPrix(produit.getPrix());
        existingProduit.setMinCommande(produit.getMinCommande());
        existingProduit.setDelaisLivraison(produit.getDelaisLivraison());
        existingProduit.setMinCommande(produit.getMinCommande());
        existingProduit.setPersonne(produit.getPersonne());
        existingProduit.setUnite(produit.getUnite());
        existingProduit.setCategorie(produit.getCategorie());

        return pr.save(existingProduit);
    }

    public List<Produit> getProduitPerPage(int idPersonne, int noPage, int nbParPage, String column, int sort) {
        List<Produit> produit = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM produit WHERE id_personne = ? ORDER BY " + column + " " + ((sort % 2) == 1 ? "ASC" : "DESC")
                    + " LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idPersonne);
            ps.setInt(2, nbParPage);
            ps.setInt(3, (noPage - 1) * nbParPage);
            ResultSet rs = ps.executeQuery();

            Produit p;
            Categorie cat;
            Unite u;
            Personne personne = per.findById(idPersonne).get();
            while (rs.next()) {
                p = new Produit();
                cat = cr.findById(rs.getInt("id_categorie")).get();
                u = ur.findById(rs.getInt("id_unite")).get();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getDouble("prix"));
                p.setMinCommande(rs.getDouble("min_commande"));
                p.setDelaisLivraison(rs.getInt("delais_livraison"));
                p.setCategorie(cat);
                p.setUnite(u);
                p.setPersonne(personne);
                produit.add(p);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return produit;
    }

    public String sql(String nom, double prixMin, double prixMax, int idUnite, int idCategorie, int idTypeProduit) {
        String sql = "SELECT * FROM produit p JOIN categorie c ON c.id = p.id_categorie WHERE 1 = 1 AND id_personne = ? ";

        if (nom != null && !nom.isEmpty()) {
            sql += "AND LOWER(p.nom) LIKE ? ";
        }

        if (prixMin > 0.0) {
            sql += "AND p.prix >= ? ";
        }

        if (prixMax > 0.0) {
            sql += "AND p.prix <= ? ";
        }

        if (idUnite > 0) {
            sql += "AND p.id_unite = ? ";
        }

        if (idCategorie > 0) {
            sql += "AND p.id_categorie = ? ";
        }

        if (idTypeProduit > 0) {
            sql += "AND c.id_type_produit = ? ";
        }

        return sql;
    }

    public List<Produit> getProduitFiltre(int idPersonne, String nom, double prixMin, double prixMax, int idUnite, int idCategorie, int idTypeProduit) {
        List<Produit> produit = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = sql(nom, prixMin, prixMax, idUnite, idCategorie, idTypeProduit);
    
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idPersonne);
            int parameterIndex = 2;
    
            if (nom != null && !nom.isEmpty()) {
                ps.setString(parameterIndex, "%" + nom.toLowerCase() + "%");
                parameterIndex++;
            }
    
            if (prixMin > 0.0) {
                ps.setDouble(parameterIndex, prixMin);
                parameterIndex++;
            }
    
            if (prixMax > 0.0) {
                ps.setDouble(parameterIndex, prixMax);
                parameterIndex++;
            }
    
            if (idUnite > 0) {
                ps.setInt(parameterIndex, idUnite);
                parameterIndex++;
            }
    
            if (idCategorie > 0) {
                ps.setInt(parameterIndex, idCategorie);
                parameterIndex++;
            }
    
            if (idTypeProduit > 0) {
                ps.setInt(parameterIndex, idTypeProduit);
                parameterIndex++;
            }
    
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                Produit p = new Produit();
                Categorie cat = cr.findById(rs.getInt("id_categorie")).get();
                Unite u = ur.findById(rs.getInt("id_unite")).get();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getDouble("prix"));
                p.setMinCommande(rs.getDouble("min_commande"));
                p.setDelaisLivraison(rs.getInt("delais_livraison"));
                p.setCategorie(cat);
                p.setUnite(u);
                p.setPersonne(per.findById(idPersonne).get());
                produit.add(p);
            }
    
            rs.close();
            ps.close();
            c.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produit;
    }
    
    public int countProduit() {
        int countProduit = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(id) as countProduit FROM produit";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countProduit = rs.getInt("countProduit");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return countProduit;
    }

    public void deleteProduit(int idProduit, Personne personne) {
    Optional<Produit> existingProduit = pr.findById(idProduit);
    List<ProduitPhotos> pp = ppr.findByProduit(existingProduit.get());

    for (ProduitPhotos p : pp) {
        ppr.delete(p);
    }
    
    if (existingProduit.isPresent() && existingProduit.get().getPersonne().equals(personne)) {
        pr.delete(existingProduit.get());
    } else {
        throw new IllegalArgumentException("Produit de la personne " + personne.getId() + " non trouvé avec l'ID : " + idProduit);
    }
}

}
