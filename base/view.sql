-- ETAT DE STOCK
CREATE OR REPLACE VIEW V_ETAT_STOCK AS
    SELECT
        P.ID                                                AS ID_PRODUIT,
        PE.ID                                               AS ID_PERSONNE,
        P.NOM                                               AS NOM_PRODUIT,
        U.NOM                                               AS NOM_UNITE,
        COALESCE(SUM(E.QTE), 0)                             AS SOMME_ENTREE,
        COALESCE(SUM(S.QTE), 0)                             AS SOMME_SORTIE,
        (COALESCE(SUM(E.QTE), 0) - COALESCE(SUM(S.QTE), 0)) AS RESTE
    FROM
        PRODUIT  P
        LEFT JOIN (
            SELECT
                ID_PRODUIT,
                SUM(QUANTITE) AS QTE
            FROM
                ENTREE
            GROUP BY
                ID_PRODUIT
        ) E
        ON P.ID = E.ID_PRODUIT
        LEFT JOIN (
            SELECT
                ID_PRODUIT,
                SUM(QUANTITE) AS QTE
            FROM
                SORTIE
            GROUP BY
                ID_PRODUIT
        ) S
        ON P.ID = S.ID_PRODUIT
        JOIN PERSONNE PE
        ON PE.ID = P.ID_PERSONNE
        JOIN UNITE U
        ON U.ID = P.ID_UNITE
    GROUP BY
        P.ID,
        PE.ID,
        P.NOM,
        U.NOM;

-- LISTE DES PRODUITS AVEC LEURS NOTES MOYENNES RESPECTIFS
CREATE OR REPLACE VIEW V_PRODUIT_NOTE AS
    SELECT
        P.ID                                AS ID_PRODUIT,
        P.NOM                               AS NOM_PRODUIT,
        P.PRIX                              AS PRIX_PRODUIT,
        P.DESCRIPTION                       AS DESCRIPTION_PRODUIT,
        P.DELAIS_LIVRAISON                  AS DELAIS_LIVRAISON_PRODUIT,
        P.MIN_COMMANDE                      AS MIN_COMMANDE_PRODUIT,
        P.DATE_AJOUT                        AS DATE_AJOUT_PRODUIT,
        P.ID_UNITE                          AS ID_UNITE,
        P.ID_CATEGORIE                      AS ID_CATEGORIE,
        P.LOCALISATION                      AS LOCALISATION_PRODUIT,
        P.ID_REGION                         AS ID_REGION,
        PE.ID                               AS ID_PERSONNE,
        COALESCE(AVG(NULLIF(E.NOTE, 0)), 0) AS NOTE_PRODUIT
    FROM
        PRODUIT    P
        JOIN PERSONNE PE
        ON PE.ID = P.ID_PERSONNE
        LEFT JOIN EVALUATION E
        ON E.ID_PRODUIT = P.ID
    GROUP BY
        P.ID,
        P.NOM,
        P.PRIX,
        PE.ID;

-- LE NOMBRE DE VENTE DE PRODUITS
WITH ANNEE_DISTINCTE AS (
    SELECT
        DISTINCT EXTRACT(YEAR FROM DATE_COMMANDE) AS ANNEE
    FROM
        COMMANDE
), MOIS_ANNEE AS (
    SELECT
        GENERATE_SERIES(1, 12) AS MOIS,
        ANNEE
    FROM
        ANNEE_DISTINCTE
)
SELECT
    P.ID                                             AS ID_PRODUIT,
    P.NOM                                            AS NOM_PRODUIT,
    U.ID                                             AS ID_UNITE,
    U.NOM                                            AS NOM_UNITE,
    COALESCE(SUM(CP.QUANTITE), 0)                    AS TOTAL_VENDUS,
    COALESCE(SUM(CP.PRIX_UNITAIRE * CP.QUANTITE), 0) AS TOTAL_VENTES,
    MA.MOIS                                          AS MOIS,
    MA.ANNEE                                         AS ANNEE
FROM
    MOIS_ANNEE       MA
    LEFT JOIN COMMANDE C
    ON EXTRACT(MONTH FROM C.DATE_COMMANDE) = MA.MOIS
    AND EXTRACT(YEAR FROM C.DATE_COMMANDE) = MA.ANNEE
    LEFT JOIN COMMANDE_PRODUIT CP
    ON CP.ID_COMMANDE = C.ID
    LEFT JOIN PRODUIT P
    ON CP.ID_PRODUIT = P.ID
    LEFT JOIN UNITE U
    ON U.ID = P.ID_UNITE
WHERE
    C.STATUS_COMMANDE >= 1
    AND P.ID = 1
    OR C.ID IS NULL
GROUP BY
    P.ID,
    P.NOM,
    U.ID,
    U.NOM,
    MA.MOIS,
    MA.ANNEE
ORDER BY
    P.ID,
    MA.ANNEE,
    MA.MOIS;

-- LE NOMBRE DE VENTE POUR TOUS LES PRODUITS D'UN VENDEUR
WITH ANNEE_DISTINCTE AS (
    SELECT
        DISTINCT EXTRACT(YEAR FROM DATE_COMMANDE) AS ANNEE
    FROM
        COMMANDE
), MOIS_ANNEE AS (
    SELECT
        GENERATE_SERIES(1, 12) AS MOIS,
        ANNEE
    FROM
        ANNEE_DISTINCTE
)
SELECT
    COALESCE(SUM(CP.PRIX_UNITAIRE * CP.QUANTITE), 0) AS TOTAL_VENTES,
    MA.MOIS                                          AS MOIS,
    MA.ANNEE                                         AS ANNEE
FROM
    MOIS_ANNEE       MA
    LEFT JOIN COMMANDE C
    ON EXTRACT(MONTH FROM C.DATE_COMMANDE) = MA.MOIS
    AND EXTRACT(YEAR FROM C.DATE_COMMANDE) = MA.ANNEE
    LEFT JOIN COMMANDE_PRODUIT CP
    ON CP.ID_COMMANDE = C.ID
    LEFT JOIN PRODUIT P
    ON CP.ID_PRODUIT = P.ID
WHERE
    C.STATUS_COMMANDE >= 1
    AND P.ID_PERSONNE = 1
    AND MA.ANNEE = 2024
    OR C.ID IS NULL
GROUP BY
    MA.MOIS,
    MA.ANNEE
ORDER BY
    MA.ANNEE,
    MA.MOIS;


-- LA LISTE DES ANNEES OU IL Y A UNE OU PLUSIEURS COMMANDES
WITH ANNEE_DISTINCTE AS (
    SELECT
        DISTINCT EXTRACT(YEAR FROM DATE_COMMANDE) AS ANNEE
    FROM
        COMMANDE
), MOIS_ANNEE AS (
    SELECT
        GENERATE_SERIES(1, 12) AS MOIS,
        ANNEE
    FROM
        ANNEE_DISTINCTE
)
SELECT
    DISTINCT(MA.ANNEE) AS ANNEE
FROM
    MOIS_ANNEE       MA
    LEFT JOIN COMMANDE C
    ON EXTRACT(YEAR FROM C.DATE_COMMANDE) = MA.ANNEE
    LEFT JOIN COMMANDE_PRODUIT CP
    ON CP.ID_COMMANDE = C.ID
    LEFT JOIN PRODUIT P
    ON CP.ID_PRODUIT = P.ID
WHERE
    C.STATUS_COMMANDE >= 1
    AND P.ID_PERSONNE = 1
    OR C.ID IS NULL
GROUP BY
    MA.ANNEE
ORDER BY
    MA.ANNEE;