/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pukarsharma
 */
public enum CheckinType {
    MANUAL(1),
    WEBBASED(2),
    BIOMETRIC(3);

    private final int id;

    private CheckinType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
