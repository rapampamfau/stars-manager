package model;

import java.io.Serializable;

public class Declination implements Serializable {
    private final int xx;
    private final int yy;
    private final double zz;

    public Declination(int xx, int yy, double zz) {
        this.xx = xx;
        this.yy = yy;
        this.zz = zz;
    }

    public int getXx() {
        return xx;
    }

    public int getYy() {
        return yy;
    }

    public double getZz() {
        return zz;
    }

    @Override
    public String toString() {
        return xx + "º" + yy + "'" + zz + "\"";
    }
}
