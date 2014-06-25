package isucon.service;

import isucon.model.OrderRequest;
import isucon.model.Stock;
import isucon.repository.OrderRequestRepository;
import isucon.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    StockRepository stockRepository;
    @Autowired
    OrderRequestRepository orderRequestRepository;

    public String purchase(Integer variationId, String memberId) {
        OrderRequest orderRequest = new OrderRequest(null, memberId);
        OrderRequest ordered = orderRequestRepository.save(orderRequest);
        Integer orderId = ordered.getId();
        int count = stockRepository.setOrderIdAtRandomSeatByVariationId(orderId, variationId);
        if (count > 0) {
            Stock stock = stockRepository.findOneByOrderId(orderId);
            return stock.getSeatId();
        } else {
            throw new SoldOutException(variationId, memberId);
        }
    }

    public static class SoldOutException extends RuntimeException {
        public SoldOutException(Integer variationId, String memberId) {
            super("sold out! -> [memberId" + memberId + ", variationId=" + variationId + "]");
        }
    }
}
