package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.StatisticsControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.filter.DailySalesFilter;
import ca.com.rlsp.rlspfoodapi.domain.model.statistics.DailySales;
import ca.com.rlsp.rlspfoodapi.domain.service.DailySalesQueryService;
import ca.com.rlsp.rlspfoodapi.domain.service.DailySalesReportService;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
//@RequestMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController implements StatisticsControllerOpenApi {

    private DailySalesQueryService dailySalesQueryService;
    private DailySalesReportService dailySalesReportService;
    private BuildLinks buildLinks;

    public StatisticsController(DailySalesQueryService dailySalesQueryService,
                                DailySalesReportService dailySalesReportService,
                                BuildLinks buildLinks) {
        this.dailySalesQueryService = dailySalesQueryService;
        this.dailySalesReportService = dailySalesReportService;
        this.buildLinks = buildLinks;
    }

    public static class StatisticModel extends RepresentationModel<StatisticModel> {    }

    @CheckSecurity.Statistics.hasPermissionToQuery
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticModel statistics() {
        var statisticModel = new StatisticModel();

        statisticModel.add(buildLinks.getLinkToStatisticsDailySales("daily-sales"));

        return statisticModel;
    }

    @CheckSecurity.Statistics.hasPermissionToQuery
    @Override
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySales> queryDailySalesJSON(DailySalesFilter dailySalesFilter,
                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return dailySalesQueryService.queryDailySales(dailySalesFilter, timeOffSet);
    }

    @CheckSecurity.Statistics.hasPermissionToQuery
    @Override
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> queryDailySalesPDF(DailySalesFilter dailySalesFilter,
                                                     @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet){
        byte[] bytesPDF = dailySalesReportService.issueDailySalesReport(dailySalesFilter, timeOffSet);

        var dateNow = OffsetDateTime.now().toLocalDateTime();
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment; filename=daily-sales__%s.pdf", dateNow));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPDF);
    }
}
