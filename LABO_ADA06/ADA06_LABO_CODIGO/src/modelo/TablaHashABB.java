package modelo;

public class TablaHashABB {
    private NodoABB[] tabla;
    private int tamaño;

    public TablaHashABB(int tamaño) {
        this.tamaño = tamaño;
        tabla = new NodoABB[tamaño];
    }

    
    public int hash(String clave) {
        int hash = 0;
        for (char c : clave.toCharArray()) {
            hash += (int) c;
        }
        return hash % tamaño;
    }

    
    public void insertar(Cliente cliente) {
        int index = hash(cliente.getClaveHash());
        if (tabla[index] == null) {
            tabla[index] = new NodoABB(cliente);
        } else {
            insertarEnABB(tabla[index], cliente);
        }
    }

    private void insertarEnABB(NodoABB nodo, Cliente cliente) {
        if (cliente.getClaveHash().compareTo(nodo.cliente.getClaveHash()) < 0) {
            if (nodo.izquierdo == null) {
                nodo.izquierdo = new NodoABB(cliente);
            } else {
                insertarEnABB(nodo.izquierdo, cliente);
            }
        } else {
            if (nodo.derecho == null) {
                nodo.derecho = new NodoABB(cliente);
            } else {
                insertarEnABB(nodo.derecho, cliente);
            }
        }
    }

    
    public Cliente buscar(String clave) {
        int index = hash(clave);
        return buscarEnABB(tabla[index], clave);
    }

    private Cliente buscarEnABB(NodoABB nodo, String clave) {
        if (nodo == null) return null;
        if (nodo.cliente.getClaveHash().equals(clave)) {
            return nodo.cliente;
        } else if (clave.compareTo(nodo.cliente.getClaveHash()) < 0) {
            return buscarEnABB(nodo.izquierdo, clave);
        } else {
            return buscarEnABB(nodo.derecho, clave);
        }
    }
}