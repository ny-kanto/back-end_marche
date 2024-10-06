package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
}
