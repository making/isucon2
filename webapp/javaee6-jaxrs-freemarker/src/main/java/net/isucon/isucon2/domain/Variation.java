package net.isucon.isucon2.domain;

import com.sun.jersey.api.view.Viewable;
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
 * チケットバリエーションドメイン
 *
 * @author matsumana
 */
@Entity
@RequestScoped
@Table(name = "variation")
@NamedQueries({
    @NamedQuery(name = "Variation.findByTicketId", query = "SELECT v FROM Variation v WHERE v.ticketId = :ticketId ORDER BY v.id")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Variation extends BaseDomain {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "ticket_id")
    private int ticketId;
    // --- extended ----------
    @Transient
    private Map<String, Boolean> stocks;
    @Transient
    private int vacancy;

    /**
     * 空席状況取得
     */
    public Viewable getVacancies(int ticketId) {
        Ticket ticket = repository.findTicketByIdWithName(ticketId);
        if (ticket == null) {
            // TODO
            throw new IllegalArgumentException("Ticket not found. id=" + ticketId);
        }

        List<Variation> variations = repository.findVariationsByTicketId(ticketId);
        for (Variation variation : variations) {
            Map<String, Boolean> stockMap = new HashMap<>(4096);
            List<Stock> resultList = repository.findStocksByVariationId(variation.getId());
            for (Stock s : resultList) {
                stockMap.put(s.getSeatId(), s.getOrderId() != null);
                repository.evict(s);
            }

            variation.setStocks(stockMap);
            variation.setVacancy(repository.countStocksByVariationId(variation.getId()));

            repository.evict(variation);
        }

        List<Stock> infos = repository.findLatest();

        Map<String, Object> map = new HashMap<>();
        map.put("ftl", "ticket");
        map.put("infos", infos);
        map.put("ticket", ticket);
        map.put("variations", variations);

        return new Viewable("/base.ftl", map);
    }
}
