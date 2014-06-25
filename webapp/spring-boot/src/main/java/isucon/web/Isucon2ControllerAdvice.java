package isucon.web;

import isucon.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class Isucon2ControllerAdvice {
    @Autowired
    TicketRepository ticketRepository;

    @ModelAttribute("infos")
    List<TicketRepository.LatestInfo> latestInfos() {
        return ticketRepository.findLastestInfo();
    }
}
