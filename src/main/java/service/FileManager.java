package service;

import model.Star;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager implements Serializable {
    private static final String FILE_WITH_STARS = "src/main/resources/stars.dat";
    private List<Star> listOfStar;

    public FileManager() {
        this.listOfStar = deserializeAndRead();
        if (this.listOfStar == null) {
            this.listOfStar = new ArrayList<>();
        }

        Path filePath = Paths.get(FILE_WITH_STARS);
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            Logger.INSTANCE.log(e.getMessage());
        }
    }

    public List<Star> deserializeAndRead() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_WITH_STARS))) {
            return (List<Star>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            Logger.INSTANCE.log(e.getMessage());
        }
        return new ArrayList<>();
    }

    public void serializeAndSave(List<Star> stars) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_WITH_STARS))) {
            objectOutputStream.writeObject(stars);
            Logger.INSTANCE.log("Object serialized and saved to file.");
        } catch (IOException e) {
            Logger.INSTANCE.log(e.getMessage());
        }
    }

    public void delete(String greekLetter, String constellation) {
        List<Star> stars = deserializeAndRead();
        for (Star star : stars) {
            star.deleteStar(greekLetter, constellation);
        }
    }

    public List<Star> findStarsInConstellation(String constellation) {
        List<Star> starsInConstellation = deserializeAndRead();
        List<Star> starsInGivenConstellation = new ArrayList<>();

        for (Star star : starsInConstellation) {
            if (star.getConstellation().equalsIgnoreCase(constellation)) {
                starsInGivenConstellation.add(star);
            }
        }
        return starsInGivenConstellation;
    }


    public List<Star> findStarsWithinDistance(double distanceInParsecs) {
        List<Star> starsWithinDistance = new ArrayList<>();
        for (Star star : listOfStar) {
            if (star.getLuminousAgesDistance() <= distanceInParsecs) {
                starsWithinDistance.add(star);
            }
        }
        return starsWithinDistance;
    }

    public List<Star> findStarsInTemperatureRange(double minTemperature, double maxTemperature) {
        List<Star> starsInTemperatureRange = new ArrayList<>();
        for (Star star : listOfStar) {
            if (star.getTemperature() >= minTemperature && star.getTemperature() <= maxTemperature) {
                starsInTemperatureRange.add(star);
            }
        }
        return starsInTemperatureRange;
    }

    public List<Star> findStarsInMagnitudeRange(double minMagnitude, double maxMagnitude) {
        List<Star> starsInMagnitudeRange = new ArrayList<>();
        for (Star star : listOfStar) {
            if (star.getApparentMagnitude() >= minMagnitude && star.getApparentMagnitude() <= maxMagnitude) {
                starsInMagnitudeRange.add(star);
            }
        }
        return starsInMagnitudeRange;
    }

    public List<Star> findStarsInHemisphere(String hemisphere) {
        List<Star> starsInHemisphere = new ArrayList<>();
        for (Star star : listOfStar) {
            if (star.getHemisphere().equalsIgnoreCase(hemisphere)) {
                starsInHemisphere.add(star);
            }
        }
        return starsInHemisphere;
    }

    public List<Star> findPotentialSupernovae() {
        List<Star> potentialSupernovae = new ArrayList<>();
        for (Star star : listOfStar) {
            if (star.getMass() > 1.44) {
                potentialSupernovae.add(star);
            }
        }
        return potentialSupernovae;
    }

    public List<Star> getListOfStar() {
        return listOfStar;
    }
}
