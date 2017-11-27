Narrative:
In order to test jbehave's capabilities out, I write this story for the google search page

Scenario: Search for some random string at the google search page

Given the user is in the Google page
And the page is populated with random data
When the user does a search
Then there should be results