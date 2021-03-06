package org.example.follow.me.manager;

/**
 * This enum describes the different illuminance goals associated with the
 * manager.
 */
public enum IlluminanceGoal {
 
    /** The goal associated with soft illuminance. */
    SOFT(1, 1500d),
    /** The goal associated with medium illuminance. */
    MEDIUM(2, 8000d),
    /** The goal associated with full illuminance. */
    FULL(3, 12000d);
 
    /** The number of lights to turn on. */
    private int numberOfLightsToTurnOn;
    /** The number of lights to turn on. */
    private double illuminanceValue;
 
    /**
     * Gets the number of lights to turn On.
     * 
     * @return the number of lights to turn On.
     */
    public int getNumberOfLightsToTurnOn() {
        return numberOfLightsToTurnOn;
    }
 
    /**
     * Gets the number of lights to turn On.
     * 
     * @return the number of lights to turn On.
     */
    public double getIlluminanceValue() {
        return illuminanceValue;
    }
    
    /**
     * Instantiates a new illuminance goal.
     * 
     * @param numberOfLightsToTurnOn
     *            the number of lights to turn on.
     */
    private IlluminanceGoal(int numberOfLightsToTurnOn, double illuminanceValue) {
        this.numberOfLightsToTurnOn = numberOfLightsToTurnOn;
        this.illuminanceValue = illuminanceValue;
    }
}