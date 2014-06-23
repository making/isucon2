package isucon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LatestInfo {
    private String seatId;
    private String variationName;
    private String ticketName;
    private String artistName;
}
