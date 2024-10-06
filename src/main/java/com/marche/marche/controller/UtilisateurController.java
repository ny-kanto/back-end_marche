package com.marche.marche.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marche.marche.api.APIResponse;
import com.marche.marche.authentification.JwtUtil;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Role;
import com.marche.marche.modele.TypeProduction;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.services.PersonneService;
import com.marche.marche.services.RoleService;
import com.marche.marche.services.TypeProductionService;
import com.marche.marche.services.UtilisateurService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UtilisateurController {
    @Autowired
    private PersonneService ps;

    @Autowired
    private UtilisateurService us;

    @Autowired
    private RoleService rs;

    @Autowired
    private TypeProductionService tps;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signup(@RequestParam("nom") String nom, @RequestParam("prenom") String prenom,
            @RequestParam("pseudo") String pseudo,
            @RequestParam("email") String email, @RequestParam("password") String password,
            @RequestParam("date_naissance") Date dateNaissance, @RequestParam("code_postal") String codePostal,
            @RequestParam("id_role") int idRole,
            @RequestParam(name = "id_type_production", required = false) int idTypeProduction,
            @RequestParam("contact") String contact, @RequestParam("localisation") String localisation) {

        try {
            Utilisateur u = new Utilisateur(pseudo, email, password);
            us.saveUtilisateur(u);
            Role role = rs.getRoleById(idRole);
            TypeProduction tp = null;
            if (idTypeProduction != 0) {
                tp = tps.getTypeProductionById(idTypeProduction);
            }

            Personne p = new Personne(nom, prenom, codePostal, contact, localisation, tp, role, u);

            APIResponse api = new APIResponse(null, p);
            ps.savePersonne(p);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur message : " + e.getMessage());
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/new-admin")
    public ResponseEntity<APIResponse> signup(@RequestParam("pseudo") String pseudo,
            @RequestParam("email") String email, @RequestParam("password") String password) {

        try {
            Utilisateur u = new Utilisateur(pseudo, email, password, 1);
            us.saveUtilisateur(u);

            APIResponse api = new APIResponse(null, u);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur message : " + e.getMessage());
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<APIResponse> getCountPanier(
            @RequestHeader(name = "Authorization") String authorizationHeader) {
        try {
            int idUtilisateur = 0;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = jwtUtil.parseJwtClaims(token);
                idUtilisateur = JwtUtil.getUserId(claims);
            }

            List<Object> obj = new ArrayList<>();
            Utilisateur u = us.getUtilisateur(idUtilisateur);
            Personne p = ps.getPersonneByUtilisateur(u);

            obj.add(p);
            obj.add(u);

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
