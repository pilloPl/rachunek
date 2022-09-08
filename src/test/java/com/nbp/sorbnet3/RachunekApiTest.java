package com.nbp.sorbnet3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RachunekApiTest {

    @Autowired
    RachunekController rachunekController;

    @Test
    void moznaOtworzyc() {

        //when
        UUID nr = rachunekController.otworzRachunek().getBody().nr;
        //then
        ResponseEntity<RachunekDto> rachunek = rachunekController.getRachunek(nr);
        assertEquals(nr, rachunek.getBody().nr);
        assertEquals("Otwarty", rachunek.getBody().status);
        assertEquals(0, rachunek.getBody().saldo);
    }

    @Test
    void moznaZamknac() {
        //given
        UUID nr = rachunekController.otworzRachunek().getBody().nr;
        //when
        rachunekController.zamknijRachunek(new ZamknijRachunekDto(nr));
        //then
        ResponseEntity<RachunekDto> rachunek = rachunekController.getRachunek(nr);
        assertEquals(nr, rachunek.getBody().nr);
        assertEquals("Zamkniety", rachunek.getBody().status);
        assertEquals(0, rachunek.getBody().saldo);

    }

}
