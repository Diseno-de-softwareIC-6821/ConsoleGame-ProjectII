/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONParser;

import java.awt.Label;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JTable;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Daniel
 */
public class JSONParser {
    
    
    public static void parseWeapons(JTable tableParse, String JSON, String warriorName){
        JSONArray classJSON = new JSONArray(JSON.split(" ")[1].replace("_", " "));
        //System.out.println(classJSON.toString());
        //System.out.println(classJSON.toString());
        for (int i = 0; i < classJSON.length(); i++){
            JSONObject actClass = classJSON.getJSONObject(i);
            String actName = actClass.getString("name");
            if (actName.equals(warriorName) || warriorName.equals("")){
                Iterator<String> it = actClass.keys();
                int k = 0;
                while(it.hasNext()){
                    String weaponName = it.next();
                    tableParse.setValueAt(weaponName, k, 0);
                    //System.out.println(weaponName);
                    if (weaponName.equals("name"))
                        continue;
                    JSONArray actDamages = actClass.getJSONObject(weaponName).getJSONArray("damage");
                    for (int j = 0; j < 10; j++){
                        //System.out.println(actDamages.get(j));
                        tableParse.setValueAt(actDamages.get(j), k, j+1);
                    }
                    k++;
                }  
                break;
            }   
        }
    }
    
    public static String parseAttackedBy(JLabel lblAttackedByText, JLabel lblAttackedByText1, JLabel lblAttackedByStats1,
            JLabel lblAttackedByStats2, JLabel lblAttackedByStats3, JLabel lblAttackedByStats4, JLabel lblLifeChar1
            , JLabel lblLifeChar2, JLabel lblLifeChar3, JLabel lblLifeChar4, JLabel lblAttackedByImg, String JSON){
        JSONObject objectJSON = new JSONObject(JSON);
        lblAttackedByText.setText("Attacked by " + objectJSON.getString("attacked") + " with " + objectJSON.getString("warrior") + " [" + objectJSON.getString("element") + "]");
        lblAttackedByText1.setText("Weapon: " + objectJSON.getString("weapon"));
        JSONObject actWar = objectJSON.getJSONObject("damageDone");
        System.out.println(actWar.toString());
        System.out.println(objectJSON.toString());
        System.out.println("Attacked by " + objectJSON.getString("attacked") + " with " + objectJSON.getString("warrior") + " [" + objectJSON.getString("element") + "]");
        System.out.println("Weapon: " + objectJSON.getString("weapon"));
        for (int i = 1; i < 5; i++){
            JSONArray actWarriorInfo = actWar.getJSONArray("W" + i);
            String warriorName = actWarriorInfo.getString(0);
            String damage = actWarriorInfo.getString(1);
            String[] getFirstLetter = warriorName.split(" ");
            warriorName = "";
            for (int j = 0; j < getFirstLetter.length;j++){
                warriorName += getFirstLetter[j].charAt(0);
            }
            switch(i){
                case 1 ->{
                    lblAttackedByStats1.setText(warriorName + ": -" + damage);
                    lblLifeChar1.setText(Integer.toString(Integer.parseInt(lblLifeChar1.getText()) - Integer.parseInt(damage)));
                    
                }
                case 2 ->{
                    lblAttackedByStats2.setText(warriorName + ": -" + damage);
                    lblLifeChar2.setText(Integer.toString(Integer.parseInt(lblLifeChar2.getText()) - Integer.parseInt(damage)));
                }
                case 3 ->{
                    lblAttackedByStats3.setText(warriorName + ": -" + damage);
                    lblLifeChar3.setText(Integer.toString(Integer.parseInt(lblLifeChar3.getText()) - Integer.parseInt(damage)));
                }
                case 4 ->{
                    lblAttackedByStats4.setText(warriorName + ": -" + damage);
                    lblLifeChar4.setText(Integer.toString(Integer.parseInt(lblLifeChar4.getText()) - Integer.parseInt(damage)));
                }
            }
            System.out.println(warriorName + ": -" + damage);
        }
        return objectJSON.getString("warriorImg"); 
    }
    
    public static String parseAttacking(JLabel lblAttackingText1, JLabel lblAttackingText2,
                JLabel lblAttackingDamage, JLabel lblAttackingImg, String JSON){
        JSONObject objectJSON = new JSONObject(JSON);
        lblAttackingText1.setText("Attacked by " + objectJSON.getString("attacked") + " with " + objectJSON.getString("warrior") + " [" + objectJSON.getString("element") + "]");
        lblAttackingText2.setText("Weapon: " + objectJSON.getString("weapon"));
        lblAttackingDamage.setText("- " + objectJSON.getString("damageDone"));
        return objectJSON.getString("warriorImg");
    }
    
     public static void main(String[] args) {
        String JSON = "{\"attacked\":\"Player 1\", \"warrior\":\"SUB ZERO\",\"element\":\"ICE\",\"weapon\":\"Ice Shoot\",\"damageDone\":{\"W1\":[\"name1 hola\",\"VALUE1\"],\"W2\":[\"name2\",\"VALUE2\"],\"W3\":[\"name3\",\"VALUE3\"],\"W4\":[\"name4\",\"VALUE4\"]},\"warriorImg\":\"src\\\\main\\\\java\\\\Images\\\\Chayanne.jpg\"}";
         System.out.println(parseAttackedBy(null, null, null, null, null, null, null, null, null, null, null, JSON));
    }
}
