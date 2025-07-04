# Wordle-Project
![image7](https://github.com/user-attachments/assets/28171dbb-50d6-4b04-9c6c-ade70484b824)
![image1](https://github.com/user-attachments/assets/3665fb10-a856-4833-9b84-6f3866f4e00e)
![image6](https://github.com/user-attachments/assets/da07a485-8d87-473a-8080-2c1902cfdb0e)
![image2](https://github.com/user-attachments/assets/fe7f4dfb-eea5-4454-9944-f47baec6c262)
![image9](https://github.com/user-attachments/assets/0128d45f-78ee-433c-853b-a14de1eb865a)
![image8](https://github.com/user-attachments/assets/85c005e5-657d-4016-9f94-196708d21c95)
![image4](https://github.com/user-attachments/assets/f824ab88-1943-4320-9544-d69317d84bf7)
![image3](https://github.com/user-attachments/assets/15d5d600-2d93-4491-b1a7-c1f4b28c16c8)
![image5](https://github.com/user-attachments/assets/d6f47a82-0ef1-4318-a0c3-a9257ae13431)



Wordle in Java based on the New York Times game.
Made as a group project in CSC 335 Object-Oriented Programming in Sophomore Year.
Includes class and sequence diagrams.

I was responsible for:

  • Letter (extended Button) class (the basis for how the whole thing worked, the letters make up the keyboard and display where the words pop up)

  • KeyboardDisplay class (bottom keyboard which you can click with your mouse and shows which letters have been used)

  • MultiWordle class: wow factor to customize the length of the word (3-7 letters) and how many guesses you get.
    Had to find and import dictionaries for possible words to be able to guess for each of the different word lengths as well as dictionaries of more common words that it
    would choose to be the word of the day
    
  • SettingsDisplay class: not only the popup window, but the entire functionality and integration with other components for hard mode, dark mode, and contrast mode.

  • ErrorMessageDisplay class: I added all of the popup messages, as well as all of the animations like shaking and letters flipping.

  • Guess class: class to make and check guesses

  • all of the word bank text files for 3-7 letter words

  • Also worked on code in nearly every class, especially the ones in the model like Word and Dictionary to restructure and rewrite most of it to work with the
    rest of the implementation, and worked a lot in the WordleGUI class.

Additionally, I made some extra effort to make the GUI look as identical to the original game by finding the exact fonts, colors, and images, as well as styling/ placing everything with the JavaFX framework.
