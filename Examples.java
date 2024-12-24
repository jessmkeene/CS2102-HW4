import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Examples {
    @Test
    public void testOkay() {assertTrue(true);}


    @Test
    public void testPercentErrorNoErrors() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20221104023042.0,35.0,34.0,34.5,34.5,
                20221104023010.0,25.0,34.0,10.0,34.5,
                20221104023012.0,35.0,0.0,34.5,100.0));
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(0.0,ghn.percentError(),0.0);
     assertEquals(0.0,ghp.percentError(),0.0);
    }




    @Test
    public void testPercentErrors() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        LinkedList<Double> data = new LinkedList<>();
        data.addAll(List.of(20221104023042.0,-999.0,-999.0,
                20221104023010.0,-999.0,-999.0,
                20221104023012.0,-999.0,-999.0));
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(100.0,ghn.percentError(),0.0);
        assertEquals(100.0,ghp.percentError(),0.0);
    }





    @Test
    public void testPercentError50() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20221104023042.0,60.0,-999.0,
                20221104023010.0,4.0,20.0,
                20221104023012.0,-999.0,-999.0));
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(50.0,ghn.percentError(),0.0);
        assertEquals(50.0,ghp.percentError(),0.0);
    }


    @Test
    public void testPercentError50Date() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20221104023042.0,60.0,-999.0,
                20221104023010.0,4.0,20.0,
                20221104023012.0,-999.0,-999.0));
        calendar.set(2023,11,15,1,2,3);
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(50.0,ghn.percentError(),0.0);
       assertEquals(50.0,ghp.percentError(),0.0);

    }


    @Test
    public void testPollDataOnlyCurrent() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20211104023042.0,1.0,10.0,2.0,20.0,
                20221104023010.0,3.0,30.0,
                20221104023012.0,4.0,40.0,5.0,50.0));
        SuperTempHumidReading correct = new SuperTempHumidReading(4.0,40.0);
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(correct,ghn.middleReading());
        assertEquals(correct,ghp.middleReading());
    }





//Array



    @Test
    public void testPercentErrorNoErrorsArray() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ghn.setStrategy(new ArrayListStrategy());
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20221104023042.0,35.0,34.0,34.5,34.5,
                20221104023010.0,25.0,34.0,10.0,34.5,
                20221104023012.0,35.0,0.0,34.5,100.0));
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(0.0,ghn.percentError(),0.0);
        assertEquals(0.0,ghp.percentError(),0.0);
    }




    @Test
    public void testPercentErrorsArray() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ghn.setStrategy(new ArrayListStrategy());
        LinkedList<Double> data = new LinkedList<>();
        data.addAll(List.of(20221104023042.0,-999.0,-999.0,
                20221104023010.0,-999.0,-999.0,
                20221104023012.0,-999.0,-999.0));
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(100.0,ghn.percentError(),0.0);
        assertEquals(100.0,ghp.percentError(),0.0);
    }





    @Test
    public void testPercentError50Array() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ghn.setStrategy(new ArrayListStrategy());
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20221104023042.0,60.0,-999.0,
                20221104023010.0,4.0,20.0,
                20221104023012.0,-999.0,-999.0));
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(50.0,ghn.percentError(),0.0);
        assertEquals(50.0,ghp.percentError(),0.0);
    }


    @Test
    public void testPercentError50DateArray() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ghn.setStrategy(new ArrayListStrategy());
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20221104023042.0,60.0,-999.0,
                20221104023010.0,4.0,20.0,
                20221104023012.0,-999.0,-999.0));
        calendar.set(2023,11,15,1,2,3);
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(50.0,ghn.percentError(),0.0);
        assertEquals(50.0,ghp.percentError(),0.0);

    }


    @Test
    public void testPollDataOnlyCurrentArray() {
        GregorianCalendar calendar = new GregorianCalendar(
                2022, Calendar.APRIL,1,23,59,0);
        GreenHouseNursery ghn = new GreenHouseNursery(calendar);
        GreenHouseProduce ghp = new GreenHouseProduce(calendar);
        ghn.setStrategy(new ArrayListStrategy());
        ArrayList<Double> data = new ArrayList<>(Arrays.asList(20211104023042.0,1.0,10.0,2.0,20.0,
                20221104023010.0,3.0,30.0,
                20221104023012.0,4.0,40.0,5.0,50.0));
        SuperTempHumidReading correct = new SuperTempHumidReading(4.0,40.0);
        ghn.pollSensorData(data);
        ghp.pollSensorData(data);


        assertEquals(correct,ghn.middleReading());
        assertEquals(correct,ghp.middleReading());
    }




}








