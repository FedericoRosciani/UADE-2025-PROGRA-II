package main.java.org.uade.util;

import main.java.org.uade.structure.definition.QueueADT;
import main.java.org.uade.structure.definition.SetADT;
import main.java.org.uade.structure.implementation.DynamicQueueADT;
import main.java.org.uade.structure.implementation.DynamicSetADT;
import main.java.org.uade.structure.implementation.StaticQueueADT;
import main.java.org.uade.structure.implementation.StaticSetADT;

import java.util.Set;

public class SetADTUtil {
    //utilizar while para recorrer los elementos
    //usar exist para verificar si un elemento está en el conjunto
    //usar add para agregar elementos al conjunto resultado
    //generar metodos union, interseccion, diferencia, size, print, copy.

    public static SetADT union(SetADT a, SetADT b) {
        SetADT result = new StaticSetADT();
        SetADT tempA = copy(a);
        SetADT tempB = copy(b);
        while (!tempA.isEmpty()) {
            int element = tempA.choose();
            result.add(element);
            tempA.remove(element);
        }
        while (!tempB.isEmpty()) {
            int element = tempB.choose();
            result.add(element);
            tempB.remove(element);
        }
        return result;
    }
    public static SetADT interseccion(SetADT a, SetADT b) {
        SetADT result = new StaticSetADT();
        SetADT tempA = copy(a);
        while (!tempA.isEmpty()) {
            int element = tempA.choose();
            if (b.exist(element)) {
                result.add(element);
            }
            tempA.remove(element);
        }
        return result;
    }
    public static SetADT diferencia(SetADT a, SetADT b) {
        SetADT result = new StaticSetADT();
        SetADT tempA = copy(a);
        while (!tempA.isEmpty()) {
            int element = tempA.choose();
            if (!b.exist(element)) {
                result.add(element);
            }
            tempA.remove(element);
        }
        return result;
    }
    public static int size(SetADT set) {
        int count = 0;
        SetADT tempSet = copy(set);
        while (!tempSet.isEmpty()) {
            int element = tempSet.choose();
            tempSet.remove(element);
            count++;
        }
        return count;
    }
    public static void printSet(SetADT set) {
        SetADT tempSet = copy(set);
        System.out.print("{ ");
        while (!tempSet.isEmpty()) {
            int element = tempSet.choose();
            System.out.print(element + " ");
            tempSet.remove(element);
        }
        System.out.println("}");
    }
    public static SetADT copy(SetADT set) {
        SetADT result;
        if (set instanceof DynamicSetADT) {
            result = new DynamicSetADT();
        } else {
            result = new StaticSetADT();
        }
        SetADT tempSet = new StaticSetADT();
        while (!set.isEmpty()) {
            int element = set.choose();
            set.remove(element);
            tempSet.add(element);
            result.add(element);
        }
        while (!tempSet.isEmpty()) {
            int element = tempSet.choose();
            tempSet.remove(element);
            set.add(element);
        }
        return result;
    }

}
