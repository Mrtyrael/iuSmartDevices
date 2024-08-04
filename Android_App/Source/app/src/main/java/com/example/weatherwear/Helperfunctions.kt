package com.example.weatherwear

import DataStoreManager
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

fun mqttInit(context : Context, dataStoreManager: DataStoreManager)
{

    //  mqttHelper = MqttHelper(this)
    val mqttHelper = MqttHelper(context, dataStoreManager)  // Initialisiere mqttHelper direkt hier


    mqttHelper.subscribe(DataStoreKeys.temperature)
    mqttHelper.subscribe(DataStoreKeys.airPressure)
    mqttHelper.subscribe(DataStoreKeys.mQTTTimestamp)
    mqttHelper.subscribe(DataStoreKeys.humidity)
    var MQTTSetList =
        listOf(
            DataStoreKeys.updateIntervall,
            "${GlobalSettings.beginTopicMQTTSet}/AirPressure2")
    mqttHelper.observeDataStoreChanges( MQTTSetList , dataStoreManager) // Liste der Topics)


    // mqttHelper.publishMessage("iuweatherappDD/Temp","App hat was geschickt")
}

fun setDefaultValues (dataStoreManager: DataStoreManager, factoryReset : Boolean = false)
{
    var setDefault = runBlocking {  dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.defaultValues)).firstOrNull() ?: "" }
    Log.d ("Default", setDefault)

    if (setDefault == "" || factoryReset)
    {

        dataStoreManager.saveToDataStore(DataStoreKeys.defaultValues,"True")
        dataStoreManager.saveToDataStore(DataStoreKeys.updateIntervall, "15")
        dataStoreManager.saveToDataStore(DataStoreKeys.shortTempMin, "23")
        dataStoreManager.saveToDataStore(DataStoreKeys.shortTempMax, "40")
        dataStoreManager.saveToDataStore(DataStoreKeys.trouserTempMin, "-20")
        dataStoreManager.saveToDataStore(DataStoreKeys.trouserTempMax, "22")
        dataStoreManager.saveToDataStore(DataStoreKeys.shirtTempMin, "20")
        dataStoreManager.saveToDataStore(DataStoreKeys.shirtTempMax, "40")
        dataStoreManager.saveToDataStore(DataStoreKeys.PulloverTempMin, "-20")
        dataStoreManager.saveToDataStore(DataStoreKeys.PulloverTempMax, "19")
        dataStoreManager.saveToDataStore(DataStoreKeys.JacketTempMin, "-20")
        dataStoreManager.saveToDataStore(DataStoreKeys.JacketTempMax, "15")
        dataStoreManager.saveToDataStore(DataStoreKeys.mQTTBrokerServerUri, "mqtt-dashboard.com:1883")

    }
}
