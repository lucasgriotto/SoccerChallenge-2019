# SoccerChallenge

You should implement a one screen application with 2 tabs (fixtures and results)

* Fixtures: https://storage.googleapis.com/cdn-og-test-api/test-task/fixtures.json
* Results: https://storage.googleapis.com/cdn-og-test-api/test-task/results.json

![Screenshot](SoccerAppDesign.png?raw=true "Optional Title")

1. Both screens should contain a list of matches divided by month

2. Every match item must contain the following info: \
   a. Competition name \
   b. Venue name \
   c. Match date and time (in user time zone) \
   d. Home and away team names

3. Match item on Fixture page should have additional info: \
   a. Match day of the month, Match day of the week \
   b. If match is in postponed state, it must be highlighted

4. Match item on Result page should have additional info \
   a. Home and Away team scores \
   b. Winner team must be highlighted

5. Add possibility to filter matches by competition

Despite task low complexity please try to apply specific architecture approach for your solution.

Please avoid using code generation frameworks (like AndroidAnnotation) which massively alter your code. Dagger framework
is an exception. Use any other libraries and languages(java/kotlin) you want. UI does not have to be exactly the same,
it’s up to you. Result code should be well written and structured.

### Requirements beyond the scope of the statement

6. Add a match detail screen where: \
   a. All match info is displayed \
   b. Teams logos are displayed \
   c. When team logo is clicked, the team's website should open

7. Store matches locally: \
   a. Add the possibility to swipe-refresh matches's lists

8. Store selected competition filters locally

9. Add unit tests

## Solution

- The architecture used is MVVM with Clean Architecture
- All requirements have been developed
- Flows and coroutines are used
- The Jetpack navigation library is implemented
- Matches are stored locally using Room
- A shared ViewModel is used to handle competition filters
- Dagger2 is used for DI
- Glide is used to display team logos
- Selected competition filters are stored
  using [Preference DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

## Developed By

* Lucas Griotto - <griottolucas@gmail.com>