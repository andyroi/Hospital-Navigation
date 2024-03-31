# Hospital-Navigation
## UI
* The Main UI will consists of three buttons which direct the user to new scenes and they're for the "Patient Overview", "Receptionist", and "Technician", and will be redirected toward different scenes through eclipse
* Receptionist scene called function "createPatientIntakeScene", will prompt the user text fields and each have labels. Will also save user information stored through text file, from a function called "savePatientInformatiom"
* Technician scene called function "TechScene". Similar to the Receiptionist one, but will have to enter into each text field, and will create a text file which will be labeled from the "patiendID" that the user enters
* Patient overview scene, with a function called "PatientScene", which will display the user Name and display all the information of the user.
## Navigation
* The user will be able to flow through the UI, by having to click through each button and will redirect the user to different scenes, then able to go back to the menu due to the "private Scene mainScene"
