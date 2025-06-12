package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCSV {
    private static final String ARCHIVO_CSV = "src/datos/clientes.csv";
    private static final String SEPARADOR = ",";

    public static void guardarClientes(List<Cliente> clientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CSV))) {
            // Escribir encabezados
            bw.write("id,codigo,nombres,apellidos,telefono,correo,direccion,codigoPostal");
            bw.newLine();
            
            // Escribir datos
            for (Cliente cliente : clientes) {
                bw.write(clienteToCSV(cliente));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar en CSV: " + e.getMessage());
        }
    }

    public static List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        File archivo = new File(ARCHIVO_CSV);
        
        if (!archivo.exists()) {
            return clientes;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            // Saltar encabezado
            br.readLine();
            
            String linea;
            while ((linea = br.readLine()) != null) {
                Cliente cliente = csvToCliente(linea);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar CSV: " + e.getMessage());
        }
        return clientes;
    }

    private static String clienteToCSV(Cliente cliente) {
        return String.join(SEPARADOR,
                String.valueOf(cliente.getId()),
                cliente.getCodigo(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getDireccion(),
                cliente.getCodigoPostal());
    }

    private static Cliente csvToCliente(String csvLine) {
        try {
            String[] datos = csvLine.split(SEPARADOR);
            int id = Integer.parseInt(datos[0]);
            String codigo = datos[1];
            String nombres = datos[2];
            String apellidos = datos[3];
            String telefono = datos[4];
            String correo = datos[5];
            String direccion = datos[6];
            String codigoPostal = datos[7];
            
            return new Cliente(id, codigo, nombres, apellidos, telefono, correo, direccion, codigoPostal);
        } catch (Exception e) {
            System.err.println("Error al parsear línea CSV: " + csvLine);
            return null;
        }
    }
}