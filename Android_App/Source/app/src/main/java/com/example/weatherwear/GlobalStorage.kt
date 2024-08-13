import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(context: Context) {


    private val dataStore = context.dataStore

    // Funktion zum Speichern eines Strings
    suspend fun saveStringToDataStore(key: Preferences.Key<String>, value: String) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    // Funktion zum Laden eines Strings
    fun readStringFromDataStore(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    // Function to save data to DataStore
    fun saveToDataStore(key: String, value: String) {
        // This function should not be @Composable because it performs a side effect
        CoroutineScope(Dispatchers.IO).launch {
            saveStringToDataStore(stringPreferencesKey(key), value)
            Log.d("Speichere Datastore Key = $key",value)
        }
    }
}


/*
Beispiele zum lesen

//Composable wird neu aufgerufen wenn sich Wert ändert
            val datastoreselectedItem by dataStoreManager.readStringFromDataStore(stringPreferencesKey("Keystring")).collectAsState(initial = null)



// Effekt, um den Wert aus dem DataStore zu lesen und MQTTBrokerServerUri zu aktualisieren
    LaunchedEffect(Unit) {
        dataStoreManager.readStringFromDataStore(stringPreferencesKey(DataStoreKeys.MQTTBrokerServerUri)).collect { value ->
            MQTTBrokerServerUri = value ?: "tcp://broker.hivemq.com:1883"
        }
    }


*/


            /*
Beispiele zum schreiben aus einer composable:

//LaunchedEffect wird automatisch aufgerufen wenn die composable neu lädt

             LaunchedEffect(Unit) {
                        // Starte eine Coroutine, um den Wert im DataStore zu speichern
                        dataStoreManager.saveStringToDataStore(stringPreferencesKey("selectedItemGlobal"), "Hallo")
                    }

//coroutine und launch == Manueller Start

            //Statt Dispatcher in der Hauptfunktion könnte man den Dispatcher auch in der composable starten oder die funktion umbauen
            val coroutineScope = rememberCoroutineScope()
            coroutineScope.launch(Dispatchers.IO) {
                // Diese Coroutine startet manuell und läuft im Hintergrund-Thread (z.B. für Netzwerkanfragen)
                dataStoreManager.saveStringToDataStore(stringPreferencesKey("selectedItemGlobal"), "Hallo")
            }

 */