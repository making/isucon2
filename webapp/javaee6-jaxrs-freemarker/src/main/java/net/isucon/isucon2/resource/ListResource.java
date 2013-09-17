package net.isucon.isucon2.resource;

import com.sun.jersey.api.view.Viewable;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.inject.Inject;
import net.isucon.isucon2.domain.Artist;

/**
 * 一覧サービス
 *
 * @author matsumana
 */
@Path("list")
@RequestScoped
public class ListResource {

    @Inject
    Artist artist;

    @GET
    public Viewable index() {
        return artist.getArtists();
    }
}
