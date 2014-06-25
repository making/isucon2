package isucon.web;

import isucon.repository.OrderRequestRepository;
import isucon.service.Isucon2Initializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    Isucon2Initializer isucon2Initializer;
    @Autowired
    OrderRequestRepository orderRequestRepository;

    @RequestMapping(method = RequestMethod.GET)
    String index() {
        return "admin";
    }

    @RequestMapping(method = RequestMethod.POST)
    String init() {
        isucon2Initializer.init();
        return "redirect:/admin";
    }

    @RequestMapping(value = "order", method = RequestMethod.GET)
    String orders(Model model) {
        OrderRequestRepository.OrderRequestReports reports = orderRequestRepository.findAllReport();
        model.addAttribute("reports", reports);
        return "orderRequestReportView";
    }
}
