package shuffleboardlib;

import java.util.HashMap;


public class Question {
    private final String question;
    private final HashMap<String, Question> answersHashMap;
    private final boolean isFinal;
    private final String output;
    // private final SendableChooser<Question> answers;
    // private static ArrayList<Question> questions = new ArrayList<Question>();

    /** 
     * Create a question
     * @param question a string for the question
     * @param answersHashMap a hashmap of all of the answers
     * @param isFinal is this the final question in the tree (might not actually do anything)
     * @param output the string output, should always be used with isFinal
     */
    public Question(String question, HashMap<String, Question> answersHashMap, boolean isFinal, String output){
        this.question = question;
        this.answersHashMap = answersHashMap;
        this.isFinal = isFinal;
        this.output = output;

        
        // questions.add(this);
    }

    public String getQuestion(){
        return question;
    }

    public HashMap<String, Question> getAnswersHashMap(){
        return answersHashMap;
    }

    public boolean isFinal(){
        return isFinal;
    }

    public String getOutput(){
        return output;
    }

}
