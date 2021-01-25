package taxi.controllers.car;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxi.lib.Injector;
import taxi.service.CarService;

public class DeleteCarController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("taxi");
    private final CarService carService = (CarService)
            INJECTOR.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String carId = req.getParameter("car_id");
        Long id = Long.valueOf(carId);
        carService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/cars/all");
    }
}
