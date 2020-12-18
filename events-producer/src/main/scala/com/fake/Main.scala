package com.fake


import config.Settings

import com.fake.model.{mtAccepted}
import com.fake.utils.{getRandomNumberOfWords}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.slf4j.LoggerFactory

import java.util.{Properties, UUID}
import java.util.concurrent.{Executors, TimeUnit}


/**
  * @author ${roberto.trujillo}
  */
object Main extends App {

  private val log = LoggerFactory.getLogger(Main.getClass.getSimpleName())

  val producerConfiguration = Settings.Producer
  val random = new scala.util.Random()


  log.info("Welcome to our producer component!")
  log.debug(producerConfiguration.bootstrapServers)
  log.debug(producerConfiguration.keySerializer)
  log.debug(producerConfiguration.valueSerializer)
  log.debug(producerConfiguration.kafkaTopic)


  val kafkaProp = new Properties()
  kafkaProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerConfiguration.bootstrapServers)
  kafkaProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerConfiguration.keySerializer)
  kafkaProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerConfiguration.valueSerializer)
  kafkaProp.put(ProducerConfig.CLIENT_ID_CONFIG, producerConfiguration.groupName)
  kafkaProp.put(ProducerConfig.RETRIES_CONFIG, "3")
  kafkaProp.put(ProducerConfig.LINGER_MS_CONFIG, "5")
  kafkaProp.put(ProducerConfig.ACKS_CONFIG, "all")

  val kafkaProducer: KafkaProducer[String,String] = new KafkaProducer[String,String](kafkaProp)

  log.info("Started the object for kafka configuration")
  sendEvent()
  log.info("Application killed by user!")

  def sendEvent() = {
    val runnable = new Runnable() {
      def run() = {

        val message = new mtAccepted(
          UUID.randomUUID(),
          "MT",
          getRandomNumberOfWords(Range(0,10)),
          System.currentTimeMillis()

        )

        val mtAcceptedData = message.toJSON()

        val producerRecord = new ProducerRecord(producerConfiguration.kafkaTopic, "", mtAcceptedData)
        kafkaProducer.send(producerRecord)
        println(mtAcceptedData)
      }
    }
    val service = Executors.newSingleThreadScheduledExecutor
    service.scheduleAtFixedRate(runnable, 0, producerConfiguration.interval, TimeUnit.SECONDS)
  }
}
