package com.example.oauth.socialmedia.demo.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.example.oauth.socialmedia.demo.controllers.util.SocialControllerUtil;
import com.example.oauth.socialmedia.demo.dao.DataDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rajakolli
 *
 */
@Controller
@Slf4j
public class MainController
{

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private DataDao dataDao;

    @Autowired
    private SocialControllerUtil util;

    @GetMapping("/")
    public String home(HttpServletRequest request, Principal currentUser, Model model)
    {
        util.setModel(request, currentUser, model);
        return "home";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Principal currentUser, Model model)
    {
        util.setModel(request, currentUser, model);
        return "login";
    }

    @PostMapping(value = "/update")
    public String update(HttpServletRequest request, Principal currentUser, Model model,
            @RequestParam(value = "data", required = true) String data)
    {

        log.debug("Update data to: {}", data);
        String userId = currentUser.getName();
        dataDao.setDate(userId, data);

        util.setModel(request, currentUser, model);
        return "home";
    }

    @PostMapping(value = "/updateStatus")
    public String updateStatus(WebRequest webRequest, HttpServletRequest request,
            Principal currentUser, Model model,
            @RequestParam(value = "status", required = true) String status)
    {
        MultiValueMap<String, Connection<?>> cmap = connectionRepository
                .findAllConnections();
        log.error("cs.size = {}", cmap.size());
        Set<Map.Entry<String, List<Connection<?>>>> entries = cmap.entrySet();
        for (Map.Entry<String, List<Connection<?>>> entry : entries)
        {
            for (Connection<?> c : entry.getValue())
            {
                log.debug("Updates {} with the status '{}'", entry.getKey(), status);
                c.updateStatus(status);
            }
        }

        return "home";
    }
}
