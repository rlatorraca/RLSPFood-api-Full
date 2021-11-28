package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.filter.DailySalesFilter;

public interface DailySalesReportService {

    byte[] issueDailySalesReport(DailySalesFilter filter, String timeOffSet);
}
