/* 
 * Estrutura da base de dados
 */
CREATE SCHEMA IF NOT EXISTS intelipost;

-- Tabela contendo as permissões disponiveis
CREATE TABLE IF NOT EXISTS permissions (
    id varchar(40) NOT NULL,
    permission varchar(40) NOT NULL,
    PRIMARY KEY (id)
)  DEFAULT CHARSET=utf8;

-- Associação n:n entre perfil e permissão
CREATE TABLE IF NOT EXISTS profile_permissions (
    id_profile varchar(40) NOT NULL,
    id_permission varchar(40) NOT NULL
)  DEFAULT CHARSET=utf8;

-- Perfis de acesso
CREATE TABLE IF NOT EXISTS profiles (
  id varchar(40) NOT NULL,
  name varchar(50) NOT NULL,
  description varchar(150) DEFAULT NULL,
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;

-- Usuários
CREATE TABLE IF NOT EXISTS users (
  id varchar(40) NOT NULL,
  name varchar(50) NOT NULL,
  username varchar(100) NOT NULL,
  password varchar(40) NOT NULL,
  id_profile varchar(40) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_username (username)
) DEFAULT CHARSET=utf8;

INSERT INTO permissions (id, permission) VALUES 
('9272fff697f4402e9dc124d6b4b1a80c','PRODUCT'), 
('a56d93662c5e49be9e936b7dcacae724','NFE'), 
('503303eb390e44658452171bc573a85c','PURCHASE_ORDER'), 
('312e4dc1458245d7ae4c124f93d706de','SALE'), 
('d1c395e2869c430fb30ac8bf76ab6e66','USER');

INSERT INTO profiles (id, name, description) VALUES
('da71d374faab4c1f8636f8f7343ad7fd', 'ADMIN' , 'Perfil administrativo com acesso total ao sistema.'),
('1af2669e3ed34f2aaa4c01643e192d8e', 'USER' , 'Perfil de usuário comum com acesso somente a primeira pagina.'),
('d41f5546a12548818ed5f26454e1938e', 'OPERATOR' , 'Perfil de operador com acesso moderado.');

INSERT INTO profile_permissions (id_permission, id_profile) VALUES 
('9272fff697f4402e9dc124d6b4b1a80c','da71d374faab4c1f8636f8f7343ad7fd'), 
('a56d93662c5e49be9e936b7dcacae724','da71d374faab4c1f8636f8f7343ad7fd'), 
('503303eb390e44658452171bc573a85c','da71d374faab4c1f8636f8f7343ad7fd'), 
('312e4dc1458245d7ae4c124f93d706de','da71d374faab4c1f8636f8f7343ad7fd'), 
('d1c395e2869c430fb30ac8bf76ab6e66','da71d374faab4c1f8636f8f7343ad7fd'),
('503303eb390e44658452171bc573a85c','d41f5546a12548818ed5f26454e1938e'), 
('312e4dc1458245d7ae4c124f93d706de','d41f5546a12548818ed5f26454e1938e'), 
('d1c395e2869c430fb30ac8bf76ab6e66','d41f5546a12548818ed5f26454e1938e'),
('d1c395e2869c430fb30ac8bf76ab6e66','1af2669e3ed34f2aaa4c01643e192d8e');

INSERT INTO users (id, name, username, password, id_profile) VALUES
('e274a744bd634a789e33d59b3dccf69c', 'Renato Rocha', 'admin@system.com', '123456', 'da71d374faab4c1f8636f8f7343ad7fd'),
('a1582ae90f0c4dfe9bfc53bdfb230f52', 'João Rocha', 'user@system.com', '123456', '1af2669e3ed34f2aaa4c01643e192d8e'),
('96c3d5a81c01472a8db5ff71c373ec97', 'Pedro Paulo', 'operator@system.com', '123456', 'd41f5546a12548818ed5f26454e1938e');