package org.springframework.samples.petclinic.owner;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.junit.*;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class PetServiceTest {

	PetService sut;
	int petId;
	Pet pet;
	String petName;
	PetTimedCache mockedCache;
	OwnerRepository mockedRepo;
	Logger mockedLogger;


	public PetServiceTest(int id, String petName, Pet pet){
		this.petId = id;
		this.pet = pet;
		this.petName = petName;
		mockedCache = mock(PetTimedCache.class);
		mockedRepo = mock(OwnerRepository.class);
		mockedLogger = mock(Logger.class);
		sut = new PetService(mockedCache, null, mockedLogger);
	}

	@Parameterized.Parameters
	public static Collection<Object[]> parameters()
	{
		Pet pet1;
		Pet pet2;
		pet1 = new Pet();
		pet2 = new Pet();
		Owner newOwner = new Owner();
		pet1.setId(2);
		pet1.setName("hapoo");
		pet1.setOwner(newOwner);
		pet2.setId(3);
		pet2.setName("gorbii");
		pet2.setOwner(newOwner);
		return Arrays.asList (new Object [][] {{2, "hapoo", pet1}, {3, "gorbii", pet2}});
	}

	@Test public void additionTest() {
		Pet ansPet = new Pet();
		ansPet.setId(this.petId);
		ansPet.setName(this.petName);
		Mockito.when(mockedCache.get(this.petId)).thenReturn(
			ansPet
		);
		assertEquals (this.pet,sut.findPet(this.petId));
	}

}
