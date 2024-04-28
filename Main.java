import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;


// I had added comments everywhere for easy understanding of code and for code testing 

// Somewhere i had also applied kotlin concept as it works here same as in kotlin 
// Enum for card suits
enum Suit {
    SPADE, CLUB, HEART, DIAMOND
}

// Enum for card ranks
enum Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

// Class representing a card
class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    
    public String toString() {
        return rank + " of " + suit;
    }
}

// Class representing a deck of cards
class Deck {
    private List<Card> cards;

    public Deck() {
        initializeDeck();
        shuffle(); // Shuffle the deck upon creation
    }

    // Initialize the deck with all 52 cards
    private void initializeDeck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    // Shuffle the deck using the Fisher-Yates algorithm
    public void shuffle() {
        Random rand = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Collections.swap(cards, i, j);
        }
    }

    // Draw a card from the top of the deck
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    // Check the size of the deck
    public int size() {
        return cards.size();
    }
}

// Comparator class to compare cards based on color, suit, and value
class CardComparator implements java.util.Comparator<Card> {
    private final EnumSet<Suit> redSuits = EnumSet.of(Suit.HEART, Suit.DIAMOND);

    
    public int compare(Card card1, Card card2) {
        // Compare based on color
        boolean isCard1Red = redSuits.contains(card1.getSuit());
        boolean isCard2Red = redSuits.contains(card2.getSuit());
        if (isCard1Red != isCard2Red) {
            return isCard1Red ? -1 : 1; // Red before black
        }

        // If colors are same, compare based on suit
        int suitComparison = card1.getSuit().compareTo(card2.getSuit());
        if (suitComparison != 0) {
            return suitComparison;
        }

        // If suits are same, compare based on rank
        return card1.getRank().compareTo(card2.getRank());
    }
}

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();

        List<Card> drawnCards = new ArrayList<>();
        // Draw 20 random cards from the deck
        for (int i = 0; i < 20; i++) {
            drawnCards.add(deck.drawCard());
        }

        // Sort the drawn cards using custom comparator
        Collections.sort(drawnCards, new CardComparator());

        // Print the sorted drawn cards
        for (Card card : drawnCards) {
            System.out.println(card);
        }
    }
}
