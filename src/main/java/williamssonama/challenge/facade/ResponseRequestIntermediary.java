package williamssonama.challenge.facade;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import williamssonama.challenge.model.RANGE_TYPE;
import williamssonama.challenge.model.RangeTuple;
import williamssonama.challenge.model.ResponseDataTransferObject;
import williamssonama.challenge.service.RangeReducer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseRequestIntermediary {

    private static final Type rangeListType  = new TypeToken<List<Integer[]>>(){}.getType();

    private Gson gson;

    private RangeReducer rangeReducer;

    @Autowired
    public ResponseRequestIntermediary(Gson gson, RangeReducer rangeReducer) {
        this.gson = gson;
        this.rangeReducer = rangeReducer;
    }

    public ResponseDataTransferObject processRequest(String gsonList) {
        ResponseDataTransferObject responseDataTransferObject = new ResponseDataTransferObject();

        try {

            List<Integer[]> list = gson.fromJson(gsonList, rangeListType);

            List<RangeTuple> rangeTupleList = getSortedList(list, responseDataTransferObject);
            List<Integer[]> processedList = rangeReducer.reduceRangeList(rangeTupleList);
            responseDataTransferObject.setElements(processedList);

        } catch (JsonSyntaxException jse) {
            responseDataTransferObject.addError("Invalid Range Array List.  Could not process. " + (gsonList.isEmpty()? "null": gsonList.substring(0, Math.min(gsonList.length(), 1000))));
        }

        return responseDataTransferObject;
    }

    private List<RangeTuple> getSortedList(List<Integer[]> input, ResponseDataTransferObject rto) {
        List<RangeTuple> formattedList = new ArrayList<>();

        if (input == null) {
            rto.addError("This list cannot be processed.  It points to null.");
            return formattedList;
        }

        for (int i = 0; i < input.size(); i++) {
            Integer[] rangeIndicator = input.get(i);

            if (rangeIndicator == null) {
                rto.addWarning("Invalid null range Element encountered: Skipping this element.");
                continue;
            }

            if (rangeIndicator.length != 2) {
                rto.addWarning("Invalid range Element encountered: Expected size was 2, actual size is " + rangeIndicator.length + ":  Skipping this element.");
                continue;
            }

            if (rangeIndicator[0] > rangeIndicator[1]) {
                rto.addWarning("Invalid range Element encountered: Start of " +  rangeIndicator[0] + " cannot be greater than end of " + rangeIndicator[1] + ": Skipping this element.");
                continue;
            }
            formattedList.add(new RangeTuple(rangeIndicator[0], RANGE_TYPE.START, i));
            formattedList.add(new RangeTuple(rangeIndicator[1], RANGE_TYPE.END, i));
        }
        formattedList.sort((r, t) -> r.compareTo(t));
        return formattedList;
    }
}
