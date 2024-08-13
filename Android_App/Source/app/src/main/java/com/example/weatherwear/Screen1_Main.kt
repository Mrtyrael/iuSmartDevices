package com.example.weatherwear

import DataStoreManager
import androidx.compose.runtime.*
import androidx.datastore.preferences.core.stringPreferencesKey

@Composable
fun screen1Home(dataStoreManager: DataStoreManager)
{

    widgetElevatedCardTemperature(dataStoreManager = dataStoreManager)
    widgetElevatedCardClothes(dataStoreManager = dataStoreManager)

}
