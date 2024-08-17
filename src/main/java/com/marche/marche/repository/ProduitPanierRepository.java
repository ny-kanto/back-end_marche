package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.ProduitPanier;
import com.marche.marche.utils.ProduitPanierId;

@Repository
public interface ProduitPanierRepository extends JpaRepository<ProduitPanier, ProduitPanierId> {
}
