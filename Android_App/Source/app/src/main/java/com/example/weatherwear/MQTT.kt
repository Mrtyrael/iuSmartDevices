package com.example.weatherwear

import DataStoreManager
import android.content.Context
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


class MqttHelper(context: Context , dataStoreManager: DataStoreManager) {

    //private val serverUri : String
    private var serverUri = "tcp://mqtt-dashboard.com:1883"

    private val clientId = MqttClient.generateClientId()
    private var mqttClient: MqttClient


    init {

        var serverUri = runBlocking {  dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.mQTTBrokerServerUri)).firstOrNull() ?: "" }
        if (serverUri == "")
        {
            serverUri = "localhost:1883"
        }
        Log.d("MQTTBroker", serverUri.toString())
        Log.d("MQTT","Connecting")
        mqttClient = MqttClient("tcp://"+serverUri.toString(), clientId, MemoryPersistence())
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                // Code für Verbindungsverlust
                Log.d("MQTT","Connection lost")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                // Hier die empfangenen Nachrichten behandeln
                message?.let {
                    handleMessage(it.toString(), topic, dataStoreManager)
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // Code für abgeschlossene Zustellung
            }
        })
        connect(dataStoreManager)
        reconnectAfterMQTTBrokerChange(dataStoreManager)
    }

    private fun connect(dataStoreManager: DataStoreManager) {
        val options = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = false
            connectionTimeout = 5 // Hier den Timeout-Wert in Sekunden einstellen
        }

        try {
            mqttClient.connect(options)
            Log.d("MQTT","Connection established")
            dataStoreManager.saveToDataStore(DataStoreKeys.mQTTStatus, "Verbunden")
        //    mqttClient.subscribe(topic)
        } catch (e: Exception) {
            Log.d("MQTT","Connection Error")
            dataStoreManager.saveToDataStore(DataStoreKeys.mQTTStatus, "Nicht verbunden")
            e.printStackTrace()
        }
    }

    fun disconnect(dataStoreManager: DataStoreManager) {
        try {
            mqttClient.disconnect()
            Log.d("MQTT", "Disconnected from broker")
            dataStoreManager.saveToDataStore(DataStoreKeys.mQTTStatus, "Nicht verbunden")
        } catch (e: Exception) {
            Log.d("MQTT", "Disconnect failed")
            e.printStackTrace()
        }
    }


    fun subscribe (topic: String)
    {
        try {
            mqttClient.subscribe(topic)
        }
        catch (e: Exception)
        {

        }
    }

    private fun handleMessage(message: String, topic: String?, dataStoreManager: DataStoreManager) {
        // Hier die Logik für die Nachrichtenverarbeitung implementieren
        Log.d("MQTT","Topic: $topic + Message $message")
        dataStoreManager.saveToDataStore(topic.toString(),message)
    }

    fun publishMessage(topic: String, message: String, qos: Int = 1) {
        try {
            val mqttMessage = MqttMessage()
            mqttMessage.payload = message.toByteArray()
            mqttMessage.qos = qos
            mqttClient.publish(topic, mqttMessage)
            Log.d("MQTT","Send erfolgreich: $topic $message")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("MQTT","Send nicht erfolgreich: $topic $message")
        }
    }

    fun observeDataStoreChanges(topics: List<String>, dataStoreManager: DataStoreManager) {

        val initialValues = mutableMapOf<String, String?>() // Map für die initialen Werte der Topics

        // Initialwerte sammeln
        runBlocking {
            for (topic in topics) {
                val initialValue = dataStoreManager.readStringFromDataStore(stringPreferencesKey(topic)).firstOrNull()
                initialValues[topic] = initialValue
            }
        }

        // Jetzt die Änderungen überwachen und nur bei tatsächlichen Änderungen publishen
        topics.forEach { topic ->
            CoroutineScope(Dispatchers.IO).launch {
                dataStoreManager.readStringFromDataStore(stringPreferencesKey(topic)).collect { value ->
                    val initialValue = initialValues[topic]
                    if (initialValue != value) {
                        Log.d("MQTT", "Änderung erkannt: $topic $value")
                        publishMessage(topic, value ?: "")
                        initialValues[topic] = value // Initialwert aktualisieren
                    }
                }
            }
        }
    }

    fun reconnectAfterMQTTBrokerChange (dataStoreManager: DataStoreManager) {

        var key = DataStoreKeys.mQTTBrokerServerUri
        var initialValue = ""

        // Initialwerte sammeln
        runBlocking {
                initialValue = dataStoreManager.readStringFromDataStore(stringPreferencesKey(key)).firstOrNull().toString()

        }

        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.readStringFromDataStore(stringPreferencesKey(key)).collect { value ->
                if (initialValue != value) {
                    Log.d("MQTT", "Reconnect Neue IP")
                    disconnect(dataStoreManager)
                    serverUri = "tcp://$value"
                    mqttClient = MqttClient(serverUri, clientId, MemoryPersistence())
                    connect(dataStoreManager)
                    initialValue = value.toString()
                }
            }

        }

    }
}
