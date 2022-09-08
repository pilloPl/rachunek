package com.nbp.sorbnet3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BlokadyApiTest {

	@Autowired
	RachunekFacade rachunekFacade;

	@Autowired
	BlokadyController blokadyController;

	@Test
	void widacBlokadyTest() {
		//given
		NumerRachunku zBlokada = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.zalozBlokade(zBlokada, Kwota.PLN(900));

		//then
		ResponseEntity<BlokadyDto> blokady = blokadyController.getBlokady(zBlokada.getId());
		BlokadyDto body = blokady.getBody();
		assertEquals(body.kwota, 900);
		assertEquals(body.numerRachunku, zBlokada.getId());
	}

}
