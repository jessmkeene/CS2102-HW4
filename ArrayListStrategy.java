import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * A strategy for the green house classes
 */
public class ArrayListStrategy implements ParsedDataStrategy{
    /**
     * Assume a sensor value is a date if it is greater jan 01, 1970 00:00:00 represented as a double
     *
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */

        public  boolean isDateTime(double sensorDatum) {
            return sensorDatum > 19700101000000.0;
        }
    /**
     * Converts the double date time format to just the date part by dividing and rounding
     *
     * @param dateTime YYYYMMDDhhmmss.0
     * @return YYYYMMDD.0
     */
        public double toDate(double dateTime) {
            return Math.floor(dateTime / 1000000.0); // convert YYYYMMDDhhmmss -> YYYYMMDD
        }
    /**
     * compares two YYYYMMDD.0 for equality
     *
     * @param date1 one YYYYMMDD.0
     * @param date2 another YYYYMMDD.0
     * @return true if they are within some error tolerance (0.001) of each other
     */
        public  boolean sameDate(double date1, double date2) {
            return Math.abs(date1 - date2) < 0.001;
        }
    /**
     * a Linked list of dates, temperature, and humidity readings after a given date
     */
        private ArrayList<Double> newDataIn=new ArrayList<>();
    /**
     * a Linked list of temperature readings
     */
        private ArrayList<Double> allTemps=new ArrayList<>();
    /**
     * a Linked list of humidity readings
     */

        private ArrayList<Double> allHumids=new ArrayList<>();
    /**
     * a Linked list of dates, temperature, and humidity readings
     */
        private ArrayList<Double> DataIn = new ArrayList<>();
    /**
     * a Linked list of Date_Time objects
     */
        private ArrayList<Date_Time_Array> Data=new ArrayList<Date_Time_Array>();
    /**
     * counts the number of humidities and temperatures in the list
     * @param dataIn is a list of temperatures, humidities, and dates
     * @return the number of humdities and temperatures
     */

        @Override
        public Double countData(List<Double> dataIn) {
            double count=0;
            for (Double d: dataIn){
                if(!(isDateTime(d))) {
                    count=count+1;
                }}
            return  count;
        }
    /**
     * gets rid of all dates, temperatures  and humidity readings before the given date
     * @param dataIn is a list of temperatures, humidities, and dates
     * @param date_calender is a calendar date as YYYYMMDDhhmmss.0
     * @return a list of dates, humdities, and temperatures after the given date
     */
        @Override
        public List<Double> getRidOf(List<Double> dataIn, double date_calender) {
            {int given_place = -1;
                for(int d=0; d<dataIn.size(); d++ ) {
                    if (dataIn.get(d) >= date_calender) {
                        given_place = d;
                        break;
                    }
                }
                if(given_place==-1) {
                    newDataIn.clear();
                }
                else{
                    for(int d=given_place; d< dataIn.size(); d++){
                        newDataIn.add(dataIn.get(d));


                    }}}
            return newDataIn;
        }
    /**
     * finds the last date from the list dataIn
     * @param date_calender is a calendar date as YYYYMMDDhhmmss.0
     * @return the date
     */
        @Override
        public Double getLastOf( Double date_calender) {
            Double date =date_calender;
            if(!(newDataIn.isEmpty())){
                for (Double d: newDataIn){
                    if(isDateTime(d)){
                        date=d;
                    }
                }

            }
            return date;
        }
    /**
     * counts the errors in the list
     * @param dataIn is a list of temperatures, humidities, and dates
     * @return the number of errors
     */
        @Override
        public Double countErrors(List<Double> dataIn) {
            double Err_count=0;
            for (Double d: dataIn){
                if(d==-999){
                    Err_count=1+Err_count;
                }
            }
            return Err_count;

        }
    /**
     * parses the data, parses the data from dataIn into a linked list of temperatures, humidities, and a list of Date_time objects
     * @param dataIn is the unparsed data
     * @param date_calender is a calendar date as YYYYMMDDhhmmss.0
     */
        @Override
        public void parseData(List<Double> dataIn, Double date_calender) {
            getRidOf(dataIn,date_calender);
            if (newDataIn.isEmpty())
            {}
            else{
                Data.add(new Date_Time_Array(newDataIn.get(0), new ArrayList<>(), new ArrayList<>()));
                for (Double d : newDataIn) {
                    if (isDateTime(d)) {
                        if (!(sameDate(toDate(d), toDate(Data.get(Data.size() - 1).date_time)))) {
                            Data.add(new Date_Time_Array(d, new ArrayList<>(), new ArrayList<>()));
                        }
                    } else if (Data.get(Data.size() - 1).humidity.isEmpty() && Data.get(Data.size() - 1).temp.isEmpty()) {
                        Data.get(Data.size() - 1).temp.add(d);
                        allTemps.add(d);
                    } else if (Data.get(Data.size() - 1).temp.size() > Data.get(Data.size() - 1).humidity.size()) {
                        Data.get(Data.size() - 1).humidity.add(d);
                        allHumids.add(d);
                    } else {
                        Data.get(Data.size() - 1).temp.add(d);
                        allTemps.add(d);
                    }
                }
            }

        }
    /**
     * sorts the data, puts the data (allTemps, allHumids, and Data) in order from smallest to largest
     */
        @Override
        public void sortData() {
            allTemps.sort(Double::compareTo);
            allHumids.sort(Double::compareTo);


            for (Date_Time_Array d : Data) {
                d.humidity.sort(Double::compareTo);
                d.temp.sort(Double::compareTo);


            }

        }
    /**
     * cleans the data, removes all the errors (-999) from the data
     */
        @Override
        public void cleanData() {
            allTemps.removeIf((number)->number==-999);
            allHumids.removeIf((number)->number==-999);
            for (Date_Time_Array d : Data) {
                d.humidity.removeIf((number)->number==-999);
                d.temp.removeIf((number)->number==-999);
            }

        }
    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     *         and the middle humidity of the sorted humidities
     *         If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
        @Override
        public TempHumidReading middleReading() {
            if ((allHumids).isEmpty() && allTemps.isEmpty()) {
                return new SuperTempHumidReading();
            } else {
                Double t = allTemps.get(allTemps.size() / 2);
                Double h = allHumids.get(allHumids.size() / 2);
                return new SuperTempHumidReading(t,h);
            }
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
            for (Date_Time_Array d: Data){
                if(toDate(d.date_time)==onDate) {
                    Double t = d.temp.get(d.temp.size() / 2);
                    Double h = d.humidity.get(d.humidity.size() / 2);
                    return new SuperTempHumidReading(t,h);
                }}
            return new SuperTempHumidReading();
        }
    /**
     * sets a new strategy
     * @param strategy is a ParseddataStrategy
     */

        @Override
        public void setStrategy(ParsedDataStrategy strategy) {
            strategy.getDataIn(this.DataIn);
            this.DataIn.clear();
        }
    /**
     * grabs the dataIn
     * @param dataIn is the list of data
     */
        @Override
        public void getDataIn(List<Double> dataIn) {
            this.DataIn.addAll(dataIn);
        }
    }


