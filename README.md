# ShuffleboardLib
Library for creating Questionaires in Shuffleboard. 

## Adding to Build System
Instructions from [CelestialWren](https://github.com/CelestialWren/LEDLibrary)

Credit for this system of adding libraries without using WPIlib jsons goes to Dannie on team 4272, Maverick Boiler Robotics.
Their code is great! You can find it here: https://github.com/maverick-boiler-robotics-team-4272

1. Create a folder called libs in the root directory of the project
2. Add built library to the libs folder
3. Go into build.gradle and add 
```groovy
repositories {
  flatDir {
    dirs "./libs" 
  }
}
```
4. Find dependencies block and add ```implementation name:'[insert name of JAR here, omit the .jar]'``` after the ```implementation wpi.java.vendor.java()``` line

5. Add ```!libs/*.jar``` to your .gitignore to allow the file to be added to your repo. (or analagous action for other version tracking software)
