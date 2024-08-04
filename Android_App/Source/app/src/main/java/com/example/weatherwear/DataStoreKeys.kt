package com.example.weatherwear

class DataStoreKeys
{

        companion object {
            val defaultValues = "defaultValues"
            val mQTTBrokerServerUri = "MQTTBrokerServerUri"
            val mQTTStatus = "MQTTStatus"
            val mQTTTimestamp = "${GlobalSettings.beginTopic}/Timestamp"
            val updateIntervall =  "${GlobalSettings.beginTopicMQTTSet}/UpdateIntervall"
            val temperature = "${GlobalSettings.beginTopic}/Temp"
            val airPressure = "${GlobalSettings.beginTopic}/AirPressure"
            val humidity = "${GlobalSettings.beginTopic}/Humidity"
            val shortTempMin = "ShortMin"
            val shortTempMax = "ShortMax"
            val trouserTempMin = "trouserMin"
            val trouserTempMax = "trouserMax"
            val shirtTempMin = "ShirtMin"
            val shirtTempMax = "ShirtMax"
            val PulloverTempMin = "PulloverMin"
            val PulloverTempMax = "PulloverMax"
            val JacketTempMin = "JacketMin"
            val JacketTempMax = "JacketMax"
        }
}