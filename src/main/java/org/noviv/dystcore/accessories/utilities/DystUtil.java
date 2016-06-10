package org.noviv.dystcore.accessories.utilities;

public class DystUtil {
    
    public static double limit(double min, double value, double max) {
        if (value <= min) {
            return min;
        } else if (value >= max) {
            return max;
        }
        return value;
    }

    private DystUtil() {
    }
}
