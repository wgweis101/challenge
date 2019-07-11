package williamssonama.challenge.facade;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import williamssonama.challenge.model.ResponseDataTransferObject;
import williamssonama.challenge.service.RangeReducer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ResponseRequestIntermediaryTest {

    private static final Type responseDataTransferObjectType  = new TypeToken<ResponseDataTransferObject>(){}.getType();

    ResponseRequestIntermediary rri;
    RangeReducer rangeReducer;

    Gson gson;

    @Before
    public void init() {
        gson = new Gson();
        rangeReducer = new RangeReducer();

        rri = new ResponseRequestIntermediary(gson, rangeReducer);
    }

    @Test
    public void processRequestNullRangeTest() {
        ResponseDataTransferObject responseDataTransferObject = rri.processRequest(null);
    }

    @Test
    public void processRequestMalformedRangeTest() {
        String malformedList = "This is not a list of Integer tuples.";

        ResponseDataTransferObject responseDataTransferObject = rri.processRequest(malformedList);

        assertTrue("Non-empty result list found for malformed list.  Expected empty result.", !responseDataTransferObject.hasElements());
    }

    @Test
    public void processRequestMalformedRangeSizerTest() {
        List<Integer[]> inputList = new ArrayList<>();
        inputList.add(new Integer[] {1, 2, 3, 4});

        String gsonList = gson.toJson(inputList);

        ResponseDataTransferObject responseDataTransferObject = rri.processRequest(gsonList);

        assertTrue("Non-empty result list found for malformed list.  Expected empty result.", !responseDataTransferObject.hasElements());
    }

    @Test
    public void processRequestOneRangeTest() {
        List<Integer[]> inputList = new ArrayList<>();
        inputList.add(new Integer[] {1, 2});
        inputList.add(new Integer[] {3, 4});
        inputList.add(new Integer[] {2, 5});
        inputList.add(new Integer[] {0, 6});

        List<Integer[]> expectedList = new ArrayList<>();
        expectedList.add(new Integer[] {0, 6});

        String gsonList = gson.toJson(inputList);

        ResponseDataTransferObject responseDataTransferObject = rri.processRequest(gsonList);

        assertTrue("Expected non-empty result back", responseDataTransferObject.hasElements());
        assertTrue("Expected Result size of 1, found result size of " +responseDataTransferObject.getElements().size(), responseDataTransferObject.getElements().size() == 1);

        for (int i = 0; i < expectedList.size(); i++) {
            Integer[] expected = expectedList.get(i);
            Integer[] actual = responseDataTransferObject.getElements().get(i);

            assertTrue("The expected start result differs from the actual start result at position " + i + ".  The expected result was " + expected[0] + ".  The actual result was " + actual[0] + ".", expected[0] == actual[0]);
            assertTrue("The expected end result differs from the actual end result at position " + i  + ".  The expected result was " + expected[1] + ".  The actual result was " + actual[1] + ".", expected[1] == actual[1]);
        }
    }

    @Test
    public void processRequestTwoRangeTest() {

        List<Integer[]> inputList = new ArrayList<>();
        inputList.add(new Integer[] {0, 3});
        inputList.add(new Integer[] {2, 5});
        inputList.add(new Integer[] {6, 10});
        inputList.add(new Integer[] {7, 8});

        List<Integer[]> expectedList = new ArrayList<>();
        expectedList.add(new Integer[] {0, 5});
        expectedList.add(new Integer[] {6, 10});

        String gsonList = gson.toJson(inputList);

        ResponseDataTransferObject responseDataTransferObject = rri.processRequest(gsonList);

        assertTrue("Expected non-empty result back", responseDataTransferObject.hasElements());
        assertTrue("Expected Result size of 2, found result size of " +responseDataTransferObject.getElements().size(), responseDataTransferObject.getElements().size() == 2);

        for (int i = 0; i < expectedList.size(); i++) {
            Integer[] expected = expectedList.get(i);
            Integer[] actual = responseDataTransferObject.getElements().get(i);

            assertTrue("The expected start result differs from the actual start result at position " + i + ".  The expected result was " + expected[0] + ".  The actual result was " + actual[0] + ".", expected[0] == actual[0]);
            assertTrue("The expected end result differs from the actual end result at position " + i  + ".  The expected result was " + expected[1] + ".  The actual result was " + actual[1] + ".", expected[1] == actual[1]);
        }
    }

    @Test
    public void processRequestNoRangeTest() {

        List<Integer[]> inputList = new ArrayList<>();
        inputList.add(new Integer[] {0, 0});
        inputList.add(new Integer[] {0, 0});
        inputList.add(new Integer[] {0, 0});
        inputList.add(new Integer[] {0, 0});

        List<Integer[]> expectedList = new ArrayList<>();
        expectedList.add(new Integer[] {0, 0});

        String gsonList = gson.toJson(inputList);

        ResponseDataTransferObject responseDataTransferObject = rri.processRequest(gsonList);

        assertTrue("Expected non-empty result back", responseDataTransferObject.hasElements());
        assertTrue("Expected Result size of 1, found result size of " +responseDataTransferObject.getElements().size(), responseDataTransferObject.getElements().size() == 1);

        for (int i = 0; i < expectedList.size(); i++) {
            Integer[] expected = expectedList.get(i);
            Integer[] actual = responseDataTransferObject.getElements().get(i);

            assertTrue("The expected start result differs from the actual start result at position " + i + ".  The expected result was " + expected[0] + ".  The actual result was " + actual[0] + ".", expected[0] == actual[0]);
            assertTrue("The expected end result differs from the actual end result at position " + i  + ".  The expected result was " + expected[1] + ".  The actual result was " + actual[1] + ".", expected[1] == actual[1]);
        }
    }

}