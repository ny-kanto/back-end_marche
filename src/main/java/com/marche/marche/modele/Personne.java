package com.marche.marche.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personne")
@SequenceGenerator(
    name = "personne_seq", 
    sequenceName = "personne_seq", 
    allocationSize = 1
)
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personne_seq")
    private int id;
    
    private String nom;

    private String prenom;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(unique = true)
    private String contact;

    private String localisation;

    @ManyToOne
    @JoinColumn(name = "id_type_production", referencedColumnName = "id", nullable = true)
    private TypeProduction typeProduction;

    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id")
    private Utilisateur utilisateur;

    public Personne(String nom, String prenom, String codePostal, String contact, String localisation, TypeProduction typeProduction, Role role, Utilisateur utilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.codePostal = codePostal;
        this.contact = contact;
        this.localisation = localisation;
        this.typeProduction = typeProduction;
        this.role = role;
        this.utilisateur = utilisateur;
    }
}
