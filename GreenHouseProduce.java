import java.util.GregorianCalendar;
import java.util.List;
/**
 * A subclass of the superclass (AbsGreenHouse)
 */
public class GreenHouseProduce extends AbsGreenHouse implements Sensible {

    /**
     * calls on the gregorian calendar from the abstract superclass
     * @param calendar is a gregorian calender
     */
    public GreenHouseProduce(GregorianCalendar calendar) {
        super(calendar);
    }

    /**
     * Reads an ordered sequence of data from the weather sensors to store in the greenhouse
     * When called multiple times, appends the new readings after the current sensor readings
     * @param values An ordered sequence of [datetime, temperature, humidity, temperature, humidity, ..., datetime, temperature, humidity,....]
     *               - a date and time in yyyymmddHHMMSS format. E.g. 20231106183930 for Nov 11, 2023, 6:39:30pm
     *               - temperature is either degrees Fahrenheit or -999 for an error case
     *               - humidity is either % from 0.0 to 100.0 or -999 for an error case
     *               Assume the sensor data always starts with a valid date
     *               The multiple temperature humidity pairs for a single datetime come from different plant sensors
     *               The values may skip dates and times when the sensors are off (you cannot assume that the date/time intervals will be regular)
     *               You *may* assume that the datetimes will be in ascending order
     */
    @Override
    public void pollSensorData(List<Double> values) {
        super.pollSensorData(values);
        process();
    }


}

