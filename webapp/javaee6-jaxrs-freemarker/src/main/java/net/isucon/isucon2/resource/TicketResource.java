package net.isucon.isucon2.resource;

import com.sun.jersey.api.view.Viewable;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.inject.Inject;
import net.isucon.isucon2.domain.Variation;

/**
 * チケットサービス
 *
 * @author matsumana
 */
@Path("ticket")
@RequestScoped
public class TicketResource {

    @Inject
    Variation variation;

    @GET
    @Path("{ticketId}")
    public Viewable index(@PathParam("ticketId") int ticketId) {

        // TODO validation

        return variation.getVacancies(ticketId);
    }
}
