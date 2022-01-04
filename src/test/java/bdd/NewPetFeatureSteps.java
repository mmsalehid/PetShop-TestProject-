package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetService;

import static org.mockito.Mockito.*;

public class NewPetFeatureSteps {

	private Owner testOwner;
	private final int TEST_OWNER_ID = 10;
	private Owner actualResult;
	private Pet newPet;

	private Logger logger = mock(Logger.class);
	private Owner ownerMock = mock(Owner.class);
	private OwnerRepository ownerRepository = mock(OwnerRepository.class);


	PetService petService = new PetService(null,ownerRepository,logger	);

	@Given("specific owner")
	public void specific_owner() {
		testOwner = ownerMock;
		when(testOwner.getId()).thenReturn(TEST_OWNER_ID);
	}


	@When("we call newPet")
	public void we_call_newPet() {
		newPet = petService.newPet(testOwner);
	}
	@Then("it should call owner add pet with a new pet.")
	public void it_should_call_owner_add_pet_with_a_new_pet() {
		verify(testOwner).addPet(any());
	}
	@Then("we should see new pet log.")
	public void we_should_see_new_pet_log() {
		verify(logger).info("add pet for owner {}", testOwner.getId());
	}

}
