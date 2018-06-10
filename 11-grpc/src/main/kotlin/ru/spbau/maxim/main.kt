package ru.spbau.maxim

import io.grpc.ServerBuilder
import java.sql.Date
import java.text.SimpleDateFormat



fun main(args2: Array<String>) {
    val args = arrayOf("maxim", "12345", "localhost", "23456")

    require(args.size == 4) {
        """
        Usage:
        user_name port peer_url peer_port
        """
    }


    val userName = args[0]
    val port = args[1].toInt()

    val peerUrl = args[2]
    val peerPort = args[3].toInt()
    val peerAddress = Messenger.PeerAddress(peerUrl, peerPort)

    val messenger = Messenger(userName, port, peerAddress, { message ->
        println("from: ${message.userName}, date: ${message.date}")
        println("\"\"\"")
        println(message.text)
        println("\"\"\"")
        println()
    })

    val messenger2 = Messenger("messenger2", peerPort, Messenger.PeerAddress("localhost", port), { message ->
        val date = Date(message.date.seconds * 1000)
        val dateformat = SimpleDateFormat("HH:mm:ss MMM dd, yyy")
        println("from: ${message.userName}, date: ${dateformat.format(date)}")
        println(message.text)
        println()
    })
    messenger2.sendMessage("прив")

    while (true) {
        val line: String? = readLine()
        if (line == null) {
            break
        } else {
            messenger.sendMessage(line)
        }
    }
}