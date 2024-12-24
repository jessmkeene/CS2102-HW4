import java.util.LinkedList;



/**
 * A date_Time DTO for temperature, dates, and humidity sensor data
 */
public class Date_Time {



    /**
     * Temperature in Fahrenheit
     */
    public LinkedList<Double> temp;
    /**
     * Temperature in percentage
     */
    public LinkedList<Double> humidity;
    /**
     * a date YYYYMMDDhhmmss.0
     */
    public Double date_time;
    /**
     * A date_Time
     * @param temp in Fahrenheit
     * @param humidity in percentage
     * @param date_time in YYYYMMDDhhmmss.0
     */
    public Date_Time(Double date_time, LinkedList<Double> temp, LinkedList<Double> humidity){
        this.temp = temp;
        this.humidity = humidity;
        this.date_time=date_time;
    }
}