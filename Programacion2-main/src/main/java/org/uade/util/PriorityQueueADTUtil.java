package main.java.org.uade.util;

import main.java.org.uade.structure.definition.PriorityQueueADT;
import main.java.org.uade.structure.implementation.*;

public class PriorityQueueADTUtil {
    public static void print(PriorityQueueADT pq) {
        if (pq == null) throw new IllegalArgumentException("pq is null");
        PriorityQueueADT copy = copy(pq);
        while (!copy.isEmpty()) {
            System.out.println("Element: " + copy.getElement() + " - Priority: " + copy.getPriority());
            copy.remove();
        }
    }

    public static int countElements(PriorityQueueADT pq) {
        if (pq == null) throw new IllegalArgumentException("pq is null");
        int count = 0;
        PriorityQueueADT copy = copy(pq);
        while (!copy.isEmpty()) {
            copy.remove();
            count++;
        }
        return count;
    }

    public static PriorityQueueADT copy(PriorityQueueADT pq) {
        if (pq == null) throw new IllegalArgumentException("pq is null");

        PriorityQueueADT temp;
        PriorityQueueADT result;

        if (pq instanceof StaticPriorityQueueADT) {
            temp = new StaticPriorityQueueADT();
            result = new StaticPriorityQueueADT();
        } else if (pq instanceof DynamicPriorityQueueADT) {
            temp = new DynamicPriorityQueueADT();
            result = new DynamicPriorityQueueADT();
        } else {
            temp = new DynamicPriorityQueueADT();
            result = new DynamicPriorityQueueADT();
        }

        while (!pq.isEmpty()) {
            int elem = pq.getElement();
            int prio = pq.getPriority();
            pq.remove();
            temp.add(elem, prio);
        }

        // Restauramos pq y armamos result
        while (!temp.isEmpty()) {
            int elem = temp.getElement();
            int prio = temp.getPriority();
            temp.remove();
            pq.add(elem, prio);
            result.add(elem, prio);
        }

        return result;
        }
    }


