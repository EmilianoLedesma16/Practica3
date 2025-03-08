import java.io.IOException;

public class LanzadorClientes {
    public static void main(String[] args) {
        int numClientes = 5; // Número de clientes a abrir

        for (int i = 0; i < numClientes; i++) {
            try {
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    // Para Windows
                    new ProcessBuilder("cmd", "/c", "start", "cmd", "/k", "java -cp bin Cliente")
                            .start();
                } else {
                    // Para Linux/macOS
                    new ProcessBuilder("gnome-terminal", "--", "java", "-cp", "bin", "Cliente")
                            .start();
                }

                System.out.println("Cliente " + (i + 1) + " iniciado.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
