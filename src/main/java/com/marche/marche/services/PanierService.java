package com.marche.marche.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Panier;
import com.marche.marche.modele.Produit;
import com.marche.marche.modele.Personne;
import com.marche.marche.modele.ProduitPanier;
import com.marche.marche.repository.PanierRepository;
import com.marche.marche.repository.ProduitPanierRepository;

@Service
public class PanierService {
    @Autowired
    private PanierRepository pr;

    @Autowired
    private ProduitPanierRepository ppr;

    public void savePanier(Panier panier) {
        pr.save(panier);
    }

    public Panier getPanierByPersonne(Personne p) {
        return pr.findByPersonne(p);
    }

    public ProduitPanier saveProduitPanier(ProduitPanier pp) {
        return ppr.save(pp);
    }

    public void deleteProduitPanier(Panier panier) {
        ppr.deleteByPanier(panier);
    }

    public List<ProduitPanier> listProduitPanier(Panier panier) {
        return ppr.findByPanierOrderByProduit(panier);
    }

    public int countProduitPanier(Panier panier) {
        return ppr.countByPanier(panier);
    }

    public void deleteProduitPanier(Panier panier, Produit produit) {
        ppr.deleteByPanierAndProduit(panier, produit);
    }

    public void updateProduitPanier(double quantite, Panier panier, Produit produit) {
        ppr.updateByPanierAndProduit(quantite, panier, produit);
    }
}
