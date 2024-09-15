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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marche.marche.api.APIResponse;
import com.marche.marche.authentification.JwtUtil;
import com.marche.marche.modele.CommandeProduit;
import com.marche.marche.modele.EtatStock;
import com.marche.marche.modele.Panier;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.ProduitPanier;
import com.marche.marche.modele.ProduitPhotos;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.modele.response.ErrorRes;
import com.marche.marche.services.EntreeService;
import com.marche.marche.services.CommandeService;
import com.marche.marche.services.PersonneService;
import com.marche.marche.services.ProduitPhotosService;
import com.marche.marche.services.ProduitService;
import com.marche.marche.services.UtilisateurService;
import com.marche.marche.utils.Utils;

import com.marche.marche.modele.response.ErrorRes;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/commande")
@CrossOrigin(origins = "*")
public class CommandeController {
    @Autowired
    private CommandeService cos;

    @Autowired
    private ProduitService pros;

    @Autowired
    private ProduitPhotosService pps;

    @Autowired
    private PersonneService pes;

    @Autowired
    private UtilisateurService us;

    @Autowired
    private EntreeService es;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/list")
    public ResponseEntity<APIResponse> getAllCommande(@RequestHeader(name = "Authorization") String authorizationHeader) {
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

            List<CommandeProduit> cp = cos.listCommandeByVendeur(p);

            double totalGlobal = 0;

            Map<String, String> photoData = null;
            ProduitPhotos photos = null;
            String mimeType = "";
            HashMap<Integer, Map<String, String>> produitData = new HashMap<>();
            Produit produit = null;

            List<Map<String, Object>> produitsAvecTotal = new ArrayList<>();

            for (CommandeProduit commandeProduit : cp) {
                produit = commandeProduit.getProduit();
                photos = pps.getOneProduitPhotosByProduit(produit);

                double total = commandeProduit.getQuantite() * commandeProduit.getProduit().getPrix();

                Map<String, Object> produitDetails = new HashMap<>();
                produitDetails.put("id", produit.getId());
                produitDetails.put("nom", produit.getNom());
                produitDetails.put("prix", produit.getPrix());
                produitDetails.put("quantite", commandeProduit.getQuantite());
                produitDetails.put("total", total);
                produitDetails.put("nom_vendeur", produit.getPersonne().getNom());
                produitDetails.put("prenom_vendeur", produit.getPersonne().getPrenom());
                produitDetails.put("pseudo_vendeur", produit.getPersonne().getUtilisateur().getPseudo());
                produitDetails.put("email_vendeur", produit.getPersonne().getUtilisateur().getEmail());
                produitDetails.put("contact_vendeur", produit.getPersonne().getContact());
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

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
