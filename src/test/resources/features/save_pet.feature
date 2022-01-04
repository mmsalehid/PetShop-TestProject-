Feature: save pet.
  we need ability to save a pet for an owner.

  Scenario: Owner ID is defined.
    Given specific owner and specific pet
    When we call savePet
    Then it should call owner add pet with given pet.
    And it should call pets add save pet with given pet.
    And we should see save pet log.
