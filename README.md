# SoccerChallenge

You should implement a one screen application with 2 tabs (fixtures and results)
  * Fixtures: ​https://storage.googleapis.com/cdn-og-test-api/test-task/fixtures.json 
  * Results: ​https://storage.googleapis.com/cdn-og-test-api/test-task/results.json

![Screenshot](SoccerAppDesign.png?raw=true "Optional Title")

1. Both screens should contains list of matches divided by month.

2. Every match item must contain following info:\
    a. Competition name\
    b. Venue name\
    c. Match date and time (in user timezone)\
    d. Home and away team names
    
3. Match item on Fixture page should have additional info: \
    a. Match day of the month, Match day of the week\
    b. If match is in postponed state, it must be highlighted
    
4. Match item on Result page should have additional info \
    a. Home and Away team scores\
    b. Winner team must be highlighted
    
5. Add possibility to filter matches by competition

Despite task low complexity please try to apply specific architecture approach for your solution.

Please avoid using code generation frameworks (like AndroidAnnotation) which massively alter your code. Dagger framework is an exception. Use any other libraries and languages(java/kotlin) you want. UI does not have to be exactly the same, it’s up to you. Result code should be well written and structured.

Do you want to contribute?
--------------------------

Feel free to report or add any useful feature, I will be glad to improve it with your help.


Developed By
------------

* Lucas Griotto  - <lucasgriotto@hotmail.com>

License
-------

    Copyright 2016 Lucas Griotto

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
