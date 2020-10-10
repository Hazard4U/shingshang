package libs;

public class Tuple<F,S> {
    private F firstElement;
    private S secondElement;

    public Tuple(F firstElement, S secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public F getFirstElement() {
        return firstElement;
    }

    public S getSecondElement() {
        return secondElement;
    }

    public void setFirstElement(F firstElement) {
        this.firstElement = firstElement;
    }

    public void setSecondElement(S secondElement) {
        this.secondElement = secondElement;
    }
}
