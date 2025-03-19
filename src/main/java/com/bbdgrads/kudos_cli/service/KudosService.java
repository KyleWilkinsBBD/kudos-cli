package com.bbdgrads.kudos_cli.service;

import org.springframework.stereotype.Service;

@Service
public class KudosService {

    private final RequestService requestService;

    public KudosService(RequestService requestService) {
        this.requestService = requestService;
    }

//    public String fetchAllKudos(){
//
//    }
}
