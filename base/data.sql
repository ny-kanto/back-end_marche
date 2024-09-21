-- UTILISATEUR
-- INSERT INTO utilisateur (id, email, password, is_admin) VALUES
-- (nextval('utilisateur_seq'), 'john.doe@example.com', 'password123', 0),
-- (nextval('utilisateur_seq'), 'jane.smith@example.com', 'securePass!456', 0),
-- (nextval('utilisateur_seq'), 'admin@example.com', 'adminSecret789', 1),
-- (nextval('utilisateur_seq'), 'alice.williams@example.com', 'alicePass123', 0),
-- (nextval('utilisateur_seq'), 'bob.johnson@example.com', 'bobSecure456', 0),
-- (nextval('utilisateur_seq'), 'charlie.brown@example.com', 'charliePass789', 0),
-- (nextval('utilisateur_seq'), 'daniel.wilson@example.com', 'danielSecret!123', 0),
-- (nextval('utilisateur_seq'), 'emily.davis@example.com', 'emilySecure456', 0),
-- (nextval('utilisateur_seq'), 'frank.miller@example.com', 'frankPass789', 0),
-- (nextval('utilisateur_seq'), 'george.moore@example.com', 'georgeSecret!123', 0);

-- ROLE
INSERT INTO role (id, nom) VALUES
(nextval('role_seq'), 'Acheteur'),
(nextval('role_seq'), 'Vendeur');

-- UNITE
INSERT INTO unite (id, nom) VALUES 
(nextval('unite_seq'), 'Kilogramme'),
(nextval('unite_seq'), 'Litre'),
(nextval('unite_seq'), 'Pièce'),
(nextval('unite_seq'), 'Mètre'),
(nextval('unite_seq'), 'Gramme'),
(nextval('unite_seq'), 'Centimètre'),
(nextval('unite_seq'), 'Millilitre'),
(nextval('unite_seq'), 'Tonne'),
(nextval('unite_seq'), 'Pack'),
(nextval('unite_seq'), 'Boîte');

-- TYPE PRODUCTION
INSERT INTO type_production (id, nom) VALUES
(nextval('type_production_seq'), 'biologique'),
(nextval('type_production_seq'), 'durable');

-- TYPE PRODUIT
INSERT INTO type_produit(id, nom) VALUES
(nextval('type_produit_seq'), 'Produits agricoles'),
(nextval('type_produit_seq'), 'Produits d''élevages');

-- REGION
INSERT INTO region(id, nom) VALUES
(nextval('region_seq'), 'Analamanga'),
(nextval('region_seq'), 'Bongolava'),
(nextval('region_seq'), 'Itasy'),
(nextval('region_seq'), 'Vakinankaratra'),
(nextval('region_seq'), 'Diana'),
(nextval('region_seq'), 'Sava'),
(nextval('region_seq'), 'Amoron''i Mania'),
(nextval('region_seq'), 'Atsimo Atsinanana'),
(nextval('region_seq'), 'Fitovinany'),
(nextval('region_seq'), 'Haute Matsiatra'),
(nextval('region_seq'), 'Ihorombe'),
(nextval('region_seq'), 'Vatovavy'),
(nextval('region_seq'), 'Betsiboka'),
(nextval('region_seq'), 'Boeny'),
(nextval('region_seq'), 'Melaky'),
(nextval('region_seq'), 'Sofia'),
(nextval('region_seq'), 'Alaotra Mangoro'),
(nextval('region_seq'), 'Analanjirofo'),
(nextval('region_seq'), 'Atsinanana'),
(nextval('region_seq'), 'Androy'),
(nextval('region_seq'), 'Anosy'),
(nextval('region_seq'), 'Atsimo Andrefana'),
(nextval('region_seq'), 'Menabe');

-- CATEGORIE
INSERT INTO categorie(id, nom, id_type_produit) VALUES
(nextval('categorie_seq'), 'Fruits et Légumes', 1),
(nextval('categorie_seq'), 'Céréales et Légumineuses', 1),
(nextval('categorie_seq'), 'Plantes et Herbes Aromatiques', 1),
(nextval('categorie_seq'), 'Noix et Graines', 1),

(nextval('categorie_seq'), 'Viande', 2),
(nextval('categorie_seq'), 'Produits Laitiers', 2),
(nextval('categorie_seq'), 'Œufs', 2),
(nextval('categorie_seq'), 'Poissons et Fruits de Mer', 2),
(nextval('categorie_seq'), 'Miel et Produits de la Ruchee', 2);



-- PRODUIT
-- FRUITS ET LEGUMES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'), 'Pommes', 'Description des pommes', 2.5, 1, 3, 1, 1, 1), -- Kilogramme
(nextval('produit_seq'),'Oranges', 'Description des oranges', 3.0, 1, 3, 1, 1, 1), -- Kilogramme
(nextval('produit_seq'),'Tomates', 'Description des tomates', 1.8, 1, 3, 1, 5, 1), -- Gramme
(nextval('produit_seq'),'Carottes', 'Description des carottes', 1.2, 1, 3, 1, 1, 1), -- Kilogramme
(nextval('produit_seq'),'Salades', 'Description des salades', 1.5, 1, 3, 1, 3, 1); -- Pièce

-- CEREALES ET LEGUMINEUSES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Blé', 'Description du blé', 1.0, 5, 7, 2, 8, 1), -- Tonne
(nextval('produit_seq'),'Maïs', 'Description du maïs', 1.2, 5, 7, 2, 8, 1), -- Tonne
(nextval('produit_seq'),'Riz', 'Description du riz', 2.0, 5, 7, 2, 8, 1), -- Tonne
-- (nextval('produit_seq'),'Lentilles', 'Description des lentilles', 2.5, 5, 7, 2, 5, 1), -- Gramme
(nextval('produit_seq'),'Pois chiches', 'Description des pois chiches', 2.8, 5, 7, 2, 5, 1); -- Gramme

-- PLANTES ET HERBES AROMATIQUES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Basilic', 'Description du basilic', 0.5, 1, 2, 3, 3, 1), -- Pièce
(nextval('produit_seq'),'Menthe', 'Description de la menthe', 0.5, 1, 2, 3, 3, 1), -- Pièce
(nextval('produit_seq'),'Persil', 'Description du persil', 0.5, 1, 2, 3, 3, 1), -- Pièce
(nextval('produit_seq'),'Thym', 'Description du thym', 0.5, 1, 2, 3, 3, 1), -- Pièce
(nextval('produit_seq'),'Lavande', 'Description de la lavande', 0.7, 1, 2, 3, 3, 1); -- Pièce

-- NOIX ET GRAINES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Amandes', 'Description des amandes', 10.0, 1, 5, 4, 5, 1), -- Gramme
(nextval('produit_seq'),'Noix', 'Description des noix', 9.0, 1, 5, 4, 5, 1), -- Gramme
(nextval('produit_seq'),'Graines de tournesol', 'Description des graines de tournesol', 5.0, 1, 5, 4, 5, 1), -- Gramme
(nextval('produit_seq'),'Graines de lin', 'Description des graines de lin', 6.0, 1, 5, 4, 5, 1); -- Gramme

-- Viande
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Bœuf', 'Description du bœuf', 20.0, 1, 2, 5, 5, 1), -- Gramme
(nextval('produit_seq'),'Porc', 'Description du porc', 15.0, 1, 2, 5, 5, 1), -- Gramme
(nextval('produit_seq'),'Poulet', 'Description du poulet', 12.0, 1, 2, 5, 5, 1), -- Gramme
(nextval('produit_seq'),'Agneau', 'Description de l''agneau', 25.0, 1, 2, 5, 5, 1), -- Gramme
(nextval('produit_seq'),'Dinde', 'Description de la dinde', 18.0, 1, 2, 5, 5, 1); -- Gramme

-- PRODUITS LAITIERS
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Lait', 'Description du lait', 1.5, 1, 1, 6, 2, 1), -- Litre
(nextval('produit_seq'),'Fromage', 'Description du fromage', 5.0, 1, 1, 6, 3, 1), -- Pièce
(nextval('produit_seq'),'Yaourt', 'Description du yaourt', 2.0, 1, 1, 6, 3, 1), -- Pièce
(nextval('produit_seq'),'Beurre', 'Description du beurre', 3.0, 1, 1, 6, 3, 1), -- Pièce
(nextval('produit_seq'),'Crème', 'Description de la crème', 4.0, 1, 1, 6, 7, 1); -- Millilitre

-- OEUFS
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Œufs de poule', 'Description des œufs de poule', 0.5, 12, 1, 7, 9, 1), -- Pack
(nextval('produit_seq'),'Œufs de caille', 'Description des œufs de caille', 1.0, 24, 1, 7, 9, 1); -- Pack

-- POISSONS ET FRUITS DE MER
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Poissons d''eau douce', 'Description des poissons d''eau douce', 10.0, 1, 2, 8, 5, 1), -- Gramme
(nextval('produit_seq'),'Poissons de mer', 'Description des poissons de mer', 15.0, 1, 2, 8, 5, 1), -- Gramme
(nextval('produit_seq'),'Crevettes', 'Description des crevettes', 20.0, 1, 2, 8, 5, 1), -- Gramme
(nextval('produit_seq'),'Coquillages', 'Description des coquillages', 18.0, 1, 2, 8, 5, 1); -- Gramme

-- MIEL ET PRODUITS DE LA RUCHE
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Miel', 'Description du miel', 8.0, 1, 3, 9, 2, 1), -- Litre
(nextval('produit_seq'),'Cire d''abeille', 'Description de la cire d''abeille', 5.0, 1, 3, 9, 5, 1), -- Gramme
(nextval('produit_seq'),'Propolis', 'Description de la propolis', 12.0, 1, 3, 9, 5, 1); -- Gramme


INSERT INTO commande (id, adresse_livraison, date_commande, num_client, status_commande, id_personne) VALUES
(nextval('commande_seq'), 'Antananarivo', '2024-09-01', 'C001', 11, 2),
(nextval('commande_seq'), 'Fianarantsoa', '2024-09-02', 'C002', 1, 2),
(nextval('commande_seq'), 'Mahajanga', '2024-09-03', 'C003', 11, 2),
(nextval('commande_seq'), 'Toamasina', '2024-09-04', 'C004', 0, 2),
(nextval('commande_seq'), 'Antsirabe', '2024-09-05', 'C005', 11, 2),
(nextval('commande_seq'), 'Toliara', '2024-09-06', 'C006', 11, 2),
(nextval('commande_seq'), 'Antananarivo', '2024-09-07', 'C007', 11, 2),
(nextval('commande_seq'), 'Fianarantsoa', '2024-09-08', 'C008', 1, 2),
(nextval('commande_seq'), 'Mahajanga', '2024-09-09', 'C009', 11, 2),
(nextval('commande_seq'), 'Toamasina', '2024-09-10', 'C010', 11, 2),
(nextval('commande_seq'), 'Antananarivo', '2023-01-15', 'C011', 11, 2),
(nextval('commande_seq'), 'Fianarantsoa', '2023-02-20', 'C012', 1, 2),
(nextval('commande_seq'), 'Mahajanga', '2023-03-25', 'C013', 11, 2),
(nextval('commande_seq'), 'Toamasina', '2023-04-05', 'C014', 0, 2),
(nextval('commande_seq'), 'Antsirabe', '2022-05-10', 'C015', 11, 2),
(nextval('commande_seq'), 'Toliara', '2022-06-22', 'C016', 11, 2),
(nextval('commande_seq'), 'Antananarivo', '2022-07-30', 'C017', 11, 2),
(nextval('commande_seq'), 'Fianarantsoa', '2021-08-18', 'C018', 1, 2),
(nextval('commande_seq'), 'Mahajanga', '2021-09-20', 'C019', 11, 2),
(nextval('commande_seq'), 'Toamasina', '2021-10-15', 'C020', 0, 2);

INSERT INTO commande_produit (id_commande, id_produit, prix_unitaire, quantite) VALUES
(1, 1, 4500, 10),
(1, 3, 3500, 20),
(2, 5, 4000, 15),
(2, 8, 5000, 30),
(3, 11, 2000, 25),
(3, 8, 4000, 5),
(5, 27, 5000, 5),
(5, 24, 1500, 50),
(6, 27, 2500, 30),
(6, 29, 6000, 20),
(7, 32, 1800, 10),
(7, 33, 5600, 8),
(8, 3, 3500, 25),
(9, 5, 4000, 10),
(9, 17, 4000, 20),
(10, 8, 5000, 40),
(10, 33, 3000, 10),
(11, 2, 2500, 12),
(11, 5, 3000, 20),
(12, 19, 4000, 8), 
(12, 11, 5000, 15),
(13, 17, 2000, 5),
(13, 19, 3000, 25),
(15, 21, 4000, 5),
(15, 32, 6000, 3),
(16, 8, 4000, 15),
(16, 1, 6000, 3),
(17, 11, 4000, 5),
(17, 17, 6000, 3),
(18, 24, 4000, 5),
(18, 3, 6000, 3),
(19, 33, 4000, 5),
(19, 19, 6000, 3);

INSERT INTO entree (id, date_entree, quantite, id_produit) VALUES
(1, '2020-08-25', 100, 2),  -- Oranges
(2, '2020-08-25', 50, 3),   -- Tomates
(3, '2020-09-01', 150, 1),  -- Pommes
(4, '2020-09-02', 75, 4),   -- Carottes
(5, '2020-09-02', 200, 5),  -- Salades
(6, '2020-09-03', 100, 8),  -- Riz
(7, '2020-09-04', 90, 11),  -- Menthe
(8, '2020-09-05', 120, 17), -- Œufs de poule
(9, '2020-09-06', 50, 19),  -- Bœuf
(10, '2020-09-06', 200, 24), -- Lait
(11, '2020-09-07', 180, 27), -- Beurre
(12, '2020-09-08', 250, 29), -- Poissons d'eau douce
(13, '2020-09-09', 100, 32), -- Coquillages
(14, '2020-09-09', 300, 3),  -- Tomates
(15, '2020-09-10', 400, 5),  -- Salades
(16, '2020-09-10', 100, 17), -- Œufs de poule
(17, '2020-09-11', 200, 8),  -- Riz
(18, '2020-09-11', 50, 19),  -- Bœuf
(19, '2020-09-12', 100, 21), -- Poulet
(20, '2020-09-12', 150, 33); -- Miel

INSERT INTO sortie (id, date_sortie, quantite, id_produit) VALUES
(nextval('sortie_seq'), '2024-09-01', 10, 1),
(nextval('sortie_seq'), '2024-09-01', 20, 3),
(nextval('sortie_seq'), '2024-09-02', 15, 5),
(nextval('sortie_seq'), '2024-09-02', 30, 8),
(nextval('sortie_seq'), '2024-09-03', 25, 11),
(nextval('sortie_seq'), '2024-09-03', 5, 8),
(nextval('sortie_seq'), '2024-09-05', 5, 27),
(nextval('sortie_seq'), '2024-09-05', 50, 24),
(nextval('sortie_seq'), '2024-09-06', 30, 27),
(nextval('sortie_seq'), '2024-09-06', 20, 29),
(nextval('sortie_seq'), '2024-09-07', 10, 32),
(nextval('sortie_seq'), '2024-09-07', 8, 33),
(nextval('sortie_seq'), '2024-09-08', 25, 3),
(nextval('sortie_seq'), '2024-09-09', 10, 5),
(nextval('sortie_seq'), '2024-09-09', 20, 17),
(nextval('sortie_seq'), '2024-09-10', 20, 8),
(nextval('sortie_seq'), '2024-09-10', 10, 33),
(nextval('sortie_seq'), '2023-01-15', 12, 2),
(nextval('sortie_seq'), '2023-01-15', 20, 5),
(nextval('sortie_seq'), '2023-02-20', 8, 19),
(nextval('sortie_seq'), '2023-02-20', 15, 11),
(nextval('sortie_seq'), '2023-03-25', 5, 17),
(nextval('sortie_seq'), '2023-03-25', 25, 19),
(nextval('sortie_seq'), '2022-05-10', 5, 21),
(nextval('sortie_seq'), '2022-05-10', 3, 32),
(nextval('sortie_seq'), '2022-06-22', 15, 8),
(nextval('sortie_seq'), '2022-06-22', 3, 1),
(nextval('sortie_seq'), '2022-07-30', 5, 11),
(nextval('sortie_seq'), '2022-07-30', 3, 17),
(nextval('sortie_seq'), '2021-08-18', 5, 24),
(nextval('sortie_seq'), '2021-08-18', 3, 3),
(nextval('sortie_seq'), '2021-09-20', 5, 33),
(nextval('sortie_seq'), '2021-09-20', 3, 19);


UPDATE produit
SET localisation = CASE
    WHEN id = 1 THEN 'Antananarivo'
    WHEN id = 2 THEN 'Antsirabe'
    WHEN id = 3 THEN 'Miarinarivo'
    WHEN id = 4 THEN 'Toamasina'
    WHEN id = 5 THEN 'Fianarantsoa'
    WHEN id = 6 THEN 'Tsiroanomandidy'
    WHEN id = 7 THEN 'Ambatondrazaka'
    WHEN id = 8 THEN 'Maevatanana'
    WHEN id = 9 THEN 'Mahajanga'
    WHEN id = 10 THEN 'Antsiranana'
    WHEN id = 11 THEN 'Sambava'
    WHEN id = 12 THEN 'Antsohihy'
    WHEN id = 13 THEN 'Maintirano'
    WHEN id = 14 THEN 'Morondava'
    WHEN id = 15 THEN 'Fénérive Est'
    WHEN id = 16 THEN 'Toliara'
    WHEN id = 17 THEN 'Ambovombe'
    WHEN id = 18 THEN 'Taolagnaro'
    WHEN id = 19 THEN 'Mananjary'
    WHEN id = 20 THEN 'Manakara'
    WHEN id = 21 THEN 'Farafangana'
    WHEN id = 22 THEN 'Ihosy'
    WHEN id = 23 THEN 'Ambositra'
    WHEN id = 24 THEN 'Betafo'
    WHEN id = 25 THEN 'Antanifotsy'
    WHEN id = 26 THEN 'Vatomandry'
    WHEN id = 27 THEN 'Moramanga'
    WHEN id = 28 THEN 'Ambanja'
    WHEN id = 29 THEN 'Vohémar'
    WHEN id = 30 THEN 'Belo sur Tsiribihina'
    WHEN id = 31 THEN 'Morombe'
    WHEN id = 32 THEN 'Beloha'
    WHEN id = 33 THEN 'Ambohimahasoa'
    WHEN id = 34 THEN 'Ifanadiana'
    WHEN id = 35 THEN 'Vangaindrano'
    ELSE localisation
END,
id_region = CASE
    WHEN id = 1 THEN 1
    WHEN id = 2 THEN 2
    WHEN id = 3 THEN 3
    WHEN id = 4 THEN 4
    WHEN id = 5 THEN 5
    WHEN id = 6 THEN 6
    WHEN id = 7 THEN 7
    WHEN id = 8 THEN 8
    WHEN id = 9 THEN 9
    WHEN id = 10 THEN 10
    WHEN id = 11 THEN 11
    WHEN id = 12 THEN 12
    WHEN id = 13 THEN 13
    WHEN id = 14 THEN 14
    WHEN id = 15 THEN 15
    WHEN id = 16 THEN 16
    WHEN id = 17 THEN 17
    WHEN id = 18 THEN 18
    WHEN id = 19 THEN 19
    WHEN id = 20 THEN 20
    WHEN id = 21 THEN 21
    WHEN id = 22 THEN 22
    WHEN id = 23 THEN 23
    WHEN id = 24 THEN 2
    WHEN id = 25 THEN 2
    WHEN id = 26 THEN 4
    WHEN id = 27 THEN 4
    WHEN id = 28 THEN 10
    WHEN id = 29 THEN 11
    WHEN id = 30 THEN 13
    WHEN id = 31 THEN 14
    WHEN id = 32 THEN 17
    WHEN id = 33 THEN 19
    WHEN id = 34 THEN 19
    WHEN id = 35 THEN 21
    ELSE id_region
END;
