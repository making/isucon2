package isucon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer variationId;
    private String memberId;
    private String seatId;
    private Date updatedAt;
}
