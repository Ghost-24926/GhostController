package org.firstinspires.ftc.teamcode.util;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.logging.Logger;

/**
 * Считает скорости робота по энкодерам GoBilda Pinpoint.
 * Отдельные таймеры для vx, vy и ω, чтобы каждый вызов опирался на свой прошлый замер.
 * y1 и x2 — плечи одометрических колёс: ими компенсируется вклад поворота в показания энкодера.
 * xSgn и ySgn переворачивают направление, если под включён наоборот.
 */
public class CalculateVelocity {
    GoBildaPinpointDriver pinpoint;
    private static final Logger LOGGER = Logger.getLogger(CalculateVelocity.class.getName());
    double oldHeadingY = 0, oldHeadingX = 0, oldHeadingO = 0;
    /**
     * Последние сырые тики энкодеров X и Y.
     */
    double xCoord;
    double yCoord;
    /**
     * Смещения одометрических колёс от центра: y1 для продольного, x2 для поперечного.
     */
    double y1;
    double x2;
    /**
     * Длина дуги одного тика энкодера, м.
     */
    double metersPerTick;
    double dt;
    long oldTimeY = 0, oldTimeX = 0, oldTimeO = 0;
    int xSgn, ySgn;


    /**
     * pinpoint — драйвер одометрии, radius — радиус колеса пода (м),
     * y1, x2 — плечи колёс от центра (м), xSgn, ySgn — ±1 для направления энкодеров.
     */
    public CalculateVelocity(GoBildaPinpointDriver pinpoint, double radius, double y1, double x2, int xSgn, int ySgn) {
        this.pinpoint = pinpoint;
        this.y1 = y1;
        this.x2 = x2;
        this.xSgn = xSgn;
        this.ySgn = ySgn;
        this.metersPerTick = Math.PI * radius / 2000;
    }
    /**
     * Нормализует угол в радианах в диапазон [-π, π).
     * Нужна, потому что Pinpoint может отдавать накопленный угол
     * (после пары оборотов робота, например, 7.5 рад вместо 1.22).
     */
    public static double normalizeRadians(double angle) {
        angle = (angle + Math.PI) % (2 * Math.PI);
        if (angle < 0) angle += 2 * Math.PI; // % в Java сохраняет знак делимого
        return angle - Math.PI;
    }

    /**
     * Продольная скорость, м/с: прирост энкодера X минус вклад поворота на плече y1.
     */
    public double getVx() {
        int newX = pinpoint.getEncoderX();
        double heading = normalizeRadians(pinpoint.getHeading(AngleUnit.RADIANS));
        long newTime = System.nanoTime();
        dt = (newTime - oldTimeX) * Math.pow(10, -9);
        double wheelOffField = xSgn * (newX - xCoord) * metersPerTick;
        double deltaHeading = heading - oldHeadingX;
        double deltaX = wheelOffField + y1 * deltaHeading;

        xCoord = newX;
        oldHeadingX = heading;
        oldTimeX = newTime;
        return deltaX / dt;
    }

    /**
     * Поперечная скорость, м/с: прирост энкодера Y минус вклад поворота на плече x2.
     */
    public double getVy() {
        int newY = pinpoint.getEncoderY();
        double heading = normalizeRadians(pinpoint.getHeading(AngleUnit.RADIANS));
        long newTime = System.nanoTime();
        dt = (newTime - oldTimeY) * Math.pow(10, -9);

        double wheelOffField = ySgn * (newY - yCoord) * metersPerTick;
        double deltaHeading = heading - oldHeadingY;
        double deltaY = wheelOffField + x2 * deltaHeading;

        yCoord = newY;
        oldHeadingY = heading;
        oldTimeY = newTime;
        return deltaY / dt;

    }

    /**
     * Угловая скорость, рад/с: разность курса Pinpoint, делённая на время между вызовами.
     */
    public double getOmega()  {
        double heading = normalizeRadians(pinpoint.getHeading(AngleUnit.RADIANS));
        long newTime = System.nanoTime();


        dt = (newTime - oldTimeO) * Math.pow(10, -9);
//        for (int i = 0; i < 10; i++){
//            LOGGER.warning("rev pidori");
//        }


        double deltaHeading = heading - oldHeadingO;
        oldHeadingO = heading;

        oldTimeO = newTime;
        return deltaHeading / dt;
    }
}
