package org.apache.james.gatling

import org.apache.james.gatling.jmap.scenari.JmapBigSetScenario

import scala.concurrent.duration._


class JmapBigSetIT extends JmapIT {
  private val MAILBOXES_NUMBER = 2
  private val MESSAGE_NUMBER = 10

  before {
    users.foreach(server.sendMessage(Fixture.homer.username))
  }

  scenario((userFeederBuilder, recipientFeederBuilder) => {
    new JmapBigSetScenario().generate(10 seconds, MAILBOXES_NUMBER, MESSAGE_NUMBER, userFeederBuilder, recipientFeederBuilder)
  })
}
