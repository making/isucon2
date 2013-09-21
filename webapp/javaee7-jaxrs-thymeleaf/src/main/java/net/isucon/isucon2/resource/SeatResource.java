package net.isucon.isucon2.resource;

import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import net.isucon.isucon2.domain.OrderRequest;
import net.isucon.isucon2.domain.Variation;
import net.isucon.isucon2.infra.Template;
import net.isucon.isucon2.response.SeatResponse;

/**
 * シートサービス
 *
 * @author matsumana
 */
@Path("seat")
@RequestScoped
public class SeatResource {

    @Inject
    Variation variation;
    @Inject
    OrderRequest orderRequest;

    @GET
    @Path("{variationId}/{seatId}")
    @Template("base.html")
    public SeatResponse index(@PathParam("variationId") int variationId, @PathParam("seatId") String seatId) {

        // TODO validation

        return orderRequest.getSeatDetail(variationId, seatId);
    }
}
