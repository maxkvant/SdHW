package ru.spbau.maxim

import io.grpc.ServerBuilder
import java.lang.Thread.sleep
import java.sql.Date
import java.text.SimpleDateFormat



fun main(args: Array<String>) {
    if(args.size != 4) {
        val help =  """|Usage:
                       |user_name port peer_url peer_port
                       |""".trimMargin()
        print(help)
    }

    val userName = args[0]
    val port = args[1].toInt()
    val peerUrl = args[2]
    val peerPort = args[3].toInt()
    val peerAddress = Messenger.PeerAddress(peerUrl, peerPort)

    val onMessage = { message: Protocol.Message ->
        val date = Date(message.date.seconds * 1000)
        val dateformat = SimpleDateFormat("HH:mm:ss MMM dd, yyy")
        println("from: ${message.userName}, date: ${dateformat.format(date)}")
        println("\"\"\"")
        println(message.text)
        println("\"\"\"")
        println()
    }

    val messenger = Messenger(userName, port, peerAddress, onMessage)
    while (true) {
        val line: String? = readLine()
        if (line == null || line == "exit") {
            break
        }
        val messageSent = messenger.sendMessage(line)
        if (!messageSent) {
            println("Couldn't send message")
        }
    }

    messenger.shutdown()
}