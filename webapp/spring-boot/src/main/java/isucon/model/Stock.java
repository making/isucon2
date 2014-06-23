package isucon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer variationId;
    private String seatId;
    private Integer orderId;

}
