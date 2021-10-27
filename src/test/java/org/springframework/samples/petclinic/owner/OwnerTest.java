package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OwnerTest {

	@Test
	public void testAddPet_behavioral(){
		Owner sut = new Owner();
		Pet petMock = mock(Pet.class);
		sut.addPet(petMock);
		verify(petMock).setOwner(sut);
	}
	@Test
	public void testAddPet_statical(){
		Owner sut = new Owner();
		Pet pet = new Pet();
		sut.addPet(pet);
		assertEquals(pet.getOwner(),sut);
	}

}
