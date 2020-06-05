package com.example.trivia.model;

public class Question {
    private String answer;
    private boolean ansTrue;

    public Question() {
    }

    public Question(String answer, boolean ansTrue) {
        this.answer = answer;
        this.ansTrue = ansTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnsTrue() {
        return ansTrue;
    }

    public void setAnsTrue(boolean ansTrue) {
        this.ansTrue = ansTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", ansTrue=" + ansTrue +
                '}';
    }
}
