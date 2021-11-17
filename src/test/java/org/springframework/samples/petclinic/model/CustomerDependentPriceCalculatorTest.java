package org.springframework.samples.petclinic.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.priceCalculators.CustomerDependentPriceCalculator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CustomerDependentPriceCalculatorTest {
	Pet newPet1;
	Pet newPet2;
	Pet newPet3;
	PetType petType1;
	PetType petType2;
	PetType petType3;
	UserType userType1;
	UserType userType2;
	UserType userType3;
	double baseCharge,basePricePerPet;

	@Before
	public void init(){
		newPet1 = Mockito.mock(Pet.class);
		newPet2 = Mockito.mock(Pet.class);
		newPet3 = Mockito.mock(Pet.class);
		petType1 = Mockito.mock(PetType.class);
		petType2 = Mockito.mock(PetType.class);
		petType3 = Mockito.mock(PetType.class);
		userType1 = UserType.NEW;
		userType2 = UserType.SILVER;
		userType3 = UserType.GOLD;
		baseCharge = 100000;
		basePricePerPet= 200000;
	}

	@Test
	public void calcPriceShouldReturnResponseWithoutDiscountAndBaseChargeWithGivenTwoRarePetAndNewUserType(){
		Mockito.when(newPet1.getType()).thenReturn(petType1);
		Mockito.when(newPet1.getBirthDate()).thenReturn(java.util.Date.from(LocalDate.now().minusYears(1).atStartOfDay()
			.atZone(ZoneId.systemDefault())
			.toInstant()));
		Mockito.when(petType1.getRare()).thenReturn(true);
		Mockito.when(newPet2.getType()).thenReturn(petType2);
		Mockito.when(newPet2.getBirthDate()).thenReturn(java.util.Date.from(LocalDate.now().minusYears(3).atStartOfDay()
			.atZone(ZoneId.systemDefault())
			.toInstant()));
		Mockito.when(petType2.getRare()).thenReturn(true);
		List<Pet> pets = Arrays.asList(newPet1,newPet2);
		CustomerDependentPriceCalculator sut = new CustomerDependentPriceCalculator();
		double actualValue = sut.calcPrice(pets, baseCharge, basePricePerPet, userType1);
		double expectedValue = 576000;
		Assert.assertEquals(expectedValue, actualValue,0.0);
	}

	@Test
	public void calcPriceShouldReturnResponseWithDiscountAndBaseChargeWithGivenTwoNotRareNotInfantPetAndGoldUserType(){
		Mockito.when(newPet1.getType()).thenReturn(petType1);
		Mockito.when(newPet1.getBirthDate()).thenReturn(java.util.Date.from(LocalDate.now().minusYears(1).atStartOfDay()
			.atZone(ZoneId.systemDefault())
			.toInstant()));
		Mockito.when(petType1.getRare()).thenReturn(false);
		Mockito.when(newPet2.getType()).thenReturn(petType2);
		Mockito.when(newPet2.getBirthDate()).thenReturn(java.util.Date.from(LocalDate.now().minusYears(3).atStartOfDay()
			.atZone(ZoneId.systemDefault())
			.toInstant()));
		Mockito.when(petType2.getRare()).thenReturn(false);
		List<Pet> pets = Arrays.asList(newPet1,newPet2);
		CustomerDependentPriceCalculator sut = new CustomerDependentPriceCalculator();
		double actualValue = sut.calcPrice(pets, baseCharge, basePricePerPet, userType3);
		double expectedValue = 452000;
		Assert.assertEquals(expectedValue, actualValue,0.0);
	}

	@Test
	public void calcPriceShouldReturnResponseWithDiscountAndBaseChargeWithGiven5RareNotInfantPetsAndNewUserType(){
		Mockito.when(newPet1.getType()).thenReturn(petType1);
		Mockito.when(petType1.getRare()).thenReturn(true);
		Mockito.when(newPet1.getBirthDate()).thenReturn(java.util.Date.from(LocalDate.now().minusYears(1).atStartOfDay()
			.atZone(ZoneId.systemDefault())
			.toInstant()));
		List<Pet> pets = Arrays.asList(newPet1,newPet1,newPet1,newPet1,newPet1);
		CustomerDependentPriceCalculator sut = new CustomerDependentPriceCalculator();
		double actualValue = sut.calcPrice(pets, baseCharge, basePricePerPet, userType1);
		double expectedValue = 1696000;
		Assert.assertEquals(expectedValue, actualValue,0.0);
	}

	@Test
	public void calcPriceShouldReturnResponseWithDiscountOnBaseChargeWithGiven5RareNotInfantPetsAndSilverUserType(){
		Mockito.when(newPet1.getType()).thenReturn(petType1);
		Mockito.when(petType1.getRare()).thenReturn(true);
		Mockito.when(newPet1.getBirthDate()).thenReturn(java.util.Date.from(LocalDate.now().minusYears(1).atStartOfDay()
			.atZone(ZoneId.systemDefault())
			.toInstant()));
		List<Pet> pets = Arrays.asList(newPet1,newPet1,newPet1,newPet1,newPet1);
		CustomerDependentPriceCalculator sut = new CustomerDependentPriceCalculator();
		double actualValue = sut.calcPrice(pets, baseCharge, basePricePerPet, userType2);
		double expectedValue = 1602000;
		Assert.assertEquals(expectedValue, actualValue,0.0);
	}
}
