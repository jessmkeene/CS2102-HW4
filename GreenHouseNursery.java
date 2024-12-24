import java.util.GregorianCalendar;
import java.util.List;
/**
 * A subclass of the superclass (AbsGreenHouse)
 */

public class GreenHouseNursery extends AbsGreenHouse implements Sensible{

    /**
     * calls on the gregorian calendar from the abstract superclass
     * @param calendar is a gregorian calender
     */

    public GreenHouseNursery(GregorianCalendar calendar) {
        super(calendar);
    }
    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */

    @Override
    public TempHumidReading middleReading() {
        process();
        return super.middleReading();
    }
    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    @Override
    public TempHumidReading middleReading(double onDate) {
        process();
        return super.middleReading(onDate);
    }
  
}
