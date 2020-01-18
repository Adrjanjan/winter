-- utworz nowego uzytkownika
-- login i haslo musza zgadzac sie z tym, co jest w application.properties
CREATE USER winter WITH PASSWORD 'admin';

-- utworz nowa baze danych
CREATE DATABASE winter OWNER winter;