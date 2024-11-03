import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Не указан файл с данными.");
            }

            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);

            if (!scanner.hasNext()) {
                throw new IllegalArgumentException("Файл пуст.");
            }

            int n;
            try {
                // Чтение размерности матрицы
                n = scanner.nextInt();
                if (n <= 0) {
                    throw new IllegalArgumentException("Размер матрицы должен быть положительным числом.");
                }
            } catch (InputMismatchException e) {
                throw new IllegalArgumentException("Неверный формат ввода размерности матрицы. Ожидалось положительное целое число.");
            }

            double[][] a = new double[n][n];
            double[] b = new double[n];

            int countElements = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!scanner.hasNext()) {
                        throw new IllegalArgumentException("Недостаточно данных для ввода матрицы.");
                    }
                    try {
                        a[i][j] = scanner.nextDouble();
                    } catch (InputMismatchException e) {
                        throw new IllegalArgumentException("Неверный формат данных. Ожидалось число.");
                    }
                    countElements++;
                }
                if (!scanner.hasNext()) {
                    throw new IllegalArgumentException("Недостаточно данных для ввода вектора свободных членов.");
                }
                try {
                    b[i] = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    throw new IllegalArgumentException("Неверный формат данных. Ожидалось число.");
                }
                countElements++;
            }

            if (scanner.hasNext()) {
                throw new IllegalArgumentException("В файле содержится больше данных, чем нужно.");
            }

            scanner.close();

            // Проверка на треугольную матрицу
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (a[i][j] != 0) {
                        throw new IllegalArgumentException("Матрица не является треугольной.");
                    }
                }
            }

            // Проверка на вырожденность матрицы
            boolean isDegenerate = false;
            for (int i = 0; i < n; i++) {
                if (a[i][i] == 0) {
                    isDegenerate = true;
                    break;
                }
            }

            if (isDegenerate) {
                System.out.println("Матрица вырожденная. Система имеет бесконечно много решений или не имеет решений.");
                return;
            }

            double[] X = solveUpperTriangular(a, b, n);

            System.out.println("Решение системы:");
            for (int i = 0; i < n; i++) {
                System.out.printf("x%d = %.6f\n", (i + 1), X[i]);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double[] solveUpperTriangular(double[][] a, double[] b, int n) {
        double[] x = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += a[i][j] * x[j];
            }

            x[i] = (b[i] - sum) / a[i][i];
        }

        return x;
    }
}