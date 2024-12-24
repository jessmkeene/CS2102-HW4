
/**
 * A subclass of TempHumidReading
 */

public class SuperTempHumidReading extends TempHumidReading {

    /**
     * calls a String that states the temperature and reading
     * @param temperature in Fahrenheit
     * @param reading in percentage
     */
    public SuperTempHumidReading(double temperature, double reading) {
        super(temperature, reading);
    }
    /**
     * calls a String that states errors (-999)
     */
    public SuperTempHumidReading() {
        super(-999, -999);
    }

    /**
     * calls a String that states the temperature and reading
     * @param thr is a TempHumidReading
     */
    public SuperTempHumidReading(TempHumidReading thr) {
        super(thr.temperature, thr.humidity);
    }


    /**
     * overrides String to provide values of temperature, humidity, or error messages
     */

    public String toString(){
        if ((super.temperature==-999.0) && (super.humidity==-999.0))
        {return "{Err;Err}";}
        else if ((super.temperature==-999.0) && !(super.humidity==-999.0))
        {return "{Err;"+super.humidity+"%}";}
        else if (!(super.temperature==-999.0) && (super.humidity==-999.0))
        {return "{"+super.temperature+"F;Err}";}
        else{
            return "{"+super.temperature+"F;"+super.humidity+"%}";
        }

    }

    /**
     * overrides equals to have an tempHumidReading equal another TempHumidReading if they have the same temperature and humidity
     */
    @Override
    public boolean equals(Object o){
        if(! (o instanceof TempHumidReading)){
            return false;
        }
        TempHumidReading t = (TempHumidReading) o;
        return ((t.temperature==this.temperature) && (t.humidity == this.humidity));
    }


}