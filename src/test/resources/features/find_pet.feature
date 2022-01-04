Feature: find a pet.
  we need ability to get a pet by its id.

  Scenario: petId is defined.
    Given petIdThatExitsForAPet
    When we call findPet
    Then it should return pet with given petId.
    And we should see find pet log.
