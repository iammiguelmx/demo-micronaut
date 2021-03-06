package com.mx.controller;

import com.mx.model.SpaceShip;
import com.mx.repository.SpaceShipRepository;
import com.sun.istack.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(value="/spaceship")
@RequiredArgsConstructor
public class SpaceShipController {

    private final SpaceShipRepository repository;

    @Get("/gimme")
    public Iterable<SpaceShip> gimme(){
        return repository.findAll();
    }

    @Delete("/delete/{id:2}")
    public SpaceShip delete(@PathVariable Integer id){
        Optional<SpaceShip> byId = repository.findById(id);
        if (byId.isEmpty())
            throw new IllegalArgumentException("Does not exist: " + id);
        repository.delete(byId.get());
        return byId.get();
    }

    @Post("/")
    public SpaceShip createWithBody(@Body SpaceShip spaceship){
        return repository.save(spaceship);
    }

    @Post("/create/{captain}/{model}/{fuel}")
    public SpaceShip create(@PathVariable String captain, @PathVariable String model, @PathVariable Integer fuel){
        return repository.save(new SpaceShip(null, model, captain, fuel));
    }


    @Post("/update")
    public SpaceShip updateWithBody(@Body SpaceShip spaceship){
        Optional<SpaceShip> byId = repository.findById(spaceship.getId());
        SpaceShip entity = byId.get();
        entity.setCaptain(spaceship.getCaptain());
        entity.setFuel(spaceship.getFuel());
        entity.setModel(spaceship.getModel());
        return repository.update(entity);
    }

    @Post("/queryparams{?model,captain,fuel}")
    public SpaceShip createWithBody(@QueryValue Optional<String> model, @QueryValue @Nullable String captain, @QueryValue @Nullable Integer fuel){
        return repository.save(new SpaceShip(null, model.get(), captain, fuel));
    }
}
