package model;

import java.io.Serializable;

public class RightAscension implements Serializable {
    private final int xxh;
    private final int yym;
    private final int zzs;

    public RightAscension(int xxh, int yym, int zzs) {
        this.xxh = xxh;
        this.yym = yym;
        this.zzs = zzs;
    }

    public int getXxh() {
        return xxh;
    }

    public int getYym() {
        return yym;
    }

    public int getZzs() {
        return zzs;
    }

    @Override
    public String toString() {
        return xxh + "h" + yym + "m" + zzs + "s";
    }
}
