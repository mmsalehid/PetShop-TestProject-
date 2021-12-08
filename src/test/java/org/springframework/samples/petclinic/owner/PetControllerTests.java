package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	})
class PetControllerTests {

	@Autowired
	private MockMvc mockMvc;


	@MockBean
	private PetRepository petRepository;

	@MockBean
	private OwnerRepository ownerRepository;



	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private static Pet newPet;
	private static Owner newOwner;

	@BeforeEach
	public void init(){
		PetType newPetType = new PetType();
		newPetType.setName("cat");
		newPet = new Pet();
		newPet.setId(2);
		newOwner = new Owner();
		newOwner.setId(3);
		when(ownerRepository.findById(3)).thenReturn(newOwner);
		when(petRepository.findById(2)).thenReturn(newPet);
		when(petRepository.findPetTypes()).thenReturn(Lists.newArrayList(newPetType));
	}

	@Test
	public void testInitUpdateFormShouldReturnOK() throws Exception {
		this.mockMvc.perform(get("/owners/3/pets/2/edit")).andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void testInitCreationFormShouldReuturnOK() throws Exception {
		this.mockMvc.perform(get("/owners/{ownerId}/pets/new",3))
			.andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));

	}

	@Test
	public void testProcessCreationFormShouldReturnOK() throws Exception{
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", 3, 2))
			.andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void testProcessCreationFormShouldReturnErrorTypeRequired() throws Exception{
		mockMvc.perform(post("/owners/{ownerId}/pets/new", 3)
			.param("birthDate", "2018-08-19")
			.param("type", "cat"))
			.andExpect(model().attributeHasNoErrors("owner"))
			.andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "name"))
			.andExpect(model().attributeHasFieldErrorCode("pet", "name", "required")).andExpect(status().isOk())
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void testProcessCreationFormShouldReturnErrorDuplicatePet() throws Exception{
		Pet newPet2 = mock(Pet.class);
		newPet2.setId(4);
		newPet2.setOwner(newOwner);
		newOwner.addPet(newPet);
		newPet2.setName("gorbi");
		when(newPet2.isNew()).thenReturn(false);
		mockMvc.perform(post("/owners/{ownerId}/pets/new", 3)
			.param("name", "gorbi")
			.param("birthDate", "2018-08-19")
			.param("type", "cat"))
			.andExpect(model().attributeHasNoErrors("owner"))
			.andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "name"))
			.andExpect(model().attributeHasFieldErrorCode("pet", "name", "duplicate")).andExpect(status().isOk())
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	void testProcessUpdateFormShouldReturnOk() throws Exception{
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", 3, 2)
			.param("name", "gorbi")
			.param("birthDate", "2018-08-19")
			.param("type", "cat"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void testProcessUpdateFormShouldReturnErrorBirthDateRequired() throws Exception{
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", 3,2)
			.param("name", "gorbi")
			.param("type", "cat"))
			.andExpect(model().attributeHasNoErrors("owner"))
			.andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
			.andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "required")).andExpect(status().isOk())
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}
}
