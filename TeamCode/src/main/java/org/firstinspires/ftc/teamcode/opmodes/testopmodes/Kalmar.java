package org.firstinspires.ftc.teamcode.opmodes.testopmodes;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.KalmanFilter;

@Configurable
@TeleOp
public class Kalmar extends LinearOpMode {
    GoBildaPinpointDriver pc;
    double x, kalmanX;
    public static double q = 1, r = 1;
    KalmanFilter kalmanFilter = new KalmanFilter(q, r);

    @Override
    public void runOpMode() throws InterruptedException {
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        pc.resetPosAndIMU();
        waitForStart();
        while (opModeIsActive()) {
            pc.update();
            x = pc.getEncoderX();
            kalmanFilter.setU(0);
            kalmanFilter.setZ((int) x);
            kalmanX = kalmanFilter.calculateKalmar();
//            PanelsTelemetry.INSTANCE.getTelemetry().addData("x: ", x);
            PanelsTelemetry.INSTANCE.getTelemetry().addData("kalmanX: ", kalmanX);
            PanelsTelemetry.INSTANCE.getTelemetry().update();
        }

    }
}
