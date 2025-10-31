package main.java.org.uade.structure.definition;

public interface ColaRecargadaTDA extends QueueADT {
    SetADT pares();
    int maximo();
    double promedio();
    void multiplicar(int factor);
}
