
# WarCard Game - Android
This project is an Android implementation of the classic card game War, built in Kotlin using XML-based layouts. Play against a CPU opponent in fast-paced rounds where the highest card wins. The game includes special joker machanics, a dedicaded war mode, animations, and sound effects. 

Made as a first App-project in the Android Developement Course. 

## App-Design
The initial design and it´s elements was created in Figma.
<img width="3245" height="1957" alt="warCardGameDesign" src="https://github.com/user-attachments/assets/a3b2f922-eaf2-43b5-a4dd-573f5b87f989" />

## Gameplay
- Player draws cards against the CPU
- Highest value wins the round
- Tie -> War mode
- Joker triggers special effects and steal cards
- Game contuinues until one player runs out of cards.

## Features
- Full 54-card deck(including Jokers) designed in Figma.
- CPU opponent
- Interactive War mode
- Sound effects
- Animations (YoYo animation library)
- Real-time score tracking
- Winner screen
- Replay / New game options

## Tech Stack
- Kotlin
- Android SDK
- MVVM architecture
- ViewModel + LiveData
- Fragments
- ViewBinding
- MediaPlayer
- YoYo Animations Library

## Architecture 
The project follows MVVM: 

UI (Fragments)

↓
   
GameViewModel

↓
   
Game / Deck / Player / Card

## Key Classes
- **Card** - Represents a playing card
- **Deck** - Builds and shuffles the deck
- **Player** - Holds a hand of cards
- **Game** - Game rules and round logic
- **GameViewModel** - Handles game state
- **PlayFragment** - Main gameplay screen
- **WarFragment** - War mode screen
- **WinnerFragment** - End screen

## How to Run
1. Clone the repository:
   https://github.com/aurevau/warCardGame.git
2. Open in Android Studio
   - Select Open
   - Choose the project folder
   - Wait for Gradle sync
3. Run the app
   - Use the emulator or physical Android device
   - Press run
  
## How to Play
1. Enter your name
2. Tap Start Game
3. Deal cards
4. If there´s a tie, select a card in war mode, winner of tie gets all cards.
5. Collect all cards to win the game.

## Joker Rules
- Jokers have a value of 15
- When played:
  - The opponent loses up to 5 cards
  - Animation and sound effects are triggered

 
Created by **Aurelie Vaudan.**

https://github.com/user-attachments/assets/234c0b1a-a91d-41d5-82da-de7f888eb53d




