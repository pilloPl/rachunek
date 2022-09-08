package com.nbp.sorbnet3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LimityApiTest {

	@Autowired
	LimityFacade limityFacade;

	@Autowired
	LimityController limityController;

	@Autowired
	RachunekFacade rachunekFacade;

	@Test
	void widacLimityTest() {
		//given
		NumerRachunku z = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		NumerRachunku na = rachunekFacade.otworzNowyRachunek(Kwota.PLN(2000));
		//and
		limityFacade.ustawLimit(z, na, Kwota.PLN(100));

		//then
		ResponseEntity<List<LimityDto>> limity = limityController.pokazLimity(z.getId());
		LimityDto body = limity.getBody().get(0);

		assertEquals(body.kwota, 100);
		assertEquals(body.rachunekZrodlowy, z.getId());
	}
}
