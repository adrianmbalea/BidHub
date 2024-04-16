import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Wed Dec 20 18:23:47 UTC 2023
 */



/**
 * @author adrian
 */
public class VAnhadirLibroSeller extends JFrame {
    private VPrincipalSeller vPrincipalSeller;
    public VAnhadirLibroSeller(VPrincipalSeller vPrincipalSeller) {
        this.vPrincipalSeller = vPrincipalSeller;
        initComponents();
    }

    private void cancelar(ActionEvent e) {
        this.textFieldNombreLibro.setText("");
        this.dispose();
    }

    private void anhadir(ActionEvent e) {
        String bookTitle = textFieldNombreLibro.getText();
        if(bookTitle.isEmpty()){
            JOptionPane.showMessageDialog(null, "El nombre del libro está en blanco", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            boolean result = vPrincipalSeller.anhadirLibro(bookTitle);
            if(result){
                this.dispose();
                JOptionPane.showMessageDialog(null, "Libro "+bookTitle+" añadido a tus libros", "Libro añadido", JOptionPane.INFORMATION_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(null, "Ya tienes el libro "+bookTitle, "Error", JOptionPane.ERROR_MESSAGE);
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

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGroup(mainPanelLayout.createParallelGroup()
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                            .addComponent(labelNombreLibro)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(textFieldNombreLibro, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                            .addGap(37, 37, 37)
                                            .addComponent(buttonAnhadir, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                            .addGap(55, 55, 55)
                                            .addComponent(buttonCancelar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))))
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                    .addGap(79, 79, 79)
                                    .addComponent(labelAnhadirNuevoLibro)))
                            .addContainerGap(32, Short.MAX_VALUE))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addGap(36, 36, 36)
                            .addComponent(labelAnhadirNuevoLibro)
                            .addGap(41, 41, 41)
                            .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelNombreLibro)
                                .addComponent(textFieldNombreLibro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle(vPrincipalSeller.getBookSellerAgent().getAID().getName().split("@")[0]+": Anhadir Libro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

    }


}
