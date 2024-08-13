package com.example.weatherwear

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(HeaderTitel: String) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE0E0E0), 
            titleContentColor = Color.Black
        ),
        title = {
            Text(
                HeaderTitel,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineLarge,
            )
        },

        scrollBehavior = scrollBehavior,
    )


}




data class NavigationItem(val label: String, val icon: ImageVector, val size: Int = 24)

@Composable
fun AppNavigationbar (selectedItem :Int,  onItemSelected: (Int) -> Unit, modifier: Modifier = Modifier) {


    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home),
        NavigationItem("Connection", Icons.Filled.Info),
      //  NavigationItem("Einstellungen", ImageVector.vectorResource(R.drawable.network_manage))
        NavigationItem("Settings",  Icons.Filled.Settings) ,
        NavigationItem("Wear",  ImageVector.vectorResource(R.drawable.pullover))
    )

    NavigationBar (){

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier
                    .align(Alignment.CenterVertically), // Zentrierung vertikal
                icon = { Icon(item.icon, contentDescription = item.label,modifier = Modifier.size(item.size.dp)) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) } ,
            )

        }
    }
}

