import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.awt.*;
import java.util.*;

public class BookSellerAgent extends Agent {
    private VPrincipalSeller gui;

    protected void setup() {
        // Create and show the GUI
        gui = new VPrincipalSeller(this);
        gui.startWindow();
    }

    // Put agent clean-up operations here
    protected void takeDown() {
        gui.dispose();
        System.out.println("Seller-agent " + getAID().getName() + " terminating.");
    }


    public void initAuction(Auction auction){
        addBehaviour(new AuctioneerServer(this, auction));
    }

    private class AuctioneerServer extends TickerBehaviour {
        private final Auction auction;
        private BiddingServer biddingServer;
        private int round;

        public AuctioneerServer(Agent a, Auction auction) {
            super(a, 25000);
            this.auction = auction;
            this.biddingServer = new BiddingServer(myAgent, auction);
            this.round = 0;
            myAgent.addBehaviour(biddingServer);
        }

        @Override
        protected void onTick() {
            // Actualiza los compradores interesados
            HashSet<AID> interestedBuyers = new HashSet<>();

            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("book-purchasing");
            sd.setName(auction.getBookTitle());
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                for (DFAgentDescription dfAgentDescription : result) {
                    interestedBuyers.add(dfAgentDescription.getName());
                }
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }

            // Si en la ultima ronda nadie ha pujado
            /*if((auction.getValue()>auction.getWinningBidValue()) ||
                // o solo ha pujado 1
                (auction.getBidders().get(auction.getWinningBidValue())!=null &&
                        auction.getBidders().get(auction.getWinningBidValue()).size()==1)
            )*/

            if(round!=0 && ((auction.getBidders().get(auction.getValue())==null) ||
                    (auction.getBidders().get(auction.getWinningBidValue())!=null &&
                        auction.getBidders().get(auction.getWinningBidValue()).size()==1)) ){

                ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                inform.setContent("auction-ended");
                inform.setConversationId(auction.getBookTitle());
                for (AID buyer : interestedBuyers) {
                    if(auction.getWinner()==null || !buyer.getName().equals(auction.getWinner().getName())){
                        inform.addReceiver(buyer);
                    }
                }
                myAgent.send(inform);

                ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                request.setContent(String.valueOf(auction.getWinningBidValue()));
                request.setConversationId(auction.getBookTitle());
                request.setReplyWith("request"+System.currentTimeMillis());
                request.addReceiver(auction.getWinner());
                myAgent.send(request);

                System.out.println("-------------------------------------------------------------------------");
                System.out.println("THE AUCTION FOR "+auction.getBookTitle()+" HAS ENDED");
                if(auction.getWinner()!=null){
                    System.out.println("Winner: "+auction.getWinner().getName().split("@")[0]);
                    System.out.println("Price: "+auction.getWinningBidValue());
                }
                System.out.println("-------------------------------------------------------------------------");

                if(auction.getWinner()!=null){
                    gui.addLine(auction.getBookTitle(), auction.getWinner().getName().split("@")[0]+" ha comprado el libro por "+auction.getWinningBidValue()+"€", Color.ORANGE);
                } else{
                    gui.addLine(auction.getBookTitle(), "Nadie ha comprado el libro.", Color.ORANGE);
                }

                gui.removeAuction(auction);
                myAgent.removeBehaviour(biddingServer);
                myAgent.removeBehaviour(this);

                return;
            }

            auction.increaseValue();

            // Send the cfp to all interested buyers
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            for (AID buyer : interestedBuyers) {
                cfp.addReceiver(buyer);
            }

            cfp.setContent(String.valueOf(auction.getValue()));
            cfp.setConversationId(auction.getBookTitle());
            cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
            myAgent.send(cfp);
            gui.addLine(auction.getBookTitle(), "Subiendo la puja a "+cfp.getContent()+"€", Color.LIGHT_GRAY);

            round++;
        }
    }

    private class BiddingServer extends CyclicBehaviour {
        private Auction auction;
        private MessageTemplate mt;

        public BiddingServer(Agent a, Auction auction) {
            super(a);
            this.auction = auction;
            mt = MessageTemplate.and(MessageTemplate.MatchConversationId(auction.getBookTitle()), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
        }

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                int bidPrice;
                try {
                    bidPrice = Integer.parseInt(msg.getContent());
                } catch (NumberFormatException ex) {
                    return;
                }

                System.out.println("Puja de "+bidPrice+" por el libro "+auction.getBookTitle()+" recibida de "+msg.getSender().getName());
                auction.addBid(msg.getSender(), bidPrice);

                ACLMessage reply = msg.createReply();
                reply.setContent(String.valueOf(bidPrice));

                if(auction.getWinner()==msg.getSender()){
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    gui.addLine(auction.getBookTitle(), msg.getSender().getName().split("@")[0]+" ha pujado "+msg.getContent()+"€", Color.GREEN);
                } else {
                    reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    gui.addLine(auction.getBookTitle(), msg.getSender().getName().split("@")[0]+" ha pujado "+msg.getContent()+"€", Color.RED);
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}