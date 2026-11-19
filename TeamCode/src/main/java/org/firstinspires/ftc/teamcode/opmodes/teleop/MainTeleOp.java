package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.CalculateVelocity;
import org.firstinspires.ftc.teamcode.util.Matrix;
import org.firstinspires.ftc.teamcode.util.WheelVelocity;

/**
 * Основной телеоп меканума.
 * Левый стик задаёт vx/vy, триггеры — поворот. Кнопка A включает и выключает мотор zov.
 * Параллельно с движением в телеметрию пишутся скорости с Pinpoint и угловые скорости колёс.
 */
@TeleOp
public class MainTeleOp extends LinearOpMode {
    DcMotor lf, rf, lb, rb, zov;
    GoBildaPinpointDriver pc;
    /** Сюда кладётся столбец угловых скоростей колёс. */
    Matrix matrix = new Matrix(4, 1);
    /** 0 — zov выключен, 1 — крутится. Нужен, чтобы A работала как тумблер. */
    double a = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        zov = hardwareMap.get(DcMotor.class, "zov");
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addLine("ya gotov sosat'");
        telemetry.update();
        CalculateVelocity velocity = new CalculateVelocity(pc, 0.024, 0.025, 0.012, 1, -1);
        // Полубазы 167.5 и 122.5 мм, радиус колеса 53 мм.
        WheelVelocity wheelVelocity = new WheelVelocity(0.1675, 0.1225, 0.053);


        waitForStart();

        while (opModeIsActive()) {
            pc.update();
            float x = gamepad1.left_stick_x;
            // Стик Y в SDK растёт вниз, для «вперёд» его переворачиваем.
            float y = -gamepad1.left_stick_y;
            float r = -gamepad1.left_trigger + gamepad1.right_trigger;
            move(x, y, r);
            if(a == 0){
                if(gamepad1.a){
                    zov.setPower(-0.8);
                    a = 1;
                    sleep(300);
                }
            }else{
                if(gamepad1.a){
                    zov.setPower(0);
                    a = 0;
                    sleep(300);
                }
            }
            double vx = velocity.getVx();
            double vy = velocity.getVy();
            double vo = velocity.getOmega();
            matrix = wheelVelocity.omegasMat(vx, vy, vo);

            telemetry.addData("vx", vx);
            telemetry.addData("vy", vy);
            telemetry.addData("vo", vo);
            telemetry.addData("r", 1 / wheelVelocity.r);
            telemetry.update();

        }

    }

    /**
     * Смешивание осей меканума без нормализации:
     * каждое колесо получает свою комбинацию страйфа, езды вперёд и поворота.
     */
    public void move(float x, float y, float r) {
        float lfPower = x + y + r;
        float rfPower = -x + y - r;
        float lbPower = -x + y + r;
        float rbPower = x + y - r;

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);
    }
}
