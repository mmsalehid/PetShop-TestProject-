package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1arr[0] != t2arr[0]")
@ClauseDefinition(clause = 'b', def = "t1arr[1] != t2arr[1]")
@ClauseDefinition(clause = 'c', def = "t1arr[2] != t2arr[2]")
@ClauseDefinition(clause = 'd', def = "t1arr[0] < 0")
@ClauseDefinition(clause = 'e', def = "t1arr[0] + t1arr[1] < t1arr[2]")

class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	@Test
	public void sampleTest() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@Test
	public void testAreCongruentGivenThreeSameEdge(){
		Triangle t1 = new Triangle(6,3,8);
		Triangle t2 = new Triangle(6,3,8);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@Test
	public void testAreCongruentGivenOneDifferentEdgeInAImplicant(){
		Triangle t1 = new Triangle(6,3,7);
		Triangle t2 = new Triangle(7,3,7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}


	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
		}
	)
	@Test
	public void testAreCongruentGivenOneDifferentEdgeInBImplicant(){
		Triangle t1 = new Triangle(7,4,7);
		Triangle t2 = new Triangle(7,3,7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}


	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testAreCongruentGivenOneDifferentEdgeInCImplicant(){
		Triangle t1 = new Triangle(7,4,7);
		Triangle t2 = new Triangle(7,4,8);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}


	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = true)
		}
	)
	@Test
	public void testAreCongruentGivenNotValidEdges(){
		Triangle t1 = new Triangle(-2,4,7);
		Triangle t2 = new Triangle(-2,4,7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}


	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
		},
		predicateValue = false
	)
	@CACC(
		predicate = "d + e",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
		},
		predicateValue = false
	)
	@Test
	public void testAreCongruentGivenValidEdges(){
		Triangle t1 = new Triangle(5,4,7);
		Triangle t2 = new Triangle(5,4,7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}


	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true),
		},
		predicateValue = true
	)
	@Test
	public void testAreCongruentNotValidEdgesInEImplicant() {
		Triangle t1 = new Triangle(3, 4, 8);
		Triangle t2 = new Triangle(3, 4, 8);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}


	@CACC(
		predicate = "d + e",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false),
		},
		predicateValue = true
	)
	@Test
	public void testAreCongruentNotValidEdgesInDImplicant() {
		Triangle t1 = new Triangle(-2, 10, 7);
		Triangle t2 = new Triangle(-2, 10, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}
	/**
	 * TODO
	 * for UTPC: TR = {TTFF,TTFT,TTTF,FFTT,FTTT,TFTT}
	 * for CUTPNFP: TR = {TTFF, FFTT, TFFF, FTFF, FFTF, FFFT}
	 * TTTF,TTFT,FTTT,TFTT from UTPC are not in CUTPNFP hence CUTPNFP does not subsume UTPC
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = (a & b) || (c & d);
//		predicate = a predicate with any number of clauses
		return predicate;
	}
}
