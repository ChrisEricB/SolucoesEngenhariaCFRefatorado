package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private static final String URL_PADRAO =
            "jdbc:mysql://localhost:3306/solucoes_engenharia_cf"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=America/Sao_Paulo";

    private static final String USUARIO_PADRAO = "root";
    private static final String SENHA_PADRAO = "";

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        String url = obterConfiguracao("DB_URL", URL_PADRAO);
        String usuario = obterConfiguracao("DB_USUARIO", USUARIO_PADRAO);
        String senha = obterConfiguracao("DB_SENHA", SENHA_PADRAO);

        return DriverManager.getConnection(url, usuario, senha);
    }

    private static String obterConfiguracao(
            String nomeVariavel,
            String valorPadrao) {

        String valor = System.getenv(nomeVariavel);

        if (valor == null || valor.isBlank()) {
            return valorPadrao;
        }

        return valor;
    }
}