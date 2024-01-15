package model;

import java.math.BigInteger;

public class Star {
    private String name; // 3 duże litery oraz 4 cyfry
    private String catalogName; // litera alfabetu greckiego oraz nazwa gwiazdozbioru
    private String declination; // współrzędne od 0 do 90 stopni dla półkuli północnej xx stopni yy minut zz.zz sekund
    private String rightAscension; // druga współrzędna od 00 do 24h xx hh yy m zz s
    private String apparentMagnitude; // min -26.74 max 15.00
    private String absoluteMagnitude; // M = m − 5· log10r + 5 m - apparentMagnitude r - luminousAgesDistance / 3.26
    private BigInteger luminousAgesDistance;
    private String constellation;
    private char hemisphere; // N S
    private BigInteger temperature; // min 2000
    private int mass; // min 0.1 max 50

    public Star(String name, String catalogName, String declination, String rightAscension, String apparentMagnitude,
                String absoluteMagnitude, BigInteger luminousAgesDistance, String constellation, char hemisphere,
                BigInteger temperature, int mass) {
        this.name = name;
        this.catalogName = catalogName;
        this.declination = declination;
        this.rightAscension = rightAscension;
        this.apparentMagnitude = apparentMagnitude;
        this.absoluteMagnitude = absoluteMagnitude;
        this.luminousAgesDistance = luminousAgesDistance;
        this.constellation = constellation;
        this.hemisphere = hemisphere;
        this.temperature = temperature;
        this.mass = mass;
    }
}
