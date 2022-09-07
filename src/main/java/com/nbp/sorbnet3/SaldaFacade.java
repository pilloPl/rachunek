package com.nbp.sorbnet3;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
class SaldaFacade {

    private final SaldaRepository saldaRepository;

    SaldaFacade(SaldaRepository saldaRepository) {
        this.saldaRepository = saldaRepository;
    }
}

@Repository
class SaldaRepository {


}