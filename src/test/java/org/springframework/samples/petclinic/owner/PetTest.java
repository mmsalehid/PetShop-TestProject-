package org.springframework.samples.petclinic.owner;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(Theories.class)
public class PetTest {


	private Pet sut;
	public static Visit visit1 = new Visit();
	public static Visit visit2 = new Visit();
	public static Visit visit3 = new Visit();
	public static Visit visit4 = new Visit();
	public static Visit visit5 = new Visit();
	public static Visit visit6 = new Visit();
	public static Visit visit7 = new Visit();
	public static Visit visit8 = new Visit();
	public static Visit visit9 = new Visit();


	@Before
	public void init(){
		PetType petType1 = new PetType();
		petType1.setName("gorbi");
		sut = new Pet();
		sut.setType(petType1);
		sut.setBirthDate(LocalDate.now());
		sut.setName("gorbi");
		sut.setId(1);
	}

	@DataPoints
	public static Set[] visits(){
		visit1.setDate(LocalDate.now().minusDays(1));
		visit2.setDate(LocalDate.now().minusDays(2));
		visit3.setDate(LocalDate.now().minusDays(3));
		visit4.setDate(LocalDate.now().minusDays(4));
		visit5.setDate(LocalDate.now().minusDays(5));
		visit6.setDate(LocalDate.now().minusDays(6));
		visit7.setDate(LocalDate.now().minusDays(7));
		visit8.setDate(LocalDate.now().minusDays(8));
		visit9.setDate(LocalDate.now().minusDays(9));

		return  new Set[]{
			new HashSet<>(Arrays.asList (visit1, visit4,visit3)),
				new HashSet<>(Arrays.asList (visit7,visit5, visit2, visit1)),
				new HashSet<>(Arrays.asList (visit6, visit1, visit3, visit9, visit8))
		};
	};

	@DataPoints
	public static List[] expectedVisits() {
		visit1.setDate(LocalDate.now().minusDays(1));
		visit2.setDate(LocalDate.now().minusDays(2));
		visit3.setDate(LocalDate.now().minusDays(3));
		visit4.setDate(LocalDate.now().minusDays(4));
		visit5.setDate(LocalDate.now().minusDays(5));
		visit6.setDate(LocalDate.now().minusDays(6));
		visit7.setDate(LocalDate.now().minusDays(7));
		visit8.setDate(LocalDate.now().minusDays(8));
		visit9.setDate(LocalDate.now().minusDays(9));
		return new List[]{
			Arrays.asList (visit1, visit3,visit4),
				Arrays.asList (visit1 , visit2, visit5, visit7),
				Arrays.asList (visit1, visit3, visit6,visit8, visit9)
		};
	}

	@Theory public void testGetVisits(Set<Visit> givenVisit, List<Visit> expectedVisits){
		sut.setVisitsInternal(givenVisit);
		Assume.assumeTrue(givenVisit.size() == expectedVisits.size());
		Assert.assertEquals(expectedVisits, sut.getVisits());
	}


}
