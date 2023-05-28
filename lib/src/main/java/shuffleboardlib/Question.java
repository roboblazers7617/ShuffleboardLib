package shuffleboardlib;

import java.util.HashMap;


public class Question {
    private final String question;
    private final HashMap<String, Question> answersHashMap;
    // private final SendableChooser<Question> answers;
    // private static ArrayList<Question> questions = new ArrayList<Question>();

    public Question(String question, HashMap<String, Question> answersHashMap){
        this.question = question;
        this.answersHashMap = answersHashMap;
        
        // questions.add(this);
    }

    public String getQuestion(){
        return question;
    }

    public HashMap<String, Question> getAnswersHashMap(){
        return answersHashMap;
    }

    

    



}
