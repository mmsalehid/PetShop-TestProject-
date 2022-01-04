Feature: add new pet.
  we need ability to add new pet for a owner when we want.

  Scenario: Owner ID is defined.
    Given specific owner
    When we call newPet
    Then it should call owner add pet with a new pet.
    And we should see new pet log.
