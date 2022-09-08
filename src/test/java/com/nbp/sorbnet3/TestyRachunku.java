package com.nbp.sorbnet3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestyRachunku {

	@Autowired
	RachunekFacade rachunekFacade;

	@Test
	void możnaPrzelaćŚrodki() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(50));
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(200));

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(30));

		//then
		assertEquals(Rezultat.Przeniesiono, rezultat);
		//sprawdzenie widoku
	}

	@Test
	void nieMożnaPrzelaćŚrodkówJeśliJestBlokadaNaRachunku() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.zalozBlokade(obciazany, Kwota.PLN(900));

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(950));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, rezultat);
	}

	@Test
	void nieMożnaPrzelaćŚrodkówJeśliRachunekJestZamknięty() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(2000));
		//and
		rachunekFacade.zamknij(zasilany);

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(100));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, rezultat);
		//sprawdzenie widoku
	}

	@Test
	void nieMożnaPrzelaćŚrodkówJeśliRachunekJeśliNieMaIchWystarczająco() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(0));
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(1200));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, rezultat);
	}



	@Test
	void możnaPrzelaćŚrodkiJeśliJestBlokadaAleWystarczyŚrodków() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.zalozBlokade(obciazany, Kwota.PLN(900));

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(50));

		//then
		assertEquals(Rezultat.Przeniesiono, rezultat);
	}


	@Test
	void możnaPrzelaćŚrodkówJeśliJestLimitAleNiewykorzystany() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.ustawLimit(obciazany, zasilany, Kwota.PLN(900));

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(50));

		//then
		assertEquals(Rezultat.Przeniesiono, rezultat);
	}

	@Test
	void nieMożnaPrzelaćŚrodkówJeśliJestUstawionyLimit() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.ustawLimit(obciazany, zasilany, Kwota.PLN(900));

		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(950));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, rezultat);
	}

	@Test
	void poPrzelaniuŚrodkówZmniejszaSięLimit() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.ustawLimit(obciazany, zasilany, Kwota.PLN(900));
		//and
		rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(50));
		//when
		Rezultat rezultat = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(860));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, rezultat);
	}


	@Test
	void możnaZdjąćLimit() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.ustawLimit(obciazany, zasilany, Kwota.PLN(900));
		//and
		Rezultat przedZdjeciemLimitu = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(860));

		//when
		rachunekFacade.zdejmijLimit(obciazany, zasilany);
		//and
		Rezultat poZdjeciuLimitu = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(860));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, przedZdjeciemLimitu);
		assertEquals(Rezultat.Przeniesiono, poZdjeciuLimitu);
	}

	@Test
	void możnaZwiększyćLimit() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.ustawLimit(obciazany, zasilany, Kwota.PLN(900));
		//when
		Rezultat przedZwiekszeniemLimitu = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(950));

		//and
		rachunekFacade.zwiekszLimit(obciazany, zasilany, Kwota.PLN(950));
		//and
		Rezultat poZwiekszeniuLimitu = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(860));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, przedZwiekszeniemLimitu);
		assertEquals(Rezultat.Przeniesiono, poZwiekszeniuLimitu);
	}

	@Test
	void możnaZmniejszyćLimit() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000000));
		//and
		rachunekFacade.ustawLimit(obciazany, zasilany, Kwota.PLN(900));
		//when
		Rezultat przedZmniejszeniemLimitu = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(800));

		//and
		rachunekFacade.zmniejszLimit(obciazany, zasilany, Kwota.PLN(700));
		//and
		Rezultat poZmniejszeniuLimitu = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(800));

		//then
		assertEquals(Rezultat.Przeniesiono, przedZmniejszeniemLimitu);
		assertEquals(Rezultat.Nie_Przeniesiono, poZmniejszeniuLimitu);
	}

	@Test
	void możnaZdjąćBlokadę() {
		//given
		NumerRachunku zasilany = rachunekFacade.otworzNowyRachunek();
		//and
		NumerRachunku obciazany = rachunekFacade.otworzNowyRachunek(Kwota.PLN(1000));
		//and
		rachunekFacade.zalozBlokade(obciazany, Kwota.PLN(900));
		//when
		Rezultat przedZdjeciemBlokady = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(800));

		//and
		rachunekFacade.zdejmijBlokade(obciazany, Kwota.PLN(900));
		//and
		Rezultat poZdjeciuBlokady = rachunekFacade.przenies(zasilany, obciazany, Kwota.PLN(800));

		//then
		assertEquals(Rezultat.Nie_Przeniesiono, przedZdjeciemBlokady);
		assertEquals(Rezultat.Nie_Przeniesiono, poZdjeciuBlokady);
	}

}
