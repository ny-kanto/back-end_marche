package com.marche.marche.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Commentaire;
import com.marche.marche.modele.Produit;
import com.marche.marche.repository.CommentaireRepository;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository cr;

    public void saveCommentaire(Commentaire Commentaire) {
        cr.save(Commentaire);
    }

    public List<Commentaire> getCommentaireByProduit(Produit produit) {
        return cr.findByProduit(produit);
    }
}
