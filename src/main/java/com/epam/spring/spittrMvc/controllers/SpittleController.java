package com.epam.spring.spittrMvc.controllers;

import com.epam.spring.spittrMvc.data.Spittle;
import com.epam.spring.spittrMvc.exceptions.DuplicateSpittleException;
import com.epam.spring.spittrMvc.interfaces.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private SpittleRepository spittleRepository;
    private static  final String MAX_LONG_AS_STRING =
            "" + Long.MAX_VALUE;;
    @Autowired
    public SpittleController(
            SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method= GET)
    public String spittles(Model model) {
        model.addAttribute("spittleList",
                spittleRepository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }

    /*@RequestMapping(method= GET)
    public List<Spittle> spittles(
            @RequestParam(value="max",
                    defaultValue=MAX_LONG_AS_STRING) long max,
            @RequestParam(value="count", defaultValue="20") int count) {
        return spittleRepository.findSpittles(max, count);
    }*/

    @RequestMapping(value="/show", method= GET)
    public String showSpittle(
            @RequestParam("spittle_id") long spittleId,
            Model model) {
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

    @RequestMapping(value="/{spittleId}", method= GET)
    public String spittle(
            @PathVariable("spittleId") Long spittleId,
            Model model) {
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

    @RequestMapping(value="/register", method=GET)
    public String showRegistrationForm() {
        return "registerForm";
    }

}