package com.example.weatherwear


import DataStoreManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.datastore.preferences.core.stringPreferencesKey







@Composable
fun widgetElevatedCardNetworkSettings(dataStoreManager: DataStoreManager) {

    var textMQTTBrokerServerUri by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.mQTTBrokerServerUri)).collect { value ->
            textMQTTBrokerServerUri = value ?: ""
        }
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = GlobalSettings.evaluationvalue
        ),
        modifier = Modifier
            .padding(
                horizontal = GlobalSettings.evaluationpaddinghorizontal,
                vertical = GlobalSettings.evaluationpaddingvertical
            )
            .fillMaxWidth() // Fills the width of the parent
            .wrapContentHeight() // Adjusts height to content
    )
    {

        RowHeadline(headlineText = "MQTT")
        RowOutlineTextfield(text = textMQTTBrokerServerUri.toString() , label = "MQTT Broker", onTextChanged = { newText -> textMQTTBrokerServerUri = newText})
        RowElevatedButton(  onClick = {dataStoreManager.saveToDataStore(DataStoreKeys.mQTTBrokerServerUri, textMQTTBrokerServerUri.toString())}, "Save")
    }

}

@Composable
fun widgetElevatedCardNWeatherStation(dataStoreManager: DataStoreManager) {


    var textUpdateIntervall by remember { mutableStateOf("") }


    // Effekt, um den Wert aus dem DataStore zu lesen und Updateintervall zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.updateIntervall))
            .collect { value ->
                textUpdateIntervall = value ?: ""
                Log.d("Screen3", "Update changed")
            }
    }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = GlobalSettings.evaluationvalue
        ),
        modifier = Modifier
            .padding(
                horizontal = GlobalSettings.evaluationpaddinghorizontal,
                vertical = GlobalSettings.evaluationpaddingvertical
            )
            .fillMaxWidth() // Fills the width of the parent
            .wrapContentHeight() // Adjusts height to content
    )
    {


        RowHeadline(headlineText = "Wetterstation")
        RowOutlineTextfield(text = textUpdateIntervall, label = "Updateintervall Wetterstation", onTextChanged = { newText -> textUpdateIntervall = newText }, onlyNumbers = true)
        RowElevatedButton(  onClick = {dataStoreManager.saveToDataStore(DataStoreKeys.updateIntervall, textUpdateIntervall)} , "Save")
    }

}

@Composable
fun widgetElevatedCardFactoryReset(dataStoreManager: DataStoreManager) {




    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = GlobalSettings.evaluationvalue
        ),
        modifier = Modifier
            .padding(
                horizontal = GlobalSettings.evaluationpaddinghorizontal,
                vertical = GlobalSettings.evaluationpaddingvertical
            )
            .fillMaxWidth() // Fills the width of the parent
            .wrapContentHeight() // Adjusts height to content
    )
    {
        RowElevatedButton(  onClick = { setDefaultValues(dataStoreManager,true) } , "Werkeinstellungen")
    }

}