import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class AddTargetBook extends OneShotBehaviour {

    private BookBuyerAgent bookBuyerAgent;
    private String newBook;
    private Integer maxPrice;

    public AddTargetBook(BookBuyerAgent a, String newBook, Integer maxPrice) {
        super(a);
        this.bookBuyerAgent = a;
        this.newBook = newBook;
        this.maxPrice = maxPrice;
    }

    @Override
    public void action() {
        bookBuyerAgent.addTargetBook(newBook, maxPrice);
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(bookBuyerAgent.getAID());
        try {
            // Obtiene la descripción actual del agente del DFD
            dfd = DFService.search(myAgent, dfd)[0];

            // Agrega el nuevo servicio al DFD
            ServiceDescription sd = new ServiceDescription();
            sd.setType("book-purchasing");
            sd.setName(newBook);
            dfd.addServices(sd);

            // Actualiza el DFD con la nueva información
            DFService.modify(myAgent, dfd);

        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        bookBuyerAgent.getGui().updateLists();
    }
}
