package com.example.weatherwear


import DataStoreManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var dataStoreManager: DataStoreManager
   // private lateinit var mqttHelper: MqttHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataStoreManager = DataStoreManager(this)
        setDefaultValues(dataStoreManager)
        mqttInit(this, dataStoreManager)
        setContent {

            var selectedItem by remember { mutableIntStateOf(0) }
            Standardscreen(selectedItem, onItemSelected = { index -> selectedItem = index }, dataStoreManager = dataStoreManager)

        }
    }
}



@Composable
fun Standardscreen (selectedItem : Int, onItemSelected: (Int) -> Unit , dataStoreManager: DataStoreManager)
{
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            when (selectedItem) {
                0 ->     TopAppBar("Home")
                1 -> TopAppBar("Connection")
                2 -> TopAppBar("Settings")
                3 -> TopAppBar("Parameter")
                else -> TopAppBar("Home")
            }


        },
        bottomBar = {

            AppNavigationbar(selectedItem, onItemSelected = onItemSelected)
        }

    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
         //   Spacer(modifier = Modifier.height(10.dp)) // Abstand am Anfang

            when (selectedItem) {
                0 -> screen1Home(dataStoreManager = dataStoreManager)
                1 -> screen2NetworkStatus(dataStoreManager = dataStoreManager)
                2 -> screen3NetworkSettings(dataStoreManager)
                3 -> screen4Wearing(dataStoreManager)
                else -> screen1Home(dataStoreManager = dataStoreManager)
            }

        }

    }

}

