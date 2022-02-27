import java.util.Iterator;
import java.util.HashSet;
import java.util.Random;

public class Deck {

	private HashSet<Card> deck;
	private Random rand;

	public Deck(Random rand) {
		this.rand = rand;
		deck = new HashSet<Card>();
		Suit spadesSuit = new Suit(Suit.SPADES_NAME, Suit.SPADES_SYMBOL, 1);
		Suit heartsSuit = new Suit(Suit.HEARTS_NAME, Suit.HEARTS_SYMBOL, 2);
		Suit diamondsSuit = new Suit(Suit.DIAMONDS_NAME, Suit.DIAMONDS_SYMBOL, 3);
		Suit clubsSuit = new Suit(Suit.CLUBS_NAME, Suit.CLUBS_SYMBOL, 4);

		Rank twoRank = new Rank("2", 2);
		Rank threeRank = new Rank("3", 3);
		Rank fourRank = new Rank("4", 4);
		Rank fiveRank = new Rank("5", 5);
		Rank sixRank = new Rank("6", 6);
		Rank sevenRank = new Rank("7", 7);
		Rank eightRank = new Rank("8", 8);
		Rank nineRank = new Rank("9", 9);
		Rank tenRank = new Rank("T", 10);
		Rank jackRank = new Rank("J", 11);
		Rank queenRank = new Rank("Q", 12);
		Rank kingRank = new Rank("K", 13);
		Rank aceRank = new Rank("A", 14);

		// i for suit and j for rank
		for (int i=0; i<4; ++i) {
				Suit suit;
				if (i==0) {
					// Spades
					suit = spadesSuit;
				} else if (i==1) {
					// Hearts
					suit = heartsSuit;
				} else if (i==2) {
					// Clubs
					suit = clubsSuit;
				} else {
					// Diamonds
					suit = diamondsSuit;
				}
			for (int j=0; j<13; ++j) {
				Rank rank;
				switch (j) {
					case 0:
						rank = twoRank;
					break;
					case 1:
						rank = threeRank;
					break;
					case 2:
						rank = fourRank;
					break;
					case 3:
						rank = fiveRank;
					break;
					case 4:
						rank = sixRank;
					break;
					case 5:
						rank = sevenRank;
					break;
					case 6:
						rank = eightRank;
					break;
					case 7:
						rank = nineRank;
					break;
					case 8:
						rank = tenRank;
					break;
					case 9:
						rank = jackRank;
					break;
					case 10:
						rank = queenRank;
					break;
					case 11:
						rank = kingRank;
					break;
					default:
						rank = aceRank;
					break;
				}

				Card card = new Card(rank, suit);
//				System.out.println(card.getText() + " added");
				deck.add(card);
//				System.out.println(deck.size());
			}
		}
				// deck.add(new Card(aceRank, heartsSuit)); //petará???
				// System.out.println(deck.size()); // ok no la ha añadido
	}

	public HashSet<Card> getDeck() {
		return this.deck;
	}

	public Card getAndRemoveCard() {
		if (deck.size() == 0) return null;
		int index = this.rand.nextInt(deck.size());
		Iterator<Card> iter = deck.iterator();
		for (int i = 0; i < index; i++) {
		    iter.next();
		}
		Card card = (Card)iter.next();
		deck.remove(card);
		// System.out.println(card.getText() + " removed");
		// System.out.println(deck.size());
		return card;
	}

	public Card getAndRemoveCardAt(int index) {
		if (deck.size() == 0) return null;
		if (index >= deck.size()) return null;
		Iterator<Card> iter = deck.iterator();
		for (int i = 0; i < index; i++) {
		    iter.next();
		}
		Card card = (Card)iter.next();
		deck.remove(card);
		// System.out.println(card.getText() + " removed");
		// System.out.println(deck.size());
		return card;
	}
}