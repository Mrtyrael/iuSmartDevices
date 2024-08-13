package com.example.weatherwear


import DataStoreManager
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey


@Composable
fun widgetElevatedCardNetworkStatus(dataStoreManager: DataStoreManager ) {
    val dataStoreMQTTStatus by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.mQTTStatus)).collectAsState(initial = "Error")
    val dataStoreMQTTTimestamp by dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.mQTTTimestamp)).collectAsState(initial = "Error")

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = GlobalSettings.evaluationvalue
        ),
        modifier = Modifier
            .padding(horizontal = GlobalSettings.evaluationpaddinghorizontal, vertical = GlobalSettings.evaluationpaddingvertical)
            .fillMaxWidth() // Fills the width of the parent
            .wrapContentHeight() // Adjusts height to content
    )
    {

        RowHeadline(headlineText = "Status")
        rowIconText(iconPainter = R.drawable.data_usage, contentValue = "MQTT: $dataStoreMQTTStatus", columnAmount = 2)
        rowIconText(iconPainter = R.drawable.update, contentValue = "Wetterstation zuletzt online:\n$dataStoreMQTTTimestamp ", columnAmount = 2)

    }

}