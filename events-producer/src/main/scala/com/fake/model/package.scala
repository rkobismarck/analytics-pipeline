package com.fake

import java.util.UUID

package object model {


  case class mtAccepted(
                      messageId: UUID,
                      messageType: String,
                      content: String,
                      acceptTs: Long
                    ) {
    def toJSON(): String = {
      "{\"mtAccepted\": {" +
        "\"messageId\": \"" + messageId + "\"," +
        "\"messageType\": " + messageType + "," +
        "\"content\": " + content + "," +
        "\"acceptTs\": " + acceptTs +
        "}}"
    }
  }

}
