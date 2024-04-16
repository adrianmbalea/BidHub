import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Thu Dec 21 18:59:36 UTC 2023
 */



/**
 * @author adrian
 */
public class VSubastaBuyer extends JFrame{
    private String bookTitle;
    private int miUltimaPuja;
    private int precioRonda;
    private int resultado;
    private BookBuyerAgent bookBuyerAgent;

    // Constantes
    public static final int GANANDO = 1;
    public static final int PERDIENDO = 2;
    public static final int GANADOR = 3;
    public static final int PERDEDOR = 4;

    public VSubastaBuyer(String bookTitle, int maxGasto, BookBuyerAgent bookBuyerAgent) {
        this.bookBuyerAgent = bookBuyerAgent;
        this.bookTitle = bookTitle;
        this.miUltimaPuja = 0;
        this.precioRonda = 0;
        this.resultado = PERDIENDO;
        initComponents();
        this.labelMaxGasto.setText("Máximo dispuesto a gastar "+maxGasto);
        this.labelSubastaLibro.setText("Subasta de "+bookTitle);
        updateLabels();
    }

    public void setMiUltimaPuja(int miUltimaPuja) {
        this.miUltimaPuja = miUltimaPuja;
        updateLabels();
    }

    public void setPrecioRonda(int precioRonda) {
        this.precioRonda = precioRonda;
        updateLabels();
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
        updateLabels();
    }

    private void updateLabels(){
        if(this.precioRonda<=0){
            this.labelPrecioRonda.setText("Precio ofrecido en esta ronda:  -");
        } else{
            this.labelPrecioRonda.setText("Precio ofrecido en esta ronda: "+this.precioRonda);
        }

        if(this.miUltimaPuja<=0){
            this.labelMiUltimaPuja.setText("Última puja realizada por mí:  -");
        } else{
            this.labelMiUltimaPuja.setText("Última puja realizada por mí: "+this.miUltimaPuja);
        }

        switch (resultado){
            case GANANDO:
                this.labelResultado.setIcon(new ImageIcon("../resources/tick.png"));
                this.labelResultado.setText("     Vas ganando la puja");
                break;
            case PERDIENDO:
                this.labelResultado.setIcon(new ImageIcon("../resources/cruz.png"));
                this.labelResultado.setText("     Vas perdiendo la puja");
                break;
            case GANADOR:
                this.labelResultado.setIcon(new ImageIcon("../resources/party.png"));
                this.labelResultado.setText("     Has comprado el libro por "+this.miUltimaPuja+"€");
                break;
            case PERDEDOR:
                this.labelResultado.setIcon(new ImageIcon("../resources/sad.png"));
                this.labelResultado.setText("     Subasta finalizada. Has perdido el libro.");
                break;
        }
    }

    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle(bookBuyerAgent.getAID().getName().split("@")[0]+":  Subasta de "+bookTitle);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

        updateLabels();
    }

    private void volver(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Adrián Martínez Balea
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        labelSubastaLibro = new JLabel();
        labelMaxGasto = new JLabel();
        labelPrecioRonda = new JLabel();
        labelResultado = new JLabel();
        labelMiUltimaPuja = new JLabel();
        buttonVolver = new JButton();

        //======== mainFrame ========
        {
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {

                //---- labelSubastaLibro ----
                labelSubastaLibro.setText("Subasta de Libro");
                labelSubastaLibro.setFont(new Font("sansserif", Font.BOLD, 24));

                //---- labelMaxGasto ----
                labelMaxGasto.setText("M\u00e1ximo dispuesto a gastar: ");
                labelMaxGasto.setFont(new Font("sansserif", Font.PLAIN, 16));

                //---- labelPrecioRonda ----
                labelPrecioRonda.setText("Precio ofrecido en esta ronda:");
                labelPrecioRonda.setFont(new Font("sansserif", Font.PLAIN, 16));

                //---- labelResultado ----
                labelResultado.setText("     Vas perdiendo la puja.");
                labelResultado.setIcon(new ImageIcon("../resources/cruz.png"));
                labelResultado.setFont(new Font("sansserif", Font.PLAIN, 16));

                //---- labelMiUltimaPuja ----
                labelMiUltimaPuja.setText("\u00daltima puja realizada por mi:");
                labelMiUltimaPuja.setFont(new Font("sansserif", Font.PLAIN, 16));

                //---- buttonVolver ----
                buttonVolver.setText("Volver");
                buttonVolver.addActionListener(e -> volver(e));

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(304, 304, 304)
                                    .addComponent(buttonVolver, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addGroup(mainPanelLayout.createParallelGroup()
                                        .addComponent(labelMiUltimaPuja)
                                        .addComponent(labelPrecioRonda)
                                        .addComponent(labelMaxGasto)
                                        .addComponent(labelSubastaLibro)
                                        .addComponent(labelResultado))))
                            .addContainerGap(41, Short.MAX_VALUE))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addComponent(labelSubastaLibro)
                            .addGap(30, 30, 30)
                            .addComponent(labelPrecioRonda)
                            .addGap(18, 18, 18)
                            .addComponent(labelMaxGasto)
                            .addGap(18, 18, 18)
                            .addComponent(labelMiUltimaPuja)
                            .addGap(30, 30, 30)
                            .addComponent(labelResultado)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(buttonVolver)
                            .addContainerGap(39, Short.MAX_VALUE))
                );
            }

            GroupLayout mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
            mainFrameContentPane.setLayout(mainFrameContentPaneLayout);
            mainFrameContentPaneLayout.setHorizontalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addComponent(mainPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private JLabel labelSubastaLibro;
    private JLabel labelMaxGasto;
    private JLabel labelPrecioRonda;
    private JLabel labelResultado;
    private JLabel labelMiUltimaPuja;
    private JButton buttonVolver;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
