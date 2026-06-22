package lojainfo.swing;

public class SessaoUsuario {

    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String tokenRecebido) {
        token = tokenRecebido;
    }

    public static void limpar() {
        token = null;
    }
}