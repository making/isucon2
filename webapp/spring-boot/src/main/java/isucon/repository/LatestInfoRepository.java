package isucon.repository;

import isucon.model.LatestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class LatestInfoRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<LatestInfo> rowMapper = new BeanPropertyRowMapper<LatestInfo>(LatestInfo.class);

    public List<LatestInfo> findAll() {
        return jdbcTemplate.query("SELECT stock.seat_id as seatId, variation.name AS variationName, ticket.name AS ticketName, artist.name AS artistName " +
                        "FROM stock " +
                        "JOIN variation ON stock.variation_id = variation.id " +
                        "JOIN ticket ON variation.ticket_id = ticket.id " +
                        "JOIN artist ON ticket.artist_id = artist.id " +
                        "WHERE order_id IS NOT NULL " +
                        "ORDER BY order_id DESC LIMIT 10",
                rowMapper);
    }
}
