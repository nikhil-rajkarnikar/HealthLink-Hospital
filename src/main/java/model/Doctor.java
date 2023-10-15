/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pukarsharma
 */

public enum Doctor {
    DrAsthana(1, 80, 90, "Dr. Asthana"),
    DrMunna(2, 60, 65, "Dr. Munna"),
    DrSuman(3, 55, 50, "Dr. Suman");

    private final int id;
    private final int normalRate;
    private final int weekEndRate;
    private final String name;
    private final List<String> availableTimes;

    private Doctor(int id, int normalRate, int weekEndRate, String name) {
        this.id = id;
        this.normalRate = normalRate;
        this.weekEndRate = weekEndRate;
        this.name = name;
        this.availableTimes = new ArrayList<>();
    }

    public void addAvailableTimesIn30MinuteIntervals(String startTime, String endTime) {
        String currentTime = startTime;
        while (currentTime.compareTo(endTime) <= 0) {
            availableTimes.add(currentTime);
            currentTime = incrementTimeBy30Minutes(currentTime);
        }
    }

    private String incrementTimeBy30Minutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        minutes += 30;
        if (minutes >= 60) {
            hours++;
            minutes -= 60;
        }

        return String.format("%02d:%02d", hours, minutes);
    }

    public int getId() {
        return id;
    }

    public int getNormalRate() {
        return normalRate;
    }

    public int getWeekEndRate() {
        return weekEndRate;
    }

    public String getName() {
        return name;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }
}
