package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	private ArrayList<Card> CardsInHand;

	private int HandStrength;
	private int HiHand;
	private int LoHand;
	private int Kicker;
	private boolean bScored = false;
	private boolean hasJokers;

	private boolean Flush;
	private boolean Straight;
	private boolean Ace;
	private boolean JFlush;
	private boolean JStraight;
	private boolean oneJAce;
	private boolean twoJAce;
	private boolean oneJoker;
	private boolean twoJoker;

	public Hand(Deck d) {
		ArrayList<Card> Import = new ArrayList<Card>();
		for (int x = 0; x < 5; x++) {
			Import.add(d.drawFromDeck());
		}
		CardsInHand = Import;
	}

	public ArrayList<Card> getCards() {
		return CardsInHand;
	}

	public int getHandStrength() {
		return HandStrength;
	}

	public int getKicker() {
		return Kicker;
	}

	public int getHighPairStrength() {
		return HiHand;
	}

	public int getLowPairStrength() {
		return LoHand;
	}

	public boolean getAce() {
		return Ace;
	}

	public static Hand EvalHand(ArrayList<Card> SeededHand) {
		Deck d = new Deck();
		Hand h = new Hand(d);
		// Saves the cards that aren't the joker so that the sorted hand doesn't
		// get overwritten
		ArrayList<Card> SavedArray = new ArrayList<Card>();
		SavedArray.add(h.CardsInHand.get(eCardNo.SecondCard.getCardNo()));
		SavedArray.add(h.CardsInHand.get(eCardNo.ThirdCard.getCardNo()));
		SavedArray.add(h.CardsInHand.get(eCardNo.FourthCard.getCardNo()));
		SavedArray.add(h.CardsInHand.get(eCardNo.FifthCard.getCardNo()));

		h.CardsInHand = SeededHand;
		/**
		 * if (h.CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() ==
		 * eRank.JOKER) { for (short i = 0; i <= 3; i++) { eSuit SuitValue =
		 * eSuit.values()[i]; for (short j = 0; j <= 12; j++) { eRank RankValue
		 * = eRank.values()[j]; Card NewCard = new Card(SuitValue, RankValue);
		 * h.CardsInHand.set(eCardNo.FirstCard.getCardNo(), NewCard);
		 * 
		 * Hand EvaluatedHand = EvalHand(h.CardsInHand); int highestHandSoFar =
		 * 0; if (highestHandSoFar <= EvaluatedHand.getHandStrength()) {
		 * highestHandSoFar = EvaluatedHand.getHandStrength(); } h.CardsInHand =
		 * new ; } } } else {}
		 */

		h.EvalHand();

		return h;
	}

	public void EvalHand() {
		// Evaluates if the hand is a flush and/or straight then figures out
		// the hand's strength attributes

		// Sort the cards!
		Collections.sort(CardsInHand, Card.CardRank);

		// Ace Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.ACE) {
			Ace = true;
		}
		// one Joker + Ace
		else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.ACE) {
			oneJAce = true;
		}
		// two Joker + Ace
		else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.ACE) {
			twoJAce = true;
		}

		// checks for any jokers
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				|| CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				|| CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.JOKER
				|| CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == eRank.JOKER
				|| CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.JOKER) {
			hasJokers = true;
		} else {
			hasJokers = false;
		}

		// checks for how many Jokers
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() != eRank.JOKER) {
			oneJoker = true;
		} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER) {
			twoJoker = true;
		}

		// Flush Evaluation
		if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getSuit()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getSuit()) {
			Flush = true;
		} else {
			Flush = false;
		}

		// Joker Flush
		// one joker
		if (hasJokers) {
			if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == eSuit.WILD
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getSuit() == CardsInHand.get(
							eCardNo.FifthCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getSuit() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getSuit() == CardsInHand.get(
							eCardNo.FifthCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getSuit() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getSuit()) {
				JFlush = true;
				// two jokers
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getSuit() == eSuit.WILD
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getSuit() == eSuit.WILD
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getSuit() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getSuit() == CardsInHand.get(
							eCardNo.FifthCard.getCardNo()).getSuit()
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getSuit() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getSuit()) {
				JFlush = true;
			} else {
				JFlush = false;
			}
		}

		// Straight Evaluation
		if (Ace) {
			// Looks for Ace, King, Queen, Jack, 10
			if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.KING
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.JACK
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
				Straight = true;
				// Looks for Ace, 2, 3, 4, 5
			} else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.FIVE) {
				Straight = true;
			} else {
				Straight = false;
			}

		}

		// Looks for straight without Ace
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
				.getRank() == CardsInHand.get(eCardNo.SecondCard.getCardNo())
				.getRank().getRank() + 1
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank().getRank() + 2
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
						.getRank() + 3
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
						.getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank().getRank() + 4) {
			Straight = true;
		} else {
			Straight = false;
		}

		// Strait Evaluation With Jokers and Ace
		// one Joker
		if (hasJokers) {
			if (oneJAce) {
				// looks for variations of Ace,King, Queen, Jack, 10 with the
				// Joker
				// suplementing anything besides Ace
				if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.QUEEN
						&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
								.getRank() == eRank.JACK
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TEN) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo())
						.getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
								.getRank() == eRank.JACK
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TEN) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo())
						.getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
								.getRank() == eRank.QUEEN
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TEN) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo())
						.getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
								.getRank() == eRank.QUEEN
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.JACK) {
					JStraight = true;
				}
			}
			// Checks for variations of Ace, 2, 3, 4, 5 with the Joker
			// suplementing anything besides Ace
			else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FIVE
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.THREE) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FIVE
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FIVE
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == eRank.FOUR
					&& CardsInHand.get(eCardNo.FourthCard.getCardNo())
							.getRank() == eRank.THREE
					&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TWO) {
				JStraight = true;
			}

			// Two Jokers
			else if (twoJAce) {
				// Looks for variaents of Ace, King, Queen, Jack, Ten with the
				// two
				// jokers replaceing anything besides the Ace
				if (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == eRank.JACK
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TEN) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.QUEEN
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TEN) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.QUEEN
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.JACK) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TEN) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.JACK) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.KING
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.QUEEN) {
					JStraight = true;
				}
				// Checks for varients of Ace, 2, 3, 4, 5 with he two Jokers
				// replacing anything besides the Ace
				else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.FIVE
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.FOUR) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.FIVE
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.THREE) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.FOUR
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.THREE) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.FIVE
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TWO) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.FOUR
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TWO) {
					JStraight = true;
				} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo())
						.getRank() == eRank.THREE
						&& CardsInHand.get(eCardNo.FifthCard.getCardNo())
								.getRank() == eRank.TWO) {
					JStraight = true;
				}
			}

			// Looks for Straight with Jokers but No Ace
			// One Joker
			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 2
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 3) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 2
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 4) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 3
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 4) {
				JStraight = true;
			}

			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() + 2
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 3
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 4) {
				JStraight = true;
			}

			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 2
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank().getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 3) {
				JStraight = true;
			}

			// Two Jokers
			else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank() + 2) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank() + 3) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 1
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank() + 4) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 2
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank() + 3) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 2
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank() + 4) {
				JStraight = true;
			} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.SecondCard.getCardNo())
							.getRank() == eRank.JOKER
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank() + 3
					&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank() == CardsInHand
							.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank() + 4) {
				JStraight = true;
			} else {
				JStraight = false;
			}
		}

		// Evaluates the hand type
		// Natural Royal
		if (Straight == true
				&& Flush == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN
				&& Ace) {
			ScoreHand(eHandStrength.NaturalRoyalFlush, 0, 0, 0);
		}

		// Royal(WithJokers)
		// one joker
		else if (JStraight == true
				&& JFlush == true
				&& oneJAce == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		} else if (JStraight == true
				&& JFlush == true
				&& oneJAce == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.JACK) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		}

		// two Jokers
		else if (JStraight == true
				&& JFlush == true
				&& twoJAce == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.TEN) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		}

		else if (JStraight == true
				&& JFlush == true
				&& twoJAce == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.JACK) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		}

		else if (JStraight == true
				&& JFlush == true
				&& twoJAce == true
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == eRank.QUEEN) {
			ScoreHand(eHandStrength.RoyalFlush, 0, 0, 0);
		}

		// Straight Flush
		else if (Straight == true && Flush == true) {
			ScoreHand(eHandStrength.StraightFlush,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}
		// Flush(With Jokers)
		// one Joker
		else if (JStraight == true && JFlush == true && oneJoker == true) {
			ScoreHand(eHandStrength.StraightFlush,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}
		// two Jokers
		else if (JStraight == true && JFlush == true && twoJoker == true) {
			ScoreHand(eHandStrength.StraightFlush,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// 5 of a Kind
		// one joker
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FiveOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}
		// two jokers

		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FiveOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// Four of a Kind

		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		}

		else if (CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank());
		}

		// Four of a kind with Jokers
		// One Joker
		else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		} else if (oneJoker
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank());
		}

		// twoJokers
		else if (twoJoker
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		} else if (twoJoker
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FourOfAKind,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank());
		}

		// Full House
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank(), 0);
		}

		else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.SecondCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0);
		}
		// Full House With Jokers
		// One Joker
		else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank(), 0);
		} else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0);
		} else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0);
		} else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0);
		}
		// Two Jokers
		else if (twoJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0, 0);

		} else if (twoJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0);
		} else if (twoJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.FullHouse,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0);

		}
		// Flush
		else if (Flush) {
			ScoreHand(eHandStrength.Flush,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}
		// Flush with Jokers
		// one
		else if (JFlush && oneJoker) {
			ScoreHand(eHandStrength.Flush,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}
		// two
		else if (JFlush && twoJoker) {
			ScoreHand(eHandStrength.Flush,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// Straight
		else if (Straight) {
			ScoreHand(eHandStrength.Straight,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// jokers
		// one
		else if (JStraight && oneJoker) {
			ScoreHand(eHandStrength.Straight,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// two
		else if (JStraight && twoJoker) {
			ScoreHand(eHandStrength.Straight,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0, 0);
		}

		// Three of a Kind
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.ThreeOfAKind,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank());
		}

		else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.ThreeOfAKind,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.ThreeOfAKind,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank());
		}

		// Three of a Kind With Jokers
		// OneJoker
		else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.ThreeOfAKind,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		}
		// Two Joker
		else if (twoJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.ThreeOfAKind,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		}

		// Two Pair
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()
				&& (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank())) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		} else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()
				&& (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank())) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank());
		} else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank())) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank(),
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank());
		}

		// Two Pair With Jokers
		// oneJoker
		else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		} else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		} else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank());
		}

		// Pair
		else if (CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.SecondCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.Pair,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank());
		} else if (CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.ThirdCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.Pair,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank());
		} else if (CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FourthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.Pair,
					CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank());
		} else if (CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() == CardsInHand
				.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.Pair,
					CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank());
		}
		// Pair with Joker
		// oneJoker
		else if (oneJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.ThirdCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.TwoPair,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		}

		// Two Jokers
		else if (twoJoker
				&& CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank() == eRank.JOKER
				&& CardsInHand.get(eCardNo.ThirdCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FourthCard.getCardNo()).getRank()
				&& CardsInHand.get(eCardNo.FourthCard.getCardNo()).getRank() != CardsInHand
						.get(eCardNo.FifthCard.getCardNo()).getRank()) {
			ScoreHand(eHandStrength.Pair,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.FifthCard.getCardNo()).getRank()
							.getRank());
		}

		else {
			ScoreHand(eHandStrength.HighCard,
					CardsInHand.get(eCardNo.FirstCard.getCardNo()).getRank()
							.getRank(), 0,
					CardsInHand.get(eCardNo.SecondCard.getCardNo()).getRank()
							.getRank());
		}
	}

	private void ScoreHand(eHandStrength hST, int HiHand, int LoHand, int Kicker) {
		this.HandStrength = hST.getHandStrength();
		this.HiHand = HiHand;
		this.LoHand = LoHand;
		this.Kicker = Kicker;
		this.bScored = true;

	}

	/**
	 * Custom sort to figure the best hand in an array of hands
	 */
	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.HandStrength - h1.HandStrength;

			if (result != 0) {
				return result;
			}

			result = h2.HiHand - h1.HiHand;
			if (result != 0) {
				return result;
			}

			result = h2.LoHand = h1.LoHand;
			if (result != 0) {
				return result;
			}

			result = h2.Kicker = h1.Kicker;
			if (result != 0) {
				return result;
			}

			return 0;
		}
	};
	
	//public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand{
		//Not sure how to actually write this one, but it should use the sorting method where it halfs the array each time to find the highest value.
	//};
}
