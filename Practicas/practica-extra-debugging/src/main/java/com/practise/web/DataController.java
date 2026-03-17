package com.practise.web;

import com.viewnext.debugservice.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private ListService listService;

    @PostMapping("/datos")
    public String obtenerDatos(@RequestBody String cadena) {
        return listService.obtenerDatos(cadena);
    }
}
