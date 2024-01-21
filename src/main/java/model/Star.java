package model;


import service.FileManager;
import service.Logger;

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

    public void deleteStar(String greekLetter, String constellation) {
        Star starToRemove = null;
        List<Star> listOfStar = fileManager.deserializeAndRead();

        for (Star star : listOfStar) {
            String catalogName = greekLetter.toUpperCase().concat(" ").concat(constellation);
            if (star.getCatalogName().equals(catalogName)) {
                starToRemove = star;
                break;
            }
        }
        if (starToRemove != null) {
            listOfStar.remove(starToRemove);
        }
        else {
            Logger.INSTANCE.log("Star not found");
            return;
        }
        if (starToRemove.getCatalogIndex() == 0) {
            Star newAlpha = findNewAlphaStar(constellation);
            if (newAlpha != null)
            {
                newAlpha.setCatalogIndex(0);
                newAlpha.setCatalogName(GreekAlphabet.ALPHA.getGreekLetter(newAlpha.getCatalogIndex()).concat(" ").concat(newAlpha.getConstellation()));
                for (Star starsToUpdate : listOfStar) {
                    if (starsToUpdate.getConstellation().equals(constellation) && starsToUpdate.getCatalogIndex() > starToRemove.getCatalogIndex()) {
                        starsToUpdate.setCatalogName(GreekAlphabet.ALPHA.getGreekLetter(starsToUpdate.getCatalogIndex()).concat(" ").concat(starsToUpdate.getConstellation()));              }
                }
            }
            else
            {
                Logger.INSTANCE.log("Star has been removed.");
            }
        } else {
            for (Star starsToUpdate : listOfStar) {
                if (starsToUpdate.getConstellation().equals(constellation) && starsToUpdate.getCatalogIndex() > starToRemove.getCatalogIndex()) {
                    starsToUpdate.setCatalogIndex(starsToUpdate.getCatalogIndex() - 1);
                    starsToUpdate.setCatalogName(GreekAlphabet.ALPHA.getGreekLetter(starsToUpdate.getCatalogIndex()).concat(" ").concat(starsToUpdate.getConstellation()));
                }
            }
        }
    }

    private Star findNewAlphaStar(String constellation) {
        Star newAlpha = null;
        double temp = 15.00;
        List<Star> listOfStar = fileManager.deserializeAndRead();

        for (Star star : listOfStar) {
            if (star.getConstellation().equals(constellation)) {
                if (star.getApparentMagnitude() < temp) {
                    temp = star.getApparentMagnitude();
                    newAlpha = star;
                }
            }
        }
        return newAlpha;
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
