package com.holydev.sher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RestController
@EnableScheduling
public class HTTPControllerRest extends HttpServlet {

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    OrderRepo orderRepo;
    List<Order> orders;
    ArrayList<String> busy_ids = new ArrayList<>();
    ArrayList<Integer> checkers = new ArrayList<>();


    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseEntity<String> UserFromMob(HttpServletRequest request) {
        try {
            String user = request.getHeader("name");

            Worker worker = workerRepository.getByUserName(user);

            String ans = worker.getId() + " " + worker.getUser_type();
            return ResponseEntity.ok().body(ans);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<String> AddWorker(HttpServletRequest request) {
        try {
            workerRepository.save(new Worker(Integer.parseInt(request.getParameter("id")), request.getParameter("time"), Integer.parseInt(request.getParameter("type")), Double.parseDouble(request.getParameter("lat")), Double.parseDouble(request.getParameter("long")), Integer.parseInt(request.getParameter("user_type")), Boolean.parseBoolean(request.getParameter("status")), request.getParameter("username")));
            return ResponseEntity.ok().
                    body("Success!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/free")
    public ResponseEntity<String> FreeWorkersByType(HttpServletRequest request) {
        try {
            int type = Integer.parseInt(request.getHeader("type"));
            List<Worker> list = workerRepository.getFreeByID(type);
            StringBuilder ans = new StringBuilder();
            for (Worker w : list) {
                ans.append(w.getId()).append(" ");
            }
            return ResponseEntity.ok().body(ans.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }

    //
    @RequestMapping(method = RequestMethod.POST, value = "/id")
    public ResponseEntity<String> WorkerByID(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            List<Worker> list = workerRepository.getByID(id);
            StringBuilder ans = new StringBuilder();
            for (Worker w : list) {
                ans.append(w.getLatitude()).append(",").append(" ").append(w.getLongitude()).append(";").append(" ").append(w.getStatus());
            }
            return ResponseEntity.ok().body(ans.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<String> AllWorkers(HttpServletRequest request) {
        try {
            List<Worker> list = workerRepository.getAll();
            StringBuilder ans = new StringBuilder();
            for (Worker w : list) {
                ans.append(w.getId()).append(",").append(" ").append(w.getLatitude()).append(",").append(" ").append(w.getLongitude()).append(",").append(" ").append(w.getStatus()).append(";");
            }
            return ResponseEntity.ok().body(ans.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


    //TODO Привязать погодный API
    @RequestMapping(method = RequestMethod.GET, value = "/weather")
    public ResponseEntity<String> Weather(HttpServletRequest request) {
        String ans = "SSW 5; Snow 5; -4";
        return ResponseEntity.ok().body(ans);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/SetOrder")
    public ResponseEntity<String> SetOrder(HttpServletRequest request) {
        try {
            Order order = new Order();
            order.setOrder_id(Integer.parseInt(request.getHeader("order_id")));
            order.setDate(""); //TODO сделать дату
            order.setLat(Double.parseDouble(request.getHeader("lat")));
            order.setLng(Double.parseDouble(request.getHeader("lng")));
            order.setOrder_type(Integer.parseInt(request.getHeader("order_type")));
            order.setWorkers_id(request.getHeader("workers_id"));
            order.setChecker_id(Integer.parseInt(request.getHeader("checker_id")));
            order.setStatus(1);
            orderRepo.save(order);
            busy_ids = new ArrayList<>(Arrays.asList(order.getWorkers_id().split(" ")));
            return ResponseEntity.ok("2");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/check")
    public Order CheckOrder(HttpServletRequest request) {
        try {
            orders = orderRepo.getAll();
            for (Order o : orders) {
                if (o.getStatus() != 3) {
                    String[] temp = o.getWorkers_id().split(" ");
                    busy_ids.addAll(Arrays.asList(temp));
                }
            }
            String id = request.getParameter("id");
            if (busy_ids.contains(id)) {
                List<Order> orders = orderRepo.getByBusyID();
                for (Order o : orders) {
                    String[] temp = o.getWorkers_id().split(" ");
                    for (String s : temp) {
                        if (s.equals(id)) {
                            return o;
                        }
                    }
                }
            } else {
                return Order.generateOkOrder();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Order.generateOkOrder();
        }
        return Order.generateOkOrder();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/kontr")
    public Order KontrOrder(HttpServletRequest request) {
        try {
            orders = orderRepo.getAll();
            for (Order o : orders) {
                if (o.getStatus() != 3) {
                    int temp = o.getChecker_id();
                    checkers.add(temp);
                }
            }
            int id = Integer.parseInt(request.getParameter("id"));
            if (checkers.contains(id)) {
                List<Order> orders = orderRepo.getByBusyID();
                for (Order o : orders) {
                    int temp = o.getChecker_id();
                    if (temp == id) {
                        return o;
                    }
                }
            } else {
                return Order.generateOkOrder();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Order.generateOkOrder();
        }
        return Order.generateOkOrder();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upd")
    public void UpdateOrderStatus(HttpServletRequest request) {
        try {
            int order_id = Integer.parseInt(request.getHeader("orderId"));
            orderRepo.updateOrder(3, order_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}