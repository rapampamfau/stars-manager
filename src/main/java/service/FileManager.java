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

    private void delete() {
        //TODO
    }

    public void displayAll() {
        //TODO
    }
}
