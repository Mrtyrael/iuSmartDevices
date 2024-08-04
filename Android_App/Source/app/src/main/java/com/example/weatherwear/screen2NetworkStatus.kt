package com.example.weatherwear

import DataStoreManager
import androidx.compose.runtime.*

@Composable
fun screen2NetworkStatus(dataStoreManager: DataStoreManager)
{
    widgetElevatedCardNetworkStatus(dataStoreManager = dataStoreManager)
}
