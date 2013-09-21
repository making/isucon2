package net.isucon.isucon2.response;

import lombok.Getter;
import lombok.Setter;

/**
 * シートレスポンス情報
 *
 * @author matsumana
 */
@Setter
@Getter
public class SeatResponse extends BaseResponse {

    private String seatId;
    private String memberId;
    private String updatedAt;
}
