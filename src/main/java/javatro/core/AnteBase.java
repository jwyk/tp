package javatro.core;

public enum AnteBase {
    ANTE_1(300),
    ANTE_2(800),
    ANTE_3(2000),
    ANTE_4(5000),
    ANTE_5(11000),
    ANTE_6(20000),
    ANTE_7(35000),
    ANTE_8(50000);

    private final int value;

    AnteBase(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
