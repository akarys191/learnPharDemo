package com.epam.spring.spittrMvc.controllers;

import com.epam.spring.spittrMvc.beans.TestBean;
import com.epam.spring.spittrMvc.data.Spitter;
import com.epam.spring.spittrMvc.interfaces.SpitterRepository;
import com.epam.spring.spittrMvc.interfaces.SpittleRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/spitter")
public class SpitterController {

   /* @Autowired
    @Qualifier("public")
    private TestBean publicTestBean;*/

    SpitterRepository spitterRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private TestBean protectedInstance;

    @Autowired
    public SpitterController(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value="/register", method=GET)
    public String showRegistrationForm() {
        //System.out.println("publicTestBean: "+publicTestBean);
        System.out.println("protectedInstance: "+protectedInstance+","+protectedInstance.getCountry());
        TestBean testBean = (TestBean)applicationContext.getBean("requestScopedInstance");
        TestBean testBean2 = (TestBean)applicationContext.getBean("requestScopedInstance");
        System.out.println("testBean: "+testBean+","+testBean2);
        return "registerForm";
    }

    @RequestMapping(value="/register", method=POST)
    public String processRegistration(
            @Valid Spitter spitter,
            Errors errors, Model model) {
        System.out.println("Has email "+spitter.toString());
        if (errors.hasErrors()) {
            return "registerForm";
        }

        spitterRepository.save(spitter);
       // return "redirect:/spitter/" + spitter.getUsername();

        model.addAttribute("username", spitter.getUsername());
        model.addAttribute("spitterId", spitter.getId());
        return "redirect:/spitter/{username}";
    }

    @RequestMapping(value="/{username}", method=GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        Spitter spitter = spitterRepository.findByUsername(username);
        model.addAttribute(spitter);
        return "profile";
    }

}