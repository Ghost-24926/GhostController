package org.firstinspires.ftc.teamcode.util;

public class Matrix {
    private double[][] matrix;

    public Matrix(int rows, int cols) {
        matrix = new double[rows][cols];
    }

    public void setMatrixElem(int row, int col, double val) {
        matrix[row-1][col-1] = val;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void sumMatrix(Matrix matrix) {
        for (int i = 0; i < this.matrix.length - 1; i++) {
            for (int j = 0; j < this.matrix[i].length - 1; j++) {
                if (matrix.getMatrix().length - 1 < i) {
                    if (matrix.getMatrix()[i].length - 1 < j) {
                        this.matrix[i][j] += matrix.getMatrix()[i][j];
                    }
                }
            }
        }
    }

    public void minusMatrix(Matrix matrix) {
        for (int i = 0; i <= this.matrix.length - 1; i++) {
            for (int j = 0; j <= this.matrix[i].length - 1; j++) {
                if (matrix.getMatrix().length - 1 < i) {
                    if (matrix.getMatrix()[i].length - 1 < j) {
                        this.matrix[i][j] -= matrix.getMatrix()[i][j];
                    }
                }
            }
        }
    }

    public double umnMatrixPerNumber(double a) {
        for (int i = 0; i <= this.matrix.length - 1; i++) {
            for (int j = 0; j <= this.matrix[i].length - 1; j++) {
                this.matrix[i][j] *= a;
            }
        }
        return a;
    }

    public void delMatrixPerNumber(double a) {
        for (int i = 0; i <= this.matrix.length - 1; i++) {
            for (int j = 0; j <= this.matrix[i].length - 1; j++) {
                this.matrix[i][j] /= a;
            }
        }
    }

    public Matrix multMatrixPerMatrix(Matrix matrix) {
        double[][] a = this.matrix;
        double[][] b = matrix.getMatrix();
        int n = a.length;
        int m = a[0].length;
        int p = b[0].length;

        if (m != b.length) {
            throw new IllegalArgumentException(
                    "Cannot multiply " + n + "x" + m + " by " + b.length + "x" + p);
        }

        double[][] result = new double[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                double s = 0;
                for (int k = 0; k < m; k++) {
                    s += a[i][k] * b[k][j];
                }
                result[i][j] = s;
            }
        }
        Matrix res = new Matrix(n, p);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                res.setMatrixElem(i+1, j+1, result[i][j]);
            }
        }
        return res;
    }
}