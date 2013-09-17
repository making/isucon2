package net.isucon.isucon2.resource;

import com.sun.jersey.api.view.Viewable;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.inject.Inject;
import net.isucon.isucon2.domain.Artist;

/**
 * アーティストサービス
 *
 * @author matsumana
 */
@Path("artist")
@RequestScoped
public class ArtistResource {

    @Inject
    Artist artist;

    @GET
    @Path("{artistId}")
    public Viewable index(@PathParam("artistId") int artistId) {

        // TODO validation

        return artist.getArtist(artistId);
    }
}
