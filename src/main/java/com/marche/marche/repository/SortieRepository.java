package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.Sortie;

@Repository
public interface SortieRepository extends JpaRepository<Sortie, Integer> {
}
