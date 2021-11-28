package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.filter.DailySalesFilter;
import ca.com.rlsp.rlspfoodapi.domain.model.statistics.DailySales;

import java.util.List;


public interface DailySalesQueryService {

    List<DailySales> queryDailySales(DailySalesFilter filter, String timeOffSet);
}
