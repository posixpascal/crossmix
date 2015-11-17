--name: create-crossmix!
-- creates a new crossmix record
INSERT INTO crossmix
(audio, video, uuid)
VALUES (:audio, :video, :uuid)