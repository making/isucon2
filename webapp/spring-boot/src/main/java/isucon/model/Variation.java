package isucon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variation implements Serializable {
    private static final long serialVersionUID = 1L;

    //basic
    private Integer id;
    private String name;

    //extended
    private Map<String, Boolean> stocks;
    private Long vacancy;
}
