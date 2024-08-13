package com.example.weatherwear

import DataStoreManager
import androidx.compose.runtime.*

@Composable
fun screen3NetworkSettings(dataStoreManager: DataStoreManager)
{

    widgetElevatedCardNetworkSettings(dataStoreManager)
    widgetElevatedCardNWeatherStation(dataStoreManager = dataStoreManager)
    widgetElevatedCardFactoryReset(dataStoreManager = dataStoreManager)

}
