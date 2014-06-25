package isucon.repository;

import isucon.model.Stock;
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
public class StockRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Stock> rowMapper = new BeanPropertyRowMapper<>(Stock.class);

    public List<Stock> findByVariationId(Integer variationId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("variationId", variationId);
        return jdbcTemplate.query(
                "SELECT seat_id as seatId, order_id as orderId FROM stock WHERE variation_id = :variationId",
                param,
                rowMapper);
    }

    public Stock findOneByOrderId(Integer orderId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("orderId", orderId);
        return jdbcTemplate.queryForObject(
                "SELECT seat_id as seatId FROM stock WHERE order_id = :orderId LIMIT 1",
                param,
                rowMapper);
    }

    public long countByVariationId(Integer variationId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("variationId", variationId);
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM stock WHERE variation_id = :variationId AND order_id IS NULL",
                param,
                Long.class);
        return count == null ? 0 : count;
    }

    public int setOrderIdAtRandomSeatByVariationId(Integer orderId, Integer variationId) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("variationId", variationId);
        return jdbcTemplate.update(
                "UPDATE stock SET order_id = :orderId WHERE variation_id = :variationId AND order_id IS NULL ORDER BY RAND() LIMIT 1",
                param);
    }

}
