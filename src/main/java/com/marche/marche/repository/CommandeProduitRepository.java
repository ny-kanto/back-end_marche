package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.CommandeProduit;
import com.marche.marche.utils.CommandeProduitId;

@Repository
public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, CommandeProduitId> {

    // @Query("SELECT cp FROM CommandeProduit cp JOIN Produit pro ON pro = cp.produit WHERE pro.personne = :personne ORDER BY CommandeProduit.commande.id ASC LIMIT :nbrParPage OFFSET :noPage")
    // List<CommandeProduit> listCommandeByVendeur(@Param("personne") Personne personne, @Param("nbrParPage") int nbrParPage, @Param("noPage") int noPage);
}
