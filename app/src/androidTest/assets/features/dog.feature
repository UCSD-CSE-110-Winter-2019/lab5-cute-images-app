Feature: Showing a dog image

  Scenario: Main activity shows an image from the fake dog image service
    Given a fake dog image service
    And a main activity
    Then the user should see a fake dog image

  @skipAndroid
  Scenario: we gonna skip this one for android
    Given whatever setup
    When whatever event
    Then whatever assert