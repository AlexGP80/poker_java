public class Hole {
	private Card card1, card2;

	public Hole(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
	}

	public Card getCard1() {
		return this.card1;

	}

	public Card getCard2() {
		return this.card2;
	}

	public String getHoleText() {
		return card1.getText() + card2.getText();
	}
}