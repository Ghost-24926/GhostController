package org.firstinspires.ftc.teamcode.util;

public class KalmanFilter {
    private Matrix F, B, X_PREVIOUS, X_PREDICT, P_PREVIOUS, P_PREDICT, Q, H, W, X, K, R, Z, P;
    private double u;



    public KalmanFilter(int sizeOfx, int numberOfSensors, double... deltaVars){

        F = new Matrix(sizeOfx, sizeOfx);
        B = new Matrix(sizeOfx,1);
        X_PREVIOUS = new Matrix(sizeOfx,1);
        X_PREDICT = new Matrix(sizeOfx,1);
        P_PREVIOUS = new Matrix(sizeOfx,sizeOfx);
        P_PREDICT = new Matrix(sizeOfx,sizeOfx);
        H = new Matrix(numberOfSensors,sizeOfx);
        W = new Matrix(sizeOfx,1);
        Q = new Matrix(sizeOfx,sizeOfx);
        X = new Matrix(sizeOfx,1);
        K = new Matrix(sizeOfx, numberOfSensors);
        R = new Matrix(numberOfSensors, numberOfSensors);
        Z = new Matrix(numberOfSensors,1);
        P = new Matrix(sizeOfx,sizeOfx);
        for (int i = 1; i <= sizeOfx; i++) {
            P_PREVIOUS.setMatrixElem(i, i, deltaVars[i-1]);
        }
    }
    public void setF(double... vars){
        for(int i = 1; i <= F.getRows(); i++){
            for(int j = 1; j <= F.getCols(); j++){
                F.setMatrixElem(i,j,vars[(i - 1) * F.getCols() + (j - 1)]);
            }
        }
    }
    public void setX(double... vars){
        for(int i = 1; i <= X.getRows(); i++){
            for(int j = 1; j <= X.getCols(); j++){
                X.setMatrixElem(i,j,vars[(i - 1) * X.getCols() + (j - 1)]);
            }
        }
    }
    public void setB(double... vars){
        for(int i = 1; i <= B.getRows(); i++){
            for(int j = 1; j <= B.getCols(); j++){
                B.setMatrixElem(i,j,vars[(i - 1) * B.getCols() + (j - 1)]);
            }
        }
    }
    public void setDeltas(double... vars){
        for(int i = 0; i < vars.length; i++){
            W.setMatrixElem(i+1,1,vars[i]);
        }
    }

    public void setH(double... vars){
        for(int i = 1; i <= H.getRows(); i++){
            for(int j = 1; j <= H.getCols(); j++){
                H.setMatrixElem(i,j,vars[(i - 1) * H.getCols() + (j - 1)]);
            }
        }
    }

    public void setU(double u) {
        this.u = u;
    }

    public void setZ(double... vars){
        for(int i = 1; i <= Z.getRows(); i++){
            for(int j = 1; j <= Z.getCols(); j++){
                Z.setMatrixElem(i,j,vars[(i - 1) * Z.getCols() + (j - 1)]);
            }
        }
    }
    public void setR(double... vars){
        for(int i = 1; i <= R.getRows(); i++){
            for(int j = 1; j <= R.getCols(); j++){
                R.setMatrixElem(i,j,vars[(i - 1) * R.getCols() + (j - 1)]);
            }
        }
    }
    private void predict(){
        X_PREDICT = F.multMatrixPerMatrix(X_PREVIOUS).sumMatrix(B.umnMatrixPerNumber(u));
        Q = Matrix.identity(W.getRows()).multMatrixPerMatrix(W).multMatrixPerMatrix(W.transpose());
        P_PREDICT = F.multMatrixPerMatrix(P_PREVIOUS.multMatrixPerMatrix(F.transpose())).sumMatrix(Q);

    }
    private void correct(){
        K = P_PREDICT.multMatrixPerMatrix(H.transpose()).multMatrixPerMatrix(H.multMatrixPerMatrix(P_PREDICT).multMatrixPerMatrix(H.transpose()).sumMatrix(R).inverse());
        X = X_PREDICT.sumMatrix(K.multMatrixPerMatrix(Z.minusMatrix(H.multMatrixPerMatrix(X_PREDICT))));
        P = P_PREDICT.minusMatrix(K.multMatrixPerMatrix(H).multMatrixPerMatrix(P_PREDICT));
        P_PREVIOUS = P;
        X_PREVIOUS = X;
    }
    public Matrix calculate(){
        predict();
        correct();
        return X;
    }
}
