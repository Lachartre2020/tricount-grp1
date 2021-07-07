--
-- Création de la base de données
--
CREATE DATABASE tricount;

--
-- Création des users
-- Même noms que les profils dans github
--

CREATE USER 'usertricount'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'tricount';

--
-- Attribution des droits
--

GRANT ALL ON tricount.* TO  'usertricount'@'localhost';

--
-- Remonté des droits
--

FLUSH PRIVILEGES
