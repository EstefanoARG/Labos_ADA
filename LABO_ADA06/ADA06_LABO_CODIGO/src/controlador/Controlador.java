package controlador;

import modelo.Cliente;
import modelo.TablaHashLineal;
import modelo.TablaHashABB;
import vista.InterfazGrafica;

public class Controlador {
    private TablaHashLineal tablaLineal;
    private TablaHashABB tablaABB;
    private InterfazGrafica vista;

    public Controlador(InterfazGrafica vista) {
        this.vista = vista;
        this.tablaLineal = new TablaHashLineal(100);
        this.tablaABB = new TablaHashABB(100);
    }

    public void insertarCliente(Cliente cliente) {
        tablaLineal.insertar(cliente);
        tablaABB.insertar(cliente);
    }

    public Cliente buscarCliente(String clave) {
        return tablaLineal.buscar(clave); // O tablaABB.buscar(clave)
    }
}