Meta:

Narrative:
As a user
I want to search in google
So that I can find the answers I seek

Scenario: I am in the google page and search for something
Given I am in the Google page
And I have written 'NaturalAutomation' in the searchBox field
When I search
Then there should be results

Scenario: I am in the google page and want to test the given action
Given I am in the Google page
And I have written 'NaturalAutomation' in the searchBox field
And I did a search
When I clickOnImagesLink
Then there should be results

