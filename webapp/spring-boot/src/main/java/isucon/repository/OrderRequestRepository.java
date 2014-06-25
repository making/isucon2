package isucon.repository;

import isucon.model.OrderRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class OrderRequestRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<OrderRequestReport> reportRowMapper = new BeanPropertyRowMapper<>(OrderRequestReport.class);

    public OrderRequest save(OrderRequest orderRequest) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
                //.withSchemaName("isucon2")
                .withTableName("order_request")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("memberId", orderRequest.getMemberId());
        Number id = insert.executeAndReturnKey(param);
        orderRequest.setId(id.intValue());
        return orderRequest;
    }

    public OrderRequestReports findAllReport() {
        return new OrderRequestReports(jdbcTemplate.query("SELECT order_request.id AS id, order_request.member_id AS memberId, " +
                "stock.seat_id AS seatId, stock.variation_id AS variationId, stock.updated_at AS updatedAt " +
                "FROM order_request " +
                "JOIN stock ON order_request.id = stock.order_id " +
                "ORDER BY order_request.id ASC", reportRowMapper));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderRequestReport implements Serializable {
        private static final long serialVersionUID = 1L;

        private Integer id;
        private Integer variationId;
        private String memberId;
        private String seatId;
        private Date updatedAt;
    }

    @RequiredArgsConstructor
    @Getter
    public static class OrderRequestReports implements Serializable {
        private final List<OrderRequestReport> reports;
    }
}
