package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetService;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SavePetFeatureSteps {

	private final int PET_TEST_ID = 10;

	private Pet testPet;
	private Owner testOwner;
	private Logger logger = mock(Logger.class);
	private PetTimedCache pets = mock(PetTimedCache.class);


	PetService petService = new PetService(pets,null,logger	);

	@Given("specific owner and specific pet")
	public void specific_pet_and_owner() {
		testPet = new Pet();
		testPet.setId(PET_TEST_ID);
		testOwner = mock(Owner.class);
	}



	@When("we call savePet")
	public void we_call_savePet() {
		petService.savePet(testPet,testOwner);
	}
	@Then("it should call owner add pet with given pet.")
	public void it_should_call_owner_add_pet_with_given_pet() {
		verify(testOwner).addPet(testPet);
	}
	@Then("it should call pets add save pet with given pet.")
	public void it_should_call_pets_add_save_pet_with_given_pet() {
		verify(pets).save(testPet);
	}
	@Then("we should see save pet log.")
	public void we_should_see_save_pet_log() {
		verify(logger).info("save pet {}", PET_TEST_ID);
	}

}
