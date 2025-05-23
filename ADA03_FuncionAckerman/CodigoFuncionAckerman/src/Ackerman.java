import java.util.Scanner;
public class Ackerman {

    public static int ackerman(int m, int n) {
        java.util.Stack<Integer> stack = new java.util.Stack<>();

        stack.push(m);

        while (!stack.isEmpty()) {
            m = stack.pop();

            if (m == 0) {
                n = n + 1;
            } else if (n == 0) {
                stack.push(m - 1);
                n = 1;
            } else {
                stack.push(m - 1);
                stack.push(m);
                n = n - 1;
            }
        }

        return n;
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        System.out.print("Ingresar primer valor: ");
        int m= scanner.nextInt();
        System.out.print("Ingresar segundo valor: ");
        int n= scanner.nextInt();

        int resultado = ackerman(m, n);
        System.out.println("Ackermann(" + m + ", " + n + ") = " + resultado);
    }    
}
