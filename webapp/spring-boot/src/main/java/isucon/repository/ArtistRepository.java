package isucon.repository;

import isucon.model.Artist;
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
public class ArtistRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Artist> rowMapper = new BeanPropertyRowMapper<>(Artist.class);

    public List<Artist> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name FROM artist ORDER BY id",
                rowMapper);
    }

    public Artist findOne(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(
                "SELECT id, name FROM artist WHERE id = :id LIMIT 1",
                param,
                rowMapper);
    }
}
