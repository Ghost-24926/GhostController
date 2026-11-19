package org.firstinspires.ftc.teamcode.opmodes.testopmodes;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.util.*;
import org.firstinspires.ftc.teamcode.util.Coefs.FeedForwardCoefs;
import org.firstinspires.ftc.teamcode.util.Coefs.PIDCoefficents;

/**
 * Стенд ПИД + feedforward на моторе "l".
 * Коэффициенты static и помечены {@code @Configurable}, их можно крутить с Dashboard.
 * A переключает цель между 0 и 3500 об/мин, B отдельно включает мотор на 0.8 в обход регулятора.
 * Мощность на мотор — сумма выхода ПИД и прямой связи.
 */
@TeleOp
@Configurable
public class PIDOpMode extends LinearOpMode {
    DcMotorEx l;
    /** Тумблеры кнопок A и B: 0 — выкл, 1 — вкл. */
    byte a = 0;
    byte b = 0;
    PIDCoefficents pidCoefficents;
    FeedForwardCoefs feedForwardCoefs;
    FeedForwardCalculate feedForwardCalculate;
    PIDCalculate pidCalculate;
    float target = 0;
    /** kV по умолчанию: 1/312, чтобы около 312 об/мин давать мощность ~1. */
    public static float kI, kP, kD, kV = 1 /312f, kA;

    @Override
    public void runOpMode() throws InterruptedException {
        l = hardwareMap.get(DcMotorEx.class, "l");
        l.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        pidCoefficents = new PIDCoefficents(kP,kI,kD);
        feedForwardCoefs = new FeedForwardCoefs(kV, kA);
        pidCalculate = new PIDCalculate(pidCoefficents);
        feedForwardCalculate = new FeedForwardCalculate(feedForwardCoefs);

        waitForStart();


        while(opModeIsActive()){
            // Коэффициенты с Dashboard применяются каждый цикл.
            pidCoefficents.setkD(kD);
            pidCoefficents.setkI(kI);
            pidCoefficents.setkP(kP);
            feedForwardCoefs.setkA(kA);
            feedForwardCalculate.updateFeedCoefs(feedForwardCoefs);
            pidCalculate.updateCoefs(pidCoefficents);


            if (a == 0){
                if(gamepad1.a) {
                    target = abs(3500);
                    a = 1;
                    sleep(300);
                }
            } else {
                if(gamepad1.a) {
                    target = 0;
                    a = 0;
                    sleep(300);
                }
            }
            if(b == 0){
                if(gamepad1.b){
                    l.setPower(0.8);
                    b = 1;
                    sleep(300);
                }
            }else{
                if(gamepad1.b){
                    l.setPower(0);
                    b = 0;
                    sleep(300);
                }
            }
            // ticks/s → об/мин: делим на 28 тиков/об и на передаточное 19.2, умножаем на 60.
            pidCalculate.updateCurrent((float) (l.getVelocity()/28/19.2*60));
            pidCalculate.updateTarget(target);
            feedForwardCalculate.setTarget(target);
            l.setPower(pidCalculate.calculate() + feedForwardCalculate.calculate());
            telemetry.addData("velocity", l.getVelocity()/28*60);
            telemetry.addData("error", pidCalculate.getError());
            telemetry.update();
        }
    }
}
