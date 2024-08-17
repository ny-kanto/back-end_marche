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
(nextval('produit_seq'), 'Pommes', 'Des pommes fraîches et croquantes, parfaites pour une collation saine ou pour cuisiner des desserts savoureux. Cultivées localement pour une qualité optimale.', 2.5, 1, 3, 1, 1, 1), -- Kilogramme //
(nextval('produit_seq'),'Oranges', 'Oranges juteuses, riches en vitamine C, idéales pour faire du jus frais ou pour une consommation quotidienne. Issues de cultures locales.', 3.0, 1, 3, 1, 1, 1), -- Kilogramme //
(nextval('produit_seq'),'Tomates', 'Tomates rouges, mûres et gorgées de soleil, parfaites pour vos salades, sauces ou plats cuisinés. Produites localement pour une fraîcheur garantie.', 1.8, 1, 3, 1, 5, 1), -- Gramme //
(nextval('produit_seq'),'Carottes', 'Carottes croquantes, riches en bêta-carotène, idéales pour les salades, les soupes ou comme collation saine. Cultivées avec soin localement.', 1.2, 1, 3, 1, 1, 1), -- Kilogramme //
(nextval('produit_seq'),'Salades', 'Salades fraîches et croustillantes, parfaites pour accompagner tous vos repas. Produites localement pour garantir la qualité et la fraîcheur.', 1.5, 1, 3, 1, 3, 1); -- Pièce //

-- CEREALES ET LEGUMINEUSES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Blé', 'Blé de haute qualité, idéal pour la production de farine et autres produits céréaliers. Cultivé de manière durable pour des rendements optimaux.', 1.0, 5, 7, 2, 8, 1), -- Tonne //
(nextval('produit_seq'),'Maïs', 'Maïs jaune riche en nutriments, utilisé pour la production de farine, d''huile ou comme aliment pour animaux. Provenant de cultures locales.', 1.2, 5, 7, 2, 8, 1), -- Tonne //
(nextval('produit_seq'),'Riz', 'Riz blanc de qualité supérieure, idéal pour tous vos plats traditionnels. Produit localement et cultivé dans le respect de l''environnement.', 2.0, 5, 7, 2, 8, 1), -- Tonne //
-- (nextval('produit_seq'),'Lentilles', 'Description des lentilles', 2.5, 5, 7, 2, 5, 1), -- Gramme
(nextval('produit_seq'),'Pois chiches', 'Pois chiches nutritifs et riches en protéines, parfaits pour les plats végétariens et les salades. Cultivés dans des conditions optimales.', 2.8, 5, 7, 2, 5, 1); -- Gramme //

-- PLANTES ET HERBES AROMATIQUES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Basilic', 'Basilic frais, aux arômes intenses, parfait pour agrémenter vos plats méditerranéens et vos sauces. Cueilli localement pour une fraîcheur garantie.', 0.5, 1, 2, 3, 3, 1), -- Pièce //
(nextval('produit_seq'),'Menthe', 'Menthe verte aux feuilles parfumées, idéale pour les tisanes, les desserts et les plats salés. Cultivée localement pour une qualité optimale.', 0.5, 1, 2, 3, 3, 1), -- Pièce //
(nextval('produit_seq'),'Persil', 'Persil frais et aromatique, un incontournable pour assaisonner vos plats. Récolté localement pour conserver toute sa saveur.', 0.5, 1, 2, 3, 3, 1), -- Pièce //
(nextval('produit_seq'),'Thym', 'Description du thym', 0.5, 1, 2, 3, 3, 1), -- Pièce
(nextval('produit_seq'),'Lavande', 'Lavande parfumée, parfaite pour la décoration ou pour infuser vos tisanes et desserts. Produite localement avec soin.', 0.7, 1, 2, 3, 3, 1); -- Pièce //

-- NOIX ET GRAINES
INSERT INTO produit (id, nom, description, prix, min_commande, delais_livraison, id_categorie, id_unite, id_personne) VALUES
(nextval('produit_seq'),'Amandes', 'Amandes croquantes et riches en nutriments, idéales pour vos collations ou pâtisseries. Issues de cultures locales pour une qualité supérieure.', 10.0, 1, 5, 4, 5, 1), -- Gramme //
(nextval('produit_seq'),'Noix', 'Description des noix', 9.0, 1, 5, 4, 5, 1), -- Gramme
(nextval('produit_seq'),'Graines de tournesol', 'Graines de tournesol riches en vitamines, parfaites pour vos salades, pains ou à consommer seules. Cultivées avec soin.', 5.0, 1, 5, 4, 5, 1), -- Gramme //
(nextval('produit_seq'),'Graines de lin', 'Graines de lin riches en oméga-3, idéales pour agrémenter vos salades ou vos plats. Provenant de cultures locales.', 6.0, 1, 5, 4, 5, 1); -- Gramme //

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
(nextval('produit_seq'),'Œufs de poule', 'Œufs de poule frais, produits localement, parfaits pour vos petits déjeuners ou pour cuisiner. Vendu par pack de 12.', 0.5, 12, 1, 7, 9, 1), -- Pack //
(nextval('produit_seq'),'Œufs de caille', 'Œufs de caille délicats, riches en nutriments, parfaits pour des recettes raffinées. Disponible en pack de 24.', 1.0, 24, 1, 7, 9, 1); -- Pack //

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
