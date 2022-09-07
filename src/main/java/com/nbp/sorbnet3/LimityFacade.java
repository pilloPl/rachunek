package com.nbp.sorbnet3;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
class LimityFacade {
    private final LimityRepository limityRepository;

    LimityFacade(LimityRepository limityRepository) {
        this.limityRepository = limityRepository;
    }
}

@Repository
class LimityRepository {

}
