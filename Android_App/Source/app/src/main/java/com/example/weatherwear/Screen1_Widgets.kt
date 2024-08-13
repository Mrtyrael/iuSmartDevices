package com.example.weatherwear


import DataStoreManager
import android.provider.ContactsContract.Data
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import android.util.Log
import kotlin.math.round

@Composable
fun widgetElevatedCardTemperature(dataStoreManager: DataStoreManager) {

    val dataStoreTemperature by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.temperature)).collectAsState(initial = "Error")
    val dataStoreAirPressure by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.airPressure)).collectAsState(initial = "Error")
    val dataStoreHumidity by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.humidity)).collectAsState(initial = "Error")
    fun safeToInt(value: String): Int? {
        return value.toIntOrNull()
    }
    fun safeToFloat(value: String): Float?
    {
        return value.toFloatOrNull()
    }
    var airPressureIcon =  R.drawable.cloud
    Log.i("MQTTAirPressure",dataStoreAirPressure.toString())
    Log.i("MQTTTemp",dataStoreTemperature.toString())
    var airPressureFloat = safeToFloat(dataStoreAirPressure.toString().dropLast(3))
    val airPressure: Int? = airPressureFloat?.let {
        round(it).toInt()
    } //Runden auf ganze zahl
    val temp = safeToFloat(dataStoreTemperature.toString().dropLast(1))
    val humidity = safeToFloat(dataStoreHumidity.toString())

    if (airPressure != null) {

        if (airPressure < 1008) {
            airPressureIcon = R.drawable.rainy
        }
        else if (airPressure > 1018)
        {
            airPressureIcon = R.drawable.sunny
        }
        else {

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

        RowHeadline(headlineText = "Temperatur", rowPadding =  GlobalSettings.rowPadding)
        rowIconText(iconPainter = R.drawable.device_thermostat,contentValue = "Temperatur:\n$temp" + " °C")
        rowIconText(iconPainter = airPressureIcon,contentValue = "Luftdruck:\n$airPressure" +  " hPA")
 //       rowIconText(iconPainter = R.drawable.water_drop,contentValue = "Luftfeuchtigkeit:\n$humidity" + "%")
    }

}


@Composable
fun widgetElevatedCardClothes(dataStoreManager: DataStoreManager) {


    val dataStoreShort1 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shortTempMin)).collectAsState(initial = "Error")
    val dataStoreShort2 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shortTempMax)).collectAsState(initial = "Error")

    val dataStoreTrouser1 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.trouserTempMin)).collectAsState(initial = "Error")
    val dataStoreTrouser2 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.trouserTempMax)).collectAsState(initial = "Error")

    val dataStoreShirt1 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shirtTempMin)).collectAsState(initial = "Error")
    val dataStoreShirt2 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.shirtTempMax)).collectAsState(initial = "Error")

    val dataStorePullover1 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.PulloverTempMin)).collectAsState(initial = "Error")
    val dataStorePullover2 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.PulloverTempMax)).collectAsState(initial = "Error")

    val dataStoreJacket1 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.JacketTempMin)).collectAsState(initial = "Error")
    val dataStoreJacket2 by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.JacketTempMax)).collectAsState(initial = "Error")

    val dataStoreTemperature by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.temperature)).collectAsState(initial = "Error")

    fun safeToInt(value: String): Int? {
        return value.toDoubleOrNull()?.toInt()
    }
    var drawableArray = Array(5) { R.drawable.cancel_color }
    val temp = safeToInt(dataStoreTemperature.toString().dropLast(1))
    val shortMin = safeToInt(dataStoreShort1.toString())
    val shortMax = safeToInt(dataStoreShort2.toString())
    val trouserMin = safeToInt(dataStoreTrouser1.toString())
    val trouserMax = safeToInt(dataStoreTrouser2.toString())
    val shirtMin = safeToInt(dataStoreShirt1.toString())
    val shirtMax = safeToInt(dataStoreShirt2.toString())
    val pulloverMin = safeToInt(dataStorePullover1.toString())
    val pulloverMax = safeToInt(dataStorePullover2.toString())
    val jacketMin = safeToInt(dataStoreJacket1.toString())
    val jacketMax = safeToInt(dataStoreJacket2.toString())
    Log.i("TempDebug1",dataStoreTemperature.toString())
    Log.i("TempDebug2",temp.toString())
    if (temp != null) {
        if (shortMin != null && shortMax != null) {
            if (temp >= shortMin && temp <= shortMax) {
                drawableArray[0] = R.drawable.check_mark_color
            }
        }

        if (trouserMin != null && trouserMax != null) {
            if (temp >= trouserMin && temp <= trouserMax) {
                drawableArray[1] = R.drawable.check_mark_color
            }
        }

        if (shirtMin != null && shirtMax != null) {
            if (temp >= shirtMin && temp <= shirtMax) {
                drawableArray[2] = R.drawable.check_mark_color
            }
        }

        if (pulloverMin != null && pulloverMax != null) {
            if (temp >= pulloverMin && temp <= pulloverMax) {
                drawableArray[3] = R.drawable.check_mark_color
            }
        }

        if (jacketMin != null && jacketMax != null) {
            if (temp >= jacketMin && temp <= jacketMax) {
                drawableArray[4] = R.drawable.check_mark_color
            }
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
    var iconsizeWear = 72.dp
        RowHeadline(headlineText = "Kleidervorschlag",)
    //    rowIconText(iconPainter = R.drawable.checkroom,  contentValue = "Folgender Vorschlag:\nDu solltest heute ein Pullover und lange Hose anziehen", columnAmount = 2)
            rowIconIcon(iconPainter1 = R.drawable.shorttrouser, iconPainter2 = drawableArray[0], iconSize = iconsizeWear  )
            rowIconIcon(iconPainter1 = R.drawable.trousers, iconPainter2 = drawableArray[1], iconSize = iconsizeWear  )
            rowIconIcon(iconPainter1 = R.drawable.shirt, iconPainter2 = drawableArray[2], iconSize = iconsizeWear  )
            rowIconIcon(iconPainter1 = R.drawable.pullover, iconPainter2 = drawableArray[3], iconSize = iconsizeWear  )
            rowIconIcon(iconPainter1 = R.drawable.jacket, iconPainter2 = drawableArray[4], iconSize = iconsizeWear  )

    }

}






/*
{
        Text(
            text = "Temperatur",
            modifier = Modifier
                .fillMaxWidth(), // Wrap content size to center it properly
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp)) // Abstand am Anfang
        Text(
            text = "Temperatur: " + Temperature + "°C",
            modifier = Modifier
                .fillMaxWidth(), // Wrap content size to center it properly
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
 */