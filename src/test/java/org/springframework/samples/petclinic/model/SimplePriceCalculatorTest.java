package org.springframework.samples.petclinic.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.priceCalculators.SimplePriceCalculator;
import org.junit.Assert;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimplePriceCalculatorTest {

	private static final double BASE_RARE_COEF = 1.2;
	Pet newPet1;
	Pet newPet2;
	PetType petType1;
	PetType petType2;
	UserType userType1;
	UserType userType2;
	double baseCharge,basePricePerPet;
	@Before
	public void init(){
		newPet1 = Mockito.mock(Pet.class);
		newPet2 = Mockito.mock(Pet.class);
		petType1 = Mockito.mock(PetType.class);
		petType2 = Mockito.mock(PetType.class);
		userType1 = UserType.NEW;
		userType2 = UserType.SILVER;
		baseCharge = 100000;
		basePricePerPet= 200000;
	}
	@Test
	public void testCalcPriceWithListOfNotRarePetsAndUserWithDiscount() {
		Mockito.when(newPet1.getType()).thenReturn(petType1);
		Mockito.when(petType1.getRare()).thenReturn(false);
		List<Pet> pets = Collections.singletonList(newPet1);
		SimplePriceCalculator sut = new SimplePriceCalculator();
		double actualValue = sut.calcPrice(pets, baseCharge, basePricePerPet, userType1);
		double expectedValue = 285000;
		Assert.assertEquals(expectedValue, actualValue,0.0);
	}

	@Test
	public void testCalcPriceWithOfRareAndNotRarePetsAndUserWithoutDiscount(){
		Mockito.when(newPet1.getType()).thenReturn(petType1);
		Mockito.when(petType1.getRare()).thenReturn(false);
		Mockito.when(newPet2.getType()).thenReturn(petType2);
		Mockito.when(petType2.getRare()).thenReturn(true);
		List<Pet> pets = Arrays.asList(newPet1,newPet2);
		SimplePriceCalculator sut = new SimplePriceCalculator();
		double actualValue = sut.calcPrice(pets, baseCharge, basePricePerPet, userType2);
		double expectedValue = 540000;
		Assert.assertEquals(expectedValue, actualValue,0.0);
	}

}
