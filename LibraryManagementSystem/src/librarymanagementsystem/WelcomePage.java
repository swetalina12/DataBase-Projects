/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package librarymanagementsystem;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static librarymanagementsystem.ImportData.conn;

/**
 *
 * @author swetalina
 */
public class WelcomePage extends javax.swing.JFrame {

    /**
     * Creates new form WelcomePage
     */
    public WelcomePage() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Welcome = new javax.swing.JLabel();
        jB_Search = new javax.swing.JButton();
        jB_CheckOut = new javax.swing.JButton();
        jB_CheckIn = new javax.swing.JButton();
        jB_AddBorrower = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jFine = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Welcome.setFont(new java.awt.Font("Comic Sans MS", 0, 48)); // NOI18N
        Welcome.setText("Welcome to Library");

        jB_Search.setText("SEARCH BOOK");
        jB_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_SearchActionPerformed(evt);
            }
        });

        jB_CheckOut.setText("CHECKOUT BOOK");
        jB_CheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_CheckOutActionPerformed(evt);
            }
        });

        jB_CheckIn.setText("CHECKIN BOOK");
        jB_CheckIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_CheckInActionPerformed(evt);
            }
        });

        jB_AddBorrower.setText("ADD BORROWER");
        jB_AddBorrower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB_AddBorrowerActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/librarymanagementsystem/pictures/library.jpg"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel2.setText("Options: ");

        jFine.setText("FINE");
        jFine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFineActionPerformed(evt);
            }
        });

        jButton1.setText("ADD BOOK COPIES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(Welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel1)
                        .addGap(110, 110, 110)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jFine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jB_Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jB_CheckOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jB_CheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jB_AddBorrower, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(145, 145, 145))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(Welcome)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)
                        .addGap(31, 31, 31)
                        .addComponent(jB_Search)
                        .addGap(18, 18, 18)
                        .addComponent(jB_CheckOut)
                        .addGap(26, 26, 26)
                        .addComponent(jB_CheckIn)
                        .addGap(27, 27, 27)
                        .addComponent(jB_AddBorrower)
                        .addGap(18, 18, 18)
                        .addComponent(jFine)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jB_CheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_CheckOutActionPerformed
        this.setVisible(false);
        BookCheckoutPage bcop = new BookCheckoutPage();
        bcop.setVisible(true);
        
    }//GEN-LAST:event_jB_CheckOutActionPerformed

    private void jB_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_SearchActionPerformed
        this.setVisible(false);
        SearchBookPage sbp= new SearchBookPage();
       sbp.setVisible(true);
    }//GEN-LAST:event_jB_SearchActionPerformed

    private void jB_CheckInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_CheckInActionPerformed
         this.setVisible(false);
        BookCheckInPage bcip = new BookCheckInPage();
        bcip.setVisible(true);
    }//GEN-LAST:event_jB_CheckInActionPerformed

    private void jB_AddBorrowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB_AddBorrowerActionPerformed
         this.setVisible(false); 
        NewBorrowerPage nbp = new NewBorrowerPage();
        nbp.setVisible(true);
    }//GEN-LAST:event_jB_AddBorrowerActionPerformed

    private void jFineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFineActionPerformed
         this.setVisible(false);
        Fine fine=new Fine();
        fine.setVisible(true);      
    }//GEN-LAST:event_jFineActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        LoginPage lp=new LoginPage();
       lp.setVisible(true);
       
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WelcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WelcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WelcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WelcomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Welcome;
    private javax.swing.JButton jB_AddBorrower;
    private javax.swing.JButton jB_CheckIn;
    private javax.swing.JButton jB_CheckOut;
    private javax.swing.JButton jB_Search;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jFine;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}