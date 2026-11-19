package org.firstinspires.ftc.teamcode.util;

/**
 * Формулы двухколёсной одометрии: по приращениям энкодеров и курса
 * собирает матрицу, которая связывает смещения колёс с dx, dy и dHeading робота.
 * <p>
 * (x1, y1) и (x2, y2) — положение колёс относительно центра робота,
 * phi1 и phi2 — углы установки роликов, wheelRadius — радиус колеса одометрии.
 */
public class CoordFormules {
    double wheelRadius, x1, y1, x2, y2, phi1, phi2;
    /** matrix1 — кинематика 3×3, matrix2 — вектор измеренных приращений 3×1. */
    Matrix matrix1, matrix2;

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
        matrix2.multMatrixPerMatrix(matrix1);
        matrix2 = matrix2.delMatrixPerNumber(wheelRadius);
    }

}
