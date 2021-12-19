package org.springframework.samples.petclinic.utility;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.util.*;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PriceCalculatorTest {

	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;
	private static int DISCOUNT_PRE_VISIT = 2;

	private static List<Pet> pets1;
	private static List<Pet> pets2;
	private static List<Pet> pets3;
	@BeforeEach
	public void init(){

		LocalDate now = LocalDate.now();

		Visit visit1 = new Visit();
		visit1.setDate(now.minusDays(50));
		Visit visit2 = new Visit();
		visit2.setDate(now.minusDays(100));
		Visit visit3 = new Visit();
		visit3.setDate(now.minusDays(200));
		Visit visit4 = new Visit();
		visit4.setDate(now.minusDays(320));
		Visit visit5 = new Visit();
		visit4.setDate(now.minusDays(400));
		List<Visit> visits1 = Arrays.asList(visit1);
		List<Visit> visits2 = Arrays.asList(visit4, visit3, visit2);
		List<Visit> visits3 = Arrays.asList(visit5, visit4);
		List<Visit> visits4 = Arrays.asList(visit1);


		Pet pet1 = mock(Pet.class);
		when(pet1.getBirthDate()).thenReturn(now.minusYears(3));
		when(pet1.getVisitsUntilAge(3)).thenReturn(visits1);
		Pet pet2 = mock(Pet.class);
		when(pet2.getBirthDate()).thenReturn(now.minusYears(2));
		when(pet2.getVisitsUntilAge(2)).thenReturn(new ArrayList<Visit>());
		Pet pet3 = mock(Pet.class);
		when(pet3.getBirthDate()).thenReturn(now.minusYears(1));
		when(pet3.getVisitsUntilAge(1)).thenReturn(visits2);
		Pet pet4 = mock(Pet.class);
		when(pet4.getBirthDate()).thenReturn(now.minusYears(1));
		when(pet4.getVisitsUntilAge(1)).thenReturn(visits2);
		Pet pet5 = mock(Pet.class);
		when(pet5.getBirthDate()).thenReturn(now.minusYears(1));
		when(pet5.getVisitsUntilAge(1)).thenReturn(visits3);
		Pet pet6 = mock(Pet.class);
		when(pet6.getBirthDate()).thenReturn(now.minusYears(4));
		when(pet6.getVisitsUntilAge(4)).thenReturn(visits1);
		Pet pet7 = mock(Pet.class);
		when(pet7.getBirthDate()).thenReturn(now.minusYears(1));
		when(pet7.getVisitsUntilAge(1)).thenReturn(visits2);
		Pet pet8 = mock(Pet.class);
		when(pet8.getBirthDate()).thenReturn(now.minusYears(1));
		when(pet8.getVisitsUntilAge(1)).thenReturn(visits4);
		Pet pet9 = mock(Pet.class);
		when(pet8.getBirthDate()).thenReturn(now.minusYears(1));
		when(pet8.getVisitsUntilAge(1)).thenReturn(visits4);
		pets1 = Arrays.asList(pet1, pet2, pet3, pet4, pet5, pet6, pet7);
		pets2 = Arrays.asList(pet1, pet2, pet3, pet4, pet5, pet8);
		pets3 = Arrays.asList(pet1, pet2, pet3, pet4, pet7, pet6, pet5);
	}

	@Test
	void testForMutations1(){
		double given = PriceCalculator.calcPrice(pets1, 10000,2000);
		Assertions.assertEquals(219680.0, given);
	}

	@Test
	void testForMutations2(){
		PriceCalculator sut = new PriceCalculator();
		double given = sut.calcPrice(pets2, 10000,2000);
		Assertions.assertEquals(45040.0, given);
	}

	@Test
	void testForMutations3(){
		PriceCalculator sut = new PriceCalculator();
		double given = sut.calcPrice(pets3, 10000,2000);
		Assertions.assertEquals(327840.0, given);
	}




}
