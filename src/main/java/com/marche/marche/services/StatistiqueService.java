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

import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.Statistique;
import com.marche.marche.modele.StatistiqueAdmin;

@Service
public class StatistiqueService {
    @Autowired
    private DataSource dataSource;

    public List<Statistique> getStatistiquesByProduitByAnnee(Produit produit, int annee) {
        List<Statistique> statistiques = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "WITH ANNEE_DISTINCTE AS (\r\n" + //
                    "    SELECT\r\n" + //
                    "        DISTINCT EXTRACT(YEAR FROM DATE_COMMANDE) AS ANNEE\r\n" + //
                    "    FROM\r\n" + //
                    "        COMMANDE\r\n" + //
                    "), MOIS_ANNEE AS (\r\n" + //
                    "    SELECT\r\n" + //
                    "        GENERATE_SERIES(1, 12) AS MOIS,\r\n" + //
                    "        ANNEE\r\n" + //
                    "    FROM\r\n" + //
                    "        ANNEE_DISTINCTE\r\n" + //
                    ")\r\n" + //
                    "SELECT\r\n" + //
                    "    P.ID                                             AS ID_PRODUIT,\r\n" + //
                    "    P.ID_UNITE                                       AS ID_UNITE,\r\n" + //
                    "    COALESCE(SUM(CP.QUANTITE), 0)                    AS TOTAL_VENDUS,\r\n" + //
                    "    COALESCE(SUM(CP.PRIX_UNITAIRE * CP.QUANTITE), 0) AS TOTAL_VENTES,\r\n" + //
                    "    MA.MOIS                                \t\t     AS MOIS,\r\n" + //
                    "    MA.ANNEE                                         AS ANNEE\r\n" + //
                    "FROM\r\n" + //
                    "    MOIS_ANNEE       MA\r\n" + //
                    "    LEFT JOIN COMMANDE C\r\n" + //
                    "    ON EXTRACT(MONTH FROM C.DATE_COMMANDE) = MA.MOIS\r\n" + //
                    "    AND EXTRACT(YEAR FROM C.DATE_COMMANDE) = MA.ANNEE\r\n" + //
                    "    LEFT JOIN COMMANDE_PRODUIT CP\r\n" + //
                    "    ON CP.ID_COMMANDE = C.ID\r\n" + //
                    "\tAND CP.ID_PRODUIT = ?\r\n" + //
                    "\tLEFT JOIN PRODUIT P\r\n" + //
                    "\tON CP.ID_PRODUIT = P.ID OR P.ID IS NULL\r\n" + //
                    "WHERE\r\n" + //
                    "\tMA.ANNEE = ?\r\n" + //
                    "    AND ((C.STATUS_COMMANDE >= 1) OR P.ID IS NULL OR C.ID IS NULL)\r\n" + //
                    "GROUP BY\r\n" + //
                    "    P.ID,\r\n" + //
                    "    P.ID_UNITE,\r\n" + //
                    "    MA.MOIS,\r\n" + //
                    "    MA.ANNEE\r\n" + //
                    "ORDER BY\r\n" + //
                    "    MA.ANNEE,\r\n" + //
                    "    MA.MOIS;";

            System.out.println("sql : " + sql);
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, produit.getId());
            ps.setInt(2, annee);
            ResultSet rs = ps.executeQuery();

            Statistique st;

            while (rs.next()) {
                st = new Statistique(produit.getId(), produit.getNom(), produit.getUnite().getId(),
                        produit.getUnite().getNom(), rs.getDouble("total_vendus"), rs.getDouble("total_ventes"),
                        rs.getInt("mois"), rs.getInt("annee"));
                statistiques.add(st);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return statistiques;
    }

    public List<Statistique> getStatistiquesByVendeur(Personne personne, int annee) {
        List<Statistique> statistiques = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "WITH ANNEE_DISTINCTE AS (\r\n" + //
                    "    SELECT\r\n" + //
                    "        DISTINCT EXTRACT(YEAR FROM DATE_COMMANDE) AS ANNEE\r\n" + //
                    "    FROM\r\n" + //
                    "        COMMANDE\r\n" + //
                    "), MOIS_ANNEE AS (\r\n" + //
                    "    SELECT\r\n" + //
                    "        GENERATE_SERIES(1, 12) AS MOIS,\r\n" + //
                    "        ANNEE\r\n" + //
                    "    FROM\r\n" + //
                    "        ANNEE_DISTINCTE\r\n" + //
                    ")\r\n" + //
                    "SELECT\r\n" + //
                    "    COALESCE(SUM(CP.PRIX_UNITAIRE * CP.QUANTITE), 0) AS TOTAL_VENTES,\r\n" + //
                    "    MA.MOIS                                          AS MOIS,\r\n" + //
                    "    MA.ANNEE                                         AS ANNEE\r\n" + //
                    "FROM\r\n" + //
                    "    MOIS_ANNEE       MA\r\n" + //
                    "    LEFT JOIN COMMANDE C\r\n" + //
                    "    ON EXTRACT(MONTH FROM C.DATE_COMMANDE) = MA.MOIS\r\n" + //
                    "    AND EXTRACT(YEAR FROM C.DATE_COMMANDE) = MA.ANNEE\r\n" + //
                    "    LEFT JOIN COMMANDE_PRODUIT CP\r\n" + //
                    "    ON CP.ID_COMMANDE = C.ID\r\n" + //
                    "    LEFT JOIN PRODUIT P\r\n" + //
                    "    ON CP.ID_PRODUIT = P.ID\r\n" + //
                    "WHERE\r\n" + //
                    "    MA.ANNEE = ? \r\n" + //
                    "    AND ((C.STATUS_COMMANDE >= 1 AND P.ID_PERSONNE = ?) OR C.ID IS NULL OR P.ID IS NULL)\r\n" + //
                    "GROUP BY\r\n" + //
                    "    MA.MOIS,\r\n" + //
                    "    MA.ANNEE\r\n" + //
                    "ORDER BY\r\n" + //
                    "    MA.ANNEE,\r\n" + //
                    "    MA.MOIS;\r\n" + //
                    "";

            System.out.println("sql : " + sql);
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, annee);
            ps.setInt(2, personne.getId());
            ResultSet rs = ps.executeQuery();

            Statistique st;

            while (rs.next()) {
                st = new Statistique(0, "Tous les produits", 0,
                        "", 0, rs.getDouble("total_ventes"),
                        rs.getInt("mois"), rs.getInt("annee"));
                statistiques.add(st);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return statistiques;
    }

    public List<Integer> getDateCommandeAnnee(Personne personne) {
        List<Integer> dateAnnee = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "WITH ANNEE_DISTINCTE AS (\r\n" + //
                    "    SELECT\r\n" + //
                    "        DISTINCT EXTRACT(YEAR FROM DATE_COMMANDE) AS ANNEE\r\n" + //
                    "    FROM\r\n" + //
                    "        COMMANDE\r\n" + //
                    "), MOIS_ANNEE AS (\r\n" + //
                    "    SELECT\r\n" + //
                    "        GENERATE_SERIES(1, 12) AS MOIS,\r\n" + //
                    "        ANNEE\r\n" + //
                    "    FROM\r\n" + //
                    "        ANNEE_DISTINCTE\r\n" + //
                    ")\r\n" + //
                    "SELECT\r\n" + //
                    "    DISTINCT(MA.ANNEE) AS ANNEE\r\n" + //
                    "FROM\r\n" + //
                    "    MOIS_ANNEE       MA\r\n" + //
                    "    LEFT JOIN COMMANDE C\r\n" + //
                    "    ON EXTRACT(YEAR FROM C.DATE_COMMANDE) = MA.ANNEE\r\n" + //
                    "    LEFT JOIN COMMANDE_PRODUIT CP\r\n" + //
                    "    ON CP.ID_COMMANDE = C.ID\r\n" + //
                    "    LEFT JOIN PRODUIT P\r\n" + //
                    "    ON CP.ID_PRODUIT = P.ID\r\n" + //
                    "WHERE\r\n" + //
                    "    C.STATUS_COMMANDE >= 1\r\n" + //
                    "    AND P.ID_PERSONNE = ?\r\n" + //
                    "    OR C.ID IS NULL\r\n" + //
                    "GROUP BY\r\n" + //
                    "    MA.ANNEE\r\n" + //
                    "ORDER BY\r\n" + //
                    "    MA.ANNEE;";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, personne.getId());
            ResultSet rs = ps.executeQuery();

            int annee = 0;

            while (rs.next()) {
                annee = rs.getInt("annee");
                dateAnnee.add(annee);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return dateAnnee;
    }

    public double getTotalVentes() {
        double totalVente = 0;
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT\r\n" + //
                    "    SUM(CP.PRIX_UNITAIRE * CP.QUANTITE) AS TOTAL_VENTES\r\n" + //
                    "FROM\r\n" + //
                    "    COMMANDE_PRODUIT CP\r\n" + //
                    "    JOIN COMMANDE C ON CP.ID_COMMANDE = C.ID\r\n" + //
                    "WHERE\r\n" + //
                    "    C.STATUS_COMMANDE >= 1;\r\n" + //
                    "";

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalVente = rs.getDouble("total_ventes");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return totalVente;
    }

    public List<StatistiqueAdmin> getStatistiqueAdminByCategory() {
        List<StatistiqueAdmin> statistiques = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM TOTAL_VENTES_PAR_CATEGORIE";

            System.out.println("sql : " + sql);
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            StatistiqueAdmin st;

            while (rs.next()) {
                st = new StatistiqueAdmin(rs.getInt("id_categorie"), rs.getString("categorie_nom"),
                        rs.getDouble("total_ventes"));
                statistiques.add(st);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return statistiques;
    }

    public List<StatistiqueAdmin> getStatistiqueAdminByTypeProduit() {
        List<StatistiqueAdmin> statistiques = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM TOTAL_VENTES_PAR_TYPE_PRODUIT";

            System.out.println("sql : " + sql);
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            StatistiqueAdmin st;

            while (rs.next()) {
                st = new StatistiqueAdmin(rs.getInt("id_type_produit"), rs.getString("type_produit_nom"),
                        rs.getDouble("total_ventes"));
                statistiques.add(st);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return statistiques;
    }

    public List<StatistiqueAdmin> getStatistiqueAdminByRegion() {
        List<StatistiqueAdmin> statistiques = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM TOTAL_VENTES_PAR_REGION ORDER BY total_ventes DESC";

            System.out.println("sql : " + sql);
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            StatistiqueAdmin st;

            while (rs.next()) {
                st = new StatistiqueAdmin(rs.getInt("id_region"), rs.getString("region_nom"),
                        rs.getDouble("total_ventes"));
                statistiques.add(st);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException p) {
            p.printStackTrace();
        }
        return statistiques;
    }
}
