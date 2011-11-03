package com.tor.mock_test;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 02.02.2010
 * Time: 18:56:29
 * Not Default Constructor
 */
public class Collaborator_NDC {
    private String collabratorStr;
    private int collabratorInt;

    /**
     * Collaborator требует строку и примитивный тип int в качестве параметров, передаваемых в конструктор
     * Дeфолтового конструктора НЕТ!
     */
    public Collaborator_NDC(int i, String s) {
        collabratorStr = s;
        collabratorInt = i;
    }

    public String executeJob() {
        return "success";
    }
}
