package org.firstinspires.ftc.teamcode.opmodes.testopmodes;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.KalmanFilter;
import org.firstinspires.ftc.teamcode.util.Matrix;
import org.opencv.core.Mat;

/**
 * Прогон фильтра Калмана по энкодеру X Pinpoint.
 * Состояние одномерное: F и B каждый такт равны dt, управление u = 0,
 * измерение Z — сырые тики. q и r вынесены в Dashboard, но в фильтр пока зашиты константы.
 */
@Configurable
@TeleOp
public class Kalmar extends LinearOpMode {
    GoBildaPinpointDriver pc;
    Matrix kalmanX;
    double x, vx, ax, prevX, prevVX, t, prevT = 0;
    /** Заготовки шума процесса и измерения для подстройки с Driver Station. */
    public static double q = 1, r = 1;
    /** Одна переменная состояния, один датчик, стартовая дисперсия 200. */
    KalmanFilter kalmanFilter = new KalmanFilter(1,1, 200);
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        // H = 1: измерение напрямую равно состоянию.
        kalmanFilter.setH(1);
        kalmanFilter.setDeltas(200);
        kalmanFilter.setR(40000);
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        pc.resetPosAndIMU();
        waitForStart();
        timer.reset();
        while (opModeIsActive()) {
            t = timer.seconds();
            pc.update();
            x = pc.getEncoderX();
            double dt = t - prevT;
            kalmanFilter.setF(dt);
            kalmanFilter.setB(dt);
            kalmanFilter.setU(0);
            kalmanFilter.setZ(x);
            kalmanX = kalmanFilter.calculate();
//            PanelsTelemetry.INSTANCE.getTelemetry().addData("x: ", x);
            for (int i = 1; i <= kalmanX.getRows(); i++){
                for (int j = 1; j <= kalmanX.getCols(); j++){
                    PanelsTelemetry.INSTANCE.getTelemetry().addData("kalmanX "+ i + j , kalmanX.getMatrixElem(i,j));

                }
            }
            PanelsTelemetry.INSTANCE.getTelemetry().addData("x ", x);
            PanelsTelemetry.INSTANCE.getTelemetry().addData("vx ", vx);
            PanelsTelemetry.INSTANCE.getTelemetry().addData("ax ", ax);


            PanelsTelemetry.INSTANCE.getTelemetry().update();

            prevVX = vx;
            prevX = x;
            prevT = t;
        }

    }
}
