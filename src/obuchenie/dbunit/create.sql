CREATE TABLE `users` ( `id_user` int NOT NULL AUTO_INCREMENT,  `fio` varchar(100),  `birthday` date,  `sex` enum('m','f'), PRIMARY KEY (`id_user`)) ENGINE=InnoDB
CREATE TABLE `articles` ( `id_article` int NOT NULL AUTO_INCREMENT,  `title` varchar(100),  `produced` date, `price` float, PRIMARY KEY (`id_article`)) ENGINE=InnoDB
CREATE TABLE `purchases` (  `id_purchase` int NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,  `id_article` int(11) NOT NULL,  `price` float DEFAULT NULL,  `qty` int(11) DEFAULT NULL,  PRIMARY KEY (`id_purchase`),
FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE,
FOREIGN KEY (`id_good`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB