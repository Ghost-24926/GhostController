package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.CalculateVelocity;

/**
 * Замер максимальной угловой скорости.
 * После старта робот 5 секунд крутится на полной мощности, затем в телеметрию
 * пишется ω, снятая один раз до разгона.
 */
@Autonomous
public class MaxVelocityOm extends LinearOpMode {
    /** Приводы меканума: lf/rf/lb/rb. zov — отдельный механизм, здесь не используется. */
    DcMotor lf, rf, lb, rb, zov;
    GoBildaPinpointDriver pc;
    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        zov = hardwareMap.get(DcMotor.class, "zov");
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        // Правая сторона реверсирована, чтобы «вперёд» на всех моторах ехал вперёд.
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Радиус пода 24 мм, плечи 25 и 12 мм, знаки энкодеров +X и −Y.
        CalculateVelocity velocity = new CalculateVelocity(pc, 0.024, 0.025, 0.012, 1, -1);
        waitForStart();
        double vo = velocity.getOmega();

        // Мощности разного знака по диагоналям — чистый поворот.
        rf.setPower(-1);
        lb.setPower(1);
        rb.setPower(-1);
        lf.setPower(1);
        sleep(5000);

        telemetry.addData("vx", vo);
        telemetry.update();
    }
}
