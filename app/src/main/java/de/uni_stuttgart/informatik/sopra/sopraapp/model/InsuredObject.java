package de.uni_stuttgart.informatik.sopra.sopraapp.model;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEvent;

/**
 * @author Stefan Zindl
 * @since 2017/11/21
 *
 *
 */

public class InsuredObject {

    private User owner;
    private double area;
    private List<Double> coordinates;
    private List<DamageEvent> damageEvents;
    private User gutachter;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public List<DamageEvent> getDamageEvents() {
        return damageEvents;
    }

    public void setDamageEvents(List<DamageEvent> damageEvents) {
        this.damageEvents = damageEvents;
    }

    public User getGutachter() {
        return gutachter;
    }

    public void setGutachter(User gutachter) {
        this.gutachter = gutachter;
    }
}