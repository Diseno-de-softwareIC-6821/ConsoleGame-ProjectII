/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSONParser;

import java.util.Iterator;
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
}
