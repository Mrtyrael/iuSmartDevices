package com.example.weatherwear

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.weatherwear.GlobalSettings.Companion.rowPadding
import kotlin.math.roundToInt

@Composable
fun RowHeadline (headlineText: String, rowPadding: Dp = GlobalSettings.rowPadding)
{
    //Überschrift
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = headlineText,
            modifier = Modifier
                .fillMaxWidth() // Wrap content size to center it properly
                .padding(rowPadding),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun rowIconText (contentValue: String, iconPainter: Int , columnAmount: Int = 3, rowPadding: Dp = GlobalSettings.rowPadding, iconSize: Dp = GlobalSettings.iconSize) {

   var midColumnWidth = 3f
    var textAlignFlex = TextAlign.Center
    if (columnAmount != 3)  //Bei 3 ungleich 3 Spalten wird immer 2 Spalten angenommen
    {
        midColumnWidth = 4f
        textAlignFlex = TextAlign.Left
    }


    Row(verticalAlignment = Alignment.CenterVertically,  modifier = Modifier
        .padding(rowPadding),) {
        Column (
            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(1f)) {
            Icon(
                painter = painterResource(id = iconPainter),
                contentDescription = "Temperature Icon",
                modifier = Modifier
                    .size(iconSize)
                    .fillMaxWidth() // Wrap content size to center it properly

            )
        }
        Column(
            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(midColumnWidth)) {




            Text(
                text = contentValue,
                modifier = Modifier
                    .fillMaxWidth(), // Wrap content size to center it properly
                style = MaterialTheme.typography.headlineSmall,
                textAlign = textAlignFlex ,

            )
        }
        if (columnAmount == 3)
        {
            Column(
                verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
                horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
                modifier = Modifier
                    .weight(1f)) {

            }
        }

    }

}

@Composable
fun RowOutlineTextfield (text: String, label: String, onTextChanged: (String) -> Unit, rowPadding: Dp = GlobalSettings.rowPadding, onlyNumbers: Boolean = false)
{

var keyboardLimitations = KeyboardType.Ascii
    if (onlyNumbers)
{
    keyboardLimitations = KeyboardType.Number
}
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = text,
            modifier = Modifier
                .fillMaxWidth() // Wrap content size to center it properly
                .padding(rowPadding),
            onValueChange = { onTextChanged(it) },
            label = { Text(label) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardLimitations)

        )
    }

}

@Composable
fun DoubleRowOutlineTextfield (text1: String, text2: String, label1: String, label2: String,onTextChanged1: (String) -> Unit,  onTextChanged2: (String) -> Unit, rowPadding: Dp = GlobalSettings.rowPadding, onlyNumbers: Boolean = false, iconPainter: Int, iconSize: Dp = GlobalSettings.iconSize)
{

    var keyboardLimitations = KeyboardType.Ascii
    if (onlyNumbers)
    {
        keyboardLimitations = KeyboardType.Number
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column (
            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(1f)) {
            Icon(
                painter = painterResource(id = iconPainter),
                contentDescription = "Temperature Icon",
                modifier = Modifier
                    .size(iconSize)
                    .fillMaxWidth() // Wrap content size to center it properly

            )
        }
        Column (
            modifier = Modifier.weight(1f) // Erstes Textfeld nimmt die andere Hälfte der Breite ein
        ){
            OutlinedTextField(
                value = text1,
                modifier = Modifier
                    .fillMaxWidth() // Wrap content size to center it properly
                    .padding(rowPadding),
                onValueChange = { onTextChanged1(it) },
                label = { Text(label1) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardLimitations)

            )
        }
        Column (
            modifier = Modifier.weight(1f) // Zweites Textfeld nimmt die andere Hälfte der Breite ein
        ) {
            OutlinedTextField(
                value = text2,
                modifier = Modifier
                    .fillMaxWidth() // Wrap content size to center it properly
                    .padding(rowPadding),
                onValueChange = { onTextChanged2(it) },
                label = { Text(label2) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardLimitations)

            )
        }

    }

}

@Composable
fun RowElevatedButton(onClick: () -> Unit, text : String, rowPadding: Dp = GlobalSettings.rowPadding ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth() // Wrap content size to center it properly
                .padding(rowPadding))
        {
            Text(text)
        }

    }
}


@Composable
fun rowIconIcon (iconPainter1: Int , iconPainter2: Int ,rowPadding: Dp = GlobalSettings.rowPadding, iconSize: Dp = GlobalSettings.iconSize) {


    var textAlignFlex = TextAlign.Center


    Row(verticalAlignment = Alignment.CenterVertically,  modifier = Modifier
        .padding(rowPadding),) {
        Column (
            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(1f)) {
            Image(
                painter = painterResource(id = iconPainter1),
                contentDescription = "Temperature Icon",
                modifier = Modifier
                    .size(iconSize)
                    .fillMaxWidth() // Wrap content size to center it properly

            )
        }
        Column(
            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(1f)) {
            Image(
                painter = painterResource(id = iconPainter2),
                contentDescription = "Temperature Icon",
                modifier = Modifier
                    .size(iconSize)
                    .fillMaxWidth() // Wrap content size to center it properly

            )
        }

    }

}
@Composable
fun rowIconSlider (start: String, end: String,iconPainter: Int ,onValueChanged: (ClosedFloatingPointRange<Float>) -> Unit,  rowPadding: Dp = GlobalSettings.rowPadding, iconSize: Dp = GlobalSettings.iconSize) {



    var sliderPosition by remember { mutableStateOf(-20f..40f) }
    var _start = start.toIntOrNull() ?: 0
    var _end = end.toIntOrNull() ?: 0
    if (_start > _end)  //Ansonsten kann es zum crash kommen
    {
        val __start = _start
        _start = _end
        _end = __start

    }

    sliderPosition = _start.toFloat().._end.toFloat()
    val roundedRange = sliderPosition.start.roundToInt()..sliderPosition.endInclusive.roundToInt()

    Row(verticalAlignment = Alignment.CenterVertically,  modifier = Modifier
        .padding(rowPadding),) {
        Column (
            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(1f)) {
            Icon(
                painter = painterResource(id = iconPainter),
                contentDescription = "Temperature Icon",
                modifier = Modifier
                    .size(iconSize)
                    .fillMaxWidth() // Wrap content size to center it properly

            )
        }
        Column(

            verticalArrangement = Arrangement.Center, // Zentriert das Icon vertikal
            horizontalAlignment = Alignment.CenterHorizontally, // Zentriert das Icon horizontal
            modifier = Modifier
                .weight(2f)) {
            RangeSlider(
                value = sliderPosition,
                steps = 60,
                onValueChange = onValueChanged, //{ range -> sliderPosition = range },
                valueRange = -20f..40f,
                onValueChangeFinished = {
                    // launch some business logic update with the state you hold
                    // viewModel.updateSelectedSliderValue(sliderPosition)
                },
            )
            //Text(text = sliderPosition.toString())
            Text(text = "${roundedRange.first}°C - ${roundedRange.last}°C")
        }

    }

}
