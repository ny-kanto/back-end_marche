package com.marche.marche.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Commande;
import com.marche.marche.modele.CommandeProduit;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.repository.CommandeRepository;
import com.marche.marche.repository.ProduitRepository;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository cr;
    
    @Autowired
    private ProduitRepository pr;

    @Autowired
    private DataSource dataSource;

    public void saveCommande(Commande Commande) {
        cr.save(Commande);
    }

    public List<CommandeProduit> listCommandeByVendeur(Personne personne, int nbrParPage, int noPage) {
        // noPage = (noPage - 1) * nbrParPage;
        // return cpr.listCommandeByVendeur(personne, nbrParPage, noPage);

        List<CommandeProduit> commandeProduits = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM commande_produit cp JOIN produit pro ON pro.id = cp.id_produit JOIN commande co ON co.id = cp.id_commande WHERE pro.id_personne = ? ORDER BY co.date_commande ASC, cp.id_produit ASC LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, personne.getId());
            ps.setInt(2, nbrParPage);
            ps.setInt(3, (noPage - 1) * nbrParPage);
            System.out.println("sql: ");
            ResultSet rs = ps.executeQuery();

            CommandeProduit es;
            Commande commande;
            Produit produit;
            while (rs.next()) {
                es = new CommandeProduit();
                commande = cr.findById(rs.getInt("id_commande")).get();
                produit = pr.findById(rs.getInt("id_produit")).get();
                es.setCommande(commande);
                es.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                es.setProduit(produit);
                es.setQuantite(rs.getDouble("quantite"));
                commandeProduits.add(es);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandeProduits;
    }

    public Commande getCommandeById(int id) {
        return cr.findById(id).get();
    }

    public int countCommande(int idPersonne) {
        int countCommande = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(id) as countCommande FROM commande_produit cp JOIN produit pro ON pro.id = cp.id_produit WHERE pro.id_personne = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idPersonne);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countCommande = rs.getInt("countCommande");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return countCommande;
    }

    public int countCommandeNonLivree(int idPersonne) {
        int countCommande = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(*) as countCommande FROM commande_produit cp JOIN produit pro ON pro.id = cp.id_produit JOIN commande co ON co.id = cp.id_commande WHERE pro.id_personne = ? AND co.status_commande = 0";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idPersonne);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countCommande = rs.getInt("countCommande");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return countCommande;
    }

    public int countCommandeEnCours(int idPersonne) {
        int countCommande = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(*) as countCommande FROM commande_produit cp JOIN produit pro ON pro.id = cp.id_produit JOIN commande co ON co.id = cp.id_commande WHERE pro.id_personne = ? AND co.status_commande = 1";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idPersonne);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countCommande = rs.getInt("countCommande");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return countCommande;
    }

    public int countCommandeLivree(int idPersonne) {
        int countCommande = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT count(*) as countCommande FROM commande_produit cp JOIN produit pro ON pro.id = cp.id_produit JOIN commande co ON co.id = cp.id_commande WHERE pro.id_personne = ? AND co.status_commande = 11";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idPersonne);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countCommande = rs.getInt("countCommande");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return countCommande;
    }
}
