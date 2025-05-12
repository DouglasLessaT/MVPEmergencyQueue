-- Criação do usuário 'api' com uma senha (substitua 'sua_senha_segura' por uma senha segura)
CREATE USER api WITH PASSWORD 'senha_super_secreta';

-- Conceda permissões de uso e criação no esquema 'public'
GRANT USAGE ON SCHEMA public TO api;
GRANT CREATE ON SCHEMA public TO api;

-- Conceda todas as permissões para todas as tabelas existentes no esquema 'public'
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO api;

-- Conceda todas as permissões para todas as sequências existentes no esquema 'public'
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO api;

-- Configure o search_path para o esquema 'public'
ALTER ROLE api SET search_path TO public;