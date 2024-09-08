import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("Invalid console arguments");
        }

        double x = 0.5;
        try {
            x = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
        }

        double precession = 0.1;
        try {
            precession = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
        }

        double sum = 0;
        int k = 1;

        double current = calculate(x, k);
        while (Math.abs(current) > precession) {
            sum += current;
            k++;
            current = calculate(x, k);
        }

        System.out.println("Sum: " + sum);
        System.out.println("Last element (not included in sum): " + current);
    }

    public static double calculate(double x, double k) {
        return Math.pow(x, 3 * Math.pow(k, 2));
    }
}