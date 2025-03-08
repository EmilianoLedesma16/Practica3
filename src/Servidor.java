import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Servidor {
    private static final int PUERTO = 1234;
    private static final int MAX_CLIENTES = 3; // Máximo de clientes permitidos
    private static CopyOnWriteArrayList<Thread> clientesActivos = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.err.println("SERVIDOR >>> Arranca el servidor, esperando conexiones...");
        
        try (ServerSocket socketEscucha = new ServerSocket(PUERTO)) {
            while (true) {
                if (clientesActivos.size() < MAX_CLIENTES) {
                    Socket conexion = socketEscucha.accept();
                    System.err.println("SERVIDOR >>> Conexión recibida. Se lanza un nuevo hilo.");
                    
                    Hilo h = new Hilo(conexion);
                    Thread hilo = new Thread(h);
                    hilo.start();
                    
                    clientesActivos.add(hilo);
                    
                    // Eliminar hilos inactivos
                    clientesActivos.removeIf(t -> !t.isAlive());
                } else {
                    // Si el servidor está lleno, rechazar la conexión con un mensaje
                    Socket conexionRechazada = socketEscucha.accept();
                    PrintWriter salida = new PrintWriter(conexionRechazada.getOutputStream(), true);
                    salida.println("[Servidor]: El servidor está lleno. Intenta conectarte más tarde.");
                    System.err.println("SERVIDOR >>> Servidor lleno. Rechazando conexión.");
                    conexionRechazada.close();
                }
            }
        } catch (IOException e) {
            System.err.println("SERVIDOR >>> Error en la ejecución del servidor.");
            e.printStackTrace();
        }
    }
}
