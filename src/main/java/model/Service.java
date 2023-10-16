/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public enum Service {    
    xray(1, 10, "X-ray"),
    bloodTest(2, 20, "Blood Test");
    
    private final String label;
    private final int value1;
    private final int value2;

    private Service(int value1, int value2, String label) {
        this.label = label;
        this.value1 = value1;
        this.value2 = value2;
    }


    public String getLabel() {
        return label;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}
