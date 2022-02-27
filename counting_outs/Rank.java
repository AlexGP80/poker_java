public class Rank {
	private String symbol;
	private int value;

	public Rank(String symbol, int value) {
		this.symbol = symbol;
		this.value = value;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Rank))return false;
	    Rank otherRank = (Rank)other;
	    if (otherRank.getValue() == this.value) return true;
	    return false;
	}

}