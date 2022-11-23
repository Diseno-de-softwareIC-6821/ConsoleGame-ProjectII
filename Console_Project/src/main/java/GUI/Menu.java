/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI;

import SocketClient.Client;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class Menu extends javax.swing.JDialog {

    private ImageIcon imagen;
    private Icon icono;
    private static Client client;
    public boolean registerName = false;
    
    /**
     * Creates new form CreateWarrior
     */
    public Menu(java.awt.Frame parent, boolean modal) throws Exception {
        super(parent, modal);
        initComponents();
        
        //WINDOW IN MIDDLE OF THE SCREEN
        this.setLocationRelativeTo(this);
        this.pintarImagen(this.background, "src\\main\\java\\Images\\fondoEstrellado.jpg");
        this.pintarImagen(this.lblTitle, "src\\main\\java\\Images\\MortalDSKombat.png");
        
        musica();
        
        //        create a new client
        this.client = new Client(this);
        client.startConnection("localhost", 8080);
        
        String name = "";
        //while(!registerName){
            name = JOptionPane.showInputDialog("Type your user name please");
            client.sendMessage(name);
        //}
        JOptionPane.showMessageDialog(null, "Welcome " + name);
    }

    private void pintarImagen(JLabel lbl, String ruta){
        this.imagen = new ImageIcon(ruta);
        this.icono = new ImageIcon(this.imagen.getImage().getScaledInstance(
                                    lbl.getWidth(), 
                                    lbl.getHeight(), 
                                    Image.SCALE_DEFAULT));
        lbl.setIcon(this.icono);
        this.repaint();
    }
    
    private void musica(){
        
        try{
           File musicPath = new File("src\\main\\java\\Sound Effects\\MortalKombat.wav");
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start(); 
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        
        catch (Exception e){
            
        } 
    }
    
    public void addPlayerConnected(String userName){
        taUsers.setText(taUsers.getText() + "\n" + userName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        ignore = new javax.swing.JLabel();
        ignore1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taUsers = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLeft.setBorderPainted(false);
        btnLeft.setContentAreaFilled(false);
        getContentPane().add(btnLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 60, 50));

        btnRight.setBorderPainted(false);
        btnRight.setContentAreaFilled(false);
        getContentPane().add(btnRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, 60, 50));

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 730, 130));

        ignore.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ignore.setForeground(new java.awt.Color(255, 255, 255));
        ignore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(ignore, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 240, 30, 20));

        ignore1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ignore1.setForeground(new java.awt.Color(255, 255, 255));
        ignore1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(ignore1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 530, 420, 20));

        taUsers.setEditable(false);
        taUsers.setBackground(new java.awt.Color(0, 0, 0));
        taUsers.setColumns(20);
        taUsers.setForeground(new java.awt.Color(51, 255, 0));
        taUsers.setRows(5);
        taUsers.setText("Users connected...");
        jScrollPane1.setViewportView(taUsers);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, 340));

        jButton2.setText("Start Game");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 210, 50));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 560));

        getAccessibleContext().setAccessibleName("Main Menu");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        GameScreen createGameScreen = new GameScreen(new javax.swing.JDialog(), true, this, client); 
        createGameScreen.setVisible(true); 
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Menu dialog = null;
                try {
                    dialog = new Menu(new javax.swing.JFrame(), true);
                } catch (Exception ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnRight;
    private javax.swing.JLabel ignore;
    private javax.swing.JLabel ignore1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea taUsers;
    // End of variables declaration//GEN-END:variables
}
