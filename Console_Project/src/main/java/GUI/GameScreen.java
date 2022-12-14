/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI;

import JSONParser.JSONParser;
import SocketClient.Client;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Daniel
 */
public class GameScreen extends javax.swing.JDialog {

    private ImageIcon imagen;
    private Icon icono;
    private Timer temporizador;
    private static Menu menuScreen;
    private static Client client;
    private static String warClass;
    private static String teamConfig;
    public boolean flag;
    public HashMap<String, JLabel > teamName = new HashMap<>();
    public HashMap<String, JLabel > teamLife = new HashMap<>();
    
    /**
     * Creates new form GameScreen
     */
    public GameScreen(javax.swing.JDialog parent, boolean modal, String warClass, String teamConfig) throws Exception {
        super(parent, modal);
        initComponents();
        
        this.warClass = warClass;
        this.client = new Client(this);
        client.startConnection("localhost", 8080);
        String name = "";
        
        while(!flag){
            name = JOptionPane.showInputDialog("Type your user name please");
            client.sendMessage("setCharacteristics " + name + " " + warClass);
            Thread.sleep(2000);
            if (!flag){
                JOptionPane.showMessageDialog(null, "Name has been already used. Please select another one");
            }
        }
        JOptionPane.showMessageDialog(null, "Welcome " + name);
        this.setTitle("Game Screen: " + name);
        
        //System.out.println(warClass);
        
        String[] screenConfig = teamConfig.split("-");
        
        
        //WINDOW IN MIDDLE OF THE SCREEN
        this.setLocationRelativeTo(this);
        /*
        //EXECUTE CREATE CLASS SCREEN
        CreateClass createClassScreen = new CreateClass(new javax.swing.JDialog(), true, client);
        createClassScreen.setVisible(true);
        */
        
        
        this.menuScreen = menuScreen;
        //change background color
        this.pintarImagen(this.background, "src\\main\\java\\Images\\fondoEstrellado.jpg");
        //taConsole.setBackground(Color.BLACK);
        this.pintarImagen(this.lblTeam1, screenConfig[1]);
        this.pintarImagen(this.lblTeam2, screenConfig[3]);
        this.pintarImagen(this.lblTeam3, screenConfig[5]);
        this.pintarImagen(this.lblTeam4, screenConfig[7]);

        //this.pintarImagen(this.lblAttackingImg, "src\\main\\java\\Images\\PennyWiseAttack.jpg");
        //this.pintarImagen(this.lblAttackedByImg, "src\\main\\java\\Images\\Lucy.jpg");
        this.pintarImagen(this.lblAttackingDamageCircle, "src\\main\\java\\Images\\RedCircle.png");
        
        lblNameChar1.setText(screenConfig[0]);
        lblNameChar2.setText(screenConfig[2]);
        lblNameChar3.setText(screenConfig[4]);
        lblNameChar4.setText(screenConfig[6]);
        
        //HELPS TO UPDATE ACT CHAR ON GUI
        teamName.put(screenConfig[0], this.lblNameChar1);
        teamName.put(screenConfig[2], this.lblNameChar2);
        teamName.put(screenConfig[4], this.lblNameChar3);
        teamName.put(screenConfig[6], this.lblNameChar4);
        
        teamLife.put(screenConfig[0], this.lblLifeChar1);
        teamLife.put(screenConfig[2], this.lblLifeChar2);
        teamLife.put(screenConfig[4], this.lblLifeChar3);
        teamLife.put(screenConfig[6], this.lblLifeChar4);

    }

    //METHODS DEFINED TO USE IN THE GUI
    
    public void iniciarTemporizador(){
        temporizador = new Timer(120000, null);    
        //temporizador = new Timer(10000, null); 
        temporizador.setRepeats(false);
        temporizador.start();
        
        temporizador.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //btnYoda.setEnabled(true);
                //TABitacora.setText(TABitacora.getText() + "Han aparecido los Yedi\n");
            }
        } );
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
    
    public void actualizarTablaArmas(String JSON, String warriorName){
         JSONParser.parseWeapons(tUserStats1, JSON, warriorName, lblActChar);
    }
    public void actualizarAttackedBy(String JSON){
         this.pintarImagen(this.lblAttackedByImg, JSONParser.parseAttackedBy(lblAttackedByText, lblAttackedByText1, 
                lblAttackedByStats1, lblAttackedByStats2, lblAttackedByStats3, lblAttackedByStats4, lblLifeChar1,
                lblLifeChar2, lblLifeChar3, lblLifeChar4, lblAttackedByImg, teamName, teamLife, lblActChar, lblLifeActChar, JSON));
    }
    public void actualizarAttacking(String JSON){
         this.pintarImagen(this.lblAttackingImg, JSONParser.parseAttacking(lblAttackingText1, lblAttackingText2, 
                lblAttackingDamage, lblAttackingImg, JSON));
    }
    
    public void sendMessageConsole(String message){
        //tpConsole.setText(tpConsole.getText() + "\n" + message);
        StyledDocument doc = tpConsole.getStyledDocument();

        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.RED);
        

        //  Add some text

        try
        {
            doc.insertString(doc.getLength(), "\n" + message, keyWord );
        }
        catch(Exception e) { System.out.println(e); }
    }
    public void sendMessageChat(String message){
        taLog.setText(taLog.getText() + message + "\n");
        
    }
    public void selectedInfoUpdate(String JSON){
        System.out.println("INFO TO PARSE: " + JSON);
        JSONParser.parseInfo(lblActChar, lblLifeActChar, tUserStats1, JSON);
    }
    
    public void updateMyStats(String JSON, int table){
        switch(table){
            case 1 ->{
                JSONParser.parseStats(tUserStats, JSON);
            }
            case 2 ->{
                JSONParser.parseStats(tAgainstStats, JSON);
            }
        }
        
    }
    
    public void showLogsFile(String JSON){
        JSONParser.parseLog(taLog, JSON);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tUserStats = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tAgainstStats = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        taLog = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        tpConsole = new javax.swing.JTextPane();
        tfEnterCommand = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tRanking = new javax.swing.JTable();
        pTeam = new javax.swing.JPanel();
        lblTeam1 = new javax.swing.JLabel();
        lblTeam2 = new javax.swing.JLabel();
        lblTeam3 = new javax.swing.JLabel();
        lblTeam4 = new javax.swing.JLabel();
        lblLifeChar1 = new javax.swing.JLabel();
        lblLifeChar2 = new javax.swing.JLabel();
        lblLifeChar3 = new javax.swing.JLabel();
        lblLifeChar4 = new javax.swing.JLabel();
        lblYourTeam = new javax.swing.JLabel();
        lblNameChar2 = new javax.swing.JLabel();
        lblNameChar1 = new javax.swing.JLabel();
        lblNameChar3 = new javax.swing.JLabel();
        lblNameChar4 = new javax.swing.JLabel();
        lblActChar = new javax.swing.JLabel();
        lblLifeActChar = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tUserStats1 = new javax.swing.JTable();
        lblAttackedByText = new javax.swing.JLabel();
        lblAttackedByText1 = new javax.swing.JLabel();
        lblAttackedByStats1 = new javax.swing.JLabel();
        lblAttackedByStats2 = new javax.swing.JLabel();
        lblAttackedByStats3 = new javax.swing.JLabel();
        lblAttackedByStats4 = new javax.swing.JLabel();
        lblAttackedByImg = new javax.swing.JLabel();
        lblAttackingText1 = new javax.swing.JLabel();
        lblAttackingText2 = new javax.swing.JLabel();
        lblAttackingDamage = new javax.swing.JLabel();
        lblAttackingDamageCircle = new javax.swing.JLabel();
        lblAttackingImg = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Game Screen");
        setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tUserStats.setBackground(new java.awt.Color(0, 0, 0));
        tUserStats.setForeground(new java.awt.Color(51, 255, 0));
        tUserStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Wins:", null},
                {"Losses:", ""},
                {"Attacks:", null},
                {"Success:", null},
                {"Failed:", null},
                {"Total Kills:", null},
                {"Giveup:", null}
            },
            new String [] {
                "MY STATUS:", "#XX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tUserStats);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 208, 140));

        tAgainstStats.setBackground(new java.awt.Color(0, 0, 0));
        tAgainstStats.setForeground(new java.awt.Color(51, 255, 0));
        tAgainstStats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Wins:", null},
                {"Losses:", ""},
                {"Attacks:", null},
                {"Success:", null},
                {"Failed:", null},
                {"Total Kills:", null},
                {"Giveup:", null}
            },
            new String [] {
                "AGAINST:", "#XX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tAgainstStats);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 208, 140));

        taLog.setEditable(false);
        taLog.setBackground(new java.awt.Color(0, 0, 0));
        taLog.setColumns(20);
        taLog.setForeground(new java.awt.Color(51, 255, 0));
        taLog.setLineWrap(true);
        taLog.setRows(5);
        taLog.setSelectionColor(new java.awt.Color(255, 153, 0));
        jScrollPane6.setViewportView(taLog);

        tpConsole.setEditable(false);
        tpConsole.setBackground(new java.awt.Color(0, 0, 0));
        tpConsole.setForeground(new java.awt.Color(51, 255, 0));
        jScrollPane7.setViewportView(tpConsole);

        tfEnterCommand.setBackground(new java.awt.Color(0, 0, 0));
        tfEnterCommand.setForeground(new java.awt.Color(51, 255, 0));
        tfEnterCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterCommand(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tfEnterCommand, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfEnterCommand)))
                .addGap(2, 2, 2))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 526, 1189, 190));

        tRanking.setBackground(new java.awt.Color(0, 0, 0));
        tRanking.setForeground(new java.awt.Color(51, 255, 0));
        tRanking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null},
                {"", "", null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "RANKING:", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tRanking);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 208, 230));

        pTeam.setBackground(new java.awt.Color(0, 0, 0));

        lblLifeChar1.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lblLifeChar1.setForeground(new java.awt.Color(255, 255, 255));
        lblLifeChar1.setText("250");

        lblLifeChar2.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lblLifeChar2.setForeground(new java.awt.Color(255, 255, 255));
        lblLifeChar2.setText("250");

        lblLifeChar3.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lblLifeChar3.setForeground(new java.awt.Color(255, 255, 255));
        lblLifeChar3.setText("250");

        lblLifeChar4.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lblLifeChar4.setForeground(new java.awt.Color(255, 255, 255));
        lblLifeChar4.setText("250");

        lblYourTeam.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        lblYourTeam.setForeground(new java.awt.Color(255, 255, 255));
        lblYourTeam.setText("YOUR TEAM");

        lblNameChar2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNameChar2.setForeground(new java.awt.Color(255, 255, 255));
        lblNameChar2.setText("MICHAEL MYERS");

        lblNameChar1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNameChar1.setForeground(new java.awt.Color(255, 255, 255));
        lblNameChar1.setText("MICHAEL MYERS");

        lblNameChar3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNameChar3.setForeground(new java.awt.Color(255, 255, 255));
        lblNameChar3.setText("MICHAEL MYERS");

        lblNameChar4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblNameChar4.setForeground(new java.awt.Color(255, 255, 255));
        lblNameChar4.setText("MICHAEL MYERS");

        lblActChar.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lblActChar.setForeground(new java.awt.Color(255, 255, 255));
        lblActChar.setText("ACTUAL CHARACTER");

        lblLifeActChar.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lblLifeActChar.setForeground(new java.awt.Color(255, 255, 255));
        lblLifeActChar.setText("250");

        tUserStats1.setBackground(new java.awt.Color(0, 0, 0));
        tUserStats1.setForeground(new java.awt.Color(51, 255, 0));
        tUserStats1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Weapon1", null, null, null, null, null, null, null, null, null, null},
                {"Weapon2", null, null, null, null, null, null, null, null, null, null},
                {"Weapon3", null, null, null, null, null, null, null, null, null, null},
                {"Weapon4", null, null, null, null, null, null, null, null, null, null},
                {"Weapon1", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Weapons", "", "", "", "", "", "", "", "", "", ""
            }
        ));
        jScrollPane5.setViewportView(tUserStats1);

        javax.swing.GroupLayout pTeamLayout = new javax.swing.GroupLayout(pTeam);
        pTeam.setLayout(pTeamLayout);
        pTeamLayout.setHorizontalGroup(
            pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTeamLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lblLifeChar1)
                .addGap(92, 92, 92)
                .addComponent(lblLifeChar2)
                .addGap(94, 94, 94)
                .addComponent(lblLifeChar3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLifeChar4)
                .addGap(55, 55, 55))
            .addGroup(pTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNameChar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pTeamLayout.createSequentialGroup()
                        .addComponent(lblTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTeam3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTeam4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pTeamLayout.createSequentialGroup()
                        .addComponent(lblNameChar2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNameChar3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNameChar4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
            .addGroup(pTeamLayout.createSequentialGroup()
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pTeamLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblYourTeam))
                    .addGroup(pTeamLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblActChar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLifeActChar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pTeamLayout.setVerticalGroup(
            pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTeamLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblYourTeam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTeam1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTeam4, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTeam3, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTeam2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLifeChar1)
                    .addComponent(lblLifeChar2)
                    .addComponent(lblLifeChar3)
                    .addComponent(lblLifeChar4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNameChar2)
                    .addComponent(lblNameChar1)
                    .addComponent(lblNameChar3)
                    .addComponent(lblNameChar4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActChar)
                    .addComponent(lblLifeActChar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pTeam, new org.netbeans.lib.awtextra.AbsoluteConstraints(691, 6, -1, 500));

        lblAttackedByText.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackedByText.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackedByText, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, -1));

        lblAttackedByText1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackedByText1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackedByText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, -1, -1));

        lblAttackedByStats1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackedByStats1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackedByStats1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        lblAttackedByStats2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackedByStats2.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackedByStats2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, -1));

        lblAttackedByStats3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackedByStats3.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackedByStats3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, -1, -1));

        lblAttackedByStats4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackedByStats4.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackedByStats4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, -1, -1));
        getContentPane().add(lblAttackedByImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 460, 240));

        lblAttackingText1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackingText1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackingText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, -1, -1));

        lblAttackingText2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAttackingText2.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackingText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, -1, -1));

        lblAttackingDamage.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblAttackingDamage.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackingDamage, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 420, -1, -1));

        lblAttackingDamageCircle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblAttackingDamageCircle.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblAttackingDamageCircle, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 110, 100));
        getContentPane().add(lblAttackingImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 460, 240));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnterCommand(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterCommand
        //CODE TO SEND COMMAND
        String consoleInp = tfEnterCommand.getText();
        String[] command = tfEnterCommand.getText().split(" ");
        switch(command[0]){
            case("MORITE")->{
                dispose();
                menuScreen.setVisible(true);
            }
            case("")->{
                
            }
            case("attack")->{
                try {
                    client.sendMessage(consoleInp);
                }
                catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("chat")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("dm")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("reload")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("info")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("skip")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("surrender")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("tie")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("stats")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case("logs")->{
                try {
                    client.sendMessage(consoleInp);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            default->{
                sendMessageConsole("INVALID COMMAND");
            }     
        }
        tpConsole.setText(tpConsole.getText() + "\n" + consoleInp);
        tfEnterCommand.setText("");        
    }//GEN-LAST:event_EnterCommand

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
            java.util.logging.Logger.getLogger(GameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameScreen dialog = new GameScreen(new javax.swing.JDialog(), true, warClass, teamConfig);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblActChar;
    private javax.swing.JLabel lblAttackedByImg;
    private javax.swing.JLabel lblAttackedByStats1;
    private javax.swing.JLabel lblAttackedByStats2;
    private javax.swing.JLabel lblAttackedByStats3;
    private javax.swing.JLabel lblAttackedByStats4;
    private javax.swing.JLabel lblAttackedByText;
    private javax.swing.JLabel lblAttackedByText1;
    private javax.swing.JLabel lblAttackingDamage;
    private javax.swing.JLabel lblAttackingDamageCircle;
    private javax.swing.JLabel lblAttackingImg;
    private javax.swing.JLabel lblAttackingText1;
    private javax.swing.JLabel lblAttackingText2;
    private javax.swing.JLabel lblLifeActChar;
    private javax.swing.JLabel lblLifeChar1;
    private javax.swing.JLabel lblLifeChar2;
    private javax.swing.JLabel lblLifeChar3;
    private javax.swing.JLabel lblLifeChar4;
    private javax.swing.JLabel lblNameChar1;
    private javax.swing.JLabel lblNameChar2;
    private javax.swing.JLabel lblNameChar3;
    private javax.swing.JLabel lblNameChar4;
    private javax.swing.JLabel lblTeam1;
    private javax.swing.JLabel lblTeam2;
    private javax.swing.JLabel lblTeam3;
    private javax.swing.JLabel lblTeam4;
    private javax.swing.JLabel lblYourTeam;
    private javax.swing.JPanel pTeam;
    private javax.swing.JTable tAgainstStats;
    private javax.swing.JTable tRanking;
    private javax.swing.JTable tUserStats;
    private javax.swing.JTable tUserStats1;
    private javax.swing.JTextArea taLog;
    private javax.swing.JTextField tfEnterCommand;
    private javax.swing.JTextPane tpConsole;
    // End of variables declaration//GEN-END:variables
}
