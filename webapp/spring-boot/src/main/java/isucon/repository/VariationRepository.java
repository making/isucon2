package isucon.repository;

import isucon.model.Variation;
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
public class VariationRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Variation> rowMapper = new BeanPropertyRowMapper<>(Variation.class);

    public List<Variation> findByTicketId(Integer ticketId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("ticketId", ticketId);
        return jdbcTemplate.query(
                "SELECT id, name FROM variation WHERE ticket_id = :ticketId ORDER BY id",
                param,
                rowMapper);
    }

    public long countByTicketId(Integer ticketId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("ticketId", ticketId);
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM variation " +
                        "INNER JOIN stock ON stock.variation_id = variation.id " +
                        "WHERE variation.ticket_id = :ticketId AND stock.order_id IS NULL",
                param,
                Long.class);
        return count == null ? 0 : count;
    }
}
