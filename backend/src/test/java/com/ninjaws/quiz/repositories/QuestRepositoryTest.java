package com.ninjaws.quiz.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ninjaws.quiz.models.Question;

@DataJpaTest
public class QuestRepositoryTest {
       @Autowired
    private QuestionRepository questionRepository;

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setType("multiple choice");
        question.setDifficulty("easy");
        question.setCategory("general knowledge");
        question.setQuestion("What is the capital of France?");
        question.setCorrectAnswer("Paris");
        question.setIncorrectAnswers(Arrays.asList("Berlin", "Madrid", "Rome"));
    }

    @Test
    @Rollback(false)
    public void testSaveQuestion() {
        Question savedQuestion = questionRepository.save(question);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.getId()).isGreaterThan(1);
    }

    @Test
    public void testFindQuestionById() {
        questionRepository.save(question);
        Optional<Question> foundQuestion = questionRepository.findById(question.getId());
        assertThat(foundQuestion).isPresent();
        assertThat(foundQuestion.get().getQuestion()).isEqualTo(question.getQuestion());
    }

    @Test
    public void testFindAllQuestions() {
        questionRepository.save(question);
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).isNotEmpty();
    }

    @Test
    public void testDeleteQuestion() {
        questionRepository.save(question);
        Long id = question.getId();
        questionRepository.deleteById(id);
        Optional<Question> deletedQuestion = questionRepository.findById(id);
        assertThat(deletedQuestion).isNotPresent();
    }

    @Test
    @Rollback(false)
    public void testSaveQuestions() {
        Question question1 = new Question();
        question1.setType("multiple choice");
        question1.setDifficulty("easy");
        question1.setCategory("general knowledge");
        question1.setQuestion("What is the capital of France?");
        question1.setCorrectAnswer("Paris");
        question1.setIncorrectAnswers(Arrays.asList("Berlin", "Madrid", "Rome"));

        Question question2 = new Question();
        question2.setType("multiple choice");
        question2.setDifficulty("medium");
        question2.setCategory("science");
        question2.setQuestion("What is the chemical symbol for water?");
        question2.setCorrectAnswer("H2O");
        question2.setIncorrectAnswers(Arrays.asList("CO2", "O2", "NaCl"));

        List<Question> savedQuestions = questionRepository.saveAll(Arrays.asList(question1, question2));

        assertThat(savedQuestions).hasSize(2);
        assertThat(savedQuestions.get(0).getQuestion()).isEqualTo("What is the capital of France?");
        assertThat(savedQuestions.get(1).getQuestion()).isEqualTo("What is the chemical symbol for water?");
    }
}
