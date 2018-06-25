package ru.spbau.maxim

import io.grpc.ManagedChannelBuilder
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import com.google.protobuf.Timestamp
import java.util.concurrent.TimeUnit
import java.time.Instant
import java.util.logging.Logger

/**
 * Messenger for peer to peer connection between two users
 */
class Messenger(val userName: String, port: Int, peerAddress: PeerAddress, val onMessage: (Protocol.Message) -> Unit) {
    private val logger = Logger.getLogger(Messenger::class.java.name)
    private val server = ServerBuilder.forPort(port)
            .addService(MessengerService())
            .build()
            .start()

    private val channel = ManagedChannelBuilder.forAddress(peerAddress.url, peerAddress.port).usePlaintext().build()
    private val stub = MessengerServiceGrpc.newBlockingStub(channel)

    init {
        Runtime.getRuntime().addShutdownHook(Thread { server.shutdown() })
        logger.info("Messenger is initialized")
    }

    fun sendMessage(text: String): Boolean {
        val time = Instant.now()
        val timestamp = Timestamp.newBuilder().setSeconds(time.epochSecond).build()

        val message = Protocol.Message.newBuilder().setUserName(userName).setText(text).setDate(timestamp).build()
        try {
            stub.sendMessage(message)
            logger.info("message sent")
        } catch (e: Exception) {
            logger.warning("error during sending message")
            return false
        }
        return true
    }

    fun shutdown() {
        try {
            logger.info("shutting down messenger")
            server.shutdown().awaitTermination(5, TimeUnit.SECONDS)
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: Exception) {
            logger.warning("error during shutdown: ${e.message}")
        }
    }

    data class PeerAddress(val url: String, val port: Int)

    private inner class MessengerService : MessengerServiceGrpc.MessengerServiceImplBase() {
        override fun sendMessage(request: Protocol.Message, responseObserver: StreamObserver<Protocol.EmptyResult>) {
            logger.entering(MessengerService::class.java.simpleName, "received message")

            onMessage(request)
            responseObserver.onNext(Protocol.EmptyResult.newBuilder().build())
            responseObserver.onCompleted()

            logger.exiting(MessengerService::class.java.simpleName, "received message")
        }
    }
}