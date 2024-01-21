package model;


import service.FileManager;
import service.Logger;

import java.io.Serializable;
import java.util.List;

public class Star implements Serializable {
    private static final long serialVersionUID = 598416251500321405L;
    private FileManager fileManager;
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
        this.fileManager = new FileManager();
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
        List<Star> listOfStar = fileManager.getListOfStar();
        int i = 0;
        if (!listOfStar.isEmpty()) {
            for (Star star : listOfStar) {
                if (this.constellation.equals(star.getConstellation())) {
                    i++;
                }
            }
            if (this.apparentMagnitude < findTheBrightestStar()) {
                setCatalogName(GreekAlphabet.getGreekLetter(0).concat(" ").concat(this.constellation));
                setCatalogIndex(0);
                for (Star star : listOfStar) {
                    if (constellation.equals(star.getConstellation())) {
                        setCatalogName(GreekAlphabet.getGreekLetter(star.getCatalogIndex() + 1).concat(" ").concat(constellation));
                        setCatalogIndex(star.getCatalogIndex() + 1);
                    }
                }
            } else {
                setCatalogName(GreekAlphabet.getGreekLetter(i).concat(" ").concat(constellation));
                setCatalogIndex(i);
            }
        } else {
            setCatalogName(GreekAlphabet.getGreekLetter(i).concat(" ").concat(constellation));
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

    public void deleteStar(String greekLetter, String constellation) {
        Star starToRemove = null;
        List<Star> listOfStar = fileManager.getListOfStar();

        for (Star star : listOfStar) {
            String catalogName = greekLetter.toUpperCase().concat(" ").concat(constellation);
            if (star.getCatalogName().equals(catalogName)) {
                starToRemove = star;
                break;
            }
        }

        if (starToRemove != null) {
            int removedIndex = starToRemove.getCatalogIndex();
            listOfStar.remove(starToRemove);
            fileManager.serializeAndSave(listOfStar);
            updateCatalogNames(listOfStar, constellation, removedIndex);
            Logger.INSTANCE.log("Star removed successfully.");
        } else {
            Logger.INSTANCE.log("Star not found");
        }
    }

    private void updateCatalogNames(List<Star> listOfStar, String constellation, int removedIndex) {
        for (Star star : listOfStar) {
            if (star.getConstellation().equals(constellation) && star.getCatalogIndex() > removedIndex) {
                star.setCatalogIndex(star.getCatalogIndex() - 1);
                star.setCatalogName(GreekAlphabet.getGreekLetter(star.getCatalogIndex()) + " " + star.getConstellation());
            }
        }
        fileManager.serializeAndSave(listOfStar);
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

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public double getLuminousAgesDistance() {
        return luminousAgesDistance;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getHemisphere() {
        return hemisphere;
    }

    public double getMass() {
        return mass;
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
