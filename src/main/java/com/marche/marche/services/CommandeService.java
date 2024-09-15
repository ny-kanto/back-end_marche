package com.marche.marche.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Commande;
import com.marche.marche.modele.CommandeProduit;
import com.marche.marche.modele.Personne;
import com.marche.marche.repository.CommandeProduitRepository;
import com.marche.marche.repository.CommandeRepository;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository cr;

    @Autowired
    private CommandeProduitRepository cpr;

    public void saveCommande(Commande Commande) {
        cr.save(Commande);
    }

    public List<CommandeProduit> listCommandeByVendeur(Personne personne) {
        return cpr.listCommandeByVendeur(personne);
    }
}
