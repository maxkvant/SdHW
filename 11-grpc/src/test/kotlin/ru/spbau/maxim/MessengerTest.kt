package ru.spbau.maxim

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import java.lang.Thread.sleep
import org.hamcrest.core.IsNull

class MessengerTest {
    @Test
    fun testChat() {
        var messageReceived1: String? = null
        var messageReceived2: String? = null

        val address1 = Messenger.PeerAddress("localhost", 12345)
        val address2 = Messenger.PeerAddress("localhost", 54321)

        val messenger1 = Messenger("", address1.port, address2) { message ->
            messageReceived1 = message.text
        }
        val messenger2 = Messenger("", address2.port, address1) { message ->
            messageReceived2 = message.text
        }


        messenger1.sendMessage("добрый день!")
        messenger2.sendMessage("прив. чё дел?")

        sleep(100)

        assertThat(messageReceived1, `is`("прив. чё дел?"))
        assertThat(messageReceived2, `is`("добрый день!"))

        messenger1.shutdown()
        messenger2.shutdown()
    }

    @Test
    fun testReceiverShutdown() {
        var messageReceived1: String? = null
        var messageReceived2: String? = null

        val address1 = Messenger.PeerAddress("localhost", 12345)
        val address2 = Messenger.PeerAddress("localhost", 54321)

        val messenger1 = Messenger("", address1.port, address2) { message ->
            messageReceived1 = message.text
        }
        val messenger2 = Messenger("", address2.port, address1) { message ->
            messageReceived2 = message.text
        }

        messenger2.shutdown()
        sleep(100)

        assertThat(messenger1.sendMessage("привет"), `is`(false))
        sleep(100)

        assertThat(messageReceived1, `is`(IsNull.nullValue()))
        assertThat(messageReceived2, `is`(IsNull.nullValue()))

        messenger1.shutdown()
    }

    @Test
    fun testNoReceiver() {
        var messageReceived1: String? = null

        val address1 = Messenger.PeerAddress("localhost", 12345)
        val address2 = Messenger.PeerAddress("localhost", 54321)

        val messenger1 = Messenger("", address1.port, address2) { message ->
            messageReceived1 = message.text
        }

        assertThat(messenger1.sendMessage("привет"), `is`(false))
        sleep(100)

        assertThat(messageReceived1, `is`(IsNull.nullValue()))

        messenger1.shutdown()
    }
}