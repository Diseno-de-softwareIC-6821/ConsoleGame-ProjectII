package org.diseno_de_softwareIC;

import Classes.ServerClasses.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
//        Server.getInstance();
//
//        String test = "[{\"name\":\"michael myers\",\"type\":\"FIRE\",\"image\":\"<path to image>\",\"weapons\":[\"knife\",\"gun\"]},{\"name\":\"michael myers\",\"type\":\"FIRE\",\"image\":\"<path to image>\",\"weapons\":[\"knife\",\"gun\"]}]";
//        JSONArray jsonObject = new JSONArray(test);
//
//        // loop through JSONArray
//        for(int i = 0; i < jsonObject.length(); i++){
//            JSONObject jsonobject = jsonObject.getJSONObject(i);
//            String name = jsonobject.getString("name");
//            String type = jsonobject.getString("type");
//            String image = jsonobject.getString("image");
//            JSONArray weapons = jsonobject.getJSONArray("weapons");
//
//            // loop array
//            String[] weaponArray = new String[weapons.length()];
//            for(int j = 0; j < weapons.length(); j++){
//                weaponArray[j] = weapons.getString(j);
//            }
//
//            System.out.println("name: " + name);
//            System.out.println("type: " + type);
//            System.out.println("image: " + image);
//            System.out.println("weapons: " + Arrays.toString(weaponArray));
//        }

        HashMap<Integer, Double> test = new HashMap<>();
        test.put(1, 1.0);
        test.put(2, 2.0);

        System.out.println(test.get(1)+1);



    }

}