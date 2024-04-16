import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.leap.Iterator;

public class RemoveTargetBook extends OneShotBehaviour {
    BookBuyerAgent bookBuyerAgent;
    String bookTitle;

    public RemoveTargetBook(BookBuyerAgent bookBuyerAgent, String bookTitle) {
        super(bookBuyerAgent);
        this.bookBuyerAgent = bookBuyerAgent;
        this.bookTitle = bookTitle;
    }

    @Override
    public void action() {
        this.bookBuyerAgent.removeTargetBook(bookTitle);
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(bookBuyerAgent.getAID());
        try {
            // Obtiene la descripción actual del agente del DFD
            dfd = DFService.search(myAgent, dfd)[0];

            Iterator services = dfd.getAllServices();
            ServiceDescription servicio = new ServiceDescription();
            while (services.hasNext()) {
                servicio = (ServiceDescription) services.next();
                if (servicio.getType().equals("book-purchasing") && servicio.getName().equals(bookTitle)) {
                    break;
                }
            }

            dfd.removeServices(servicio);

            // Actualiza el DFD con la nueva información
            DFService.modify(myAgent, dfd);

        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        bookBuyerAgent.getGui().updateLists();
    }
}
