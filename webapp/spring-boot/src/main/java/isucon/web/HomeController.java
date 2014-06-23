package isucon.web;

import isucon.model.Artist;
import isucon.model.LatestInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping
    String home(Model model) {
        model.addAttribute("infos", Arrays.asList(new LatestInfo("hogehoge", "b", "c", "yamada"), new LatestInfo("foo", "b", "c", "bar")));
        model.addAttribute("artists", Arrays.asList(new Artist(1, "aaa"),new Artist(2, "bbb")));
        return "list";
    }
}
