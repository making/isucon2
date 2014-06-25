package isucon.web;

import isucon.repository.OrderRequestRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

@Component
public class OrderRequestReportView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        OrderRequestRepository.OrderRequestReports reports = (OrderRequestRepository.OrderRequestReports) model.get("reports");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        reports.getReports().forEach((report) -> {
            writer.print(report.getId());
            writer.print(',');
            writer.print(report.getMemberId());
            writer.print(',');
            writer.print(report.getSeatId());
            writer.print(',');
            writer.print(format.format(report.getUpdatedAt()));
            writer.print('\n');
        });
        response.setContentType("text/csv");
    }
}
