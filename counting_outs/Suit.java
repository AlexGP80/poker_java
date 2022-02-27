public class Suit {

	public static final String SPADES_NAME = "spades";
	public static final String HEARTS_NAME = "hearts";
	public static final String CLUBS_NAME = "clubs";
	public static final String DIAMONDS_NAME = "diamonds";
	public static final String SPADES_SYMBOL = "♠";
	public static final String HEARTS_SYMBOL = "♥";
	public static final String CLUBS_SYMBOL = "♣";
	public static final String DIAMONDS_SYMBOL = "♦";

	private String name;
	private String symbol;
	private String workingSymbol;
	private int order;

	public String getSymbol() {
		return this.symbol;
	}

	public int getOrder() {
		return this.order;
	}

	public String getName() {
		return this.name;
	}

	public String getWorkingSymbol() {
		return this.workingSymbol;
	}

	public Suit(String name, String symbol, int order) {
		this.name = name;
		this.workingSymbol = Character.toString(name.charAt(0));
		this.symbol = symbol;
		this.order = order;
	}

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Suit))return false;
	    Suit otherSuit = (Suit)other;
	    if (otherSuit.getName().compareTo(this.getName()) == 0) return true;
	    return false;
	}
}