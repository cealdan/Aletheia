CREATE TABLE CreationPageBackup ( /* Les sauvegardes par utilisateur */
    username VARCHAR(255) NOT NULL PRIMARY KEY, 
	chathistory JSONB,
	mymodels JSONB,
	titlelist JSONB
);

