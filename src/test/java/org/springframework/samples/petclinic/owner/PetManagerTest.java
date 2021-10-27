package org.springframework.samples.petclinic.owner;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.visit.Visit;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetManagerTest {

	PetManager sut;
	OwnerRepository ownerRepositoryMock;
	Logger loggerMock;
	PetTimedCache petTimedCacheMock;


	//All tests use behavior verification
	//All tests are implemented with mockisty attitude
	@BeforeEach
	public void init(){
		ownerRepositoryMock = mock(OwnerRepository.class);
		loggerMock = mock(Logger.class);
		petTimedCacheMock = mock(PetTimedCache.class);
		sut = new PetManager(petTimedCacheMock, ownerRepositoryMock, loggerMock);
	}


	//Mockisty(Owner does not have a interesting behavior )
	@Test
	public void testFindOwner(){
		Owner testOwner = new Owner();
		testOwner.setId(2);
		when(ownerRepositoryMock.findById(2)).thenReturn(testOwner);//stub
		Owner givenOwner = sut.findOwner(2);
		verify(loggerMock).info("find owner {}",2);//mock
		assertEquals(testOwner.getId(),givenOwner.getId());
	}

	//Mockisty
	@Test
	public void testAddNewPet(){
		Owner testOwner = mock(Owner.class);
		when(testOwner.getId()).thenReturn(2);
		Pet pet = sut.newPet(testOwner);
		verify(testOwner, times(1)).addPet(pet);//mock
		verify(loggerMock).info("add pet for owner {}",2);//mock
	}

	//Mockisty(Owner does not have a interesting behavior)
	@Test
	public void testFindPet(){
		Pet testPet = new Pet();//using actual object
		when(petTimedCacheMock.get(anyInt())).thenReturn(testPet);//stub
		Pet givenPet = sut.findPet(2);
		assertEquals(testPet, givenPet);
		verify(loggerMock).info("find pet by id {}",2);//mock
	}


	@Test
	public void testSavePet(){
		Pet testPet = new Pet();//using actual object
		testPet.setId(2);
		Owner owner = mock(Owner.class);
		sut.savePet(testPet, owner);
		verify(owner).addPet(testPet);//mock
		verify(loggerMock).info("save pet {}", testPet.getId());//mock
	}

	@Test
	public void testGetOwnersPetHappyPath(){
		Owner owner = mock(Owner.class);
		when(sut.findOwner(2)).thenReturn(owner); //stub
		List<Pet> pets = Arrays.asList (new Pet(), new Pet(),new Pet());
		when(owner.getPets()).thenReturn(pets);//stub
		List<Pet> givenPets = sut.getOwnerPets(2);
		assertEquals(pets,givenPets);
		verify(loggerMock).info("finding the owner's pets by id {}", 2);//mock
	}
	@Test
	public void testGetOwnerPetWithNoOwnerExist(){
		Owner owner = mock(Owner.class);
		when(sut.findOwner(2)).thenReturn(null); //stub
		Assertions.assertThrows(NullPointerException.class, () -> sut.getOwnerPets(2));
	}
	@Test
	public void testGetOwnerPetTypesHappyPath(){
		Pet pet1 = new Pet();
		PetType petType1 = new PetType();
		petType1.setName("hapoo");
		pet1.setType(petType1);
		Pet pet2 = new Pet();
		PetType petType2 = new PetType();
		petType2.setName("gorbi");
		pet2.setType(petType2);
		Owner owner = mock(Owner.class);
		when(sut.findOwner(2)).thenReturn(owner); //stub
		List<Pet> pets = Arrays.asList (pet1,pet2);
		when(owner.getPets()).thenReturn(pets);//stub
		Set<PetType> givenPetTypes = sut.getOwnerPetTypes(2);
		Set<PetType> expectedPetTypes = new HashSet<>(Arrays.asList(petType1, petType2));
		assertEquals(expectedPetTypes,givenPetTypes);
		verify(loggerMock).info("finding the owner's petTypes by id {}", 2);//mock
	}

	@Test
	public void testGetOwnerPetTypesWithNoOwnerExist(){
		when(sut.findOwner(2)).thenReturn(null); //stub
		Assertions.assertThrows(NullPointerException.class, () -> sut.getOwnerPetTypes(2));
	}

	@Test
	public void testGetVisitsBetween(){
		Visit visit1 = new Visit();
		Visit visit2 = new Visit();
		Visit visit3 = new Visit();
		List<Visit> expectedVisits= Arrays.asList(visit1, visit2, visit3);
		Pet pet = mock(Pet.class);
		when(petTimedCacheMock.get(2)).thenReturn(pet); //stub
		LocalDate startDate = LocalDate.now().minusDays(2);
		LocalDate endDate = LocalDate.now().minusDays(2);
		when(pet.getVisitsBetween(startDate, endDate)).thenReturn(expectedVisits);//stub
		List<Visit> actualVisits = sut.getVisitsBetween(2,startDate, endDate);
		assertEquals(expectedVisits, actualVisits);
		verify(loggerMock).info("get visits for pet {} from {} since {}", 2, startDate, endDate);
	}
	@Test
	public void testGetVisitsBetweenWithNoPetExist(){
		LocalDate startDate = LocalDate.now().minusDays(2);
		LocalDate endDate = LocalDate.now().minusDays(2);
		when(petTimedCacheMock.get(2)).thenReturn(null); //stub
		Assertions.assertThrows(NullPointerException.class, () -> sut.getVisitsBetween(2, startDate, endDate));
	}
}
