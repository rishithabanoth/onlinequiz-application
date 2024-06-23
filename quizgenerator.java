import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

// Main class to run the Quiz Application.

public class quizgenerator {
    private static List<Quiz> quizzes = new ArrayList<>(); // Common List of quizzes

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Scanner class object to take input from user

        while (true){
            System.out.println("QUIZ GENERATOR");

//           Displays the menu options to the user.
            System.out.println("1. Create a new quiz.");
            System.out.println("2. Add questions to a quiz.");
            System.out.println("3. Take a quiz.");
            System.out.println("4. List all quizzes.");
            System.out.println("5. Exit.");
            System.out.println("Enter your choice: ");

           int choice = sc.nextInt();

           switch(choice){
               case 1:
                   createQuiz(sc);
                   break;
               case 2:
                   addQuestion(sc);
                   break;
               case 3:
                   takeQuiz(sc);
                   break;
               case 4:
                   listQuizzes();
                   break;
               case 5:
                   try{
                       exit();
                   }
                   catch (InterruptedException e){
                       throw new RuntimeException(e);
                   }
                   sc.close();
                   return;
               default:
                   System.out.println("Invalid choice! Try again....");
           }
        }
    }

//    Prompts the user to create a new quiz.
    private static void createQuiz(Scanner sc) {
        System.out.println("Enter quiz title: ");
        String title = sc.nextLine();
        quizzes.add(new Quiz(title));
        System.out.println("Quiz created successfully :)");
    }

//    Prompts the user to add a question to an existing quiz.
    private static void addQuestion(Scanner sc) {
        if(quizzes.isEmpty()){
            System.out.println("No quizzes are available to add questions :(");
            return;
        }
        listQuizzes();
        System.out.println("Enter quiz number: ");
        int quizIndex = sc.nextInt() - 1; // Adjusting for zero-based index
        sc.nextLine();

        if(quizIndex >= 0 && quizIndex < quizzes.size()){
            System.out.println("Enter question text: ");
            String questionText = sc.nextLine();

            System.out.println("Enter number of options: ");
            int numOptions = sc.nextInt();
            sc.nextLine();

            List<String> options = new ArrayList<>();
            int i;
            for(i = 0; i < numOptions; i++){
                System.out.println("Enter option " + (i + 1) + ": ");
                options.add(sc.nextLine());
            }
            System.out.println("Enter correct answer number: ");
            int correctAnswerIndex = sc.nextInt() - 1; // Adjusting for zero-based index

            quizzes.get(quizIndex).addQuestion(new Question(questionText, options, correctAnswerIndex));
            System.out.println("Question added successfully :)");
        }
        else {
            System.out.println("Invalid quiz number!");
        }
    }

//    Prompts the user to take a quiz.
    private static void takeQuiz(Scanner sc) {
        if(quizzes.isEmpty()){
            System.out.println("No quizzes are available to take quiz :(");
            return;
        }
        listQuizzes();
        System.out.println("Enter quiz number: ");
        int quizIndex = sc.nextInt() - 1; // Adjusting for zero-based index
        sc.nextLine();

        if(quizIndex >= 0 && quizIndex < quizzes.size()){
            quizzes.get(quizIndex).takeQuiz(sc);
        }
        else {
            System.out.println("Invalid quiz number!");
        }
    }

//    Lists all the quizzes available.
    private static void listQuizzes() {
        if(quizzes.isEmpty()){
            System.out.println("No quizzes available :)");
        }
        else {
            int i;
            for(i = 0; i < quizzes.size(); i++){
                System.out.println((i + 1) + ". " + quizzes.get(i).getTitle());
            }
        }
    }

    private static void exit() throws InterruptedException{
        System.out.print("Exiting");
        int i = 5;
        while (i!=0){
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thanks for using Quiz Generator System :)");
    }

//    Class representing a Question in a quiz.

    static class Question{
        private String questionText;
        private List<String> options;
        private int correctAnswerIndex;

        /*
        Constructor to create a new Question.
         # questionText -> the text of the question
         # options -> the list of multiple-choice options
         # correctAnswerIndex -> the index of the correct answer
         */
        public Question(String questionText, List<String> options, int correctAnswerIndex){
            this.questionText = questionText;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }
        public String getQuestionText(){
            return questionText;
        }
        public List<String> getOptions(){
            return options;
        }
        public int getCorrectAnswerIndex(){
            return correctAnswerIndex;
        }

        /*
         # Checks if the provided answer index is correct.
         # answerIndex -> the index of the provided answer
         # return true if the answer is correct, false otherwise
         */
        public boolean isCorrect(int answerIndex){
            return answerIndex == correctAnswerIndex;
        }

        /*
         # Returns a string representation of the question and its options.
         # return the string representation of the question
         */
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(questionText).append("\n");
            int i;
            for(i = 0; i < options.size(); i++){
                sb.append(i + 1).append(". ").append(options.get(i)).append("\n");
            }
            return sb.toString();
        }
    }

//    Class representing a Quiz, containing multiple questions.
    static class Quiz{
        private String title;
        private List<Question> questions;

    /*
     # Constructor to create a new Quiz with a title.
     # title -> the title of the quiz
     */

        public Quiz(String title){
            this.title = title;
            this.questions = new ArrayList<>();
        }

        public String getTitle(){
            return title;
        }
        public void addQuestion(Question question){
            questions.add(question);
        }

        public List<Question> getQuestions(){
            return questions;
        }

//        Allows the user to take the quiz, answering questions and displaying the score.
        public void takeQuiz(Scanner sc){
            int score = 0;
            for(Question question : questions){
                System.out.println(question);
                System.out.println("Your answer: ");
                int answer = sc.nextInt();
                if(question.isCorrect(answer - 1)){ // Adjusting for zero-based index
                    score++;
                }
            }
            System.out.println("------------------------------------");
            System.out.println("Quiz completed :) ");
            System.out.println("Your score: " + score + "/" + questions.size());
        }
    }
}