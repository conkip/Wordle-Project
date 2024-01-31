# Wordle-Project
Wordle in java based off of the New York Times game. Made as a group project in CSC 335 Object-Oriented Programing.
Fair warning, currently the program has an error with JavaFX because I tried to transfer over the files, but the code ran perfectly before and I am working on a fix. You are still able to see the code and techniques used.

I had to use Git (in a different repository, I just have this here so I can show it) and Scrum to communicate with three other team members.
There were several classes I worked with all interacting with each other.
The project gave me very good experience with coding in a team, applying several object-oriented design patterns like model-view-controller design, testing code with JUnit, and making graphics with JavaFX.

I also got good experience working on much of the design, backend, and core components of the front end.

This is by far my most impressive program and the one I have spent the most time on. It may look simple, just being a wordle game, but there is so much more to it.

I worked on many of the original ideas for the design and structure, as well as making the class and sequence diagrams.

This was a team project, but I took a lot of the lead in SCRUM and I put in a lot of extra hours/ implemented a lot more features on this project because I wanted to see my vision come to fruition (making it look as close as possible to the real New York Times Wordle) and I enjoyed doing it. My group was great and did their parts well, however I would say I had a lead role and did most of the work for this, and put in some extra hours, hence why I wanted to show it off on my git repository.

I was primarily responsible for:
  • Letter class (the basis for how the whole thing worked, the letters make up the keyboard and display where the words pop up on)

  • KeyboardDisplay class (bottom keyboard where it 

  • MultiWordle class: x factor to make the letter of word and how many guesses you get customizable
    Had to find and import dictionaries for possible words to be able to guess for each of the different word lengths as well as dictionaries of more common words that it
    would choose to be the word of the day)
    
  • SettingsDisplay class: not only the popup window, but the entire functionality and integration with other components for hard mode, dark mode, and contrast mode. I did
    not do much with the reset button functionality, however.

  • ErrorMessageDisplay class: I added all of the popup messages, as well as all of the animations like shaking and letters flipping.

  • Additionally, the animations were tricky to get right and find out how to do on the web, but I am very proud of how they turned out. Also, I had to go back and optimize
    some code in checking and reading in words by changing some datatypes and algorithms that I figured out was causing the flipping to skip frames and look choppy. So,
    after doing that the animations ran smoothly.

  • Guess class: class to make and check guesses

  • Furthermore, I worked on code in nearly every class, especially the ones in the model like Word and Dictionary to restructure and rewrite most of it to work with the
    rest of the implementation, and worked a great deal in the WordleGUI class.

I purposely tried to give myself some of the trickier and more core classes so I could learn more and write it in the way I envisioned, and it worked out well and my partners were great helps.

Much of the effort was to make the GUI look perfect by finding the exact fonts, colors, and images, as well as styling/ placing everything with the JavaFX framework.

I did a lottttt of googling, but I learned a lot and also got a lot of experience using git, scrum, and working on a team.
