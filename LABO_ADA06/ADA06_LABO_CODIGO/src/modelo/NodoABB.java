package modelo;

public class NodoABB {
    Cliente cliente;
    NodoABB izquierdo, derecho;

    public NodoABB(Cliente cliente) {
        this.cliente = cliente;
        this.izquierdo = this.derecho = null;
    }
}