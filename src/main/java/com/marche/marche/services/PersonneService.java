package com.marche.marche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Personne;
import com.marche.marche.modele.Utilisateur;
import com.marche.marche.repository.PersonneRepository;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository pr;

    public void savePersonne(Personne personne) {
        pr.save(personne);
    }

    public Personne getPersonneByUtilisateur(Utilisateur u) {
        return pr.findByUtilisateur(u);
    }
}
