# Welcome to UHelper! 🤖 (last updated on July 18 by Min)

UHelper is an interactive bot that is designed to meet students' needs on Discord.

## Overview 📖

The main goal of UHelper is to assist with time management and wellness. 
Users can create events for exams or assignments at specified times and details. Once scheduled, 
the bot will notify users individually in direct messages a day before the events. 
For study assistance, they can input questions and answers for UHelper to quiz them later, 
with optional hints available. To help boost students’ productivity and focus, 
the bot can set up a Pomodoro timer, including customizable presets and collaborative features that 
allow multiple users to have a timer in common to work remotely.

The bot supports student wellness by suggesting healthy and handy cooking recipes based on given 
ingredients. If requested, the bot can also display nutritional facts about each dish. Additionally,
users can subscribe to receive daily motivational quotes and mental health tips. During breaks, 
users can engage in mini-games, which track their records, to stay entertained. Finally, 
to avoid command conflicts with other bots in the server, users can customize UHelper's command 
lines. The customizability ensures a seamless and non-disruptive experience across various 
functionalities.


## Project Progresses 👀
Currently, we are working to implement the following features. 
You can find all the user stories and other details in our blueprint from [here](https://docs.google.com/document/d/1OcYBGoSZbEqtA47CwSlzFe1wVuZo28Xl-FKUkS_0AUI/edit#heading=h.rwi1fv3j8vi2).

- **Timer** ⏱️
  - [X] Create a timer preset by providing the amount of time wanted for work and break (July 3)
  and record it in a json file. (July 18)
  - [X] (NEW) Provide a list of the timers that are created by the caller when requested (July 18)
  - [ ] Start a timer by giving the bot the name of that timer and notify users when time is up 
  via DM. In doing so, the user can invite other users so that they'd be also notified.
  - [ ] Cancel an ongoing timer.

- **Minigames** 🎮
  - [X] Rock-paper-scissors
  - [X] Trivia

- **Study help** 📚
  - [ ] QuizMe

- **Scheduled Reminders** 📅
  - [X] Create a scheduled event by providing the event name and date (day + time) of the event. (July 14)
    - [X] Create a scheduled assignment by providing the course code, due date, and name of the assignment. (July 5)
    - [X] Create a scheduled exam by providing the course code, location, and date (day + time) of the exam. (July 5)
  - [ ] Bot sends scheduled direct message reminders the day before each event.
  - [X] Bot displays a schedule with ongoing events (incl. assignments, general events, and exams). (July 17)
  - [X] Bot clears the user's current schedule. (July 17)
    - [ ] Deletes reminder alerts
  - [ ] Bot accepts an exam schedule and creates reminders for each exam.

- **Recipe** 🍽️
  - [X] Implement the API call to fetch recipe data (July 6)
  - [X] Handling parameters input for API calls
    - [X] Encoding whitespace
    - [X] Support multiple parameters for API calls (e.g., meal type, diet, dish type, etc.) 
  - [ ] Style recipe suggestions with Embed Builder
    - [ ] Add directions to external recipes
    - [ ] images(if possible) of the cuisine 
    - [ ] Add Nutritional Information

- **Motivational Quotes** 💪

- **Customizable Commands** ⚙️


## Important Notes 🗣️

**July 21** - [Phase 1](https://q.utoronto.ca/courses/345741/pages/phase-1-10-percent?module_item_id=5764241)
(Access to the course page may be restricted)

**August 8** - Phase 2 & Final Presentation
