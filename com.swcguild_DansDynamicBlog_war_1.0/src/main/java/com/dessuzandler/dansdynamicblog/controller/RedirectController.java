/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dessuzandler.dansdynamicblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class RedirectController {
    
    
      @RequestMapping(value={"/redirect"}, method=RequestMethod.GET)
    public String displayRedirectPage(){
        return "redirect";
    }
}
