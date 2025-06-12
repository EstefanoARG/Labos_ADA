package modelo;

public class TablaHashLineal {
    private Cliente[] tabla;
    private int tamaño;

    public TablaHashLineal(int tamaño) {
        this.tamaño = tamaño;
        tabla = new Cliente[tamaño];
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
        while (tabla[index] != null) {
            index = (index + 1) % tamaño; // Avanza linealmente
        }
        tabla[index] = cliente;
    }

    
    public Cliente buscar(String clave) {
        int index = hash(clave);
        int intentos = 0;
        while (tabla[index] != null && intentos < tamaño) {
            if (tabla[index].getClaveHash().equals(clave)) {
                return tabla[index];
            }
            index = (index + 1) % tamaño;
            intentos++;
        }
        return null; 
    }
}