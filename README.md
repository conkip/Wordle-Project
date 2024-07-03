# Wordle-Project
Wordle in Java based on the New York Times game. Made as a group project in CSC 335 Object-Oriented Programming.

Used Git (in a different repository, I just transferred it here to show) and Scrum to communicate with three other team members.

There were several classes I wrote and maintained. 

This project gave good experience with coding in a team, applying several object-oriented design patterns like model-view-controller design, testing code with JUnit, and making graphics with JavaFX.

Also, got experience working on much of the design, backend, and front end.

It may seem simple, just being a wordle game, but there are a lot of features and functionality added to it.

I worked on many of the original ideas for the design and structure, as well as making the class and sequence diagrams.

I was primarily responsible for:
  • Letter class (the basis for how the whole thing worked, the letters make up the keyboard and display where the words pop up)

  • KeyboardDisplay class (bottom keyboard which you can click with your mouse and shows which letters have been used)

  • MultiWordle class: wow factor to customize the length of the word (3-7 letters) and how many guesses you get.
    Had to find and import dictionaries for possible words to be able to guess for each of the different word lengths as well as dictionaries of more common words that it
    would choose to be the word of the day
    
  • SettingsDisplay class: not only the popup window, but the entire functionality and integration with other components for hard mode, dark mode, and contrast mode. I did
    not do much with the reset button functionality, however.

  • ErrorMessageDisplay class: I added all of the popup messages, as well as all of the animations like shaking and letters flipping.

  • Additionally, the animations were tricky to get right and find out how to do on the web, but they turned out well. Also, I had to go back and optimize
    some code in checking and reading in words by changing some datatypes and algorithms that I figured out was causing the flipping to skip frames and look choppy.
    After doing that the animations ran smoothly.

  • Guess class: class to make and check guesses

  • Furthermore, I worked on code in nearly every class, especially the ones in the model like Word and Dictionary to restructure and rewrite most of it to work with the
    rest of the implementation, and worked a great deal in the WordleGUI class.

Additionally, I made some extra effort to make the GUI look perfect by finding the exact fonts, colors, and images, as well as styling/ placing everything with the JavaFX framework.
