package taxi.controllers.car;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxi.lib.Injector;
import taxi.model.Car;
import taxi.model.Manufacturer;
import taxi.service.CarService;
import taxi.service.ManufacturerService;

public class AddCarController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("taxi");
    private CarService carService =
            (CarService) INJECTOR.getInstance(CarService.class);
    private ManufacturerService manufacturerService =
            (ManufacturerService) INJECTOR.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cars/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String model = req.getParameter("model");
            Long manufacturerId = Long.parseLong(req.getParameter("manufacturer_id"));
            Manufacturer manufacturer = manufacturerService.get(manufacturerId);
            Car car = new Car(model, manufacturer);
            carService.create(car);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (NoSuchElementException e) {
            req.setAttribute("message", "You put incorrect data. No manufacturer with such ID");
            req.getRequestDispatcher("/WEB-INF/views/cars/add.jsp").forward(req, resp);
        }
    }
}
