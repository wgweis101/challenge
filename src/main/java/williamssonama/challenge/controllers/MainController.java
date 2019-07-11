package williamssonama.challenge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Simply return the index page
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String getWelcomePage(){
        return "index";
    }

}
