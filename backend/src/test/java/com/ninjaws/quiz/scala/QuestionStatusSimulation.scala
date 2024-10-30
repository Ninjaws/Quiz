package com.ninjaws.quiz.scala;
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class QuestionStatusSimulation extends Simulation {
    val httpProtocol = http.baseUrl("http://localhost:9090").acceptHeader("application/json")

    val requestQuestions = exec(http("Request Questions").get("/questions/bla").check(status.is(200)).check(jsonPath("$.id").saveAs("sessionId")))

    val checkStatus = exec(http("Check Status").get("/status/${sessionId}").check(status.is(200),jsonPath("$.status").notNull))

    val scn = scenario("Check Questions Status Completion").exec(requestQuestions).pause(1).asLongAsDuring(session => session("completed").asOption[Boolean].getOrElse(false) == false, 30.seconds) {
        exec(checkStatus).pause(1).exec{ session => val status = session("status").asOption[String] 
        session.set("completed", status.isDefined && status.get != "null")
        }
    }
    setUp(
        scn.inject(atOnceUsers(10))
    ).protocols(httpProtocol);
}