package SolitaireFrontend.GUI.entity;

public class Card {
    private String rank;
    private String suit;
    private boolean isVisible;

    public Card() {

    }

    public Card(String rank, String suit, boolean isVisible) {
        this.rank = rank;
        this.suit = suit;
        this.isVisible = isVisible;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
