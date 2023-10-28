# Code for Creating a Questionarie
Import the Library
``` java
import shuffleboardlib.Question;
import shuffleboardlib.Questionnaire;

public class DriverStation extends ShuffleboardTabBase{
    private Questionnaire questionnaire;

    public DriverStation(){
        //create a hashmap to store the options if the robot is in the left position
        HashMap<String, Question> leftPieceNumber = new HashMap<>();
        //add items to the hashmap, the first arguement is the answer, the next is a question
        //the question arguements are the question, a Hashmap<String, Question> for the answers, a boolean for if it is the last question on that path of the tree, and if it is the output for the questionaire
        leftPieceNumber.put("1", new Question("1 piece", null, true, "LeftOne"));
        leftPieceNumber.put("2", new Question("2 piece", null, true, "LeftTwo"));
        Question leftPieceNumberQuestion = new Question("How many pieces?", leftPieceNumber, false, null);

        //create a hashmap for the answers to the root question
        HashMap<String, Question> answersHashMap = new HashMap<>();
        answersHashMap.put("left", leftPieceNumberQuestion);
        answersHashMap.put("middle", new Question ("middle", null, true, "midCone"));

        Question rootQuestion = new Question("starting position", answersHashMap, false, null);

        questionnaire = new Questionnaire("Driver Station", rootQuestion, 5);

    }
}
```

