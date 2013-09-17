package net.isucon.isucon2.domain;

import com.sun.jersey.api.view.Viewable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 購入依頼ドメイン
 *
 * @author matsumana
 */
@Entity
@RequestScoped
@Table(name = "order_request")
@NamedQueries({
    @NamedQuery(name = "OrderRequest.findCsvData", query = "SELECT NEW net.isucon.isucon2.domain.OrderRequest(o.id, o.memberId, s.seatId, s.variationId, s.updatedAt) FROM OrderRequest o, Stock s WHERE o.id = s.orderId ORDER BY o.id ASC")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class OrderRequest extends BaseDomain {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "member_id")
    private String memberId;
    // --- extended ----------
    @Transient
    private String seatId;
    @Transient
    private int variationId;
    @Transient
    private Date updatedAt;

    /**
     * 購入処理
     */
    public Viewable buy(int variationId, String memberId) {
        List<Stock> infos = repository.findLatest();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setMemberId(memberId);
        repository.persistOrderRequest(orderRequest);

        int orderId = repository.getLastInsertId();

        int updatedRows = repository.updateStock(orderId, variationId);
        if (updatedRows > 0) {
            // 買えた
            Stock stock = repository.findStockByOrderId(orderId);

            Map<String, Object> map = new HashMap<>();
            map.put("ftl", "confirm");
            map.put("infos", infos);
            map.put("memberId", memberId);
            map.put("variationId", variationId);
            map.put("seatId", stock.getSeatId());

            return new Viewable("/base.ftl", map);
        } else {
            // 売り切れ
            Map<String, Object> map = new HashMap<>();
            map.put("ftl", "soldout");
            map.put("infos", infos);

            return new Viewable("/base.ftl", map);
        }
    }
}
