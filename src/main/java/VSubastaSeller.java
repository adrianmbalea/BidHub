import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/*
 * Created by JFormDesigner on Wed Dec 20 16:19:19 UTC 2023
 */


/**
 * @author adrian
 */
public class VSubastaSeller extends JFrame {
    private Auction auction;
    private BookSellerAgent bookSellerAgent;
    
    public VSubastaSeller(BookSellerAgent bookSellerAgent, Auction auction) {
        this.auction = auction;

        this.bookSellerAgent = bookSellerAgent;
        initComponents();
        
        this.labelSubastaDe.setText("Subasta de "+this.auction.getBookTitle());
        
        // Creas el modelo personalizado
        listModelPujas = new CustomListModel();

        // Asignas el modelo a la lista
        listPujas.setModel(listModelPujas);

        // Configuras el renderer personalizado
        listPujas.setCellRenderer(new ColoredListCellRenderer(listModelPujas));
    }

    public void addLine(String text, Color color){
        listModelPujas.addElement(text, color);

        // Significa que ha cambiado el ganador temporal
        if(color.equals(Color.GREEN)){ 
            labelGanador.setText("Ganador temporal: "+auction.getWinner().getName().split("@")[0]);
        } 
        // Significa que ha finalizado la subasta
        else if(color.equals(Color.ORANGE)){
            if(auction.getWinner()!=null){
                labelGanador.setText("<html>Subasta finalizada.<br>"+auction.getWinner().getName().split("@")[0]+
                        " se ha llevado el libro por "+auction.getWinningBidValue()+"€.</html>");
            } else{
                labelGanador.setText("<html>Subasta finalizada.<br>Nadie ha comprado el libro.</html>");
            }

        }
    }

    private void volver(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Adrián Martínez Balea
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        scrollPanePujas = new JScrollPane();
        listPujas = new JList();
        buttonVolver = new JButton();
        labelSubastaDe = new JLabel();
        labelGanador = new JLabel();

        //======== mainFrame ========
        {
            mainFrame.setBackground(Color.white);
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {
                mainPanel.setBackground(Color.white);
                mainPanel.setForeground(Color.white);
                mainPanel.setFont(new Font("sansserif", Font.PLAIN, 13));

                //======== scrollPanePujas ========
                {

                    //---- listPujas ----
                    listPujas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listPujas.setFont(new Font("sansserif", Font.PLAIN, 16));
                    scrollPanePujas.setViewportView(listPujas);
                }

                //---- buttonVolver ----
                buttonVolver.setText("Volver");
                buttonVolver.setFont(new Font("sansserif", Font.PLAIN, 16));
                buttonVolver.addActionListener(e -> volver(e));

                //---- labelSubastaDe ----
                labelSubastaDe.setText(" Subasta de");
                labelSubastaDe.setFont(new Font("sansserif", Font.BOLD, 26));
                labelSubastaDe.setHorizontalTextPosition(SwingConstants.CENTER);

                //---- labelGanador ----
                labelGanador.setText("Ganador temporal: -");
                labelGanador.setFont(new Font("sansserif", Font.PLAIN, 16));

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(buttonVolver, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                                .addGroup(mainPanelLayout.createParallelGroup()
                                    .addComponent(labelGanador)
                                    .addComponent(scrollPanePujas, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelSubastaDe)))
                            .addGap(0, 36, Short.MAX_VALUE))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(labelSubastaDe)
                            .addGap(36, 36, 36)
                            .addComponent(scrollPanePujas, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(labelGanador)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonVolver)
                            .addContainerGap())
                );
            }

            GroupLayout mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
            mainFrameContentPane.setLayout(mainFrameContentPaneLayout);
            mainFrameContentPaneLayout.setHorizontalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                        .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
            );
            mainFrameContentPaneLayout.setVerticalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addComponent(mainPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    public class CustomListModel extends DefaultListModel<String> {

        private final Map<String, Color> colorMap = new HashMap<>();

        public void addElement(String texto, Color color) {
            // Añadir el elemento a la lista
            super.addElement(texto);

            // Almacenar el color en el mapa
            colorMap.put(texto, color);
        }

        public Color getColor(String texto) {
            return colorMap.get(texto);
        }
    }

    public class ColoredListCellRenderer extends DefaultListCellRenderer {

        private CustomListModel model;

        public ColoredListCellRenderer(CustomListModel model) {
            this.model = model;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Obtener el color asociado al texto
            Color color = model.getColor(value.toString());

            // Configurar el color de fondo de la celda
            if (color != null) {
                renderer.setBackground(color);
            }

            return renderer;
        }
    }


    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle(bookSellerAgent.getAID().getName().split("@")[0]+": Subasta de "+auction.getBookTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Adrián Martínez Balea
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JScrollPane scrollPanePujas;
    private JList listPujas;
    private JButton buttonVolver;
    private JLabel labelSubastaDe;
    private JLabel labelGanador;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private CustomListModel listModelPujas;
}
