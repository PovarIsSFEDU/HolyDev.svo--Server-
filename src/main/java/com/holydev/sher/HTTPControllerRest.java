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
import java.util.List;


@Controller
@RestController
@EnableScheduling
public class HTTPControllerRest extends HttpServlet {

    @Autowired
    WorkerRepository workerRepository;




    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseEntity<String> UserFromMob(HttpServletRequest request) {
        try {
            String user = request.getHeader("name");

            Worker worker = workerRepository.getByUserName(user);
            int ans = worker.getId();
            return ResponseEntity.ok().body("" + ans);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!");
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<String> AddWorker(HttpServletRequest request, HttpServletResponse response) {
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
    public ResponseEntity<String> FreeWorkersByID(HttpServletRequest request) {
        try {
            int type = Integer.parseInt(request.getParameter("type"));
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
    public ResponseEntity<String> WorkerByID(HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping(method = RequestMethod.POST, value = "/weather")
    public void Weather(HttpServletRequest request, HttpServletResponse response) {
        //TODO Возвращает состояние погоды (направление и скорость ветра, осадки, миллиметраж осадков)
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public void Order(HttpServletRequest request, HttpServletResponse response) {
        /*TODO Возвращает статус принятия приказа (принят/в обработке)
        TODO Вид приказа - (тип приказа, место приказа LatLng, id свободных машин, id контролера)*/
    }

    @RequestMapping(method = RequestMethod.POST, value = "/check")
    public void CheckOrder(HttpServletRequest request, HttpServletResponse response) {
        /*TODO Респонс - приказ на мобильное приложение с определенным id.
        TODO Вид приказа - (тип приказа, место приказа LatLng)*/
    }


}
