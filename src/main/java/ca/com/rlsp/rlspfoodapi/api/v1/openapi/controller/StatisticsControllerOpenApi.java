package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.domain.filter.DailySalesFilter;
import ca.com.rlsp.rlspfoodapi.domain.model.statistics.DailySales;
import ca.com.rlsp.rlspfoodapi.api.v1.controller.StatisticsController.StatisticModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags="Statistics")
public interface StatisticsControllerOpenApi {

    @ApiOperation(value = "Statistiscs", hidden = true)
    StatisticModel statistics();

    @ApiOperation("Query of dialy sales statistics")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "Restaurant Id",
                    example = "1", dataType = "int"),
            @ApiImplicitParam(name = "createdDateStart", value = "Start date/time for orders",
                    example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "createdDateEnd", value = "End date/time for ordes",
                    example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    public List<DailySales> queryDailySalesJSON(@ApiParam(name = "body", value = "A DTO for inputs a resource of statistics" , required =true)
                                                        DailySalesFilter dailySalesFilter,
                                                @ApiParam(value = "UTC offset time to be used by system",
                                                        defaultValue = "+00:00")
                                                        String timeOffSet);


    public ResponseEntity<byte[]> queryDailySalesPDF(DailySalesFilter dailySalesFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet);
}