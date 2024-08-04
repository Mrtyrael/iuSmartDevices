package com.example.weatherwear


import DataStoreManager
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey



@Composable
fun widgetElevatedCardWear(dataStoreManager: DataStoreManager) {

    var textShort1 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shortTempMin)).collect { value ->
            textShort1 = value ?: ""
        }
    }

    var textShort2 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shortTempMax)).collect { value ->
            textShort2 = value ?: ""
        }
    }

    var textTrouser1 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.trouserTempMin)).collect { value ->
            textTrouser1  = value ?: ""
        }
    }

    var textTrouser2 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.trouserTempMax)).collect { value ->
            textTrouser2  = value ?: ""
        }
    }

    var textShirt1 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shirtTempMin)).collect { value ->
            textShirt1  = value ?: ""
        }
    }

    var textShirt2 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shirtTempMax)).collect { value ->
            textShirt2  = value ?: ""
        }
    }


    var textPullover1 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.PulloverTempMin)).collect { value ->
            textPullover1  = value ?: ""
        }
    }
    var textPullover2 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.PulloverTempMax)).collect { value ->
            textPullover2  = value ?: ""
        }
    }

    var textJacket1 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.JacketTempMin)).collect { value ->
            textJacket1  = value ?: ""
        }
    }
    var textJacket2 by remember { mutableStateOf("") }

    // Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.JacketTempMax)).collect { value ->
            textJacket2 = value ?: ""
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

        RowHeadline(headlineText = "Kleidungstemperaturen")
        rowIconSlider(start = textShort1, end = textShort2, iconPainter =  R.drawable.shorttrouser, iconSize = 72.dp, onValueChanged = { value ->
            textShort1 = value.start.toInt().toString()
            textShort2 = value.endInclusive.toInt().toString()})
        rowIconSlider(start = textTrouser1, end = textTrouser2, iconPainter =  R.drawable.trousers, iconSize = 72.dp, onValueChanged = { value ->
            textTrouser1 = value.start.toInt().toString()
            textTrouser2 = value.endInclusive.toInt().toString()})
        rowIconSlider(start = textShirt1, end =  textShirt2, iconPainter =  R.drawable.shirt, iconSize = 72.dp, onValueChanged = { value ->
            textShirt1 = value.start.toInt().toString()
            textShirt2 = value.endInclusive.toInt().toString()})
        rowIconSlider(start = textPullover1, end = textPullover2, iconPainter =  R.drawable.pullover, iconSize = 72.dp, onValueChanged = { value ->
            textPullover1 = value.start.toInt().toString()
            textPullover2 = value.endInclusive.toInt().toString()})
        rowIconSlider(start = textJacket1, end = textJacket2, iconPainter =  R.drawable.jacket, iconSize = 72.dp, onValueChanged = { value ->
            textJacket1 = value.start.toInt().toString()
            textJacket2 = value.endInclusive.toInt().toString()})
     /*   DoubleRowOutlineTextfield(
            text1 = textShort1,
            text2 = textShort2,
            label1 = "Shorts min",
            label2 = "Shorts max",
            iconPainter = R.drawable.shorttrouser,
            onTextChanged1 = {newText -> textShort1 = newText},
            onTextChanged2 = {newText -> textShort2 = newText},
            iconSize = 72.dp
        )
        DoubleRowOutlineTextfield(
            text1 = textTrouser1,
            text2 = textTrouser2,
            label1 = "min",
            label2 = "max",
            iconPainter = R.drawable.trousers,
            onTextChanged1 = {newText -> textTrouser1 = newText},
            onTextChanged2 = {newText -> textTrouser2 = newText},
            iconSize = 72.dp
        )
        DoubleRowOutlineTextfield(
            text1 = textshirt1,
            text2 = textshirt2,
            label1 = "min",
            label2 = "max",
            iconPainter = R.drawable.shirt,
            onTextChanged1 = {newText -> textshirt1 = newText},
            onTextChanged2 = {newText -> textshirt2 = newText},
            iconSize = 72.dp
        )
        DoubleRowOutlineTextfield(
            text1 = textPullover1,
            text2 = textPullover2,
            label1 = "min",
            label2 = "max",
            iconPainter = R.drawable.pullover,
            onTextChanged1 = {newText -> textPullover1 = newText},
            onTextChanged2 = {newText -> textPullover2 = newText},
            iconSize = 72.dp
        )
        DoubleRowOutlineTextfield(
            text1 = textJacket1,
            text2 = textJacket2,
            label1 = "Jacke min",
            label2 = "Jacke max",
            iconPainter = R.drawable.jacket,
            onTextChanged1 = {newText -> textJacket1 = newText},
            onTextChanged2 = {newText -> textJacket2 = newText},
            iconSize = 72.dp
        ) */

        RowElevatedButton(onClick = { saveAllClothes(dataStoreManager, textShort1, textShort2, textTrouser1, textTrouser2, textShirt1, textShirt2, textPullover1, textPullover2, textJacket1, textJacket2 ) }, text = "Save")
        //RowOutlineTextfield(text = textMQTTBrokerServerUri.toString() , label = "MQTT Broker", onTextChanged = { newText -> textMQTTBrokerServerUri = newText})


  //      RowElevatedButton(  onClick = {dataStoreManager.saveToDataStore(DataStoreKeys.mQTTBrokerServerUri, textMQTTBrokerServerUri.toString())}, "Save")
    }

}

fun saveAllClothes(dataStoreManager: DataStoreManager, textShort1 : String, textShort2 : String, textTrouser1 : String, textTrouser2 : String,
                   textShirt1 : String, textShirt2 : String, textPullover1 : String, textPullover2 : String, textJacket1 : String, textJacket2 : String)
{
    dataStoreManager.saveToDataStore(DataStoreKeys.shortTempMin, textShort1.toString())
    dataStoreManager.saveToDataStore(DataStoreKeys.shortTempMax, textShort2.toString())

    dataStoreManager.saveToDataStore(DataStoreKeys.trouserTempMin, textTrouser1.toString())
    dataStoreManager.saveToDataStore(DataStoreKeys.trouserTempMax, textTrouser2.toString())

    dataStoreManager.saveToDataStore(DataStoreKeys.shirtTempMin, textShirt1.toString())
    dataStoreManager.saveToDataStore(DataStoreKeys.shirtTempMax, textShirt2.toString())

    dataStoreManager.saveToDataStore(DataStoreKeys.PulloverTempMin, textPullover1.toString())
    dataStoreManager.saveToDataStore(DataStoreKeys.PulloverTempMax, textPullover2.toString())

    dataStoreManager.saveToDataStore(DataStoreKeys.JacketTempMin, textJacket1.toString())
    dataStoreManager.saveToDataStore(DataStoreKeys.JacketTempMax, textJacket2.toString())

}

