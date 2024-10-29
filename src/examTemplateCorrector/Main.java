package examTemplateCorrector;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends javax.swing.JFrame {

    String path = "";
    
    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBackground = new javax.swing.JPanel();
        jLabelHolaMundo = new javax.swing.JLabel();
        jButtonSearchImage = new javax.swing.JButton();
        jLabelResults = new javax.swing.JLabel();
        jLabelDNIorNIE = new javax.swing.JLabel();
        jLabelExamCode = new javax.swing.JLabel();
        jLabelMark = new javax.swing.JLabel();
        jLabelDNIorNIEResult = new javax.swing.JLabel();
        jLabelExamCodeResult = new javax.swing.JLabel();
        jLabelMarkResult = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelBackground.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelHolaMundo.setBackground(new java.awt.Color(255, 255, 255));
        jLabelHolaMundo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelHolaMundo.setForeground(new java.awt.Color(0, 0, 0));
        jLabelHolaMundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHolaMundo.setText("HELLO WORLD");
        jPanelBackground.add(jLabelHolaMundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 480, 44));

        jButtonSearchImage.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSearchImage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonSearchImage.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSearchImage.setText("Search Image");
        jButtonSearchImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSearchImageMouseClicked(evt);
            }
        });
        jButtonSearchImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchImageActionPerformed(evt);
            }
        });
        jPanelBackground.add(jButtonSearchImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 220, 70));

        jLabelResults.setBackground(new java.awt.Color(255, 255, 255));
        jLabelResults.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelResults.setForeground(new java.awt.Color(0, 0, 0));
        jLabelResults.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelResults.setText("Results:");
        jPanelBackground.add(jLabelResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 480, 44));

        jLabelDNIorNIE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDNIorNIE.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDNIorNIE.setText("DNI or NIE:");
        jPanelBackground.add(jLabelDNIorNIE, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 80, -1));

        jLabelExamCode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelExamCode.setForeground(new java.awt.Color(0, 0, 0));
        jLabelExamCode.setText("Exam code:");
        jPanelBackground.add(jLabelExamCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 80, -1));

        jLabelMark.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMark.setForeground(new java.awt.Color(0, 0, 0));
        jLabelMark.setText("Mark:");
        jPanelBackground.add(jLabelMark, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 80, -1));

        jLabelDNIorNIEResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDNIorNIEResult.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground.add(jLabelDNIorNIEResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 100, -1));

        jLabelExamCodeResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelExamCodeResult.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground.add(jLabelExamCodeResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 70, -1));

        jLabelMarkResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMarkResult.setForeground(new java.awt.Color(0, 0, 0));
        jPanelBackground.add(jLabelMarkResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 50, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSearchImageActionPerformed

    private void jButtonSearchImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSearchImageMouseClicked
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes", "jpg"));
        fileChooser.setCurrentDirectory(new File("C:\\Users\\loco8\\Downloads"));
        
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            path = selectedFile.getAbsolutePath();
                        
            MainController mainController = new MainController(path);

            jLabelDNIorNIEResult.setText(mainController.getDniOrNieResult());
            jLabelExamCodeResult.setText(mainController.getExamCodeResult());
            jLabelMarkResult.setText(mainController.getExamMarkResult());
            
        } else {
            System.out.println("Selección de archivo cancelada");
        }
    }//GEN-LAST:event_jButtonSearchImageMouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSearchImage;
    private javax.swing.JLabel jLabelDNIorNIE;
    private javax.swing.JLabel jLabelDNIorNIEResult;
    private javax.swing.JLabel jLabelExamCode;
    private javax.swing.JLabel jLabelExamCodeResult;
    private javax.swing.JLabel jLabelHolaMundo;
    private javax.swing.JLabel jLabelMark;
    private javax.swing.JLabel jLabelMarkResult;
    private javax.swing.JLabel jLabelResults;
    private javax.swing.JPanel jPanelBackground;
    // End of variables declaration//GEN-END:variables
}
