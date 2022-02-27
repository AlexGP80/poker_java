import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class CountingOuts {
	public static void main (String args[]) {
		DecimalFormat df = new DecimalFormat("#.##");
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Scanner keyboard = new Scanner(System.in);
		df.setRoundingMode(RoundingMode.CEILING);
		long totalTimePracticing = 0L;
		Random rand = new Random(System.currentTimeMillis());
		boolean done = false;
		int aciertos = 0;
		int fallos = 0;
		Double porcentaje = new Double(0);
		int currentStage = 0;


		while (!done) {
			currentStage = rand.nextInt(2) + Board.STAGE_FLOP; // FLOP or TURN
			Deck deck = new Deck(rand);
			Hole hero, villain;
			Hand heroHand, villainHand;
			boolean tie = false;

			// for (int i=0; i<52; ++i) {
			// 	deck.getAndRemoveCard();
			// }

			Hole hole1 = new Hole(deck.getAndRemoveCard(), deck.getAndRemoveCard());
			Hole hole2 = new Hole(deck.getAndRemoveCard(), deck.getAndRemoveCard());
			Board board = new Board();
			board.setFlop(deck.getAndRemoveCard(), deck.getAndRemoveCard(), deck.getAndRemoveCard());
			if (currentStage == Board.STAGE_TURN) board.setTurn(deck.getAndRemoveCard());
			//board.setRiver(deck.getAndRemoveCard());
			Hand hand1 = Hand.getBestHand(hole1, board);
			Hand hand2 = Hand.getBestHand(hole2, board);


			hero = hole1;
			heroHand = hand1;
			villain = hole2;
			villainHand = hand2;

			int comparison = hand1.compareTo(hand2);
			if (comparison > 0) {
				hero = hole2;
				heroHand = hand2;
				villain = hole1;
				villainHand = hand1;
			} else if (comparison == 0) {
				tie = true;
			} 

			if (!tie) {
				// villain.getCard2().setVisible(false);
				int heroOuts = 0;
				int chopOuts = 0;
				int backdoorOuts = 0;
				String backdoorHands = "";
				ArrayList<Card> outCards = new ArrayList<Card>();
				ArrayList<Card> chopCards = new ArrayList<Card>();

				// Todo: backdoor outs
				System.out.println();
				System.out.println("---------------------------------------------------------------------");
				System.out.println(sdf.format(Calendar.getInstance().getTime()));
				System.out.println();
				System.out.println("Hero   : " + hero.getHoleText());
				System.out.println("Villain: " + villain.getHoleText());
				System.out.println("Board  : " + board.getBoardText());
				System.out.println();

				boolean straightIsAnOut = false;
				boolean flushIsAnOut = false;
				boolean straightFlushIsAnOut = false;

				ArrayList<Card> remainingCards = new ArrayList<Card>(deck.getDeck());
				for (Card nextCard: remainingCards) {
					if (currentStage == Board.STAGE_TURN) {
						if (!board.setRiver(nextCard)) board.replaceRiver(nextCard);
					} else if (currentStage == Board.STAGE_FLOP) {
						if (!board.setTurn(nextCard)) board.replaceTurn(nextCard);
					}
					// System.out.println(board.getBoardText());
					heroHand = Hand.getBestHand(hero, board);
					villainHand = Hand.getBestHand(villain, board);

					if (heroHand.compareTo(villainHand) > 0) {
						heroOuts++;
						outCards.add(nextCard);
						if (heroHand.isStraight()) straightIsAnOut = true;
						if (heroHand.isFlush()) flushIsAnOut = true;
						if (heroHand.isStraightFlush()) straightFlushIsAnOut = true;

					} else if (heroHand.compareTo(villainHand) == 0) {
						chopOuts++;
						chopCards.add(nextCard);
						if (heroHand.isStraight()) straightIsAnOut = true;
						if (heroHand.isFlush()) flushIsAnOut = true;
						if (heroHand.isStraightFlush()) straightFlushIsAnOut = true;
					}
				}
				// Backdoor outs
				if (currentStage==Board.STAGE_FLOP) {
					ArrayList<Card> arrayTurn = new ArrayList<Card>(deck.getDeck());
					ArrayList<Card> arrayRiver = new ArrayList<Card>(deck.getDeck());
					Collections.sort(arrayTurn);
					Collections.sort(arrayRiver);
					boolean backdoorStraight = false;
					boolean backdoorFlush = false;
					for (Card turnCard: arrayTurn) {
						if (!board.setTurn(turnCard)) board.replaceTurn(turnCard);
						for (Card riverCard: arrayRiver) {
							if (!turnCard.equals(riverCard)) {
								if (!board.setRiver(riverCard)) board.replaceRiver(riverCard);
								// System.out.println(board.getBoardText());
								heroHand = Hand.getBestHand(hero, board);
								villainHand = Hand.getBestHand(villain, board);
								if ((heroHand.isStraight()&&!straightIsAnOut) || (heroHand.isFlush()&&!flushIsAnOut) || (heroHand.isStraightFlush()&&!straightFlushIsAnOut)) {
									if (heroHand.compareTo(villainHand) > 0) {	
										if ((!backdoorStraight)&&(heroHand.isStraight()||heroHand.isStraightFlush())) {
											backdoorStraight = true;
											backdoorOuts++;
											backdoorHands += "Straight ";
										}
										if ((!backdoorFlush)&&(heroHand.isFlush()||heroHand.isStraightFlush())) {
											backdoorFlush = true;
											backdoorOuts++;
											backdoorHands += "Flush ";
										}
									}
								}
								// System.out.println(turnCard.getText() + riverCard.getText() + " " + backdoorHands);
							}
							if (backdoorFlush&&backdoorStraight) break;
						}
						if (backdoorFlush&&backdoorStraight) break;
					}
					board.backToStage(Board.STAGE_FLOP);
				}





				
				
				Collections.sort(outCards);
				Collections.sort(chopCards);

				
				// System.out.println("Hero best hand   : " + heroHand.getHandTextWithRankingCategory());
				// System.out.println("Villain best hand: " + villainHand.getHandTextWithRankingCategory());
				// System.out.println();

				long startTime = System.nanoTime();

				System.out.print("How many outs does our Hero have to win the hand?: ");
				int outGuess = keyboard.nextInt();
				System.out.print("How many backdoor outs does our Hero have to win the hand?: ");
				int backdoorGuess = keyboard.nextInt();
				System.out.print("How many outs does our Hero have to a chop?: ");
				int chopGuess = keyboard.nextInt();

				long endTime = System.nanoTime();

				long elapsed = (endTime - startTime);  //divide by 1000000 to get milliseconds.
														//divide by 1000000000 to get seconds
														//divide by 60000000000 to get minutes
				totalTimePracticing += elapsed;
				System.out.println("Hero outs: " + heroOuts + ".   Backdoor outs: " + backdoorOuts +".    Chop outs: " + chopOuts + ".");
				String outCardsStr = "";
				String chopCardsStr = "";

				for (Card oc : outCards) {
					outCardsStr += oc.getText();
				}
				for (Card cc : chopCards) {
					chopCardsStr += cc.getText();
				}

				System.out.println("Out cards : " + outCardsStr);
				System.out.println("Chop cards: " + chopCardsStr);
				System.out.println("Backdoor hands: " + backdoorHands);
				System.out.println();

				if (heroOuts == outGuess && chopOuts == chopGuess && backdoorOuts == backdoorGuess) {
					System.out.println("Correct.");
					aciertos++;

				} else {
					System.out.println("WRONG!!! Keep practicing...");
					fallos++;
				}

				porcentaje = ((double)aciertos / (double)(aciertos+fallos))*100;
				System.out.println("Preguntas: " + (aciertos+fallos) + "    Aciertos: " + aciertos + "    Fallos: " + fallos + "    % aciertos: " + df.format(porcentaje) + "%");
				System.out.println("This question was answered in " + df.format((double)elapsed/(double)1000000000L) + "s");
				System.out.println("Average response time: " + df.format(((double)totalTimePracticing/(double)(aciertos+fallos))/(double)1000000000L) + "s");
				System.out.println();

				System.out.print("Enter 0 to quit, any other number to continue: ");
				int myint = keyboard.nextInt();
				if (myint == 0) done = true;
			}


		}
		double minutes = (double)totalTimePracticing / (double)60000000000L;
		Double minutesDouble = new Double(minutes);
		System.out.println("Total practice: " + df.format(minutesDouble) + " minutes.");
		keyboard.close();

	}
}