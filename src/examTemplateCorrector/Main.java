package examTemplateCorrector;

import examTemplateCorrector.logic.CorrectExamController;
import examTemplateCorrector.logic.SaveExamTemplateController;
import static database.UtilityCSV.saveCorrectExamTemplate;
import examTemplateCorrector.view.ErrorModal;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import utilities.MyException;

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
        jButtonSelectCorrectTemplate = new javax.swing.JButton();
        jButtonSelectExamToCorrect = new javax.swing.JButton();
        jLabelResults = new javax.swing.JLabel();
        jLabelDNIorNIE = new javax.swing.JLabel();
        jLabelExamCode = new javax.swing.JLabel();
        jLabelMark = new javax.swing.JLabel();
        jLabelDNIorNIEResult = new javax.swing.JLabel();
        jLabelExamCodeResult = new javax.swing.JLabel();
        jLabelMarkResult = new javax.swing.JLabel();
        jLabelExamTemplateSave = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelBackground.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelHolaMundo.setBackground(new java.awt.Color(255, 255, 255));
        jLabelHolaMundo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelHolaMundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHolaMundo.setText("HELLO WORLD S.L.");
        jPanelBackground.add(jLabelHolaMundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 510, 44));

        jButtonSelectCorrectTemplate.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSelectCorrectTemplate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonSelectCorrectTemplate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSelectCorrectTemplate.setText("Select correct template");
        jButtonSelectCorrectTemplate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSelectCorrectTemplateMouseClicked(evt);
            }
        });
        jButtonSelectCorrectTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectCorrectTemplateActionPerformed(evt);
            }
        });
        jPanelBackground.add(jButtonSelectCorrectTemplate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 240, 70));

        jButtonSelectExamToCorrect.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSelectExamToCorrect.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonSelectExamToCorrect.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSelectExamToCorrect.setText("Select exam to correct");
        jButtonSelectExamToCorrect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSelectExamToCorrectMouseClicked(evt);
            }
        });
        jButtonSelectExamToCorrect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectExamToCorrectActionPerformed(evt);
            }
        });
        jPanelBackground.add(jButtonSelectExamToCorrect, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 230, 70));

        jLabelResults.setBackground(new java.awt.Color(255, 255, 255));
        jLabelResults.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelResults.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelResults.setText("Results:");
        jPanelBackground.add(jLabelResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 510, 44));

        jLabelDNIorNIE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDNIorNIE.setText("DNI or NIE:");
        jPanelBackground.add(jLabelDNIorNIE, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 80, -1));

        jLabelExamCode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelExamCode.setText("Exam code:");
        jPanelBackground.add(jLabelExamCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 80, -1));

        jLabelMark.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMark.setText("Mark:");
        jPanelBackground.add(jLabelMark, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 80, -1));

        jLabelDNIorNIEResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelDNIorNIEResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 100, -1));

        jLabelExamCodeResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelExamCodeResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 70, -1));

        jLabelMarkResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelMarkResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 50, -1));

        jLabelExamTemplateSave.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabelExamTemplateSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelBackground.add(jLabelExamTemplateSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 430, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectExamToCorrectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectExamToCorrectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSelectExamToCorrectActionPerformed

    private void jButtonSelectExamToCorrectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectExamToCorrectMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes", "jpg"));
        fileChooser.setCurrentDirectory(new File("/Users/manueldavidcastilloperez/Downloads"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            path = selectedFile.getAbsolutePath();

            try {
                CorrectExamController correctExamController = new CorrectExamController(path);

                jLabelDNIorNIEResult.setText(correctExamController.getDniOrNieResult());
                jLabelExamCodeResult.setText(correctExamController.getExamCodeResult());
                jLabelMarkResult.setText(correctExamController.getExamMarkResult());

                jLabelExamTemplateSave.setText("");
                
            } catch (MyException e) {
                ErrorModal jDialog = new ErrorModal(this, true, e.getMessage());
                jDialog.setVisible(true);
            } catch (Exception e) {
                String message = e.getMessage() != null ? e.getMessage() : "Error desconocido";

                ErrorModal jDialog = new ErrorModal(this, true, message);
                jDialog.setVisible(true);
            }

        } else {
            System.out.println("Selección de archivo cancelada");
        }
    }//GEN-LAST:event_jButtonSelectExamToCorrectMouseClicked

    private void jButtonSelectCorrectTemplateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectCorrectTemplateMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes", "jpg"));
        fileChooser.setCurrentDirectory(new File("/Users/manueldavidcastilloperez/Downloads"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            path = selectedFile.getAbsolutePath();
            
            try {
                SaveExamTemplateController saveExamTemplateController = new SaveExamTemplateController(path);

                String examCode = saveExamTemplateController.getExamCodeResult();
                String[] resultString = saveExamTemplateController.getArrayResult();

                saveCorrectExamTemplate(examCode, resultString);

                jLabelExamTemplateSave.setText("Correct template save susccessfully");
                
                jLabelDNIorNIEResult.setText("");
                jLabelExamCodeResult.setText("");
                jLabelMarkResult.setText("");
                
            } catch (MyException e) {
                ErrorModal jDialog = new ErrorModal(this, true, e.getMessage());
                jDialog.setVisible(true);
            } catch (Exception e) {
                String message = e.getMessage() != null ? e.getMessage() : "Error desconocido";

                ErrorModal jDialog = new ErrorModal(this, true, message);
                jDialog.setVisible(true);
            }
        } else {
            System.out.println("Selección de archivo cancelada");
        }
    }//GEN-LAST:event_jButtonSelectCorrectTemplateMouseClicked

    private void jButtonSelectCorrectTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectCorrectTemplateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSelectCorrectTemplateActionPerformed

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
    private javax.swing.JButton jButtonSelectCorrectTemplate;
    private javax.swing.JButton jButtonSelectExamToCorrect;
    private javax.swing.JLabel jLabelDNIorNIE;
    private javax.swing.JLabel jLabelDNIorNIEResult;
    private javax.swing.JLabel jLabelExamCode;
    private javax.swing.JLabel jLabelExamCodeResult;
    private javax.swing.JLabel jLabelExamTemplateSave;
    private javax.swing.JLabel jLabelHolaMundo;
    private javax.swing.JLabel jLabelMark;
    private javax.swing.JLabel jLabelMarkResult;
    private javax.swing.JLabel jLabelResults;
    private javax.swing.JPanel jPanelBackground;
    // End of variables declaration//GEN-END:variables
}
