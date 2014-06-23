package isucon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    //basic
    private Integer id;
    private String name;

    //extended
    private String artistName;
    private Integer count;
}
