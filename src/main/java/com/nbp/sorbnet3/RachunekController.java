package com.nbp.sorbnet3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
class RachunekController {

    private final RachunekFacade rachunekFacade;

    RachunekController(RachunekFacade rachunekFacade) {
        this.rachunekFacade = rachunekFacade;
    }

    @PostMapping(path = "/rachunki/otwarcia")
    ResponseEntity<RachunekDto> otworzRachunek() {
        NumerRachunku numerRachunku = rachunekFacade.otworzNowyRachunek();
        Saldo saldo = rachunekFacade.zaladuj(numerRachunku);
        return ResponseEntity.ok(new RachunekDto(numerRachunku, saldo.stan()));    }

    @PostMapping(path = "/rachunki/zamkniecia")
    ResponseEntity<RachunekDto> zamknijRachunek(@RequestBody ZamknijRachunekDto zamknijRachunekDto) {
        NumerRachunku numerRachunku = rachunekFacade.zamknij(new NumerRachunku(zamknijRachunekDto.nr));
        Saldo saldo = rachunekFacade.zaladuj(numerRachunku);
        return ResponseEntity.ok(new RachunekDto(numerRachunku, saldo.stan()));
    }

    @GetMapping(path = "/rachunki/{nr}")
    ResponseEntity<RachunekDto> getRachunek(@PathVariable UUID nr) {
        NumerRachunku numerRachunku = new NumerRachunku(nr);
        Saldo saldo = rachunekFacade.zaladuj(numerRachunku);
        return ResponseEntity.ok(new RachunekDto(saldo.numerRachunku(), saldo.stan()));
    }

}

class ZamknijRachunekDto {
    public UUID nr;
    public ZamknijRachunekDto(UUID nr) {
        this.nr = nr;
    }
}

class RachunekDto {
    public int saldo;
    public UUID nr;
    public String status;

    RachunekDto(NumerRachunku numerRachunku, Kwota stan) {
        this.nr = numerRachunku.getId();
        this.status = numerRachunku.getStatus();
        this.saldo = stan.getAmount();
    }
}


