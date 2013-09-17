package net.isucon.isucon2.domain;

import com.sun.jersey.api.view.Viewable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.hibernate.ScrollableResults;

/**
 * 管理者ドメイン
 *
 * @author matsumana
 */
@RequestScoped
public class Admin extends BaseDomain {

    /**
     * 画面初期表示
     */
    public Viewable index() {

        List<Stock> infos = repository.findLatest();

        Map<String, Object> map = new HashMap<>();
        map.put("ftl", "admin");
        map.put("infos", infos);

        return new Viewable("/base.ftl", map);
    }

    /**
     * データ初期化
     */
    public Response initialize() throws UnsupportedEncodingException, IOException {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(this.getClass()
                .getResourceAsStream("../../../../sql/initial_data.sql"), "UTF-8"))) {

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                repository.executeUpdate(line);
            }
        }

        URI uri = UriBuilder.fromUri("/admin").build();
        Response response = Response.temporaryRedirect(uri).status(302).build();

        return response;
    }

    /**
     * CSVデータ取得
     */
    public String getCsvData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder result = new StringBuilder(64 * 4096 * 10);

        ScrollableResults results = null;
        try {
            results = repository.fetchCsvData();
            while (results.next()) {
                OrderRequest order = (OrderRequest) results.get(0);

                result.append(order.getId()).append(",");
                result.append(order.getMemberId()).append(",");
                result.append(order.getSeatId()).append(",");
                result.append(order.getVariationId()).append(",");
                result.append(sdf.format(order.getUpdatedAt())).append("\n");

                repository.evict(order);
            }

            return result.toString();
        } finally {
            if (results != null) {
                results.close();
            }
        }
    }
}
