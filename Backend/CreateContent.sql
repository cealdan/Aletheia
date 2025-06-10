CREATE TABLE Content ( /* Les shorts à afficher dans la HomePage() (video + audio) */
	Date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(), /*Date de création du contenu */
	UserName VARCHAR(255) NOT NULL, /* User qui poste le contenu */
	ContentType VARCHAR(255) NOT NULL, /* image, video, text */
	ContentURL TEXT NOT NULL, /* La route de la vidéo ou image etc, ex: video pour http://192.168.10.5:5000/video , inconnue des utilisateurs*/
	AudioURL VARCHAR(255) NOT NULL, /* La route de l'audio, ex: audio pour http://192.168.10.5:5000/audio , inconnue des utilisateurs*/
	ContentPrompt TEXT NOT NULL, /* Le prompt qui a généré le contenu */
	AIContentModel VARCHAR(255) NOT NULL, /* Modèle utilisé pour générer le contenu visuel */
	AIAudioModel VARCHAR(255) NOT NULL, /* Modèle utilisé pour générer le contenu audio */
    Caption TEXT, /* Une description associée, peut être null */
	FOREIGN KEY (UserName) REFERENCES UserData(username)
);

CREATE INDEX idx_content_userdata ON Content(UserName); /*Pour accélérer la recherche et éviter de parcourir toute la table à chaque query*/
