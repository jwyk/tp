package javatro.core;

public enum AnteBase {
	LEVEL_1(300),
	LEVEL_2(800),
	LEVEL_3(2000),
	LEVEL_4(5000),
	LEVEL_5(11000),
	LEVEL_6(20000),
	LEVEL_7(35000),
	LEVEL_8(50000);

	private final int value;

	AnteBase(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}