package org.apache.james.gatling.jmap.scenari

import io.gatling.core.Predef.{atOnceUsers, clock, exec, openInjectionProfileFactory, randomSwitch, scenario}
import io.gatling.core.scenario.Simulation
import org.apache.james.gatling.control.UserCreator
import org.apache.james.gatling.jmap.JmapMessages
import org.apache.james.gatling.jmap.scenari.common.Configuration._
import org.apache.james.gatling.jmap.scenari.common.{CommonSteps, HttpSettings}

import scala.concurrent.duration._

class JmapMessageUpdateScenario extends Simulation {

  val users = new UserCreator(BaseJamesWebAdministrationUrl).createUsersWithInboxAndOutbox(UserCount)

  val scn = scenario("JmapUpdateMessages")
    .exec(CommonSteps.provisionUsersWithMessageList(users))
    .during(ScenarioDuration) {
      randomSwitch(
        70.0 -> exec(JmapMessages.markAsRead()),
        20.0 -> exec(JmapMessages.markAsAnswered()),
        10.0 -> exec(JmapMessages.markAsFlagged())
      )
        .pause(1 second , 2 seconds)
    }

  setUp(scn.inject(atOnceUsers(UserCount))).protocols(HttpSettings.httpProtocol)
}
