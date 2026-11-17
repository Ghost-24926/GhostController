package org.firstinspires.ftc.teamcode.util;

public class KalmanFilter {
    final double Q, R;
    int z;
    double u, x, xPrevious, xPredict, pPredict, pPrevious, k, p;

    public KalmanFilter(double q, double r) {
        Q = q;
        R = r;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setU(double u) {
        this.u = u;
    }
    private void predict(){
        xPredict = xPrevious + u;
        pPredict = pPrevious + Q;
    }
    private void update(){
        k = pPredict /(pPredict +R);
        x = xPredict*(1-k)+k*z;
        p = (1-k)*pPredict;

        pPrevious = p;
        xPrevious = x;
    }
    public double calculateKalmar(){
        predict();
        update();
        return x;
    }
}
