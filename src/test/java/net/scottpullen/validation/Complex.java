package net.scottpullen.validation;

import java.util.List;

public class Complex {
    private List<Simple> simples;

    public Complex(List<Simple> simples) {
        this.simples = simples;
    }

    public List<Simple> getSimples() { return simples; }
}
