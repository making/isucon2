package net.isucon.isucon2.domain;

import com.sun.jersey.api.view.Viewable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * アーティストドメイン
 *
 * @author matsumana
 */
@Entity
@RequestScoped
@Table(name = "artist")
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a ORDER BY a.id"),
    @NamedQuery(name = "Artist.findById", query = "SELECT a FROM Artist a WHERE a.id = :id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Artist extends BaseDomain {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    /**
     * アーティスト取得
     */
    public Viewable getArtist(int artistId) {
        Artist artist = repository.findArtistById(artistId);
        if (artist == null) {
            // TODO
            throw new IllegalArgumentException("Artist not found. id=" + artistId);
        }

        List<Ticket> tickets = repository.findTicketsByArtistId(artistId);
        for (Ticket ticket : tickets) {
            int count = repository.countTickets(ticket.getId());
            ticket.setCount(count);
        }

        List<Stock> infos = repository.findLatest();

        Map<String, Object> map = new HashMap<>();
        map.put("ftl", "artist");
        map.put("infos", infos);
        map.put("artist", artist);
        map.put("tickets", tickets);

        return new Viewable("/base.ftl", map);
    }

    /**
     * アーティスト一覧取得
     */
    public Viewable getArtists() {
        List<Artist> artists = repository.findAllArtists();

        List<Stock> infos = repository.findLatest();

        Map<String, Object> map = new HashMap<>();
        map.put("ftl", "list");
        map.put("infos", infos);
        map.put("artists", artists);

        return new Viewable("/base.ftl", map);
    }
}
