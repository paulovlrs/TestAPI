package com.auxiliary;

import java.util.Random;

public class RandomAux {
    /**
     * Retorna uma sequência de string aleatória com total de caracteres pares,
     * se o valor limite for impar, será retornara o valor + 1
     * @param limit Se o limite for igual a 10
     * @return Retorna como exemplo o valor "a9f1v3a2H2"
     */
    public static String rndCaracterAndNumber(int limit) {
        Random rand = new Random();
        String something = "";

        for (int i = 0; i <= limit; i = i + 2) {
            int rnd = (int) (Math.random() * 52); // or use Random or whatever
            char base = (rnd < 26) ? 'A' : 'a';
            something = something
                    + String.valueOf((char) (base + rnd % 26))
                    + String.valueOf(rand.nextInt(10));
        }
        return something;
    }

    /**
     * Retorna uma sequência de números aleatórios do tipo String
     * de acordo com o limite informado
     * @param limit Se o limite for igual a 10
     * @return Retorna como exemplo o valor "8745982145"
     */
    public static String rndNumber(int limit) {
        Random rand = new Random();
        String something = "";
        for (int i = 0; i < limit; i++) {
            something = something
                    + String.valueOf(rand.nextInt(10));
        }
        return something;
    }
}
