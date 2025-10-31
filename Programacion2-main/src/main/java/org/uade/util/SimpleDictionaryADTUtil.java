package main.java.org.uade.util;

import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;

public class SimpleDictionaryADTUtil {
    public static void print(SimpleDictionaryADT dic){
        SetADT keys = dic.getKeys();
        while (!keys.isEmpty()){
            int key = keys.choose();
            System.out.println(key + "\t" + dic.get(key));
            keys.remove(key);
        }
    }
}
