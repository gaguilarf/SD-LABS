package Lab2;

import java.util.List;

public class BerkeleyAlgorithm {
    public static void synchronizeClocks(List<Node> nodes) {
        long sum = 0;
        int count = 0;

        // Calcular el tiempo promedio de todos los relojes
        for (Node node : nodes) {
            sum += node.getClock().getTime();
            count++;
        }
        long averageTime = sum / count;

        // Distribuir el tiempo promedio a todos los nodos
        for (Node node : nodes) {
            long deviation = averageTime - node.getClock().getTime();
            node.getClock().setTime(node.getClock().getTime() + deviation);
        }
    }
    
    public static void main(String[] args) {
        // Crear nodos
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        // Simular envío y recepción de mensajes entre nodos
        // (Debes implementar estos métodos según tu aplicación)
        node1.sendMessage(node2, 100);
        node2.sendMessage(node3, 200);
        node1.sendMessage(node3, 300);

        // Sincronizar los relojes utilizando el algoritmo de Berkeley
        synchronizeClocks(List.of(node1, node2, node3));

        // Mostrar los tiempos de los relojes después de la sincronización
        System.out.println("Tiempo de Node 1 después de la sincronización: " + node1.getClock().getTime());
        System.out.println("Tiempo de Node 2 después de la sincronización: " + node2.getClock().getTime());
        System.out.println("Tiempo de Node 3 después de la sincronización: " + node3.getClock().getTime());
    }
}
