package com.marche.marche.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.marche.marche.modele.Commande;
import com.marche.marche.modele.CommandeProduit;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.ProduitPhotos;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.services.CommandeService;
import com.marche.marche.services.PersonneService;
import com.marche.marche.services.ProduitPhotosService;
import com.marche.marche.services.UtilisateurService;
import com.marche.marche.utils.Utils;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/commande")
@CrossOrigin(origins = "*")
public class CommandeController {
    @Autowired
    private CommandeService cos;

    @Autowired
    private ProduitPhotosService pps;

    @Autowired
    private PersonneService pes;

    @Autowired
    private UtilisateurService us;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/list")
    public ResponseEntity<APIResponse> getAllCommande(@RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "5") int nbrParPage, @RequestParam(defaultValue = "1") int noPage) {
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

            List<CommandeProduit> cp = cos.listCommandeByVendeur(p, nbrParPage, noPage);

            int totalPages = (int) Math.ceil((double) cos.countCommande(p.getId()) / nbrParPage);

            double totalGlobal = 0;

            Map<String, String> photoData = null;
            ProduitPhotos photos = null;
            String mimeType = "";
            HashMap<Integer, Map<String, String>> produitData = new HashMap<>();
            Produit produit = null;
            Commande commande = null;
            
            int count = cos.countCommande(idUtilisateur);
            int countLivree = cos.countCommandeLivree(idUtilisateur);
            int countNonLivree = cos.countCommandeNonLivree(idUtilisateur);
            int countEnCours = cos.countCommandeEnCours(idUtilisateur);

            List<Map<String, Object>> produitsAvecTotal = new ArrayList<>();
            double total = 0;
            Map<String, Object> produitDetails = null;

            for (CommandeProduit commandeProduit : cp) {
                produit = commandeProduit.getProduit();
                commande = commandeProduit.getCommande();
                photos = pps.getOneProduitPhotosByProduit(produit);

                total = commandeProduit.getQuantite() * commandeProduit.getPrixUnitaire();

                produitDetails = new HashMap<>();
                produitDetails.put("id", produit.getId());
                produitDetails.put("nom", produit.getNom());
                produitDetails.put("prix", commandeProduit.getPrixUnitaire());
                produitDetails.put("quantite", commandeProduit.getQuantite());
                produitDetails.put("unite", produit.getUnite().getNom());
                produitDetails.put("total", total);
                produitDetails.put("nom_vendeur", produit.getPersonne().getNom());
                produitDetails.put("prenom_vendeur", produit.getPersonne().getPrenom());
                produitDetails.put("pseudo_vendeur", produit.getPersonne().getUtilisateur().getPseudo());
                produitDetails.put("email_vendeur", produit.getPersonne().getUtilisateur().getEmail());
                produitDetails.put("contact_vendeur", produit.getPersonne().getContact());
                produitDetails.put("id_acheteur", commande.getPersonne().getId());
                produitDetails.put("nom_acheteur", commande.getPersonne().getNom());
                produitDetails.put("prenom_acheteur", commande.getPersonne().getPrenom());
                produitDetails.put("pseudo_vendeur", commande.getPersonne().getUtilisateur().getPseudo());
                produitDetails.put("contact_acheteur", commande.getPersonne().getContact());
                produitDetails.put("status", commande.getStatusCommande());
                produitsAvecTotal.add(produitDetails);

                totalGlobal += total;

                if (photos != null) {
                    mimeType = Utils.guessMimeType(photos.getPhotos());
                    photoData = new HashMap<>();
                    photoData.put("base64", Base64.getEncoder().encodeToString(photos.getPhotos()));
                    photoData.put("mimeType", mimeType);
                    produitData.put(produit.getId(), photoData);
                }
            }

            obj.add(produitsAvecTotal);
            obj.add(produitData);
            obj.add(totalGlobal);
            obj.add(noPage);
            obj.add(totalPages);
            obj.add(count);
            obj.add(countLivree);
            obj.add(countNonLivree);
            obj.add(countEnCours);

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/count")
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
            Personne p = pes.getPersonneByUtilisateur(u);
            int count = cos.countCommandeNonLivree(idUtilisateur);
            count += cos.countCommandeEnCours(idUtilisateur);

            obj.add(p);
            obj.add(u);
            obj.add(count);

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
