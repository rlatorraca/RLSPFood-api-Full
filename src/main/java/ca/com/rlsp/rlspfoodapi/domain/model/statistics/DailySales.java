package ca.com.rlsp.rlspfoodapi.domain.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class DailySales {
    private Date date;
    private Long amountOfInvoices;
    private BigDecimal totalSold;
}

