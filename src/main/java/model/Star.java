package model;

public class Star {
    private String name;
    private String catalogName; // litera alfabetu greckiego oraz nazwa gwiazdozbioru
    private Declination declination;
    private RightAscension rightAscension;
    private double apparentMagnitude;
    private double absoluteMagnitude; // M = m − 5· log10r + 5 m - apparentMagnitude r - luminousAgesDistance / 3.26
    private double luminousAgesDistance;
    private String constellation;
    private String hemisphere;
    private double temperature;
    private double mass;

    public Star(String name, String catalogName, Declination declination, RightAscension rightAscension,
                double apparentMagnitude, double luminousAgesDistance,
                String constellation, double temperature, double mass) {
        this.name = name;
        this.catalogName = catalogName;
        this.declination = declination;
        this.rightAscension = rightAscension;
        this.apparentMagnitude = apparentMagnitude;
        this.luminousAgesDistance = luminousAgesDistance;
        this.constellation = constellation;
        this.temperature = temperature;
        this.mass = mass;
        setAbsoluteMagnitude(apparentMagnitude, luminousAgesDistance);
        setHemisphere(declination);
    }

    private void setHemisphere(Declination declination) {
        if (declination.getXx() > 0) {
            this.hemisphere = "NORTH";
        } else if (declination.getXx() < 0) {
            this.hemisphere = "SOUTH";
        } else {
            this.hemisphere = "BOTH";
        }
    }

    private void setAbsoluteMagnitude(double m, double r) {
        this.absoluteMagnitude = m - 5 * Math.log10(r) + 5;
    }

    @Override
    public String toString() {
        return "Star{" +
                "name='" + name + '\'' +
                ", catalogName='" + catalogName + '\'' +
                ", declination=" + declination +
                ", rightAscension='" + rightAscension + '\'' +
                ", apparentMagnitude=" + apparentMagnitude +
                ", absoluteMagnitude=" + absoluteMagnitude +
                ", luminousAgesDistance=" + luminousAgesDistance +
                ", constellation='" + constellation + '\'' +
                ", hemisphere='" + hemisphere + '\'' +
                ", temperature=" + temperature + "ºC" +
                ", mass=" + mass +
                '}';
    }
}
