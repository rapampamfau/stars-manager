package service;

import model.Star;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_WITH_STARS = "src/main/resources/stars.dat";
    private List<Star> listOfStar = new ArrayList<>();

    public FileManager() {
        Path filePath = Paths.get("src/main/resources/" + FILE_WITH_STARS);
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            Logger.INSTANCE.log(e.getMessage());
        }
    }

    public List<Star> deserializeAndRead() {
        try (
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_WITH_STARS))
        ) {
            return (List<Star>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.INSTANCE.log(e.getMessage());
        }
        return new ArrayList<>();
    }

    public void serializeAndSave(Star star) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(FILE_WITH_STARS);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(star);
            Logger.INSTANCE.log("Object serialized and saved to file.");
        } catch (IOException e) {
            Logger.INSTANCE.log(e.getMessage());
        }
    }

    public void delete(String greekLetter, String constellation) {
        for (Star star : listOfStar) {
            star.deleteStar(greekLetter, constellation);
        }
    }

    public List<Star> findStarsInConstellation(String constellation) {
        List<Star> starsInConstellation = deserializeAndRead();
        for (Star star : listOfStar) {
            if (star.getConstellation().equalsIgnoreCase(constellation)) {
                starsInConstellation.add(star);
            }
        }
        return starsInConstellation;
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
