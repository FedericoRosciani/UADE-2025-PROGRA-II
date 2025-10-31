package main.java.org.uade.util;

import main.java.org.uade.structure.definition.QueueADT;
import main.java.org.uade.structure.definition.StackADT;
import main.java.org.uade.structure.implementation.DynamicQueueADT;
import main.java.org.uade.structure.implementation.DynamicStackADT;
import main.java.org.uade.structure.implementation.StaticQueueADT;
import main.java.org.uade.structure.implementation.StaticStackADT;

public class QueueADTUtil {
    public static void print(QueueADT queue){
        QueueADT copy = copy(queue);
        while (!copy.isEmpty()){
            System.out.println(copy.getElement());
            copy.remove();
        }
    }

    public static int countElements (QueueADT queue){
        int count = 0;
        QueueADT copy = copy(queue);
        while (!copy.isEmpty()){
            copy.remove();
            count++;
        }
        return count;
    }

    public static QueueADT copy (QueueADT queue){
        QueueADT temp, result;

        if (queue instanceof DynamicQueueADT) {
            temp   = new DynamicQueueADT();
            result = new DynamicQueueADT();
        } else {
            temp   = new StaticQueueADT();
            result = new StaticQueueADT();
        }
        while (!queue.isEmpty()){
            int element = queue.getElement();
            queue.remove();
            temp.add(element);
            result.add(element);
        }
        while (!temp.isEmpty()){
            queue.add(temp.getElement());
            temp.remove();
        }
        return result;
    }
}
