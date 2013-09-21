package net.isucon.isucon2.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import net.isucon.isucon2.response.BuyResponse;
import net.isucon.isucon2.response.SeatResponse;

/**
 * 購入依頼ドメイン
 *
 * @author matsumana
 */
@Entity
@RequestScoped
@Table(name = "order_request")
@NamedQueries({
    @NamedQuery(name = "OrderRequest.findCsvData", query = "SELECT NEW net.isucon.isucon2.domain.OrderRequest(o.id, o.memberId, s.seatId, s.variationId, s.updatedAt) FROM OrderRequest o, Stock s WHERE o.id = s.orderId ORDER BY o.id ASC"),
    @NamedQuery(name = "OrderRequest.findBySeatId", query = "SELECT NEW net.isucon.isucon2.domain.OrderRequest(o.id, o.memberId, s.seatId, s.variationId, s.updatedAt) FROM OrderRequest o, Stock s WHERE o.id = s.orderId AND s.variationId = :variationId AND s.seatId = :seatId")})
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
    public BuyResponse buy(int variationId, String memberId) {

        List<Stock> infos = repository.findLatest();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setMemberId(memberId);
        repository.persistOrderRequest(orderRequest);

        int orderId = repository.getLastInsertId();

        int updatedRows = repository.updateStock(orderId, variationId);
        if (updatedRows > 0) {
            // 買えた
            Stock stock = repository.findStockByOrderId(orderId);

            BuyResponse response = new BuyResponse();
            response.setTemplateName("confirm");
            response.setInfos(infos);
            response.setMemberId(memberId);
            response.setVariationId(variationId);
            response.setSeatId(stock.getSeatId());

            return response;
        } else {
            // 売り切れ
            BuyResponse response = new BuyResponse();
            response.setTemplateName("soldout");
            response.setInfos(infos);

            return response;
        }
    }

    /**
     * シート詳細情報取得
     */
    public SeatResponse getSeatDetail(int variationId, String seatId) {
        List<Stock> infos = repository.findLatest();

        OrderRequest orderRequest = repository.findOrderRequestBySeatId(variationId, seatId);
        SeatResponse response = new SeatResponse();
        response.setTemplateName("seat");
        response.setInfos(infos);
        response.setMemberId(orderRequest.getMemberId());
        response.setSeatId(orderRequest.getSeatId());

        if (orderRequest.getUpdatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setUpdatedAt(sdf.format(orderRequest.getUpdatedAt()));
        }

        return response;
    }
}
