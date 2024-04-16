import jade.core.Agent;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.core.event.MessageAdapter;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.util.leap.Iterator;

import java.util.HashMap;

import static jade.tools.sniffer.Agent.i;

public class BookBuyerAgent extends Agent {
    // bookTitle - maxPrice
    private HashMap<String, Integer> targetBooks;
    private VPrincipalBuyer gui;

    // bookTitle - price
    private HashMap<String, Integer> purchasedBooks;
    private HashMap<String, Integer> activeAuctions;

    public HashMap<String, Integer> getTargetBooks() {
        return targetBooks;
    }

    public HashMap<String, Integer> getPurchasedBooks() {
        return purchasedBooks;
    }

    public HashMap<String, Integer> getActiveAuctions() {
        return activeAuctions;
    }

    public VPrincipalBuyer getGui() {
        return gui;
    }

    public void addTargetBook(String bookTitle, Integer value){
        this.targetBooks.put(bookTitle, value);
    }

    public void removeTargetBook(String bookTitle){
        this.targetBooks.remove(bookTitle);
    }

    protected void setup() {
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Printout a welcome message
        System.out.println("Hallo! Buyer-agent " + getAID().getName() + " is ready.");

        this.targetBooks = new HashMap<>();
        this.purchasedBooks = new HashMap<>();
        this.activeAuctions = new HashMap<>();
        this.gui = new VPrincipalBuyer(this);


        this.gui.startWindow();

        addBehaviour(new CyclicBehaviour() {
            private int step = 0;
            private MessageTemplate mt;
            private ACLMessage msg;
            private int price = Integer.MAX_VALUE;
            private String bookTitle;
            private AID seller;

            @Override
            public void action() {
                switch (step) {
                    case 0:
                        mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                        msg = myAgent.receive(mt);
                        if (msg != null) {
                            seller = msg.getSender();
                            bookTitle = msg.getConversationId();
                            try {
                                price = Integer.parseInt(msg.getContent());
                            } catch (NumberFormatException ex) {
                                break;
                            }

                            // Recibe el nuevo valor de la puja
                            if (msg.getPerformative() == ACLMessage.CFP) {
                                activeAuctions.put(bookTitle, price);
                                gui.recibirCFP(bookTitle, price);
                                // Si esta interesado y la puja esta menor que su precio maximo
                                if (targetBooks.containsKey(bookTitle) && price <= targetBooks.get(bookTitle)) {
                                    ACLMessage reply = msg.createReply();
                                    reply.setPerformative(ACLMessage.PROPOSE);
                                    reply.setConversationId(bookTitle);
                                    reply.setContent(String.valueOf(price));
                                    myAgent.send(reply);
                                    gui.hacerPuja(bookTitle, price);


                                    mt = MessageTemplate.and(MessageTemplate.and(
                                                    MessageTemplate.MatchConversationId(bookTitle),
                                                    MessageTemplate.MatchSender(seller)),
                                            MessageTemplate.or(
                                                    MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                                                    MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL))
                                    );
                                    step = 1;
                                }
                            } else {
                                break;
                            }
                        } else {
                            block();
                        }
                        break;

                    case 1:
                        // Recibe un accept/reject proposal del mismo libro y vendedor
                        ACLMessage sellerReply = myAgent.receive();
                        if (sellerReply != null) {
                            String bookTitle = sellerReply.getConversationId();
                            int price;
                            try {
                                price = Integer.parseInt(msg.getContent());
                            } catch (NumberFormatException ex) {
                                break;
                            }
                            if (sellerReply.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                                // Va perdiendo la subasta
                                gui.recibirResultado(bookTitle, VSubastaBuyer.PERDIENDO);
                            } else if (sellerReply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                                // Va ganando la subasta
                                gui.recibirResultado(bookTitle, VSubastaBuyer.GANANDO);
                            }
                            step = 0;
                        } else {
                            block();
                        }
                        break;
                }
            }
        });

        addBehaviour(new CyclicBehaviour() {
            private ACLMessage msg;
            private MessageTemplate mt;
            private String bookTitle;
            private int price;

            @Override
            public void action() {
                mt = MessageTemplate.or(
                        MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
                );
                msg = myAgent.receive(mt);

                if(msg!=null){
                    bookTitle = msg.getConversationId();

                    // Ha ganado la subasta
                    if (msg.getPerformative() == ACLMessage.REQUEST) {
                        try {
                            price = Integer.parseInt(msg.getContent());
                        } catch (NumberFormatException ex) {
                            return;
                        }

                        gui.recibirResultado(bookTitle, VSubastaBuyer.GANADOR);
                        if (targetBooks.containsKey(bookTitle) && price <= targetBooks.get(bookTitle)) {
                            removeTargetBook(bookTitle);
                            purchasedBooks.put(bookTitle, price);
                            activeAuctions.remove(bookTitle);
                            gui.updateLists();
                        }

                    // Ha perdido la subasta
                    } else if (msg.getPerformative() == ACLMessage.INFORM) {
                        gui.recibirResultado(bookTitle, VSubastaBuyer.PERDEDOR);
                        activeAuctions.remove(bookTitle);
                        gui.updateLists();
                    }

                } else{
                    block();
                }

            }
        });

    }

    // Put agent clean-up operations here
    protected void takeDown() {
        gui.dispose();
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Printout a dismissal message
        System.out.println("Buyer-agent " + getAID().getName() + " terminating.");
    }
}
