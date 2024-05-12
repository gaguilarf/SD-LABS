package ejer1;

import java.io.*;
import java.net.*;
import java.util.Date;

public class CristianCliente {
    private String host;
    private int port;

    public CristianCliente(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void syncTime() {
        try {
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("SYNC");
            long tiempoLocal = new Date().getTime();

            long tiempoServidor = Long.parseLong(in.readLine());
            long diferenciaTiempo = tiempoServidor - tiempoLocal;
            System.out.println("Tiempo del servidor: " + tiempoServidor);
            System.out.println("Tiempo del cliente: " + tiempoLocal);

            System.out.println("Diferencia de tiempo: " + diferenciaTiempo + " milisegundos");
            System.out.println("El nuevo tiempo del cliente es: "+(tiempoServidor+diferenciaTiempo/2));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        CristianCliente client = new CristianCliente(host, port);
        client.syncTime();
    }
}