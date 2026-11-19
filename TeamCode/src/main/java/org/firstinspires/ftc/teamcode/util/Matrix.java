
package org.firstinspires.ftc.teamcode.util;

public class Matrix {
    private final double[][] matrix;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Matrix size must be positive: " + rows + "x" + cols);
        }
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];
    }

    /** Создаёт матрицу из готового массива (массив копируется). */
    public Matrix(double[][] data) {
        this(data.length, data[0].length);
        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
            System.arraycopy(data[i], 0, matrix[i], 0, cols);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /** Индексы с 1, как и раньше. Возвращает эту же матрицу, чтобы можно было писать цепочкой. */
    public Matrix setMatrixElem(int row, int col, double val) {
        matrix[row - 1][col - 1] = val;
        return this;
    }

    /** Индексы с 1. */
    public double getMatrixElem(int row, int col) {
        return matrix[row - 1][col - 1];
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public Matrix sumMatrix(Matrix other) {
        checkSameSize(other, "add");
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.matrix[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return res;
    }

    public Matrix minusMatrix(Matrix other) {
        checkSameSize(other, "subtract");
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.matrix[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return res;
    }

    public Matrix umnMatrixPerNumber(double a) {
        Matrix res = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.matrix[i][j] = this.matrix[i][j] * a;
            }
        }
        return res;
    }

    public Matrix delMatrixPerNumber(double a) {
        if (a == 0) {
            throw new ArithmeticException("Division of matrix by zero");
        }
        return umnMatrixPerNumber(1.0 / a);
    }

    public Matrix multMatrixPerMatrix(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException(
                    "Cannot multiply " + rows + "x" + cols + " by " + other.rows + "x" + other.cols);
        }
        Matrix res = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double s = 0;
                for (int k = 0; k < this.cols; k++) {
                    s += this.matrix[i][k] * other.matrix[k][j];
                }
                res.matrix[i][j] = s;
            }
        }
        return res;
    }

    public Matrix transpose() {
        Matrix res = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.matrix[j][i] = this.matrix[i][j];
            }
        }
        return res;
    }

    /** Обратная матрица (A^-1) методом Гаусса–Жордана с выбором ведущего элемента. */
    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                    "Only square matrices can be inverted, got " + rows + "x" + cols);
        }
        int n = rows;
        final double EPS = 1e-12;

        // Рабочая копия A и единичная матрица, которая превратится в A^-1
        double[][] a = new double[n][n];
        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, a[i], 0, n);
            inv[i][i] = 1.0;
        }

        for (int col = 0; col < n; col++) {
            // Ищем строку с максимальным по модулю элементом в столбце
            int pivot = col;
            for (int r = col + 1; r < n; r++) {
                if (Math.abs(a[r][col]) > Math.abs(a[pivot][col])) {
                    pivot = r;
                }
            }
            if (Math.abs(a[pivot][col]) < EPS) {
                throw new ArithmeticException("Matrix is singular (determinant = 0), cannot invert");
            }

            // Меняем строки местами
            double[] tmp = a[col]; a[col] = a[pivot]; a[pivot] = tmp;
            tmp = inv[col]; inv[col] = inv[pivot]; inv[pivot] = tmp;

            // Делим ведущую строку, чтобы на диагонали была 1
            double p = a[col][col];
            for (int j = 0; j < n; j++) {
                a[col][j] /= p;
                inv[col][j] /= p;
            }

            // Обнуляем столбец во всех остальных строках
            for (int r = 0; r < n; r++) {
                if (r == col) continue;
                double f = a[r][col];
                if (f == 0) continue;
                for (int j = 0; j < n; j++) {
                    a[r][j] -= f * a[col][j];
                    inv[r][j] -= f * inv[col][j];
                }
            }
        }

        return new Matrix(inv);
    }

    private void checkSameSize(Matrix other, String op) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException(
                    "Cannot " + op + " " + rows + "x" + cols + " and " + other.rows + "x" + other.cols);
        }
    }
    public static Matrix identity(int n) {
        Matrix res = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            res.matrix[i][i] = 1.0;
        }
        return res;
    }
}
