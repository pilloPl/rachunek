package com.nbp.sorbnet3;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
class BlokadyFacade {

    private final BlokadyRepository blokadyRepository;

    BlokadyFacade(BlokadyRepository blokadyRepository) {
        this.blokadyRepository = blokadyRepository;
    }
}


@Repository
class BlokadyRepository {


}