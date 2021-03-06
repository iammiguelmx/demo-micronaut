package com.mx.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
@RequiredArgsConstructor
public class HomeController {

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index(Principal principal) {
        return principal.getName();
    }

}
