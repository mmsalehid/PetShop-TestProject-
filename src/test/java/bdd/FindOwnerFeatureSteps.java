package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetService;

import static org.mockito.Mockito.*;

public class FindOwnerFeatureSteps {
	private Owner testOwner;
	private final int TEST_OWNER_ID = 10;
	private Owner actualResult;

	Logger logger = mock(Logger.class);

	OwnerRepository ownerRepository = mock(OwnerRepository.class);


	PetService petService = new PetService(null,ownerRepository,logger	);

	@Given("defined ownerID")
	public void difinedOwnerId() {
		testOwner = new Owner();
		testOwner.setId(10);
		testOwner.setFirstName("ali");
		testOwner.setFirstName("alavi");
		when(ownerRepository.findById(TEST_OWNER_ID)).thenReturn(testOwner);
	}



	@When("we call findOwner")
	public void we_call_findOwner() {
		actualResult = petService.findOwner(TEST_OWNER_ID);
	}
	@Then("we should get owner from owners repo.")
	public void we_should_get_owner_from_owners_repo() {
		Assertions.assertEquals(testOwner.getId(), actualResult.getId());
	}
	@Then("we should see find owner log.")
	public void we_should_see_find_owner_log() {
		verify(logger).info("find owner {}", TEST_OWNER_ID);
	}



}
