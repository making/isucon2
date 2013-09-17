package net.isucon.isucon2.resource;

import com.sun.jersey.api.view.Viewable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import net.isucon.isucon2.application.IsuconApplication;
import net.isucon.isucon2.domain.Admin;

/**
 * 管理者サービス
 *
 * @author matsumana
 */
@Path("admin")
@RequestScoped
public class AdminResource {

    @Inject
    Admin admin;
    @Inject
    IsuconApplication application;

    @GET
    public Viewable index() {
        return admin.index();
    }

    @POST
    public Response initialize() throws UnsupportedEncodingException, IOException {
        return application.initialize();
    }

    @GET
    @Path("orders")
    @Produces("text/csv")
    public String csv() {
        return application.getCsvData();
    }
}
