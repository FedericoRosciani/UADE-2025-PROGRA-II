package main.java.org.uade.util;

import main.java.org.uade.structure.definition.LinkedListADT;
import main.java.org.uade.structure.implementation.DynamicLinkedListADT;
import main.java.org.uade.structure.implementation.StaticLinkedListADT;

public class LinkedListADTUtil {
    public static LinkedListADT createLinkedListADT (LinkedListADT list){
        LinkedListADT result = null;
        if (list instanceof DynamicLinkedListADT){
            result = new DynamicLinkedListADT();
        }else{
            result = new StaticLinkedListADT();
        }
        return result;
    }

    public static LinkedListADT imprimir (LinkedListADT lista){
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
        return lista;
    }
}
