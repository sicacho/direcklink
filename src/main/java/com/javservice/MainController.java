package com.javservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/15/2016.
 */
@Controller
public class MainController {

  @Autowired
  HttpSession session;

  @RequestMapping(value = "/",method = RequestMethod.GET)
  public String index() {
    return "index";
  }

  @RequestMapping(value = "/get",method = RequestMethod.POST)
  public String getDirectLink(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String password,
                              @RequestParam(required = true) String links, Model model) throws IOException {
    RapidgatorClient rapidgatorClient = new RapidgatorClient();
    if(session.getAttribute("username")==null || session.getAttribute("password")==null) {
      session.setAttribute("username",username);
      session.setAttribute("password",password);
    }
    username = (String) session.getAttribute("username");
    password = (String) session.getAttribute("password");
    SessionResponse sessionResponse = rapidgatorClient.getSessionId(username,password);
    String[] linkArray = links.split("\n");
    List<ResultDTO> results = new ArrayList<>();
    for (String link : linkArray) {
      String dlink = rapidgatorClient.getLinkDownload(sessionResponse.response.session_id,link).response.url;
      results.add(new ResultDTO(dlink,link));
    }
    model.addAttribute("results",results);
    return "index";
  }
}
