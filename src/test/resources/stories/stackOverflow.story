Narrative:
In order to test jbehave's capabilities out, I write this story for the stackoverflow jobs page

Scenario: Search for react jobs in stackoverflow

Given I am in the StackOverflowJobs page
And I have written 'react' in the searchBox field
And I have written 'Barcelona, Espanya' in the locationBox field
When I search
Then there should be results