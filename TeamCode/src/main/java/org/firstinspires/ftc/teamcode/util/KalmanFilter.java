package org.firstinspires.ftc.teamcode.util;

/**
 * Линейный фильтр Калмана: прогноз состояния по модели и поправка по зашумлённым измерениям.
 * <p>
 * Размерность состояния — sizeOfx, число датчиков — numberOfSensors.
 * Матрицы заполняются построчно через varargs (индексы с 1, как у {@link Matrix}).
 * <ul>
 *   <li>F — переход состояния, B — влияние управления u</li>
 *   <li>H — как состояние видно в измерениях Z</li>
 *   <li>Q — шум модели (строится из вектора W), R — шум датчиков</li>
 *   <li>P — ковариация оценки, K — коэффициент Калмана</li>
 * </ul>
 */
public class KalmanFilter {
    private Matrix F, B, X_PREVIOUS, X_PREDICT, P_PREVIOUS, P_PREDICT, Q, H, W, X, K, R, Z, P;
    /**
     * Скалярное управление на текущем такте.
     */
    private double u;


    /**
     * Выделяет все матрицы. deltaVars — начальная дисперсия по диагонали P
     * (насколько мы не уверены в стартовом состоянии).
     */
    public KalmanFilter(int sizeOfx, int numberOfSensors, double... deltaVars) {

        F = new Matrix(sizeOfx, sizeOfx);
        B = new Matrix(sizeOfx, 1);
        X_PREVIOUS = new Matrix(sizeOfx, 1);
        X_PREDICT = new Matrix(sizeOfx, 1);
        P_PREVIOUS = new Matrix(sizeOfx, sizeOfx);
        P_PREDICT = new Matrix(sizeOfx, sizeOfx);
        H = new Matrix(numberOfSensors, sizeOfx);
        W = new Matrix(sizeOfx, 1);
        Q = new Matrix(sizeOfx, sizeOfx);
        X = new Matrix(sizeOfx, 1);
        K = new Matrix(sizeOfx, numberOfSensors);
        R = new Matrix(numberOfSensors, numberOfSensors);
        Z = new Matrix(numberOfSensors, 1);
        P = new Matrix(sizeOfx, sizeOfx);
        if (deltaVars.length == sizeOfx) {
            for (int i = 1; i <= sizeOfx; i++) {
                P_PREVIOUS.setMatrixElem(i, i, deltaVars[i - 1]);
            }
        } else {
            throw new RuntimeException("deltas must be sme size as x");
        }
    }

    /**
     * Матрица перехода F, элементы идут по строкам.
     */
    public void setF(double... vars) {
        for (int i = 1; i <= F.getRows(); i++) {
            for (int j = 1; j <= F.getCols(); j++) {
                F.setMatrixElem(i, j, vars[(i - 1) * F.getCols() + (j - 1)]);
            }
        }
    }

    /**
     * Начальное или внешнее состояние X (столбец).
     */
    public void setX(double... vars) {
        for (int i = 1; i <= X_PREVIOUS.getRows(); i++) {
            for (int j = 1; j <= X_PREVIOUS.getCols(); j++) {
                X_PREVIOUS.setMatrixElem(i, j, vars[(i - 1) * X_PREVIOUS.getCols() + (j - 1)]);
            }
        }
    }

    /**
     * Матрица управления B.
     */
    public void setB(double... vars) {
        for (int i = 1; i <= B.getRows(); i++) {
            for (int j = 1; j <= B.getCols(); j++) {
                B.setMatrixElem(i, j, vars[(i - 1) * B.getCols() + (j - 1)]);
            }
        }
    }

    /**
     * Стандартные отклонения шума процесса: из них потом собирается Q = I · W · Wᵀ.
     */
    public void setDeltas(double... vars) {
        for (int i = 0; i < vars.length; i++) {
            W.setMatrixElem(i + 1, 1, vars[i]);
        }
    }

    /**
     * Матрица наблюдения H.
     */
    public void setH(double... vars) {
        for (int i = 1; i <= H.getRows(); i++) {
            for (int j = 1; j <= H.getCols(); j++) {
                H.setMatrixElem(i, j, vars[(i - 1) * H.getCols() + (j - 1)]);
            }
        }
    }

    /**
     * Скалярное управление u на текущем такте.
     */
    public void setU(double u) {
        this.u = u;
    }

    /**
     * Вектор измерений с датчиков.
     */
    public void setZ(double... vars) {
        for (int i = 1; i <= Z.getRows(); i++) {
            for (int j = 1; j <= Z.getCols(); j++) {
                Z.setMatrixElem(i, j, vars[(i - 1) * Z.getCols() + (j - 1)]);
            }
        }
    }

    /**
     * Ковариация шума измерений R.
     */
    public void setR(double... vars) {
        for (int i = 1; i <= R.getRows(); i++) {
            for (int j = 1; j <= R.getCols(); j++) {
                R.setMatrixElem(i, j, vars[(i - 1) * R.getCols() + (j - 1)]);
            }
        }
    }

    /**
     * Прогноз: X⁻ = F·X + B·u, P⁻ = F·P·Fᵀ + Q.
     */
    private void predict() {
        X_PREDICT = F.multMatrixPerMatrix(X_PREVIOUS).sumMatrix(B.umnMatrixPerNumber(u));
        Q = Matrix.identity(W.getRows()).multMatrixPerMatrix(W).multMatrixPerMatrix(W.transpose());

        P_PREDICT = F.multMatrixPerMatrix(P_PREVIOUS.multMatrixPerMatrix(F.transpose())).sumMatrix(Q);

    }

    /**
     * Коррекция по измерению.
     * K = P⁻·Hᵀ·(H·P⁻·Hᵀ + R)⁻¹,
     * X = X⁻ + K·(Z − H·X⁻), P = (I − K·H)·P⁻ в форме P⁻ − K·H·P⁻.
     * Результат становится предыдущим состоянием для следующего такта.
     */
    private void correct() {
        K = P_PREDICT.multMatrixPerMatrix(H.transpose()).multMatrixPerMatrix(H.multMatrixPerMatrix(P_PREDICT).multMatrixPerMatrix(H.transpose()).sumMatrix(R).inverse());
        X = X_PREDICT.sumMatrix(K.multMatrixPerMatrix(Z.minusMatrix(H.multMatrixPerMatrix(X_PREDICT))));
        P = Matrix.identity(P.getCols()).minusMatrix(K.multMatrixPerMatrix(H)).multMatrixPerMatrix(P_PREDICT).multMatrixPerMatrix(Matrix.identity(P.getCols()).minusMatrix(K.multMatrixPerMatrix(H)).transpose()).sumMatrix(K.multMatrixPerMatrix(R).multMatrixPerMatrix(K.transpose()));
        P_PREVIOUS = P;
        X_PREVIOUS = X;
    }

    /**
     * Один такт фильтра: прогноз, затем коррекция. Возвращает оценку состояния X.
     */
    public Matrix calculate() {
        predict();
        correct();
        return X;
    }
}
