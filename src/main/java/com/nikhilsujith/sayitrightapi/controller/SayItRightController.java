package com.nikhilsujith.sayitrightapi.controller;

import com.nikhilsujith.sayitrightapi.model.SayItRight;
import com.nikhilsujith.sayitrightapi.service.SayItRightService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/sir")
public class SayItRightController {

    SayItRightService service;

//

    public SayItRightController(SayItRightService service) {
        this.service = service;
    }

    @GetMapping
    public List<SayItRight> getAllData(){
        return service.getAllData();
    }
}
