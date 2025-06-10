CREATE TABLE Models ( /* Les shorts à afficher dans la HomePage() (video + audio) */
	ModelName VARCHAR(255) NOT NULL PRIMARY KEY,
	ModelID VARCHAR(255) NOT NULL,
	GenData VARCHAR(255) NOT NULL
);

CREATE INDEX idx_ModelID ON Models(ModelID); /*Pour accélérer la recherche et éviter de parcourir toute la table à chaque query*/
