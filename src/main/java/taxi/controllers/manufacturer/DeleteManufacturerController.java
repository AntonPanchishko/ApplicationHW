package taxi.controllers.manufacturer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxi.lib.Injector;
import taxi.service.ManufacturerService;

public class DeleteManufacturerController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("taxi");
    private ManufacturerService manufacturerService =
            (ManufacturerService) INJECTOR.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String manufacturerId = req.getParameter("manufacturer_id");
        Long id = Long.parseLong(manufacturerId);
        manufacturerService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/manufacturers/all");
    }
}
