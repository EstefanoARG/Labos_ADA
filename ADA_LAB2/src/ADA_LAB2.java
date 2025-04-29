
import java.util.Scanner;


public class ADA_LAB2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        System.out.print("Ingresar el tamano del arreglo: ");
        int n = scanner.nextInt();
       
        if (n <= 0) {
            System.out.println("El tamaño debe ser mayor que 0");
            return;
        }
       
        int[] numeros = new int[n];
        System.out.println("Ingrese los " + n + " numeros:");
        for (int i = 0; i < n; i++) {
            numeros[i] = scanner.nextInt();
        }
       
        int maxSuma = SumaMaxima(numeros);
       
        System.out.println("La suma maxima de la secuencia es: " + maxSuma);
    }
   
    public static int SumaMaxima(int[] arr) {
        int maxActual = 0;
        int maxGlobal = 0;
       
        boolean todosNegativos = true;
        for (int num : arr) {
            if (num >= 0) {
                todosNegativos = false;
                break;
            }
            maxGlobal = Math.max(maxGlobal, num);
        }
       
        if (todosNegativos) {
            return 0;
        }
       
        for (int i = 0; i < arr.length; i++) {
            int sumaActual = maxActual + arr[i];
            if (sumaActual > 0) {
                maxActual = sumaActual;
            } else {
                maxActual = 0;
            }

            if (maxActual > maxGlobal) {
                maxGlobal = maxActual;
            }
        }
       
        return maxGlobal;
    }
}