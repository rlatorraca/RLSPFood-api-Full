package ca.com.rlsp.rlspfoodapi.infra.service.report;

import ca.com.rlsp.rlspfoodapi.domain.filter.DailySalesFilter;
import ca.com.rlsp.rlspfoodapi.domain.service.DailySalesQueryService;
import ca.com.rlsp.rlspfoodapi.domain.service.DailySalesReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;


@Service
public class PDFDailySalesReportServiceImpl implements DailySalesReportService {

    @Autowired
    private DailySalesQueryService dailySalesQueryService;

    @Override
    public byte[] issueDailySalesReport(DailySalesFilter filter, String timeOffSet) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");

            var params = new HashMap<String, Object>();

            params.put("REPORT_LOCALE", new Locale("en", "CA"));

            var dailyVendas = dailySalesQueryService.queryDailySales(filter,timeOffSet);

            var dataSource = new JRBeanCollectionDataSource(dailyVendas) ; // Pega um colacao de um class JAVA

            var jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);


            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReportException("Cannot create a Daily Sales report",e);
        }
    }
}
