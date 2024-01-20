package model;

public enum GreekAlphabet {
    ALPHA("ALPHA", 1),
    BETA("BETA", 2),
    GAMMA("GAMMA", 3),
    DELTA("DELTA", 4),
    EPSILON("EPSILON", 5),
    ZETA("ZETA", 6),
    ETA("ETA", 7),
    THETA("THETA", 8),
    IOTA("IOTA", 9),
    KAPPA("KAPPA", 10),
    LAMBDA("LAMBDA", 11),
    MU("MU", 12),
    NU("NU", 13),
    XI("XI", 14),
    OMICRON("OMICRON", 15),
    PI("PI", 16),
    RHO("RHO", 17),
    SIGMA("SIGMA", 18),
    TAU("TAU", 19),
    UPSILON("UPSILON", 20),
    PHI("PHI", 21),
    CHI("CHI", 22),
    PSI("PSI", 23),
    OMEGA("OMEGA", 24);

    GreekAlphabet(String letter,  int index) {
    }
    public String getGreekLetter(int i ){
        GreekAlphabet[] greekAlphabets = GreekAlphabet.values();
        return greekAlphabets[i].toString();
    }
}
