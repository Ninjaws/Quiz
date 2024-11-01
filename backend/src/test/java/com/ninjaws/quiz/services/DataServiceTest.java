package com.ninjaws.quiz.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ninjaws.quiz.models.Question;
import com.ninjaws.quiz.repositories.QuestionRepository;

public class DataServiceTest {
    
    @InjectMocks
    private DataService dataService;

    @Mock
    private QuestionRepository questionRepository;

    private Question question;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        question = new Question();
        question.setType("multiple choice");
        question.setDifficulty("easy");
        question.setCategory("general knowledge");
        question.setQuestion("What is the capital of France?");
        question.setCorrectAnswer("Paris");
        question.setIncorrectAnswers(Arrays.asList("Berlin", "Madrid", "Rome"));
    }

    @Test
    public void testSaveQuestion() {
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question savedQuestion = dataService.saveQuestion(question);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.getQuestion()).isEqualTo("What is the capital of France?");
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void testGetAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question));

        List<Question> questions = dataService.getAllQuestions();
        assertThat(questions).isNotEmpty();
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions.get(0).getQuestion()).isEqualTo("What is the capital of France?");
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void testFindQuestionById() {
        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.of(question));

        Optional<Question> foundQuestion = questionRepository.findById(1L);
        assertThat(foundQuestion).isPresent();
        assertThat(foundQuestion.get().getQuestion()).isEqualTo("What is the capital of France?");
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
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

        when(questionRepository.saveAll(anyList())).thenReturn(Arrays.asList(question1, question2));

        List<Question> questions = dataService.saveQuestions(Arrays.asList(question1, question2));

        assertThat(questions).hasSize(2);
        assertThat(questions.get(0).getQuestion()).isEqualTo("What is the capital of France?");
        assertThat(questions.get(1).getQuestion()).isEqualTo("What is the chemical symbol for water?");
        verify(questionRepository, times(1)).saveAll(anyList());
    }
}
