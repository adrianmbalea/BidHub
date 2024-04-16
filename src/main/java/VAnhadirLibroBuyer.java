import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/*
 * Created by JFormDesigner on Wed Dec 20 18:23:47 UTC 2023
 */


/**
 * @author adrian
 */
public class VAnhadirLibroBuyer extends JFrame {
    private VPrincipalBuyer vPrincipalBuyer;
    private BookBuyerAgent bookBuyerAgent;
    public VAnhadirLibroBuyer(VPrincipalBuyer vPrincipalBuyer, BookBuyerAgent bookBuyerAgent) {
        this.vPrincipalBuyer = vPrincipalBuyer;
        this.bookBuyerAgent = bookBuyerAgent;
        initComponents();
    }

    private void cancelar(ActionEvent e) {
        this.textFieldNombreLibro.setText("");
        this.textFieldMaximoPagar.setText("");
        this.dispose();
    }

    private void anhadir(ActionEvent e) {
        String bookTitle = textFieldNombreLibro.getText();
        String priceText = textFieldMaximoPagar.getText();
        if(bookTitle.isEmpty() || priceText.isEmpty()){
            JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            int price;
            try{
                price = Integer.parseInt(priceText); 
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "El precio máximo no es válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!bookBuyerAgent.getTargetBooks().containsKey(bookTitle) && !bookBuyerAgent.getPurchasedBooks().containsKey(bookTitle)){
                bookBuyerAgent.addBehaviour(new AddTargetBook(bookBuyerAgent, bookTitle, price));
                this.vPrincipalBuyer.updateLists();
                this.dispose();
                JOptionPane.showMessageDialog(null, "Libro "+bookTitle+" añadido a tus libros interesados", "Libro añadido", JOptionPane.INFORMATION_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(null, "Ya estás interesado/tienes el libro "+bookTitle, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Adrián Martínez Balea
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        textFieldNombreLibro = new JTextField();
        labelAnhadirNuevoLibro = new JLabel();
        labelNombreLibro = new JLabel();
        buttonAnhadir = new JButton();
        buttonCancelar = new JButton();
        textFieldMaximoPagar = new JTextField();
        labelMaximoPagar = new JLabel();
        labelEuro = new JLabel();

        //======== mainFrame ========
        {
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {
                mainPanel.setBackground(Color.white);

                //---- labelAnhadirNuevoLibro ----
                labelAnhadirNuevoLibro.setText("A\u00f1adir un nuevo libro");
                labelAnhadirNuevoLibro.setFont(new Font("sansserif", Font.BOLD, 22));

                //---- labelNombreLibro ----
                labelNombreLibro.setText("Nombre del libro:");

                //---- buttonAnhadir ----
                buttonAnhadir.setText("A\u00f1adir");
                buttonAnhadir.addActionListener(e -> anhadir(e));

                //---- buttonCancelar ----
                buttonCancelar.setText("Cancelar");
                buttonCancelar.addActionListener(e -> cancelar(e));

                //---- labelMaximoPagar ----
                labelMaximoPagar.setText("M\u00e1ximo a pagar:");

                //---- labelEuro ----
                labelEuro.setText("\u20ac");

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(77, 77, 77)
                                    .addComponent(labelAnhadirNuevoLibro))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                            .addComponent(labelMaximoPagar)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(textFieldMaximoPagar, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(labelEuro)
                                            .addGap(154, 154, 154))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                            .addComponent(labelNombreLibro)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(textFieldNombreLibro, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(62, 62, 62)
                                    .addComponent(buttonAnhadir, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                    .addGap(55, 55, 55)
                                    .addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(28, Short.MAX_VALUE))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(labelAnhadirNuevoLibro)
                            .addGap(32, 32, 32)
                            .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelNombreLibro)
                                .addComponent(textFieldNombreLibro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelMaximoPagar)
                                .addComponent(textFieldMaximoPagar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelEuro))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(buttonAnhadir)
                                .addComponent(buttonCancelar))
                            .addGap(52, 52, 52))
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
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private JTextField textFieldNombreLibro;
    private JLabel labelAnhadirNuevoLibro;
    private JLabel labelNombreLibro;
    private JButton buttonAnhadir;
    private JButton buttonCancelar;
    private JTextField textFieldMaximoPagar;
    private JLabel labelMaximoPagar;
    private JLabel labelEuro;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle(bookBuyerAgent.getAID().getName().split("@")[0]+": Anhadir libro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

    }


}
