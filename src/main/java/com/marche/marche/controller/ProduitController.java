package com.marche.marche.controller;

import org.springframework.web.multipart.MultipartFile;

import com.marche.marche.api.APIResponse;
import com.marche.marche.authentification.JwtUtil;
import com.marche.marche.modele.Categorie;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.ProduitPhotos;
import com.marche.marche.modele.TypeProduit;
import com.marche.marche.modele.Unite;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.services.CategorieService;
import com.marche.marche.services.PersonneService;
import com.marche.marche.services.ProduitPhotosService;
import com.marche.marche.services.ProduitService;
import com.marche.marche.services.TypeProduitService;
import com.marche.marche.services.UniteService;
import com.marche.marche.services.UtilisateurService;
import com.marche.marche.utils.Utils;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Base64;

@RestController
@RequestMapping("/produit")
@CrossOrigin(origins = "*")
public class ProduitController {
    @Autowired
    private ProduitService ps;

    @Autowired
    private TypeProduitService tps;

    @Autowired
    private ProduitPhotosService pps;

    @Autowired
    private PersonneService pes;

    @Autowired
    private UtilisateurService us;

    @Autowired
    private CategorieService cs;

    @Autowired
    private UniteService uns;

    @Autowired
    private JwtUtil jwtUtil;

    // CONTROLLEUR D'AJOUT DE PRODUIT DU VENDEUR
    @PostMapping("/save")
    public ResponseEntity<APIResponse> saveProduct(@RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam String nom, @RequestParam String description,
            @RequestParam String prix, @RequestParam("min_commande") String minCommande,
            @RequestParam("delais_livraison") String delaisLivraison, @RequestParam("id_categorie") String idCategorie,
            @RequestParam("id_unite") String idUnite, @RequestParam MultipartFile[] photo) {
        try {
            int idUtilisateur = 0;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = jwtUtil.parseJwtClaims(token);
                idUtilisateur = JwtUtil.getUserId(claims);
            }

            Utilisateur u = us.getUtilisateur(idUtilisateur);
            Personne p = pes.getPersonneByUtilisateur(u);
            Categorie categorie = cs.getCategorieById(Integer.valueOf(idCategorie));
            Unite unite = uns.getUniteById(Integer.valueOf(idUnite));

            Produit produit = new Produit(nom, description, Double.valueOf(prix), Double.valueOf(minCommande),
                    Integer.valueOf(delaisLivraison), categorie, p, unite);

            ProduitPhotos produitPhotos = null;

            ps.saveProduit(produit);

            List<Object> obj = new ArrayList<>();

            for (int index = 0; index < photo.length; index++) {
                byte[] photoBytes = photo[index].getBytes();

                produitPhotos = new ProduitPhotos();
                produitPhotos.setPhotos(photoBytes);
                produitPhotos.setProduit(produit);

                pps.saveProduitPhotos(produitPhotos);

                obj.add(produitPhotos);
            }

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // CONTROLLEUR DE MODIFICATION DE PRODUIT
    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> update(@RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable int id, @RequestBody Produit produit) {
        try {
            int idUtilisateur = 0;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims = jwtUtil.parseJwtClaims(token);
                idUtilisateur = JwtUtil.getUserId(claims);
            }
            // List<Object> obj = new ArrayList<>();
            Utilisateur u = us.getUtilisateur(idUtilisateur);
            Personne p = pes.getPersonneByUtilisateur(u);
            Categorie categorie = cs.getCategorieById(produit.getCategorie().getId());
            Unite unite = uns.getUniteById(produit.getUnite().getId());

            produit.setPersonne(p);
            produit.setCategorie(categorie);
            produit.setUnite(unite);

            Produit te = ps.updateProduit(id, produit, p);

            // obj.add(te);
            APIResponse api = new APIResponse(null, te);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // CONTROLLEUR POUR VOIR LA LISTE DES PRODUITS DU VENDEUR
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAll(@RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "10") int nbrParPage,
            @RequestParam(defaultValue = "1") int noPage,
            @RequestParam(name = "filtre_nom", defaultValue = "") String nom,
            @RequestParam(name = "filtre_prix_min", defaultValue = "0.0") double prixMin,
            @RequestParam(name = "filtre_prix_max", defaultValue = "0.0") double prixMax,
            @RequestParam(name = "filtre_unite", defaultValue = "0") int idUnite,
            @RequestParam(name = "filtre_categorie", defaultValue = "0") int idCategorie,
            @RequestParam(name = "filtre_type_produit", defaultValue = "0") int idTypeProduit,
            @RequestParam(defaultValue = "id") String column,
            @RequestParam(defaultValue = "1") int sort) {
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
            List<Produit> produit = new ArrayList<>();
            List<Categorie> categorie = cs.getAll();
            List<Unite> unite = uns.getAll();
            List<TypeProduit> typeProduit = tps.getAll();
            int totalPages = 0;
            if (nom.equals("") && prixMin == 0.0 && prixMax == 0.0 && idUnite == 0 && idCategorie == 0
                    && idTypeProduit == 0) {
                produit = ps.getProduitPerPage(p.getId(), noPage, nbrParPage, column, sort);
                totalPages = (int) Math.ceil((double) ps.countProduit() / nbrParPage);
            } else {
                produit = ps.getProduitFiltre(p.getId(), nom, prixMin, prixMax, idUnite, idCategorie, idTypeProduit);
                totalPages = 1;
            }
            obj.add(produit);
            obj.add(totalPages);
            obj.add(categorie);
            obj.add(unite);
            obj.add(typeProduit);
            obj.add(noPage);
            obj.add(sort);
            obj.add(column);
            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<APIResponse> findById(@RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable int id) {
        try {
            List<Object> obj = new ArrayList<>();
            Produit te = ps.getProduit(id);
            List<ProduitPhotos> pp = pps.getProduitPhotosByProduit(te);

            List<Map<String, String>> photosWithMime = new ArrayList<>();
            for (ProduitPhotos photo : pp) {
                String mimeType = Utils.guessMimeType(photo.getPhotos());
                Map<String, String> photoData = new HashMap<>();
                photoData.put("base64", Base64.getEncoder().encodeToString(photo.getPhotos()));
                photoData.put("mimeType", mimeType);
                photosWithMime.add(photoData);
            }

            obj.add(te);
            obj.add(photosWithMime);
            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // CONTROLLEUR POUR SUPPRIMER UN PRODUIT DU VENDEUR
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> delete(@RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable int id) {
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

            ps.deleteProduit(id, p);

            obj.add(ps);
            obj.add(p);

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}