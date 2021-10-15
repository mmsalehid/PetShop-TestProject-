package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.*;


class OwnerTest {

	Owner sut;
	Set<Pet> pets;
	Pet pet3;
	Pet pet1;
	Pet pet2;


	@BeforeEach
	public void setup(){
		sut = new Owner();
		PetType petType1 = new PetType();
		petType1.setName("gorbi");
		pet1 = new Pet();
		pet1.setType(petType1);
		pet1.setBirthDate(LocalDate.now());
		pet1.setName("gorbi");
		pet1.setId(1);
		PetType petType2 = new PetType();
		petType2.setName("hapoo");
		pet2 = new Pet();
		pet2.setType(petType2);
		pet2.setBirthDate(LocalDate.now());
		pet2.setName("hapoo");
		pet2.setId(2);
		PetType petType3 = new PetType();
		petType3.setName("gav");
		pets = new HashSet<Pet>(Arrays.asList(pet1, pet2));
		pet3 = new Pet();
		pet3.setType(petType3);
		pet3.setBirthDate(LocalDate.now());
		pet3.setName("gav");
	}

	@Test
	public void testGetterAndSettersMethods(){
		sut.setAddress("Test Address");
		sut.setCity("Tehran");
		sut.setTelephone("09129375633");

		Assert.assertEquals("Test Address", sut.getAddress());
		Assert.assertEquals("Tehran", sut.getCity());
		Assert.assertEquals("09129375633", sut.getTelephone());
	}

	@Test
	public void testPetsGetterSetter(){
		sut.setPetsInternal(pets);
		Set<Pet> expectedPets = new HashSet<>(pets);
		Set<Pet> givenPets = sut.getPetsInternal();
		assertEquals(expectedPets,givenPets);
	}

	@Test
	public void testAddAndGetPet(){
		sut.addPet(pet3);
		Pet givenPet = sut.getPet("gav", true);
		Assert.assertNull(givenPet);
		pet3.setId(3);
		givenPet = sut.getPet("gav");
		Assert.assertEquals(pet3,givenPet);
	}

	@Test
	public void testRemovePet(){
		sut.setPetsInternal(pets);
		sut.addPet(pet3);
		sut.removePet(pet3);
		Set<Pet> givenPets = sut.getPetsInternal();
		Assert.assertEquals(pets, givenPets);
		sut.removePet(pet1);
		Assert.assertEquals(Collections.singleton(pet2), givenPets);
	}


}
