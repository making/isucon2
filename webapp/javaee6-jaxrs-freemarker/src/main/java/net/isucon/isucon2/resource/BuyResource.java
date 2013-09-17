package net.isucon.isucon2.resource;

import com.sun.jersey.api.view.Viewable;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import net.isucon.isucon2.application.IsuconApplication;

/**
 * 購入サービス
 *
 * @author matsumana
 */
@Path("buy")
@RequestScoped
public class BuyResource {

    @Inject
    IsuconApplication application;

    @POST
    public Viewable buy(@FormParam("variation_id") int variationId,
            @FormParam("member_id") String memberId) {

        // TODO validation

        return application.buy(variationId, memberId);
    }
}
