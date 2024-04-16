import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
/*
 * Created by JFormDesigner on Wed Dec 20 16:19:19 UTC 2023
 */


/**
 * @author adrian
 */
public class VPrincipalBuyer extends JFrame {
    private BookBuyerAgent bookBuyerAgent;
    private HashMap<String, VSubastaBuyer> vSubastasBuyers;

    public VPrincipalBuyer(BookBuyerAgent bookBuyerAgent) {
        this.bookBuyerAgent = bookBuyerAgent;
        this.vSubastasBuyers = new HashMap<>();

        initComponents();
        // Creamos los modelos de las listas
        this.listModelLibrosInteresado = new DefaultListModel<>();
        this.listModelLibrosComprados = new DefaultListModel<>();
        this.listModelSubastasActivas = new DefaultListModel<>();

        // Asignamos los modelos de las listas
        this.listLibrosInteresado.setModel(this.listModelLibrosInteresado);
        this.listLibrosComprados.setModel(this.listModelLibrosComprados);
        this.listSubastasActivas.setModel(this.listModelSubastasActivas);

        updateLists();
    }

    public void updateLists(){
        this.listModelLibrosInteresado.removeAllElements();
        this.listModelLibrosComprados.removeAllElements();
        this.listModelSubastasActivas.removeAllElements();

        for(String book: this.bookBuyerAgent.getTargetBooks().keySet()){
            listModelLibrosInteresado.addElement(book+" ("+this.bookBuyerAgent.getTargetBooks().get(book)+" € )");
        }

        for(String book: this.bookBuyerAgent.getPurchasedBooks().keySet()){
            listModelLibrosComprados.addElement(book+" ("+this.bookBuyerAgent.getPurchasedBooks().get(book)+" € )");
        }

        for(String book: this.bookBuyerAgent.getActiveAuctions().keySet()){
            listModelSubastasActivas.addElement(book);
        }
    }

    public void recibirCFP(String bookTitle, int value){
        if(!this.vSubastasBuyers.containsKey(bookTitle)){
            updateLists();
            vSubastasBuyers.put(bookTitle, new VSubastaBuyer(bookTitle, bookBuyerAgent.getTargetBooks().get(bookTitle), bookBuyerAgent));
        }
        VSubastaBuyer vSubastaBuyer = this.vSubastasBuyers.get(bookTitle);
        vSubastaBuyer.setPrecioRonda(value);
    }

    public void hacerPuja(String bookTitle, int value){
        VSubastaBuyer vSubastaBuyer = this.vSubastasBuyers.get(bookTitle);
        vSubastaBuyer.setMiUltimaPuja(value);
    }

    public void recibirResultado(String bookTitle, int value){
        if(!this.vSubastasBuyers.containsKey(bookTitle)){
            updateLists();
            vSubastasBuyers.put(bookTitle, new VSubastaBuyer(bookTitle, bookBuyerAgent.getTargetBooks().get(bookTitle), bookBuyerAgent));
        }
        VSubastaBuyer vSubastaBuyer = this.vSubastasBuyers.get(bookTitle);
        vSubastaBuyer.setResultado(value);
    }

    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle(this.bookBuyerAgent.getAID().getName().split("@")[0]+": Ventana Principal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

        this.updateLists();
    }

    private void openVAnhadirLibro(ActionEvent e) {
        VAnhadirLibroBuyer vAnhadirLibroBuyer = new VAnhadirLibroBuyer(this, this.bookBuyerAgent);
        vAnhadirLibroBuyer.startWindow();
    }

    private void observarSubasta(ActionEvent e) {
        Object book = this.listSubastasActivas.getSelectedValue();
        if(book!=null){
            String bookTitle = (String) book;
            VSubastaBuyer vSubastaBuyer = this.vSubastasBuyers.computeIfAbsent(bookTitle, k -> new VSubastaBuyer(bookTitle, bookBuyerAgent.getTargetBooks().get(bookTitle), bookBuyerAgent));
            vSubastaBuyer.startWindow();
        } else{
            JOptionPane.showMessageDialog(null, "No has seleccionado ningun libro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro(ActionEvent e) {
        Object book = this.listLibrosInteresado.getSelectedValue();
        if(book!=null){
            String bookTitle = (String) book;
            bookTitle = bookTitle.split(" ")[0];
            if(this.bookBuyerAgent.getTargetBooks().containsKey(bookTitle)){
                this.bookBuyerAgent.addBehaviour(new RemoveTargetBook(bookBuyerAgent, bookTitle));
            }
        } else{
            JOptionPane.showMessageDialog(null, "No has seleccionado ningun libro", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Adrián Martínez Balea
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        leftPanel = new JPanel();
        logo = new JLabel();
        bidHub = new JLabel();
        scrollPaneTusLibros = new JScrollPane();
        listLibrosInteresado = new JList();
        buttonEliminar = new JButton();
        buttonAnhadirLibro = new JButton();
        scrollPaneLibrosComprados = new JScrollPane();
        listLibrosComprados = new JList();
        labelLibrosInteresado = new JLabel();
        labelLibrosComprados = new JLabel();
        buttonObservar = new JButton();
        labelSubastasActivas = new JLabel();
        scrollPaneSubastasActivas = new JScrollPane();
        listSubastasActivas = new JList();

        //======== mainFrame ========
        {
            mainFrame.setBackground(Color.white);
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {
                mainPanel.setBackground(Color.white);
                mainPanel.setForeground(Color.white);

                //======== leftPanel ========
                {
                    leftPanel.setForeground(new Color(0x3366ff));
                    leftPanel.setBackground(new Color(0x3366ff));

                    //---- logo ----
                    logo.setIcon(new ImageIcon("../resources/logo.png"));

                    //---- bidHub ----
                    bidHub.setIcon(new ImageIcon("../resources/bidhub.png"));

                    GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
                    leftPanel.setLayout(leftPanelLayout);
                    leftPanelLayout.setHorizontalGroup(
                        leftPanelLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                                .addContainerGap(55, Short.MAX_VALUE)
                                .addGroup(leftPanelLayout.createParallelGroup()
                                    .addComponent(logo)
                                    .addComponent(bidHub))
                                .addGap(53, 53, 53))
                    );
                    leftPanelLayout.setVerticalGroup(
                        leftPanelLayout.createParallelGroup()
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(logo)
                                .addGap(34, 34, 34)
                                .addComponent(bidHub)
                                .addContainerGap(104, Short.MAX_VALUE))
                    );
                }

                //======== scrollPaneTusLibros ========
                {

                    //---- listLibrosInteresado ----
                    listLibrosInteresado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listLibrosInteresado.setFont(new Font("sansserif", Font.PLAIN, 16));
                    scrollPaneTusLibros.setViewportView(listLibrosInteresado);
                }

                //---- buttonEliminar ----
                buttonEliminar.setText("Eliminar");
                buttonEliminar.addActionListener(e -> eliminarLibro(e));

                //---- buttonAnhadirLibro ----
                buttonAnhadirLibro.setText("A\u00f1adir libro");
                buttonAnhadirLibro.setFont(new Font("sansserif", Font.BOLD, 12));
                buttonAnhadirLibro.addActionListener(e -> openVAnhadirLibro(e));

                //======== scrollPaneLibrosComprados ========
                {
                    scrollPaneLibrosComprados.setBackground(Color.white);

                    //---- listLibrosComprados ----
                    listLibrosComprados.setFont(new Font("sansserif", Font.PLAIN, 16));
                    scrollPaneLibrosComprados.setViewportView(listLibrosComprados);
                }

                //---- labelLibrosInteresado ----
                labelLibrosInteresado.setText("Libros interesado");
                labelLibrosInteresado.setFont(new Font("sansserif", Font.BOLD, 24));

                //---- labelLibrosComprados ----
                labelLibrosComprados.setText("Libros comprados");
                labelLibrosComprados.setFont(new Font("sansserif", Font.BOLD, 24));

                //---- buttonObservar ----
                buttonObservar.setText("Observar");
                buttonObservar.addActionListener(e -> observarSubasta(e));

                //---- labelSubastasActivas ----
                labelSubastasActivas.setText("Subastas activas");
                labelSubastasActivas.setFont(new Font("sansserif", Font.BOLD, 24));

                //======== scrollPaneSubastasActivas ========
                {

                    //---- listSubastasActivas ----
                    listSubastasActivas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listSubastasActivas.setFont(new Font("sansserif", Font.PLAIN, 16));
                    scrollPaneSubastasActivas.setViewportView(listSubastasActivas);
                }

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addComponent(scrollPaneSubastasActivas, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonObservar, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                                .addComponent(labelLibrosInteresado, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelLibrosComprados)
                                .addComponent(labelSubastasActivas)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(scrollPaneTusLibros)
                                        .addComponent(scrollPaneLibrosComprados, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(mainPanelLayout.createParallelGroup()
                                        .addComponent(buttonAnhadirLibro, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonEliminar, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))))
                            .addContainerGap(49, Short.MAX_VALUE))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addComponent(leftPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(labelLibrosInteresado)
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPaneTusLibros, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(buttonAnhadirLibro)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonEliminar)))
                            .addGap(18, 18, 18)
                            .addComponent(labelLibrosComprados)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPaneLibrosComprados, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(labelSubastasActivas)
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPaneSubastasActivas, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(28, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                    .addComponent(buttonObservar)
                                    .addGap(94, 94, 94))))
                );
            }

            GroupLayout mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
            mainFrameContentPane.setLayout(mainFrameContentPaneLayout);
            mainFrameContentPaneLayout.setHorizontalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Adrián Martínez Balea
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JLabel logo;
    private JLabel bidHub;
    private JScrollPane scrollPaneTusLibros;
    private JList listLibrosInteresado;
    private JButton buttonEliminar;
    private JButton buttonAnhadirLibro;
    private JScrollPane scrollPaneLibrosComprados;
    private JList listLibrosComprados;
    private JLabel labelLibrosInteresado;
    private JLabel labelLibrosComprados;
    private JButton buttonObservar;
    private JLabel labelSubastasActivas;
    private JScrollPane scrollPaneSubastasActivas;
    private JList listSubastasActivas;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private DefaultListModel<String> listModelLibrosInteresado;
    private DefaultListModel<String> listModelLibrosComprados;
    private DefaultListModel<String> listModelSubastasActivas;
}
