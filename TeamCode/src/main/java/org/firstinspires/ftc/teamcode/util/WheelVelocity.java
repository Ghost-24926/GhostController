package org.firstinspires.ftc.teamcode.util;

public class WheelVelocity {
    public double l, b, r;
    Matrix matrixR = new Matrix(4,3), matrixV = new Matrix(3,1), matrixO = new Matrix(4,1);

    public WheelVelocity(double l, double b, double r) {
        this.l = l;
        this.b = b;
        this.r = r;
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
        matrixR.umnMatrixPerNumber(1/r);
    }
    public Matrix omegasMat(double vx, double vy, double vo){
        matrixV.setMatrixElem(1,1,vx);
        matrixV.setMatrixElem(2,1,vy);
        matrixV.setMatrixElem(3,1,vo);

        return matrixR.multMatrixPerMatrix(matrixV);
    }
}
