package com.marche.marche.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.CommandeProduit;
import com.marche.marche.modele.Personne;
import com.marche.marche.utils.CommandeProduitId;

@Repository
public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, CommandeProduitId> {
    
    @Query("SELECT cp FROM CommandeProduit cp JOIN Produit pro ON pro = cp.produit WHERE pro.personne = :personne")
    List<CommandeProduit> listCommandeByVendeur(@Param("personne") Personne personne);
}
