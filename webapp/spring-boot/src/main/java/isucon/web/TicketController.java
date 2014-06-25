package isucon.web;

import isucon.model.Stock;
import isucon.model.Ticket;
import isucon.model.Variation;
import isucon.repository.StockRepository;
import isucon.repository.TicketRepository;
import isucon.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ticket")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    VariationRepository variationRepository;
    @Autowired
    StockRepository stockRepository;

    @RequestMapping(value = "{ticketId}", method = RequestMethod.GET)
    String ticket(@PathVariable("ticketId") Integer ticketId, Model model) {
        Ticket ticket = ticketRepository.findOne(ticketId);
        List<Variation> variations = variationRepository.findByTicketId(ticketId);
        for (Variation variation : variations) {
            Map<String, Boolean> stocks = new HashMap<>();
            for (Stock s : stockRepository.findByVariationId(variation.getId())) {
                stocks.put(s.getSeatId(), s.getOrderId() == null /* availability */);
            }
            variation.setStocks(stocks);
            variation.setVacancy(stockRepository.countByVariationId(variation.getId()));
        }
        model.addAttribute(ticket);
        model.addAttribute("variations", variations);
        return "ticket";
    }
}
