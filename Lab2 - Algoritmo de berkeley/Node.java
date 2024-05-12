package Lab2;

class Node {
    private Clock clock;

    public Node() {
        this.clock = new Clock();
    }

    public Clock getClock() {
        return clock;
    }

    public void sendMessage(Node node, long time) {
        // Simula el envío de un mensaje estableciendo el tiempo del reloj del nodo receptor
        node.receiveMessage(time);
    }

    public void receiveMessage(long time) {
        // Simula la recepción de un mensaje actualizando el tiempo del reloj
        clock.setTime(time);
    }
}