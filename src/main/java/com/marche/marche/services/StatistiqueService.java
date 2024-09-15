package com.marche.marche.services;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.Statistique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

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
                    "    P.NOM                                            AS NOM_PRODUIT,\r\n" + //
                    "    U.ID                                             AS ID_UNITE,\r\n" + //
                    "    U.NOM                                            AS NOM_UNITE,\r\n" + //
                    "    COALESCE(SUM(CP.QUANTITE), 0)                    AS TOTAL_VENDUS,\r\n" + //
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
                    "    LEFT JOIN UNITE U\r\n" + //
                    "    ON U.ID = P.ID_UNITE\r\n" + //
                    "WHERE\r\n" + //
                    "    C.STATUS_COMMANDE >= 1\r\n" + //
                    "    AND P.ID = ?\r\n" + //
                    "    AND MA.ANNEE = ?\r\n" + //
                    "    OR C.ID IS NULL\r\n" + //
                    "GROUP BY\r\n" + //
                    "    P.ID,\r\n" + //
                    "    P.NOM,\r\n" + //
                    "    U.ID,\r\n" + //
                    "    U.NOM,\r\n" + //
                    "    MA.MOIS,\r\n" + //
                    "    MA.ANNEE\r\n" + //
                    "ORDER BY\r\n" + //
                    "    P.ID,\r\n" + //
                    "    MA.ANNEE,\r\n" + //
                    "    MA.MOIS;";

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
                                "\tLEFT JOIN PRODUIT P\r\n" + //
                                "\tON CP.ID_PRODUIT = P.ID\r\n" + //
                                "WHERE\r\n" + //
                                "    C.STATUS_COMMANDE >= 1\r\n" + //
                                "\tAND P.ID_PERSONNE = ?\r\n" + //
                                "    AND MA.ANNEE = ?\r\n" + //
                                "    OR C.ID IS NULL\r\n" + //
                                "GROUP BY\r\n" + //
                                "    MA.MOIS,\r\n" + //
                                "    MA.ANNEE\r\n" + //
                                "ORDER BY\r\n" + //
                                "    MA.ANNEE,\r\n" + //
                                "    MA.MOIS;";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, personne.getId());
            ps.setInt(2, annee);
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
}
