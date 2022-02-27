public class Card implements Comparable<Card> {
	private static final boolean DEFAULT_CARD_VISIBLE_STATE = true;
	private Rank rank;
	private Suit suit;
	private boolean visible;

	public Card (Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		this.visible = DEFAULT_CARD_VISIBLE_STATE;
	}

	public Card (Rank rank, Suit suit, boolean visible) {
		this.rank = rank;
		this.suit = suit;
		this.visible = visible;
	}	

	public Card setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public Rank getRank() {
		return this.rank;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public String getText() {
		if (!this.visible) return "_?";
		String cardText = "";
		if (this.suit.getName().compareTo(Suit.SPADES_NAME)== 0) {
			cardText += Colors.ANSI_BLACK;
		} else if (this.suit.getName().compareTo(Suit.HEARTS_NAME)==0) {
			cardText += Colors.ANSI_RED;
		} else if (this.suit.getName().compareTo(Suit.CLUBS_NAME)==0) {
			cardText += Colors.ANSI_BLACK;
		} else if (this.suit.getName().compareTo(Suit.DIAMONDS_NAME)==0) {
			cardText += Colors.ANSI_RED;
		} else {
			cardText += Colors.ANSI_BLACK;
		}
		cardText += this.rank.getSymbol() + this.suit.getSymbol();
		cardText += Colors.ANSI_RESET;
		return cardText;
	}

	public String getWorkingText() {
		return this.rank.getSymbol() + this.suit.getWorkingSymbol();
	}

	public String getRankText() {
		return this.rank.getSymbol();
	}

	public String getSuitWorkingText() {
		return this.suit.getWorkingSymbol();
	}

	public int getValue() {
		return this.rank.getValue();
	}

	@Override
	public int hashCode() {
		return rank.getValue() * 10^suit.getOrder();
	}

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Card))return false;
	    Card otherCard = (Card)other;
	    boolean isEqual = (otherCard.getSuit().equals(this.getSuit()) && otherCard.getRank().equals(this.getRank()));
	    return isEqual;
	}

	@Override
	public int compareTo(Card anotherCard) {
		if (anotherCard.getValue() > this.getValue()) return -1;
		if (anotherCard.getValue() < this.getValue()) return 1;
		return 0;
	}

}