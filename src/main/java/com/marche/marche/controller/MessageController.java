package com.marche.marche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marche.marche.api.APIResponse;
import com.marche.marche.authentification.JwtUtil;
import com.marche.marche.modele.Commentaire;
import com.marche.marche.modele.Conversation;
import com.marche.marche.modele.Message;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.services.CommentaireService;
import com.marche.marche.services.ConversationService;
import com.marche.marche.services.MessageService;
import com.marche.marche.services.PersonneService;
import com.marche.marche.services.ProduitService;
import com.marche.marche.services.UtilisateurService;

import java.sql.Timestamp;
import java.time.Instant;

import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*")
public class MessageController {
    @Autowired
    private MessageService ms;

    @Autowired
    private ConversationService cos;

    @Autowired
    private UtilisateurService us;

    @Autowired
    private ProduitService ps;

    @Autowired
    private PersonneService pes;

    @Autowired
    private JwtUtil jwtUtil;

    // CONTROLLEUR D'AJOUT DE COMMENTAIRE DE PRODUIT PAR L'ACHETEUR
    @PostMapping("/save/{id_acheteur}")
    public ResponseEntity<APIResponse> saveProduitCommentaire(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable(name = "id_acheteur") int idAcheteur, @RequestParam(name = "contenu_message") String contenuMessage) {
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

            Utilisateur u1 = us.getUtilisateur(idAcheteur);
            Personne p1 = pes.getPersonneByUtilisateur(u1);

            Conversation conversation = new Conversation(p, p1);

            Timestamp dateMessage = Timestamp.from(Instant.now());

            Message message = new Message(contenuMessage, dateMessage, conversation, p);

            ms.saveMessage(message);

            APIResponse api = new APIResponse(null, obj);
            return ResponseEntity.ok(api);
        } catch (Exception e) {
            e.printStackTrace();
            APIResponse response = new APIResponse(e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
