Meta:

Narrative:
As a user
I want to add stuff in my fiddle page
So that I can show off examples tables of all kinds to some people here

Scenario: If we use a general examples table, the whole scenario will repeat one time for each row
Given I am in the MyFiddle page
Given I have written '<<text>>' in the <<field>> field
When I add
Then there should be additions

Examples:
|    field    |      text     |
|   textOne   |  Hello World  |
|   textTwo   | ByeBye World  |

Scenario: If we use auto form population
Given I am in the MyFiddle page
Given I populate the page with random data
When I add
Then there should be additions