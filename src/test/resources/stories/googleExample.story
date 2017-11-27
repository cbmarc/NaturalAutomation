Narrative:
In order to test jbehave's capabilities out, I write this story for the google search page

Scenario: Search for some random string at the google search page

Given I am in the Google page
And I have written 'hello world' in the searchBox
When I search
Then there should be results