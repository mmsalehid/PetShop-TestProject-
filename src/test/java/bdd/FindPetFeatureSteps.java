package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetService;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class FindPetFeatureSteps {
	private Pet actualPet;
	private Pet testPet;
	private final int PET_TEST_ID = 10;


	private Logger logger = mock(Logger.class);
	private PetTimedCache pets = mock(PetTimedCache.class);


	PetService petService = new PetService(pets,null,logger	);

	@Given("petIdThatExitsForAPet")
	public void definePet() {
		testPet = new Pet();
		testPet.setId(PET_TEST_ID);
		when(pets.get(PET_TEST_ID)).thenReturn(testPet);
	}


	@When("we call findPet")
	public void we_call_findPet() {
		actualPet = petService.findPet(PET_TEST_ID);
	}
	@Then("it should return pet with given petId.")
	public void it_should_return_pet_with_given_petID() {
		Assertions.assertEquals(testPet, actualPet);
	}
	@Then("we should see find pet log.")
	public void we_should_see_find_pet_log() {
		verify(logger).info("find pet by id {}", PET_TEST_ID);
	}

}
