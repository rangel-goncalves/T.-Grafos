package grafosatv2;

/**
 *
 * @author Rangel
 */
public class DistanceCalculation {

    public DistanceCalculation() {
    }
    public Double latAndLgnToDistance(double firstLatitude, double firstLongitude,double secondLatitude,double secondLongitude){
        double EARTH_RADIUS_KM = 6378.1;
        double firstLatToRad = Math.toRadians(firstLatitude);
        double secondLatToRad = Math.toRadians(secondLatitude);
// Diferença das longitudes
        double deltaLongitudeInRad = Math.toRadians(secondLongitude
                                    - firstLongitude);
        Double a = Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
                   * Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
                   * Math.sin(secondLatToRad))* EARTH_RADIUS_KM;
        return a;
    }

}
