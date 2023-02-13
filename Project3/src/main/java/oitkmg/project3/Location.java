package oitkmg.project3;

/**
 * Used to represent a gym location object. A Location object represents
 * a gym location town, zipcode, and county. The only valid inputs
 * for this class is EDISON, PISCATAWAY, BRIDGEWATER, FRANKLIN,
 * and SOMERVILLE.
 * @author Kevin George, Omar Talaat
 */
public enum Location {

    /**
     * Edison, New Jersey 08837
     */
    EDISON("EDISON", "08837", "MIDDLESEX"),
    /**
     * Piscataway, New Jersey 08854
     */
    PISCATAWAY("PISCATAWAY", "08854", "MIDDLESEX"),
    /**
     * Bridgewater, New Jersey 08807
     */
    BRIDGEWATER("BRIDGEWATER", "08807", "SOMERSET"),
    /**
     * Franklin, New Jersey 08873
     */
    FRANKLIN("FRANKLIN", "08873", "SOMERSET"),
    /**
     * Somerville, New Jersey 08876
     */
    SOMERVILLE("SOMERVILLE", "08876", "SOMERSET");

    /**
     * Town of gym location.
     */
    private final String town;
    /**
     * Zipcode of gym location.
     */
    private final String zipCode;
    /**
     * County of gym location.
     */
    private final String county;

    /**
     * Instantiates the values of a gym location's town, zipcode,
     * nd county.
     * @param town the gym location's town.
     * @param zipCode the gym location's zipcode.
     * @param county the gym location's county.
     */
    Location(String town, String zipCode, String county){
        this.town = town;
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Gets the town of this location.
     * @return a String representing the town of this location.
     */
    public String getTown(){
        return town;
    }

    /**
     * Gets a string of a Location. The string will consist of
     * the gym location's town, zipcode, and county.
     * @return a string the represents a location.
     */
    @Override
    public String toString(){
        return town + ", " + zipCode + ", " + county;
    }
}
