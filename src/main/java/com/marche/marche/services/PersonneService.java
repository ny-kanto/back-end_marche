package com.marche.marche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.EtatStock;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Role;
import com.marche.marche.modele.TypeProduction;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.repository.PersonneRepository;
import com.marche.marche.repository.RoleRepository;
import com.marche.marche.repository.TypeProductionRepository;
import com.marche.marche.repository.UtilisateurRepository;

import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository pr;

    @Autowired
    private RoleRepository rr;

    @Autowired
    private TypeProductionRepository tpr;

    @Autowired
    private UtilisateurRepository ur;

    @Autowired
    private DataSource dataSource;

    public void savePersonne(Personne personne) {
        pr.save(personne);
    }

    public Personne getPersonneById(int idPersonne) {
        return pr.findById(idPersonne).get();
    }

    public Personne getPersonneByUtilisateur(Utilisateur u) {
        return pr.findByUtilisateur(u);
    }

    public List<Personne> getListPersonneNonAdmin(int noPage, int nbParPage) {
        List<Personne> personnes = new ArrayList<>();
        try {
            Connection c = dataSource.getConnection();
            String sql = "SELECT * FROM personne p JOIN utilisateur u ON u.id = p.id_utilisateur WHERE u.is_admin = 0 LIMIT ? OFFSET ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nbParPage);
            ps.setInt(2, (noPage - 1) * nbParPage);
            ResultSet rs = ps.executeQuery();

            Personne p;
            Role role;
            TypeProduction tp;
            Utilisateur u;
            while (rs.next()) {
                role = rr.findById(rs.getInt("id_role")).get();
                tp = tpr.findById(rs.getInt("id_type_production")).get();
                u = ur.findById(rs.getInt("id_utilisateur")).get();
                p = new Personne(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("code_postal"), rs.getString("contact"), rs.getString("localisation"), tp, role,
                        u);
                personnes.add(p);
            }

            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnes;
    }
}
