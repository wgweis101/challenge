package williamssonama.challenge.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import williamssonama.challenge.facade.ResponseRequestIntermediary;
import williamssonama.challenge.model.ResponseDataTransferObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class ReducerRestResponder {

    private Gson gson;
    private ResponseRequestIntermediary responseRequestIntermediary;

    @Autowired
    public ReducerRestResponder(Gson gson, ResponseRequestIntermediary responseRequestIntermediary) {
        this.gson = gson;
        this.responseRequestIntermediary = responseRequestIntermediary;
    }

    @RequestMapping(value = "reducer", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getEbrsCcbfExportReportValidation(HttpServletRequest request)
    {
        Map<String, String[]> parameters = request.getParameterMap();
        String  reductionData = parameters.get("data")[0];

        ResponseDataTransferObject rdto = responseRequestIntermediary.processRequest(reductionData);

        rdto.addInformational("Processed Request");

        if (rdto.hasErrors()) {
            return new ResponseEntity<>(gson.toJson(rdto), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(gson.toJson(rdto), HttpStatus.OK);
    }

}
