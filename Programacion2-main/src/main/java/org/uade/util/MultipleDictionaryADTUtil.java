package main.java.org.uade.util;

import main.java.org.uade.structure.definition.MultipleDictionaryADT;
import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.definition.SimpleDictionaryADT;

import java.util.Arrays;

public class MultipleDictionaryADTUtil {
    public static void print(MultipleDictionaryADT dic){
        SetADT keys = dic.getKeys();
        while (!keys.isEmpty()){
            int key = keys.choose();
            Object val = dic.get(key); // puede ser int[] u otra cosa
            if (val instanceof int[]) {
                System.out.println(key + "\t" + Arrays.toString((int[]) val));
            } else {
                System.out.println(key + "\t" + val);
            }
            keys.remove(key);
        }
    }
}
