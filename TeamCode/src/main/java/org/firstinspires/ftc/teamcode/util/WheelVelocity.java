package org.firstinspires.ftc.teamcode.util;

/**
 * Переводит скорость шасси (vx, vy, ω) в угловые скорости четырёх колёс меканума.
 * <p>
 * Матрица 4×3 задаёт кинематику: строки — колёса, столбцы — vx, vy и угловая скорость.
 * Знак (l + b) зависит от расположения колеса: l — половина длины базы, b — половина ширины.
 * Деление на радиус r переводит линейную скорость точки контакта в рад/с колеса.
 */
public class WheelVelocity {
    /** Половина продольной базы, половина поперечной базы и радиус колеса, м. */
    public double l, b, r;
    /** Кинематическая матрица 4×3 и вектор скоростей шасси 3×1. */
    Matrix matrixR = new Matrix(4,3), matrixV = new Matrix(3,1);

    /**
     * Собирает кинематическую матрицу один раз.
     * Порядок строк: левое переднее, правое переднее, левое заднее, правое заднее.
     */
    public WheelVelocity(double l, double b, double r) {
        this.l = l;
        this.b = b;
        this.r = r;
        // [vx, vy, ω] → линейная скорость точки контакта каждого колеса
        matrixR.setMatrixElem(1,1,1);
        matrixR.setMatrixElem(1,2,-1);
        matrixR.setMatrixElem(1,3,-(l+b));
        matrixR.setMatrixElem(2,1,1);
        matrixR.setMatrixElem(2,2,1);
        matrixR.setMatrixElem(2,3,-(l+b));
        matrixR.setMatrixElem(3,1,1);
        matrixR.setMatrixElem(3,2,-1);
        matrixR.setMatrixElem(3,3,(l+b));
        matrixR.setMatrixElem(4,1,1);
        matrixR.setMatrixElem(4,2,1);
        matrixR.setMatrixElem(4,3,(l+b));
        matrixR = matrixR.umnMatrixPerNumber(1/r);
    }

    /**
     * Угловые скорости колёс (рад/с) для заданных vx, vy и ω шасси.
     * Результат — столбец 4×1 в том же порядке, что и строки матрицы.
     */
    public Matrix omegasMat(double vx, double vy, double vo){
        matrixV.setMatrixElem(1,1,vx);
        matrixV.setMatrixElem(2,1,vy);
        matrixV.setMatrixElem(3,1,vo);

        return matrixR.multMatrixPerMatrix(matrixV);
    }
}
