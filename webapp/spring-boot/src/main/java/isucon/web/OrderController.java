package isucon.web;

import isucon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "buy", method = RequestMethod.POST)
    String buy(@RequestParam("variation_id") Integer variationId,
               @RequestParam("member_id") String memberId, RedirectAttributes attributes) {
        try {
            String seatId = orderService.purchase(variationId, memberId);
            attributes.addFlashAttribute("variationId", variationId);
            attributes.addFlashAttribute("memberId", memberId);
            attributes.addFlashAttribute("seatId", seatId);
        } catch (OrderService.SoldOutException e) {
            return "soldout";
        }

        return "redirect:/confirm";
    }

    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    String confirm() {

        return "confirm";
    }
}
