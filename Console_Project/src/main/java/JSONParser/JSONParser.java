/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONParser;

import java.awt.Label;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Daniel
 */
public class JSONParser {
    
    
    public static void parseWeapons(JTable tableParse, String JSON, String warriorName, JLabel lblActChar){
        System.out.println(JSON);
        JSONArray classJSON = new JSONArray(JSON.split(" ")[1].replace("_", " "));
        System.out.println(classJSON.toString());
        //System.out.println(classJSON.toString());
        for (int i = 0; i < classJSON.length(); i++){
            JSONObject actClass = classJSON.getJSONObject(i);
            String actName = actClass.getString("name");
            if (actName.equals(warriorName) || warriorName.equals("")){
                lblActChar.setText(actName);
                Iterator<String> it = actClass.keys();
                int k = 0;
                while(it.hasNext()){
                    String weaponName = it.next();
                    System.out.println(weaponName);
                    if (weaponName.equals("name"))
                        continue;
                    tableParse.setValueAt(weaponName, k, 0);
                    JSONArray actDamages = actClass.getJSONObject(weaponName).getJSONArray("damage");
                    for (int j = 0; j < 10; j++){
                        System.out.println(actDamages.get(j));
                        tableParse.setValueAt(actDamages.get(j), k, j+1);
                    }
                    k++;
                }  
                break;
            }   
        }
    }
    
    public static String parseAttackedBy(JLabel lblAttackedByText, JLabel lblAttackedByText1, JLabel lblAttackedByStats1,
            JLabel lblAttackedByStats2, JLabel lblAttackedByStats3, JLabel lblAttackedByStats4, JLabel lblLifeChar1, JLabel lblLifeChar2
            , JLabel lblLifeChar3, JLabel lblLifeChar4, JLabel lblAttackedByImg, HashMap<String,JLabel> guiTeam, HashMap<String,JLabel> guiLife, JLabel lblActChar, JLabel lblLifeActChar, String JSON){
        JSONObject objectJSON = new JSONObject(JSON);
        lblAttackedByText.setText("Attacked by " + objectJSON.getString("attacked") + " with " + objectJSON.getString("warrior") + " [" + objectJSON.getString("element") + "]");
        lblAttackedByText1.setText("Weapon: " + objectJSON.getString("weapon"));
        JSONArray actWar = objectJSON.getJSONArray("damageDone");
        System.out.println(actWar.toString());
        //System.out.println(objectJSON.toString());
        //System.out.println("Attacked by " + objectJSON.getString("attacked") + " with " + objectJSON.getString("warrior") + " [" + objectJSON.getString("element") + "]");
        //System.out.println("Weapon: " + objectJSON.getString("weapon"));
        for (int i = 0; i < 4; i++){
            JSONObject actWarriorInfo = actWar.getJSONObject(i);
            String warriorName = actWarriorInfo.getString("name");
            String damage = actWarriorInfo.getString("damage");
            System.out.println("WARRIOR NAME: " + warriorName.replace("_", " "));
            System.out.println("JLABEL: " + guiTeam.get(warriorName.replace("_", " ")));
            
            JLabel actCharTest = guiTeam.get(warriorName.replace("_", " "));
            JLabel actLifeCharTest = guiLife.get(warriorName.replace("_", " "));
            if(lblActChar.getText().equals(warriorName.replace("_", " "))){
                System.out.println("ENTERED EQUALS");
                lblLifeActChar.setText(Integer.toString(Integer.parseInt(lblLifeActChar.getText()) - Integer.parseInt(damage)));
            }
            actLifeCharTest.setText(Integer.toString(Integer.parseInt(actLifeCharTest.getText()) - Integer.parseInt(damage)));
            
            String[] getFirstLetter = warriorName.split(" ");
            warriorName = "";
            for (int j = 0; j < getFirstLetter.length;j++){
                warriorName += getFirstLetter[j].charAt(0);
            }
            switch(i){
                case 0 ->{
                    lblAttackedByStats1.setText(warriorName + ": -" + damage);
                    //lblLifeChar1.setText(Integer.toString(Integer.parseInt(lblLifeChar1.getText()) - Integer.parseInt(damage)));
                    
                }
                case 1 ->{
                    lblAttackedByStats2.setText(warriorName + ": -" + damage);
                    //lblLifeChar2.setText(Integer.toString(Integer.parseInt(lblLifeChar2.getText()) - Integer.parseInt(damage)));
                }
                case 2 ->{
                    lblAttackedByStats3.setText(warriorName + ": -" + damage);
                    //lblLifeChar3.setText(Integer.toString(Integer.parseInt(lblLifeChar3.getText()) - Integer.parseInt(damage)));
                }
                case 3 ->{
                    lblAttackedByStats4.setText(warriorName + ": -" + damage);
                    //lblLifeChar4.setText(Integer.toString(Integer.parseInt(lblLifeChar4.getText()) - Integer.parseInt(damage)));
                }
            }
            
            System.out.println(warriorName + ": -" + damage);
        }
        return objectJSON.getString("warriorImg"); 
    }
    
    public static String parseAttacking(JLabel lblAttackingText1, JLabel lblAttackingText2,
                JLabel lblAttackingDamage, JLabel lblAttackingImg, String JSON){
        JSONObject objectJSON = new JSONObject(JSON);
        lblAttackingText1.setText("You attacked " + objectJSON.getString("attacked") + " with " + objectJSON.getString("warrior") + " [" + objectJSON.getString("element") + "]");
        lblAttackingText2.setText("Weapon: " + objectJSON.getString("weapon"));
        lblAttackingDamage.setText("- " + objectJSON.getString("damageTotal"));
        return objectJSON.getString("warriorImg");
    }
    
    public static void parseInfo(JLabel lblActChar, JLabel lblLifeActChar, JTable tableParse, String JSON){
        JSONObject objectJSON = new JSONObject(JSON.split(" ")[1].replace("_", " "));
        lblActChar.setText(objectJSON.getString("name"));
        lblLifeActChar.setText(objectJSON.getString("health"));
        
        JSONArray classJSON = objectJSON.getJSONArray("weapons");
        //System.out.println(classJSON.toString());
        //System.out.println(classJSON.toString());
        for (int i = 0; i < classJSON.length(); i++){
            JSONObject actClass = classJSON.getJSONObject(i);
            String weaponName = actClass.getString("name");
            tableParse.setValueAt(weaponName, i, 0);
            //System.out.println(weaponName);
            JSONArray actDamages = actClass.getJSONArray("damage");
            for (int j = 0; j < 10; j++){
                //System.out.println(actDamages.get(j));
                tableParse.setValueAt(actDamages.get(j), i, j+1);
            }
        }  
    }
    
    public static void parseStats(JTable stats, String JSON){
        JSONObject objectJSON = new JSONObject(JSON.split(" ")[1]);
        stats.setValueAt(objectJSON.getString("wins"), 0, 1);
        stats.setValueAt(objectJSON.getString("losses"), 1, 1);
        stats.setValueAt(objectJSON.getString("totalAttacks"), 2, 1);
        stats.setValueAt(objectJSON.getString("successfulAttacks"), 3, 1);
        stats.setValueAt(objectJSON.getString("failedAttacks"), 4, 1);
        stats.setValueAt(objectJSON.getString("totalKilledEnemies"), 5, 1);
        stats.setValueAt(objectJSON.getString("surrenderedGames"), 6, 1);
    }
    
    public static void parseLog(JTextArea taLog, String JSON){
        JSONArray objectJSON = new JSONObject(JSON.split(" ")[1]).getJSONArray("logs");
        for (int i = 0; i < objectJSON.length(); i++){
            taLog.setText(taLog.getText() + objectJSON.getString(i));
        }
        
        
    }
    
     public static void main(String[] args) {
        //String JSON = "{\"attacked\":\"Player 1\", \"warrior\":\"SUB ZERO\",\"element\":\"ICE\",\"weapon\":\"Ice Shoot\",\"damageTotal\":[{\"name\":\"name1\",\"damage\":\"VALUE1\"},{\"name\":\"name2\",\"damage\":\"VALUE2\"},{\"name\":\"name3\",\"damage\":\"VALUE3\"},{\"name\":\"name4\",\"damage\":\"VALUE4\"}],\"warriorImg\":\"src\\\\main\\\\java\\\\Images\\\\Chayanne.jpg\"}";
        //String JSON = "{\"weapon\":\"Knife\",\"warrior\":\"Penny Wise\",\"warriorImg\":\"src\\\\main\\\\java\\\\Images\\\\PennyWise.jpg\",\"attacked\":\"Daniel\",\"damageDone\":[{\"damage\":173,\"name\":\"Michael Myers\"},{\"damage\":91,\"name\":\"Chayanne\"},{\"damage\":121,\"name\":\"Penny Wise\"},{\"damage\":87,\"name\":\"Toledo\"}],\"element\":\"MAGIABLANCA\"}";
        String JSON = "setPlayerCharacteristics [{\"A1\":{\"damage\":[0.6520694906726254,0.30730826660782884,0.5671354468390217,0.24954168564507456,0.24497039500241904,0.7662538228204103,0.23387200662236962,0.5762682493851718,0.9819329836049073,0.8619707283709366]},\"A2\":{\"damage\":[0.3956097735076591,0.5343410310406959,0.31674547269510606,0.8538885258233124,0.7580406942006219,0.39010732766226863,0.9682187368668191,0.791449766190738,0.8036524045292386,0.9502958072940568]},\"A3\":{\"damage\":[0.6387888392544256,0.3400011022648679,0.7416087700473926,0.3433707689653243,0.873367011727983,0.9658588053944643,0.8572648213227796,0.22696726879730367,0.42428165990948474,0.8567448970675786]},\"A4\":{\"damage\":[0.6244266577451675,0.5104944755277019,0.7389383628632542,0.8858213752662476,0.20674784116259845,0.32652500827130326,0.4626052461621517,0.6845815355181892,0.46293512549654486,0.4541199593753887]},\"A5\":{\"damage\":[0.9495120409054734,0.6718882557172523,0.8837176773116837,0.2917701693468202,0.9012326372849169,0.2934804646116838,0.8268151397544432,0.7971654818514138,0.6021021053921234,0.3583005841061936]},\"name\":\"Warrior_1\"},{\"S3\":{\"damage\":[0.4908642052585874,0.7569524110173131,0.3933619940559887,0.27057789105896346,0.7193356955312387,0.6452168743232602,0.9846104271858724,0.9878204286516223,0.228327613285978,0.9669915683360029]},\"S4\":{\"damage\":[0.4338997017247514,0.7621523612322862,0.2513057159821339,0.9606424722604106,0.6320678656741802,0.22524258916008347,0.5212120191734766,0.89844193937677,0.24758783739521661,0.41028541614546593]},\"S5\":{\"damage\":[0.9283904543425601,0.20837161567692197,0.6813653410282912,0.8385621955844833,0.7572729436126246,0.5927339789523383,0.5165204910461064,0.737491681467553,0.9407177607730342,0.7896906842176012]},\"name\":\"NonWarrior2\",\"S1\":{\"damage\":[0.37942097832677074,0.3555356057899974,0.7972003696048013,0.6150265476741184,0.3659992368834415,0.9078364880954042,0.8592160218410754,0.5942234459784422,0.4324735763118658,0.46458810495161756]},\"S2\":{\"damage\":[0.31628514323350587,0.6485611137108847,0.9761690427912266,0.9507006880059874,0.6636808176940543,0.2555752751743895,0.954148165635299,0.7711572953208345,0.258010861823668,0.40448087647013087]}},{\"D4\":{\"damage\":[0.7140031277281695,0.2685316602376953,0.5411535203590205,0.33499307824829827,0.8295435586006117,0.7225700384115428,0.9630602447053223,0.8741676560964977,0.6510163462891854,0.5816326981688786]},\"D5\":{\"damage\":[0.6645796145319316,0.3244267963035292,0.8428925757234311,0.2085500234770474,0.8418872432883666,0.26228428182262803,0.6824444959194531,0.8609290340418294,0.5382429654977328,0.9984326697985486]},\"name\":\"NonWarrior3\",\"D1\":{\"damage\":[0.7680603981219905,0.36792805491620834,0.6591728066535066,0.29337707057215373,0.7381310248842858,0.6204965440346197,0.9251179085605317,0.44277366264916074,0.8083182009222487,0.24778797115509993]},\"D2\":{\"damage\":[0.531521623781317,0.4090462376311216,0.8512487572572496,0.9312647562944558,0.30632913109430193,0.7555980410644638,0.7195238647295863,0.43876212906042844,0.8032788106146553,0.4146082557242572]},\"D3\":{\"damage\":[0.47079825176662937,0.2104997202475868,0.7726270901899648,0.918362504011597,0.21359985462032569,0.6185496036841649,0.5936413818156165,0.9432123149591636,0.7092660717446377,0.8767385379167201]}},{\"name\":\"NonWarrior4\",\"F1\":{\"damage\":[0.7756659586194619,0.7692117659088595,0.8192714829282666,0.45052854532388853,0.7798587305928677,0.7790957960851175,0.3738662174714302,0.7021296226414133,0.7209949244918876,0.4724041392547661]},\"F2\":{\"damage\":[0.9823425540386685,0.3810112590705229,0.5572471697795676,0.3250224560448466,0.9692476126423466,0.5471211450264042,0.5283593747241074,0.5300532000761542,0.6590148968819378,0.5624624344569283]},\"F3\":{\"damage\":[0.9438122869617469,0.2088672070736835,0.7784338647328373,0.42222606062184403,0.4010189953085228,0.652264604911518,0.30068504750677694,0.4585255537437619,0.7880671618371655,0.5784884529220244]},\"F4\":{\"damage\":[0.8014731525427718,0.5851173805643632,0.21380577174338608,0.9643652484343597,0.79857159803787,0.5335224428927501,0.8213554809591892,0.6003781445410443,0.5718731219539053,0.23799457418063624]},\"F5\":{\"damage\":[0.479402870201767,0.34000966506805047,0.4118226003492522,0.45881392769372575,0.408920436802858,0.8582221681038424,0.7198890501089286,0.690413802556532,0.8912347682313524,0.40347554720755996]}}]";
        parseWeapons(null, JSON, "", null);
    }
}
