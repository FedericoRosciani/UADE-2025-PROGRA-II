package main.java.org.uade.util;

import main.java.org.uade.structure.definition.StackADT;
import main.java.org.uade.structure.implementation.DynamicStackADT;
import main.java.org.uade.structure.implementation.StaticStackADT;

public class StackADTUtil {
    public static StackADT copy(StackADT stack) {
        StackADT temp, result;

        if (stack instanceof DynamicStackADT) {
            temp   = new DynamicStackADT();
            result = new DynamicStackADT();
        } else {
            temp   = new StaticStackADT();
            result = new StaticStackADT();
        }

        // 1) paso a temp (invierto)
        while (!stack.isEmpty()) {
            int v = stack.getElement();
            stack.remove();
            temp.add(v);
        }
        // 2) restauro 'stack' y, a la vez, armo 'result' en el mismo orden original
        while (!temp.isEmpty()) {
            int v = temp.getElement();
            temp.remove();
            stack.add(v);   // restaura la pila original
            result.add(v);  // copia en el mismo orden
        }
        return result;
    }


    public static void imprimir(StackADT stack) {
        StackADT c = copy(stack);
        while (!c.isEmpty()) {
            System.out.println(c.getElement());
            c.remove();
        }
    }

    public static int countElements (StackADT stack){
        StackADT c = copy(stack);
        int count = 0;
        while (!c.isEmpty()) {
            count++;
            c.remove();
        }
        return count;
    }

}
