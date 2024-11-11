package examTemplateCorrector;

import examTemplateCorrector.logic.CorrectExamController;
import examTemplateCorrector.logic.SaveExamTemplateController;
import static database.UtilityCSV.saveCorrectExamTemplate;
import examTemplateCorrector.view.ErrorModal;
import examTemplateCorrector.view.SavePathLibreryModal;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import utilities.MyException;
import utilities.AllUtilities;

public class Main extends javax.swing.JFrame {

    String pathImage;
    String pathLibrary;

    public Main() {
        initComponents();

        SavePathLibreryModal modalPath = new SavePathLibreryModal(this, true);
        modalPath.setVisible(true);
        pathLibrary = modalPath.getPath();
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
        jLabelImage = new javax.swing.JLabel();
        jLabelContentAnswers1 = new javax.swing.JLabel();
        jLabelContentAnswers2 = new javax.swing.JLabel();
        jLabelContentAnswers3 = new javax.swing.JLabel();
        jLabelContentAnswers4 = new javax.swing.JLabel();
        jLabelCorrectsAnswers = new javax.swing.JLabel();
        jLabelWrongAnswers = new javax.swing.JLabel();
        jLabelEmptyAnswers = new javax.swing.JLabel();
        jLabelCorrectAnswersResult = new javax.swing.JLabel();
        jLabelWrongAnswersResult = new javax.swing.JLabel();
        jLabelEmptyAnswersResult = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelBackground.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelHolaMundo.setBackground(new java.awt.Color(255, 255, 255));
        jLabelHolaMundo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelHolaMundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHolaMundo.setText("HELLO WORLD S.L.");
        jPanelBackground.add(jLabelHolaMundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 820, 44));

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
        jPanelBackground.add(jButtonSelectCorrectTemplate, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 240, 70));

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
        jPanelBackground.add(jButtonSelectExamToCorrect, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 230, 70));

        jLabelResults.setBackground(new java.awt.Color(255, 255, 255));
        jLabelResults.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelResults.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelResults.setText("Results:");
        jPanelBackground.add(jLabelResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 350, 44));

        jLabelDNIorNIE.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDNIorNIE.setText("DNI or NIE:");
        jPanelBackground.add(jLabelDNIorNIE, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 80, -1));

        jLabelExamCode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelExamCode.setText("Exam code:");
        jPanelBackground.add(jLabelExamCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 80, -1));

        jLabelMark.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMark.setText("Mark:");
        jPanelBackground.add(jLabelMark, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 310, 80, -1));

        jLabelDNIorNIEResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelDNIorNIEResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 100, -1));

        jLabelExamCodeResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelExamCodeResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 270, 70, -1));

        jLabelMarkResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelMarkResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 50, -1));

        jLabelExamTemplateSave.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabelExamTemplateSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelBackground.add(jLabelExamTemplateSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 820, 40));

        jLabelImage.setBackground(new java.awt.Color(255, 255, 255));
        jLabelImage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImage.setText("Answers:");
        jPanelBackground.add(jLabelImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 440, 40));

        jLabelContentAnswers1.setBackground(new java.awt.Color(204, 204, 204));
        jLabelContentAnswers1.setOpaque(true);
        jPanelBackground.add(jLabelContentAnswers1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 110, 240));

        jLabelContentAnswers2.setBackground(new java.awt.Color(204, 204, 204));
        jLabelContentAnswers2.setOpaque(true);
        jPanelBackground.add(jLabelContentAnswers2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 110, 240));

        jLabelContentAnswers3.setBackground(new java.awt.Color(204, 204, 204));
        jLabelContentAnswers3.setOpaque(true);
        jPanelBackground.add(jLabelContentAnswers3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 110, 240));

        jLabelContentAnswers4.setBackground(new java.awt.Color(204, 204, 204));
        jLabelContentAnswers4.setOpaque(true);
        jPanelBackground.add(jLabelContentAnswers4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, 110, 240));

        jLabelCorrectsAnswers.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelCorrectsAnswers.setText("Corrects:");
        jPanelBackground.add(jLabelCorrectsAnswers, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 80, -1));

        jLabelWrongAnswers.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelWrongAnswers.setText("Wrongs:");
        jPanelBackground.add(jLabelWrongAnswers, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 390, 80, -1));

        jLabelEmptyAnswers.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelEmptyAnswers.setText("Empty:");
        jPanelBackground.add(jLabelEmptyAnswers, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 80, -1));

        jLabelCorrectAnswersResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelCorrectAnswersResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, 100, -1));

        jLabelWrongAnswersResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelWrongAnswersResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 390, 70, -1));

        jLabelEmptyAnswersResult.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanelBackground.add(jLabelEmptyAnswersResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 430, 50, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectExamToCorrectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectExamToCorrectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSelectExamToCorrectActionPerformed

    private void jButtonSelectExamToCorrectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectExamToCorrectMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes", "jpg", "png"));
        fileChooser.setCurrentDirectory(new File("/Users/manueldavidcastilloperez/Downloads/exámenes"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            pathImage = selectedFile.getAbsolutePath();

            try {
                CorrectExamController correctExamController = new CorrectExamController(pathImage, pathLibrary);

                AllUtilities.SetImageLabel(jLabelContentAnswers1, correctExamController.getAnswersRectangle1());
                AllUtilities.SetImageLabel(jLabelContentAnswers2, correctExamController.getAnswersRectangle2());
                AllUtilities.SetImageLabel(jLabelContentAnswers3, correctExamController.getAnswersRectangle3());
                AllUtilities.SetImageLabel(jLabelContentAnswers4, correctExamController.getAnswersRectangle4());

                jLabelDNIorNIEResult.setText(correctExamController.getDniOrNieResult());
                jLabelExamCodeResult.setText(correctExamController.getExamCodeResult());
                jLabelMarkResult.setText(correctExamController.getExamMarkResult());

                jLabelCorrectAnswersResult.setText(String.valueOf(correctExamController.getCorrectAnswers()));
                jLabelWrongAnswersResult.setText(String.valueOf(correctExamController.getWrongAnswers()));
                jLabelEmptyAnswersResult.setText(String.valueOf(correctExamController.getEmptyAnswers()));

                jLabelExamTemplateSave.setText("");

            } catch (MyException e) {
                ErrorModal jDialog = new ErrorModal(this, true, e.getMessage());
                jDialog.setVisible(true);
            } catch (Exception e) {
                ErrorModal jDialog = new ErrorModal(this, true, "Ocurrió un error inesperado: " + e.getMessage());
                jDialog.setVisible(true);
            }

        } else {
            System.out.println("Selección de archivo cancelada");
        }
    }//GEN-LAST:event_jButtonSelectExamToCorrectMouseClicked

    private void jButtonSelectCorrectTemplateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectCorrectTemplateMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes", "jpg", "png"));
        fileChooser.setCurrentDirectory(new File("/Users/manueldavidcastilloperez/Downloads/exámenes"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            pathImage = selectedFile.getAbsolutePath();

            try {
                SaveExamTemplateController saveExamTemplateController = new SaveExamTemplateController(pathImage, pathLibrary);

                AllUtilities.SetImageLabel(jLabelContentAnswers1, saveExamTemplateController.getAnswersRectangle1());
                AllUtilities.SetImageLabel(jLabelContentAnswers2, saveExamTemplateController.getAnswersRectangle2());
                AllUtilities.SetImageLabel(jLabelContentAnswers3, saveExamTemplateController.getAnswersRectangle3());
                AllUtilities.SetImageLabel(jLabelContentAnswers4, saveExamTemplateController.getAnswersRectangle4());

                String examCode = saveExamTemplateController.getExamCodeResult();
                String[] resultString = saveExamTemplateController.getArrayResult();

                saveCorrectExamTemplate(examCode, resultString);

                jLabelExamTemplateSave.setText("Correct template save susccessfully");

                jLabelDNIorNIEResult.setText("");
                jLabelExamCodeResult.setText("");
                jLabelMarkResult.setText("");
                jLabelCorrectAnswersResult.setText("");
                jLabelWrongAnswersResult.setText("");
                jLabelEmptyAnswersResult.setText("");

            } catch (MyException e) {
                ErrorModal jDialog = new ErrorModal(this, true, e.getMessage());
                jDialog.setVisible(true);
            } catch (Exception e) {
                ErrorModal jDialog = new ErrorModal(this, true, "Ocurrió un error inesperado: " + e.getMessage());
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
    private javax.swing.JLabel jLabelContentAnswers1;
    private javax.swing.JLabel jLabelContentAnswers2;
    private javax.swing.JLabel jLabelContentAnswers3;
    private javax.swing.JLabel jLabelContentAnswers4;
    private javax.swing.JLabel jLabelCorrectAnswersResult;
    private javax.swing.JLabel jLabelCorrectsAnswers;
    private javax.swing.JLabel jLabelDNIorNIE;
    private javax.swing.JLabel jLabelDNIorNIEResult;
    private javax.swing.JLabel jLabelEmptyAnswers;
    private javax.swing.JLabel jLabelEmptyAnswersResult;
    private javax.swing.JLabel jLabelExamCode;
    private javax.swing.JLabel jLabelExamCodeResult;
    private javax.swing.JLabel jLabelExamTemplateSave;
    private javax.swing.JLabel jLabelHolaMundo;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelMark;
    private javax.swing.JLabel jLabelMarkResult;
    private javax.swing.JLabel jLabelResults;
    private javax.swing.JLabel jLabelWrongAnswers;
    private javax.swing.JLabel jLabelWrongAnswersResult;
    private javax.swing.JPanel jPanelBackground;
    // End of variables declaration//GEN-END:variables
}
