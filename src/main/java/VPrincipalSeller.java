import java.awt.event.*;
import jade.core.Agent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Wed Dec 20 16:19:19 UTC 2023
 */



/**
 * @author adrian
 */
public class VPrincipalSeller extends JFrame {
    private BookSellerAgent bookSellerAgent;
    private HashMap<String, Auction> auctions;
    private HashMap<String, Auction> books;
    private HashMap<String, VSubastaSeller> vSubastaSellers;

    public VPrincipalSeller(BookSellerAgent bookSellerAgent) {
        this.bookSellerAgent = bookSellerAgent;
        this.auctions = new HashMap<>();
        this.books = new HashMap<>();
        this.vSubastaSellers = new HashMap<>();

        initComponents();
        // Creamos los modelos de las listas
        this.listModelTusLibros = new DefaultListModel<>();
        this.listModelSubastasActivas = new DefaultListModel<>();

        // Asignamos los modelos de las listas
        this.listTusLibros.setModel(this.listModelTusLibros);
        this.listSubastasActivas.setModel(this.listModelSubastasActivas);


        updateLists();
    }
    public BookSellerAgent getBookSellerAgent() {
        return bookSellerAgent;
    }

    public void updateLists(){
        this.listModelTusLibros.removeAllElements();
        this.listModelSubastasActivas.removeAllElements();

        for(String book: this.books.keySet()){
            listModelTusLibros.addElement(book);
        }

        for(String book: this.auctions.keySet()){
            listModelSubastasActivas.addElement(book);
        }
    }

    public void removeAuction(Auction auction){
        if(auction.getWinner()==null){
            this.books.put(auction.getBookTitle(), auction);
        }
        this.auctions.remove(auction.getBookTitle());
        this.vSubastaSellers.remove(auction.getBookTitle());
        this.updateLists();
    }

    public void addLine(String bookTitle, String text, Color color){
        vSubastaSellers.get(bookTitle).addLine(text, color);
    }


    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle(bookSellerAgent.getAID().getName().split("@")[0]+": Ventana Principal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

        this.updateLists();

    }
    
    public boolean anhadirLibro(String newBook){
        for(int i=0; i<this.listModelTusLibros.size(); i++){
            String book = this.listModelTusLibros.getElementAt(i);
            if(book.equals(newBook)){
                return false;
            }
        }

        for(int i=0; i<this.listModelSubastasActivas.size(); i++){
            String book = this.listModelSubastasActivas.getElementAt(i);
            if(book.equals(newBook)){
                return false;
            }
        }

        this.books.put(newBook, new Auction(newBook));
        this.updateLists();
        return true;
    }

    private void openVAnhadirLibro(ActionEvent e) {
        VAnhadirLibroSeller vAnhadirLibroSeller = new VAnhadirLibroSeller(this);
        vAnhadirLibroSeller.startWindow();
    }

    private void observarSubasta(ActionEvent e) {
        Object book = this.listSubastasActivas.getSelectedValue();
        if(book!=null){
            VSubastaSeller vSubastaSeller = this.vSubastaSellers.get((String) book);
            vSubastaSeller.startWindow();
        } else{
            JOptionPane.showMessageDialog(null, "No has seleccionado ningun libro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro(ActionEvent e) {
        Object book = this.listTusLibros.getSelectedValue();
        if(book!=null){
            this.books.remove(book);
            this.updateLists();
        } else{
            JOptionPane.showMessageDialog(null, "No has seleccionado ningun libro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarSubasta(ActionEvent e) {
        Object book = this.listTusLibros.getSelectedValue();
        if(book!=null){
            String bookTitle = (String) book;
            Auction auction = this.books.get(bookTitle);
            this.books.remove(bookTitle);
            this.auctions.put(bookTitle, auction);
            VSubastaSeller vSubastaSeller = new VSubastaSeller(bookSellerAgent, auction);
            this.vSubastaSellers.put(bookTitle, vSubastaSeller);
            this.bookSellerAgent.initAuction(auction);
            this.updateLists();
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
        listTusLibros = new JList();
        buttonIniciarSubasta = new JButton();
        buttonEliminar = new JButton();
        buttonAnhadirLibro = new JButton();
        scrollPaneSubastasActivas = new JScrollPane();
        listSubastasActivas = new JList();
        labelTusLibros = new JLabel();
        labelSubastasActivas = new JLabel();
        buttonObservar = new JButton();

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
                                .addContainerGap(56, Short.MAX_VALUE)
                                .addGroup(leftPanelLayout.createParallelGroup()
                                    .addComponent(logo)
                                    .addComponent(bidHub))
                                .addGap(52, 52, 52))
                    );
                    leftPanelLayout.setVerticalGroup(
                        leftPanelLayout.createParallelGroup()
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(logo)
                                .addGap(33, 33, 33)
                                .addComponent(bidHub)
                                .addContainerGap(105, Short.MAX_VALUE))
                    );
                }

                //======== scrollPaneTusLibros ========
                {

                    //---- listTusLibros ----
                    listTusLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    listTusLibros.setFont(new Font("sansserif", Font.PLAIN, 16));
                    scrollPaneTusLibros.setViewportView(listTusLibros);
                }

                //---- buttonIniciarSubasta ----
                buttonIniciarSubasta.setText("Iniciar subasta");
                buttonIniciarSubasta.addActionListener(e -> iniciarSubasta(e));

                //---- buttonEliminar ----
                buttonEliminar.setText("Eliminar");
                buttonEliminar.addActionListener(e -> eliminarLibro(e));

                //---- buttonAnhadirLibro ----
                buttonAnhadirLibro.setText("A\u00f1adir libro");
                buttonAnhadirLibro.setFont(new Font("sansserif", Font.BOLD, 12));
                buttonAnhadirLibro.addActionListener(e -> openVAnhadirLibro(e));

                //======== scrollPaneSubastasActivas ========
                {
                    scrollPaneSubastasActivas.setBackground(Color.white);

                    //---- listSubastasActivas ----
                    listSubastasActivas.setFont(new Font("sansserif", Font.PLAIN, 16));
                    scrollPaneSubastasActivas.setViewportView(listSubastasActivas);
                }

                //---- labelTusLibros ----
                labelTusLibros.setText("Tus Libros");
                labelTusLibros.setFont(new Font("sansserif", Font.BOLD, 36));

                //---- labelSubastasActivas ----
                labelSubastasActivas.setText("Subastas activas");
                labelSubastasActivas.setFont(new Font("sansserif", Font.BOLD, 36));

                //---- buttonObservar ----
                buttonObservar.setText("Observar");
                buttonObservar.addActionListener(e -> observarSubasta(e));

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addComponent(labelTusLibros)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGroup(mainPanelLayout.createParallelGroup()
                                        .addComponent(scrollPaneSubastasActivas, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelSubastasActivas)
                                        .addComponent(scrollPaneTusLibros, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(mainPanelLayout.createParallelGroup()
                                        .addComponent(buttonObservar, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createParallelGroup()
                                            .addComponent(buttonEliminar, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buttonAnhadirLibro, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buttonIniciarSubasta, GroupLayout.Alignment.TRAILING)))))
                            .addContainerGap())
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addComponent(leftPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                            .addContainerGap(30, Short.MAX_VALUE)
                            .addComponent(labelTusLibros)
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(scrollPaneTusLibros, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(labelSubastasActivas)
                                    .addGap(18, 18, 18)
                                    .addComponent(scrollPaneSubastasActivas, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(buttonAnhadirLibro)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonEliminar)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonIniciarSubasta)
                                    .addGap(156, 156, 156)
                                    .addComponent(buttonObservar)))
                            .addGap(54, 54, 54))
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
    private JList listTusLibros;
    private JButton buttonIniciarSubasta;
    private JButton buttonEliminar;
    private JButton buttonAnhadirLibro;
    private JScrollPane scrollPaneSubastasActivas;
    private JList listSubastasActivas;
    private JLabel labelTusLibros;
    private JLabel labelSubastasActivas;
    private JButton buttonObservar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private DefaultListModel<String> listModelTusLibros;
    private DefaultListModel<String> listModelSubastasActivas;
}
