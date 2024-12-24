import java.util.*;

/**
 * An abstract superclass to provide template methods for performance specific subclasses.
 */
public abstract class AbsGreenHouse implements QualityControlable{
    /**
     * a gregorian calender
     */
    GregorianCalendar calendar;
    /**
     * clones the calendar and puts into this calendar
     * @param calendar is a gregorian calender
     */
  public AbsGreenHouse (GregorianCalendar calendar) {
        this.calendar= (GregorianCalendar) calendar.clone();
    }

    /**
     * the linked list strategy
     */
   private ParsedDataStrategy strategy = new LinkedListStrategy();
    /**
     * amount of errors in list
     */
    private Double countErrors;
    /**
     * amount of values in list
     */
    private Double countData;
    /**
     * a Linked list of dates, temperature, and humidity readings from before the given date
     */
    private List<Double> newDataIn=new LinkedList<>();
    /**
     * a Linked list of dates, temperature, and humidity readings
     */
    private List<Double> dataIn=new LinkedList<>();


    /**
     * computes the current percentage of non-datetime sensor values that are -999.0s
     * @return a percent value between 0.0 and 100.0 inclusive
     */
    public double percentError() {
        countErrors=this.strategy.countErrors(newDataIn);
        countData=this.strategy.countData(newDataIn);
        return countErrors/countData*100;
    }

    /**
     * Reads an ordered sequence of data from the weather sensors to store in the greenhouse, ignores the data before
     * a certain a date, and updates the gregorian
     * with the oldest date in the data
     * When called multiple times, appends the new readings after the current sensor readings
     *
     * @param values An ordered sequence of [datetime, temperature, humidity, temperature, humidity, ..., datetime, temperature, humidity,....]
     *               - a date and time in yyyymmddHHMMSS format. E.g. 20231106183930 for Nov 11, 2023, 6:39:30pm
     *               - temperature is either degrees Fahrenheit or -999 for an error case
     *               - humidity is either % from 0.0 to 100.0 or -999 for an error case
     *               Assume the sensor data always starts with a valid date
     *               The multiple temperature humidity pairs for a single datetime come from different plant sensors
     *               The values may skip dates and times when the sensors are off (you cannot assume that the date/time intervals will be regular)
     *               You *may* assume that the datetimes will be in ascending order
     */
    public void pollSensorData(List<Double> values) {
        dataIn.addAll(values);
        newDataIn.addAll(strategy.getRidOf(dataIn,clockAsDatetime()));
        setCalender();

    }
    /**
     * sets a gregorian calendar to a certain date
     * @param datetime is a YYYYMMDDhhmmss.0
     */
    public void setClockTo(double datetime) {
        String datetimeStr = String.format("%.0f", datetime);

        int year = Integer.parseInt(datetimeStr.substring(0, 4));
        // Subtract 1 from month because GregorianCalendar months are 0-based
        int month = Integer.parseInt(datetimeStr.substring(4, 6)) - 1;
        int day = Integer.parseInt(datetimeStr.substring(6, 8));
        int hour = Integer.parseInt(datetimeStr.substring(8, 10));
        int minute = Integer.parseInt(datetimeStr.substring(10, 12));
        int second = Integer.parseInt(datetimeStr.substring(12, 14));
        this.calendar = new GregorianCalendar(year, month, day, hour, minute, second);
    }

    /**
     * sets a gregorian calendar to the most recent date in the data
     */
    public void setCalender(){
        Double date=this.strategy.getLastOf(clockAsDatetime());
        setClockTo(date);
    }

    /**
     * returns the calendar as a YYYYMMDDhhmmss.0
     */
    private double clockAsDatetime(){
        double year = calendar.get(Calendar.YEAR);
        double month = calendar.get(Calendar.MONTH) + 1;
        double day = calendar.get(Calendar.DAY_OF_MONTH);
        double hour = calendar.get(Calendar.HOUR_OF_DAY);
        double minute = calendar.get(Calendar.MINUTE);
        double second = calendar.get(Calendar.SECOND);
        return second +
                (minute * 100.0) + //shifted 2 decimal places
                (hour * 100.0 * 100.0) + //shifted 4 decimal places
                (day * 100.0 * 100.0 * 100.0) + //shifted 6 decimal places
                (month * 100.0 * 100.0 * 100.0 * 100.0) + //shifted 8 decimal places
                (year * 100.0 * 100.0 * 100.0 * 100.0 * 100.0); //shifted 10 decimal places
    }


    /**
     * parses, cleans, and sorts the data
     */
    public void process() {
        strategy.parseData(newDataIn,clockAsDatetime());
        strategy.cleanData();
        strategy.sortData();
    }

    /**
     * produces a pair of the middle temperature and humidity (a SuperTempHumidReading) from the stored readings ignoring error values (-999s)
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    public TempHumidReading middleReading() {
        return strategy.middleReading();

    }

    /**
     * produces a pair of the middle temperature and humidity (a SuperTempHumidReading) from the stored readings ignoring error values (-999s)
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    public TempHumidReading middleReading(double onDate) {
        return strategy.middleReading(onDate);
    }

    /**
     * sets a new strategy
     * @param otherStrategy is a ParseddataStrategy
     */
    void setStrategy(ParsedDataStrategy otherStrategy){
        this.strategy.setStrategy(otherStrategy);
        this.strategy=otherStrategy;
    }


}
