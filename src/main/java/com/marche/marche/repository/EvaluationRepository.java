package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.Evaluation;
import com.marche.marche.utils.EvaluationId;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, EvaluationId> {
}
