package org.firstinspires.ftc.teamcode.util;

/**
 * Формулы двухколёсной одометрии: по приращениям энкодеров и курса
 * собирает матрицу, которая связывает смещения колёс с dx, dy и dHeading робота.
 * <p>
 * (x1, y1) и (x2, y2) — положение колёс относительно центра робота,
 * phi1 и phi2 — углы установки роликов, wheelRadius — радиус колеса одометрии.
 */
   //Todo: сделать возврат класса матрицы одной из
public class CoordFormules {
    double wheelRadius, x1, y1, x2, y2, phi1, phi2;
    /** matrix1 — кинематика 3×3, matrix2 — вектор измеренных приращений 3×1. */
    Matrix matrix1, matrix2;

    /** Запоминает геометрию колёс и выделяет матрицы 3×3 и 3×1. */
    public CoordFormules(double wheelRadius, double x1, double y1, double x2, double y2, double phi1, double phi2) {
        this.wheelRadius = wheelRadius;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.phi1 = phi1;
        this.phi2 = phi2;
        matrix1 = new Matrix(3,3);
        matrix2 = new Matrix(3,1);
    }

    /**
     * Заполняет кинематику текущими углами роликов и делит измеренный вектор на радиус,
     * чтобы перейти от дуги колеса к его углу поворота.
     * dx, dy — пройденное колёсами, dHeading — изменение курса.
     */
    //Todo: Клод написал какую-то залупу из того что я понял - перепиши под обновленный класс матриц и перепроверь формулы в т.ч. порядок умножения
    public void calculate(double dx, double dy, double dHeading){
        matrix1.setMatrixElem(1,1,Math.cos(phi1));
        matrix1.setMatrixElem(1,2,Math.sin(phi1));
        matrix1.setMatrixElem(1,3,x1 * Math.sin(phi1) - y1 * Math.cos(phi1));
        matrix1.setMatrixElem(2,1, Math.cos(phi2));
        matrix1.setMatrixElem(2,2, Math.sin(phi2));
        matrix1.setMatrixElem(2,3,x2 * Math.sin(phi2) - y2 * Math.cos(phi2));
        matrix1.setMatrixElem(3,1,0);
        matrix1.setMatrixElem(3,2,0);
        matrix1.setMatrixElem(3,3,wheelRadius);
        matrix2.setMatrixElem(1,1, dx);
        matrix2.setMatrixElem(2,1, dy);
        matrix2.setMatrixElem(3,1, dHeading);
        // БАГ: всегда бросает IllegalArgumentException — умножается 3×1 на 3×3 (у левой 1 столбец, у правой 3 строки).
        // Порядок перепутан: нужно matrix1.multMatrixPerMatrix(matrix2).
        // БАГ: результат умножения выбрасывается — Matrix возвращает новую матрицу и не меняет исходную.
        // Нужно matrix2 = matrix1.multMatrixPerMatrix(matrix2).
        // БАГ (по смыслу): чтобы из приращений колёс получить dx, dy, dHeading робота,
        // кинематическую матрицу надо обращать (matrix1.inverse()), а не умножать на неё.
        matrix1.multMatrixPerMatrix(matrix2);
        // БАГ: делится весь вектор, в том числе dHeading, который в радиусе колеса не измеряется.
        // А элемент (3,3) = wheelRadius в matrix1 уже домножает курс на r, так что масштабы строк не сходятся.
        matrix2 = matrix2.delMatrixPerNumber(wheelRadius);
    }

}
