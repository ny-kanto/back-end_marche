package com.marche.marche.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marche.marche.api.APIResponse;
import com.marche.marche.authentification.JwtUtil;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.Statistique;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.services.PersonneService;
import com.marche.marche.services.ProduitService;
import com.marche.marche.services.StatistiqueService;
import com.marche.marche.services.UtilisateurService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/statistique")
@CrossOrigin(origins = "*")
public class StatistiqueController {
    @Autowired
    private StatistiqueService ss;

    @Autowired
    private ProduitService ps;

    @Autowired
    private PersonneService pes;

    @Autowired
    private UtilisateurService us;

    @Autowired
    private JwtUtil jwtUtil;
    
    // CONTROLLEUR POUR VOIR LA LISTE DES PRODUITS DU VENDEUR
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAll(@RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int idProduit,
            @RequestParam(defaultValue = "2024") int annee) {
        try {
            int idUtilisateur = 0;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = jwtUtil.parseJwtClaims(token);
                idUtilisateur = JwtUtil.getUserId(claims);
            }

            List<Object> obj = new ArrayList<>();
            Utilisateur u = us.getUtilisateur(idUtilisateur);
            Personne p = pes.getPersonneByUtilisateur(u);
            Produit produit;
            List<Statistique> statistiques = new ArrayList<>();
            if (idProduit == 0) {
                statistiques = ss.getStatistiquesByVendeur(p, annee);
            } else {
                produit = ps.getProduit(idProduit);
                statistiques = ss.getStatistiquesByProduitByAnnee(produit, annee);
            }

            obj.add(statistiques);
            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
