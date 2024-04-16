import jade.core.AID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Auction {
    private final String bookTitle;
    private AID winner;
    private int winningBidValue;
    private HashMap<Integer, Set<AID>> bidders;
    private int auctionValue;

    public Auction(String bookTitle) {
        this.bookTitle = bookTitle;
        this.bidders = new HashMap<>();
        this.auctionValue = 0;
        this.winningBidValue = 0;
    }

    // Getters
    public String getBookTitle() {
        return bookTitle;
    }
    public AID getWinner() {
        return winner;
    }
    public int getWinningBidValue(){ return winningBidValue; }
    public HashMap<Integer, Set<AID>> getBidders() {
        return
                bidders;
    }
    public int getValue() {
        return auctionValue;
    }



    public void addBid(AID bidder, int bidValue){
        // Si no esta inicializado el set para ese valor, se inicializa
        // Significa que es el ganador temporal de la puja
        if(bidders.get(bidValue)==null){
            bidders.put(bidValue, new HashSet<>());
            this.winner = bidder;
            this.winningBidValue = bidValue;
        }

        bidders.get(bidValue).add(bidder);
    }

    public void increaseValue(){
        this.auctionValue++;
    }
}