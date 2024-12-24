import java.util.List;
/** An interface to build a strategy */
public interface ParsedDataStrategy {
    /**
     * counts the number of humidities and temperatures in the list
     * @param dataIn is a list of temperatures, humidities, and dates
     * @return the number of humdities and temperatures
     */
    public Double countData (List<Double> dataIn);
    /**
     * gets rid of all dates, temperatures  and humidity readings before the given date
     * @param dataIn is a list of temperatures, humidities, and dates
     * @param date_calender is a calendar date as YYYYMMDDhhmmss.0
     * @return a list of dates, humdities, and temperatures after the given date
     */
    public List<Double> getRidOf(List<Double> dataIn, double date_calender);
    /**
     * finds the last date from the list dataIn
     * @param date_calender is a calendar date as YYYYMMDDhhmmss.0
     * @return the date
     */
    public Double getLastOf(Double date_calender);
    /**
     * counts the errors in the list
     * @param dataIn is a list of temperatures, humidities, and dates
     * @return the number of errors
     */
    public Double countErrors (List<Double> dataIn);
    /**
     * parses the data, parses the data from dataIn into a linked list of temperatures, humidities, and a list of Date_time objects
     * @param dataIn is the unparsed data
     * @param date_calender is a calendar date as YYYYMMDDhhmmss.0
     */
    public void parseData (List<Double> dataIn, Double date_calender);
    /**
     * cleans the data, removes all the errors (-999) from the data
     */
    public void cleanData();
    /**
     * sorts the data, puts the data (allTemps, allHumids, and Data) in order from smallest to largest
     */
    public void sortData ();
    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    public TempHumidReading middleReading();
    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    public TempHumidReading middleReading(double onDate);
    /**
     * grabs the dataIn
     * @param dataIn is the list of data
     */
    public void getDataIn(List<Double> dataIn);
    /**
     * sets a new strategy
     * @param strategy is a ParseddataStrategy
     */
    public void setStrategy(ParsedDataStrategy strategy);
}
