import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable<Hand> {

	public static final int STRAIGHT_FLUSH_VALUE = 80000;
	public static final int FOUR_OF_A_KIND_VALUE = 70000;
	public static final int FULL_HOUSE_VALUE = 60000;
	public static final int FLUSH_VALUE = 50000;
	public static final int STRAIGHT_VALUE = 40000;
	public static final int THREE_OF_A_KIND_VALUE = 30000;
	public static final int TWO_PAIR_VALUE = 20000;
	public static final int PAIR_VALUE = 10000;
	public static final int HIGH_CARD_VALUE = 0;

	public static final String STRAIGHT_FLUSH_TEXT = "Straight flush";
	public static final String FOUR_OF_A_KIND_TEXT = "Four of a kind";
	public static final String FULL_HOUSE_TEXT = "Full house";
	public static final String FLUSH_TEXT = "Flush";
	public static final String STRAIGHT_TEXT = "Straight";
	public static final String THREE_OF_A_KIND_TEXT = "Three of a kind";
	public static final String TWO_PAIR_TEXT = "Two pair";
	public static final String PAIR_TEXT = "Pair";
	public static final String HIGH_CARD_TEXT = "High card";


	private HashSet<Card> hand;	
	private List<Card> sortedHand;
	private boolean assessed;
	private int handValue;
	private String handRankingCategory;

	private Card highCard, card2, card3, card4, lowestCard;
	private int lowestPairValue, highestPairValue, threeOfAKindValue, fourOfAKindValue;
	private boolean straightFlush;
	private boolean fourOfAKind;
	private boolean fullHouse;
	private boolean flush;
	private boolean straight;
	private boolean threeOfAKind;
	private boolean twoPair;
	private boolean pair;

	public String getHandRankingCategory() {
		return this.handRankingCategory;
	}

	public int getHandValue() {
		return this.handValue;
	}

	public boolean isStraightFlush() {
		return this.straightFlush;
	}

	public boolean isFourOfAKind() {
		return this.fourOfAKind;
	}

	public boolean isFullHouse() {
		return this.fullHouse;
	}

	public boolean isFlush() {
		return this.flush;
	}

	public boolean isStraight() {
		return this.straight;
	}

	public boolean isThreeOfAKind() {
		return this.threeOfAKind;
	}

	public boolean isTwoPair() {
		return this.twoPair;
	}

	public boolean isPair() {
		return this.pair;
	}

	public boolean isAssessed() {
		return this.assessed;
	}

	public Card getHighCard() {
		return this.highCard;
	}

	public Card getCard2() {
		return this.card2;
	}

	public Card getCard3() {
		return this.card3;
	}

	public Card getCard4() {
		return this.card4;
	}

	public Card getLowestCard() {
		return this.lowestCard;
	}

	public int getLowestPairValue() {
		return this.lowestPairValue;
	}

	public int getHighestPairValue() {
		return this.highestPairValue;
	}

	public int getThreeOfAKindValue() {
		return this.threeOfAKindValue;
	}

	public int getFourOfAKindValue() {
		return this.fourOfAKindValue;
	}


	public Hand(Card c1, Card c2, Card c3, Card c4, Card c5) {
		this.handRankingCategory = "";

		this.handValue = 0;
		this.assessed = false;
		this.pair = false;
		this.twoPair = false;
		this.threeOfAKind = false;
		this.straight = false;
		this.flush = false;
		this.fullHouse = false;
		this.fourOfAKind = false;
		this.straightFlush = false;
		this.lowestPairValue = 0;
		this.highestPairValue = 0;
		this.threeOfAKindValue = 0;
		this.fourOfAKindValue = 0;

		this.hand = new HashSet<Card>();
		
		this.hand.add(c1);
		this.hand.add(c2);
		this.hand.add(c3);
		this.hand.add(c4);
		this.hand.add(c5);

		// sort
		sortedHand = new ArrayList<Card>(hand);
		Collections.sort(sortedHand);

		highCard = sortedHand.get(4);
		card2 = sortedHand.get(3);
		card3 = sortedHand.get(2);
		card4 = sortedHand.get(1);
		lowestCard = sortedHand.get(0);

		this.assessed = this.getHandRankingCategories();

		// System.out.println("high: " + highCard.getText());
		// System.out.println("low: " + lowestCard.getText());


	}


	public String getHandText() {
		String handText = "";
		for (Card card : sortedHand) {
			handText += card.getText();
		}
		return handText;
	}

	public String getHandTextWithRankingCategory() {
		String handText = "";
		for (Card card : sortedHand) {
			handText += card.getText();
		}	
		handText += " " + handRankingCategory;	
		return handText;
	}

	@Override
	public int compareTo(Hand anotherHand) {
		// this method assumes both hands are assessed;

		if (this.handValue > anotherHand.getHandValue()) return 1;
		if (this.handValue < anotherHand.getHandValue()) return -1;

		int comparison = this.highCard.compareTo(anotherHand.getHighCard());
		if (comparison != 0) return comparison;

		comparison = this.card2.compareTo(anotherHand.getCard2());
		if (comparison != 0) return comparison;
		
		comparison = this.card3.compareTo(anotherHand.getCard3());
		if (comparison != 0) return comparison;
		
		comparison = this.card4.compareTo(anotherHand.getCard4());
		if (comparison != 0) return comparison;
		
		return this.lowestCard.compareTo(anotherHand.getLowestCard());

	}

	private boolean getHandRankingCategories(){

		if (sortedHand == null || sortedHand.size() != 5) return false;

		int i = 0;
		boolean straightOrFlushImpossible = false;

		while (i<4) {
			boolean match = true;
			int count = 1;
			int j = i;
			while (match && j<4) {
				match = (sortedHand.get(j).compareTo(sortedHand.get(j+1)) == 0);
				if (match) {
					count++;
					straightOrFlushImpossible = true;
				}
				j++;
			}
			if (count == 2) {
				if (this.pair) {
					this.twoPair = true;
					this.pair = false;
					this.highestPairValue = sortedHand.get(i).getRank().getValue();

				} else if (this.threeOfAKind) {
					this.fullHouse = true;
					this.threeOfAKind = false;
					this.lowestPairValue = sortedHand.get(i).getRank().getValue();
				} else {
					this.pair = true;
					this.lowestPairValue = sortedHand.get(i).getRank().getValue();
				}
			} else if (count == 3) {
				if (this.pair) {
					this.fullHouse = true;
					this.pair = false;
					this.threeOfAKindValue = sortedHand.get(i).getRank().getValue();
				} else {
					this.threeOfAKind = true;
					this.threeOfAKindValue = sortedHand.get(i).getRank().getValue();					
				}
			} else if (count == 4) {
				this.fourOfAKind = true;
				this.fourOfAKindValue = sortedHand.get(i).getRank().getValue();
			}
			i += count;
		}

		if (!straightOrFlushImpossible) {
			boolean straightPossible = true;
			boolean flushPossible = true;

			for (i = 0; i<4 && (straightPossible || flushPossible); ++i) {
				straightPossible = straightPossible && (sortedHand.get(i+1).getRank().getValue() - sortedHand.get(i).getRank().getValue() == 1);
				flushPossible = flushPossible && (sortedHand.get(i).getSuit().equals(sortedHand.get(i+1).getSuit()));
			}
			this.straightFlush = (straightPossible && flushPossible);
			this.straight = (!straightFlush) && straightPossible;
			this.flush = (!straightFlush) && flushPossible;
		}

		if (this.straightFlush) {
			this.handValue = STRAIGHT_FLUSH_VALUE;
			this.handRankingCategory = STRAIGHT_FLUSH_TEXT;
		} else if (this.fourOfAKind) {
			this.handValue = FOUR_OF_A_KIND_VALUE + this.fourOfAKindValue;
			this.handRankingCategory = FOUR_OF_A_KIND_TEXT;
		} else if (this.fullHouse) {
			this.handValue = FULL_HOUSE_VALUE + this.threeOfAKindValue*100 + this.lowestPairValue;
			this.handRankingCategory = FULL_HOUSE_TEXT;
		} else if (this.flush) {
			this.handValue = FLUSH_VALUE;
			this.handRankingCategory = FLUSH_TEXT;
		} else if (this.straight) {
			this.handValue = STRAIGHT_VALUE;
			this.handRankingCategory = STRAIGHT_TEXT;
		} else if (this.threeOfAKind) {
			this.handValue = THREE_OF_A_KIND_VALUE + this.threeOfAKindValue;
			this.handRankingCategory = THREE_OF_A_KIND_TEXT;
		} else if (this.twoPair) {
			this.handValue = TWO_PAIR_VALUE + this.highestPairValue*100 + this.lowestPairValue;
			this.handRankingCategory = TWO_PAIR_TEXT;
		} else if (this.pair) {
			this.handValue = PAIR_VALUE + this.lowestPairValue;
			this.handRankingCategory = PAIR_TEXT;
		} else {
			this.handValue = HIGH_CARD_VALUE;
			this.handRankingCategory = HIGH_CARD_TEXT;
		}

		return true;

	}


	public static Hand getBestHand(Hole hole, Board board) {

		if (board.getStage() == Board.STAGE_PRE_FLOP) return null;
		if (board.getStage() == Board.STAGE_FLOP) return new Hand(hole.getCard1(), hole.getCard2(), board.getFlop1(), board.getFlop2(), board.getFlop3());

		int maxBitToCheck = 7;
		if (board.getStage() == Board.STAGE_TURN) maxBitToCheck = 6;
		int maxNumber = 1<<maxBitToCheck;
		Hand bestHand = null;

		Card[] cards = {hole.getCard1(), hole.getCard2(), board.getFlop1(), board.getFlop2(), board.getFlop3(), board.getTurn(), board.getRiver()};

		for (int hands = 0; hands < maxNumber; ++hands) {
			// System.out.println(maxNumber + " " + hands);
			ArrayList<Card> cardsToPlay = new ArrayList<Card>();
			int bitsActive = 0;
			for (int i = 0; i<maxBitToCheck; ++i) {
				if (PokerUtils.getBit(hands, i)) {
					bitsActive++;
					cardsToPlay.add(cards[i]);
				}
			}

			if (bitsActive == 5) {
				// System.out.println(hands);
				// System.out.println(cardsToPlay);
				Hand nextHand = new Hand(cardsToPlay.get(0), cardsToPlay.get(1), cardsToPlay.get(2), cardsToPlay.get(3), cardsToPlay.get(4));
				if (bestHand == null) {
					bestHand = nextHand;
				} else if (nextHand.compareTo(bestHand) > 0) bestHand = nextHand;
			}

		}



		return bestHand;

	}

}