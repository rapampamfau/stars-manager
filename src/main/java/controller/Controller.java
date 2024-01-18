package controller;

import model.Declination;
import model.RightAscension;
import model.Star;
import service.FileManager;
import service.Logger;
import java.util.Scanner;

public class Controller {
    private static Controller instance;
    private static FileManager fileManager;
    private Scanner scan = new Scanner(System.in);

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void runApp() {
        int option = 0;
        fileManager = new FileManager();

        do {
            showInstructions();
            try {
                option = Integer.parseInt(scan.next());
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a number.");
            }
        } while (option <= 0 || option >= 4);
            switch (option) {
                case 1:
                    Star s = createNewStar();
                    System.out.println(s);
                    break;
                case 2: // deleteStar(catalogName); TODO
                    break;
                case 3: // displayAllStars(); TODO
                    break;
            }
    }

    private void showInstructions() {
        System.out.println(
          """
          Welcome to stars manager, what do you want do do?
          1. Add new star.
          2. Delete star.
          3. Display all stars.
          """
        );
    }

    private Star createNewStar() {
        String name = askForStarName();
        String catalogName = "";
        Declination declination = askForDeclination();
        RightAscension rightAscension = askForRightAscension();
        double apparentMagnitude = askForApparentMagnitude();
        double distance = askForDistance();
        String constellation = askForConstellation();
        double temperature = askForTemperature();
        double mass = askForMass();

        return new Star(name, catalogName, declination, rightAscension, apparentMagnitude, distance,
                constellation, temperature, mass);
    }

    private String askForStarName() {
        String letters;
        String numbers;
        do {
            System.out.println("Enter 3 letters for name: ");
            letters = scan.next();

        } while (letters.length() != 3 || !letters.matches("[a-zA-Z]+"));

        do {
            System.out.println("Enter 4 following numbers for name: ");
            numbers = scan.next();
        } while (numbers.length() != 4 || !numbers.matches("\\d+"));

        return (letters + numbers).toUpperCase();
    }

    private String askForConstellation() {
        String constellation;
        boolean flag = true;
        do {
            System.out.println("Enter what constellation the star is in: ");
            constellation = scan.next();
            if (!constellation.matches("[a-zA-Z]+")) {
                Logger.INSTANCE.log("The constellation must consist only of letters of the English alphabet");
            } else {
                flag = false;
            }
        } while (flag);
        return constellation.toUpperCase();
    }

    private double askForMass() {
        double mass = 0;
        boolean flag = true;

        while (flag) {
            System.out.println("Enter mass of star: ");
            try {
                mass = Double.parseDouble(scan.next());

                if (mass < 0.1) {
                    Logger.INSTANCE.log("Minimum mass cannot be less than 0.1");
                } else if (mass > 50) {
                    Logger.INSTANCE.log("Maximum mass cannot be greater than 50");
                } else {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid mass as a number.");
            }
        }
        return mass;
    }

    private double askForTemperature() {
        double temperature = 0;
        boolean flag = true;

        while (flag) {
            System.out.println("Enter temperature of star: ");
            try {
                temperature = Double.parseDouble(scan.next());

                if (temperature < 2000) {
                    Logger.INSTANCE.log("Minimum temperature cannot be less than 2000ºC");
                } else {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid temperature as a number.");
            }
        }
        return temperature;
    }

    private Declination askForDeclination() {
        int xx = 0;
        int yy = 0;
        double zz = 0;
        boolean xflag = true;
        boolean yflag = true;
        boolean zflag = true;

        while (xflag) {
            System.out.println("Enter declination degrees (xx or -xx): ");
            try {
                xx = Integer.parseInt(scan.next());
                if (xx > 90 || xx < -90) {
                    Logger.INSTANCE.log("Degree to high (max value is 90º or -90º)");
                } else {
                    xflag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid degree as a number.");
            }
        }

        while (yflag) {
            System.out.println("Enter declination minutes (yy or -yy): ");
            try {
                yy = Integer.parseInt(scan.next());
                if (yy > 60) {
                    Logger.INSTANCE.log("Degree to high (max value is 60)");
                } else {
                    yflag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid minutes as a number.");
            }
        }

        while (zflag) {
            System.out.println("Enter declination seconds (zz.zz): ");
            try {
                zz = Double.parseDouble(scan.next());
                if (zz > 60.00) {
                    Logger.INSTANCE.log("Seconds to high (max value is 60.00)");
                } else {
                    zflag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid seconds as a number.");
            }
        }

        return new Declination(xx, yy, zz);
    }

    private RightAscension askForRightAscension() {
        int xxh = 0;
        int yym = 0;
        int zzs = 0;
        boolean xflag = true;
        boolean yflag = true;
        boolean zflag = true;

        while (xflag) {
            System.out.println("Enter right ascension hours (24h format): ");
            try {
                xxh = Integer.parseInt(scan.next());
                if (xxh > 24) {
                    Logger.INSTANCE.log("Hour to high (max value is 24)");
                } else {
                    xflag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid hour as a number.");
            }
        }

        while (yflag) {
            System.out.println("Enter right ascension minutes: ");
            try {
                yym = Integer.parseInt(scan.next());
                if (yym > 60) {
                    Logger.INSTANCE.log("Too much minutes (max value is 60)");
                } else {
                    yflag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid minutes as a number.");
            }
        }

        while (zflag) {
            System.out.println("Enter right ascension seconds: ");
            try {
                zzs = Integer.parseInt(scan.next());
                if (zzs > 60) {
                    Logger.INSTANCE.log("Seconds to high (max value is 60)");
                } else {
                    zflag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid seconds as a number.");
            }
        }

        return new RightAscension(xxh, yym, zzs);
    }

    private double askForApparentMagnitude() {
        double magnitudo = 0;
        boolean flag = true;

        while (flag) {
            System.out.println("Enter apparent magnitude of star: ");
            try {
                magnitudo = Double.parseDouble(scan.next());

                if (magnitudo < -26.74) {
                    Logger.INSTANCE.log("Minimum apparent magnitude cannot be less than -26.74");
                } else if (magnitudo > 15) {
                    Logger.INSTANCE.log("Maximum apparent magnitude cannot be greater than 15.00");
                } else {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid magnitude number.");
            }
        }
        return magnitudo;
    }

    private double askForDistance() {
        double distance = 0;
        boolean flag = true;

        while (flag) {
            System.out.println("Enter distance in luminous ages: ");
            try {
                distance = Double.parseDouble(scan.next());

                if (distance < 0) {
                    Logger.INSTANCE.log("Minimum distance cannot be less than 0");
                } else if (distance > Double.MAX_VALUE) {
                    Logger.INSTANCE.log("Reached max double value");
                } else {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a valid distance as a number.");
            }
        }
        return distance;
    }

}
