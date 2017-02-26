package com.mycompany.cache.rest;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mycompany.cache.AbstractCacheUtil;
import com.mycompany.cache.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;

@RestController
@RequestMapping("/cache")
public class CacheRestController {

    @Autowired
    private AbstractCacheUtil cacheUtil;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> create(
            @RequestParam(value = "key") final String key,
            @RequestParam(value = "value") final String value
    ) {
        cacheUtil.put(key, value, 360000L );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> ping() {return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> get(
            @PathParam(value = "key") final String key
    ) {
        Object value = cacheUtil.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(
            @RequestParam(value = "login") final String login,
            @RequestParam(value = "password") final String password
    ) {
        return new ResponseEntity<>(login + password, HttpStatus.OK);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity<String> check(
            HttpServletRequest request
    ) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token.equals("user1password1")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}