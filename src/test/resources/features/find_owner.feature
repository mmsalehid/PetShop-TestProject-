Feature: Find qwner by its id.
  we need to find every owner that we want.

  Scenario: Owner ID is defined.
    Given defined ownerID
    When we call findOwner
    Then we should get owner from owners repo.
    And we should see find owner log.
