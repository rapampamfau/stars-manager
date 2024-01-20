package model;


import service.FileManager;

import java.io.Serializable;
import java.util.List;

public class Star implements Serializable {

    private FileManager fileManager = new FileManager();
    private String name;
    private String catalogName;
    private Declination declination;
    private RightAscension rightAscension;
    private double apparentMagnitude;
    private double absoluteMagnitude;
    private double luminousAgesDistance;
    private String constellation;
    private String hemisphere;
    private double temperature;
    private double mass;
    private int catalogIndex;

    public Star(String name, Declination declination, RightAscension rightAscension,
                double apparentMagnitude, double luminousAgesDistance,
                String constellation, double temperature, double mass) {
        this.name = name;
        this.declination = declination;
        this.rightAscension = rightAscension;
        this.apparentMagnitude = apparentMagnitude;
        this.luminousAgesDistance = luminousAgesDistance;
        this.constellation = constellation;
        this.temperature = temperature;
        this.mass = mass;
        setAbsoluteMagnitude(apparentMagnitude, luminousAgesDistance);
        setHemisphere(declination);
        setCatalogName();
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

    public void setCatalogName() {
        int i = 0;
        List<Star> listOfStar = fileManager.deserializeAndRead();
        if (!listOfStar.isEmpty()) {
            for (Star star : listOfStar) {
                if (constellation.equals(star.getConstellation())) {
                    i++;
                }
            }
            if (apparentMagnitude < findTheBrightestStar()) {
                catalogName = GreekAlphabet.ALPHA.getGreekLetter(0).concat(" ").concat(constellation);
                setCatalogIndex(0);
                for (Star star : listOfStar) {
                    if (star.getConstellation().equals(constellation)) {
                        star.catalogName = GreekAlphabet.ALPHA.getGreekLetter(star.getCatalogIndex() + 1).concat(" ").concat(star.constellation);
                        star.setCatalogIndex(star.getCatalogIndex() + 1);
                    }
                }
            } else {
                catalogName = GreekAlphabet.ALPHA.getGreekLetter(i).concat(" ").concat(constellation);
                setCatalogIndex(i);
            }
        } else {
            catalogName = GreekAlphabet.ALPHA.getGreekLetter(i).concat(" ").concat(constellation);
            setCatalogIndex(i);
        }
    }

    private double findTheBrightestStar() {
        double theBrightest = 15.00;
        for (Star star : fileManager.deserializeAndRead()) {
            if (star.getConstellation().equals(this.constellation)) {
                if (theBrightest > star.getApparentMagnitude())
                    theBrightest = star.getApparentMagnitude();
            }
        }
        return theBrightest;
    }

    public String getConstellation() {
        return constellation;
    }

    public double getApparentMagnitude() {
        return apparentMagnitude;
    }

    public void setCatalogIndex(int catalogIndex) {
        this.catalogIndex = catalogIndex;
    }

    public int getCatalogIndex() {
        return catalogIndex;
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
                "};";
    }
}
