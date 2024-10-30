package com.ninjaws.quiz.services;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// @EnableScheduling
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuizServiceTest {
    
    // @InjectMocks
    // private QuizService quizService;

    // @Mock
    // private ApiService apiService;

    // @BeforeAll
    // public void setUp() {
    //     MockitoAnnotations.openMocks(this);
    //     // Cache<String, Session> cache = Caffeine.newBuilder()
    //     //     .expireAfterWrite(1, TimeUnit.HOURS)
    //     //     .build(); // Create a mock cache or a real cache for tests
    //     // quizService = new QuizService(cache); 
    // }

    // @Test
    // public void testCreateSession() {
    //     QuizSettings settings = new QuizSettings();
    //     String response = quizService.createSession(settings);
    //     assertTrue(response instanceof String);
    // }

    // /**
    //  * TODO: Fix the test
    //  * Seems to clear the cache and queue when the createSession is finished 
    //  */
    // @Test
    // public void testCheckSession() throws InterruptedException {
        
    //     QuizSettings settings = new QuizSettings();
    //     when(apiService.requestQuestions(settings)).thenReturn("{\"response_code\":0,\"results\":[{\"type\":\"multiple\",\"difficulty\":\"hard\",\"category\":\"Entertainment: Video Games\",\"question\":\"The map featured in Arma 3 named &quot;Altis&quot; is based off of what Greek island?\",\"correct_answer\":\"Lemnos\",\"incorrect_answers\":[\"Ithaca\",\"Naxos\",\"Anafi\"]},{\"type\":\"multiple\",\"difficulty\":\"medium\",\"category\":\"Entertainment: Music\",\"question\":\"What is the last song on the first Panic! At the Disco album?\",\"correct_answer\":\"Build God, Then We&#039;ll Talk\",\"incorrect_answers\":[\"Nails for Breakfast, Tacks for Snacks\",\"I Write Sins Not Tragedies\",\"Lying Is The Most Fun A Girl Can Have Without Taking Her Clothes Off\"]},{\"type\":\"multiple\",\"difficulty\":\"easy\",\"category\":\"Entertainment: Video Games\",\"question\":\"Which of these is NOT a playable character in the 2016 video game Overwatch?\",\"correct_answer\":\"Invoker\",\"incorrect_answers\":[\"Mercy\",\"Winston\",\"Zenyatta\"]},{\"type\":\"multiple\",\"difficulty\":\"medium\",\"category\":\"Science: Computers\",\"question\":\"What does AD stand for in relation to Windows Operating Systems? \",\"correct_answer\":\"Active Directory\",\"incorrect_answers\":[\"Alternative Drive\",\"Automated Database\",\"Active Department\"]},{\"type\":\"multiple\",\"difficulty\":\"medium\",\"category\":\"Entertainment: Japanese Anime &amp; Manga\",\"question\":\"In &quot;Highschool DxD&quot;, Koneko Toujou is from what race?\",\"correct_answer\":\"Nekomata\",\"incorrect_answers\":[\"Kitsune\",\"Human\",\"Kappa\"]}]}");
    //     String sessionId = quizService.createSession(settings);


    //     int interval = 1000;
    //     int maxTime = 5000;
    //     long startTime = System.currentTimeMillis();
    //     Session session = null;

    //     while((System.currentTimeMillis() - maxTime) < startTime) {
    //         Optional<Session> retrieved = quizService.checkSession(sessionId);
    //         System.out.println(retrieved);
    //         if (retrieved.isPresent()) {
    //             System.out.print("Present!");
    //             session = retrieved.get();
    //             break;
    //         } else {
    //             try {
    //                 Thread.sleep(interval);
    //             } catch (InterruptedException e) {
    //                 Thread.currentThread().interrupt(); // Restore interrupted status
    //                 fail("Thread was interrupted during sleep");
    //             }
    //         }
    //     }

    //     assertNotEquals(session, null);
    // }

    // public void testCalculateScore(){

    // }
}
