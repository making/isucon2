package isucon.repository;

import isucon.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TicketRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Ticket> rowMapper = new BeanPropertyRowMapper<>(Ticket.class);

    private final RowMapper<LatestInfo> latestInfoRowMapper = new BeanPropertyRowMapper<>(LatestInfo.class);

    public List<Ticket> findByArtistId(Integer artistId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("artistId", artistId);
        return jdbcTemplate.query(
                "SELECT id, name FROM ticket WHERE artist_id = :artistId ORDER BY id",
                param,
                rowMapper);
    }


    public Ticket findOne(Integer ticketId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("ticketId", ticketId);
        return jdbcTemplate.queryForObject(
                "SELECT t.*, a.name AS artistName FROM ticket t" +
                        " INNER JOIN artist a ON t.artist_id = a.id " +
                        "WHERE t.id = :ticketId LIMIT 1",
                param,
                rowMapper);
    }

    public List<LatestInfo> findLastestInfo() {
        return jdbcTemplate.query("SELECT stock.seat_id as seatId, variation.name AS variationName, ticket.name AS ticketName, artist.name AS artistName " +
                        "FROM stock " +
                        "JOIN variation ON stock.variation_id = variation.id " +
                        "JOIN ticket ON variation.ticket_id = ticket.id " +
                        "JOIN artist ON ticket.artist_id = artist.id " +
                        "WHERE order_id IS NOT NULL " +
                        "ORDER BY order_id DESC LIMIT 10",
                latestInfoRowMapper);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LatestInfo {
        private String seatId;
        private String variationName;
        private String ticketName;
        private String artistName;
    }
}
