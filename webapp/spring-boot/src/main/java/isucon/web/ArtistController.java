package isucon.web;

import isucon.model.Artist;
import isucon.model.Ticket;
import isucon.repository.ArtistRepository;
import isucon.repository.TicketRepository;
import isucon.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("artist")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    VariationRepository variationRepository;

    @RequestMapping(value = "{artistId}", method = RequestMethod.GET)
    String artist(@PathVariable("artistId") Integer artistId, Model model) {
        Artist artist = artistRepository.findOne(artistId);
        List<Ticket> tickets = ticketRepository.findByArtistId(artistId);
        for (Ticket ticket : tickets) {
            ticket.setCount((int) variationRepository.countByTicketId(ticket.getId()));
        }

        model.addAttribute("tickets", tickets);
        model.addAttribute(artist);
        return "artist";
    }
}
