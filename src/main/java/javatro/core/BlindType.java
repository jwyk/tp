package javatro.core;

public enum BlindType {
	SMALL(1.0),
	LARGE(1.5),
	BOSS(2.0);

	private final double multiplier;

	BlindType(double multiplier) {
		this.multiplier = multiplier;
	}

	public double getMultiplier() {
		return multiplier;
	}
}