package ru.spbau.maxim

import io.grpc.ManagedChannelBuilder
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import com.google.protobuf.Timestamp
import java.util.concurrent.TimeUnit
import java.time.Instant



class Messenger(val userName: String, port: Int, peerAddress: PeerAddress, val onMessage: (Protocol.Message) -> Unit) {
    data class PeerAddress(val url: String, val port: Int)

    private val server = ServerBuilder.forPort(port)
            .addService(MessengerService())
            .build()
            .start()

    private val channel = ManagedChannelBuilder.forAddress(peerAddress.url, peerAddress.port).usePlaintext().build()
    private val stub = MessengerServiceGrpc.newBlockingStub(channel)

    fun sendMessage(text: String) {
        val time = Instant.now()
        val timestamp = Timestamp.newBuilder().setSeconds(time.epochSecond).build()

        val message = Protocol.Message.newBuilder().setUserName(userName).setText(text).setDate(timestamp).build()
        try {
            stub.sendMessage(message)
        } catch (e: Exception) {
            //TODO log
        }
    }

    private inner class MessengerService : MessengerServiceGrpc.MessengerServiceImplBase() {
        override fun sendMessage(request: Protocol.Message, responseObserver: StreamObserver<Protocol.EmptyResult>) {
            //TODO log

            onMessage(request)
            responseObserver.onNext(Protocol.EmptyResult.newBuilder().build())
            responseObserver.onCompleted()

            //TODO log
        }
    }
}