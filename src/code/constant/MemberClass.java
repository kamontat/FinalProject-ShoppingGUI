package code.constant;

/**
 * @author kamontat
 * @since 1/6/59 - 19:48
 */
public enum MemberClass {
	NONE("None"),
	GREEN("Green"),
	SILVER("Silver"),
	GOLD("Gold");

	private String name;
	private double discount;

	private MemberClass(String name) {
		this.name = name;
		if (name.equals("Gold")) {
			discount = 0.2;
		} else if (name.equals("Silver")) {
			discount = 0.1;
		} else if (name.equals("Green")) {
			discount = 0.05;
		} else {
			discount = 0;
		}
	}

	public static MemberClass checkMember(String className) {
		for (MemberClass name : MemberClass.values()) {
			if (name.getName().equals(className)) return name;
		}
		System.err.println("Don't have " + className + " class yet.");
		return null;
	}

	public String getName() {
		return name;
	}

	public double getDiscount() {
		return discount;
	}
}
