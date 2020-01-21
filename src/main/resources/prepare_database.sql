-- utworz nowego uzytkownika
-- login i haslo musza zgadzac sie z tym, co jest w application.properties
CREATE USER winter WITH PASSWORD 'admin';

-- utworz nowa baze danych
CREATE DATABASE winter OWNER winter;

-- nastepnie polacz sie w psql do bazy winter:
-- \c winter

-- ustaw ownera schema public na winter:
-- ALTER SCHEMA public OWNER TO winter;