package com.rendezvous.myapplication

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rendezvous.myapplication.ui.theme.ByteInsightTheme
import com.rendezvous.myapplication.ui.theme.Fonts

class Preferences : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByteInsightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
                    PreferencesLayout(sharedPref)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesLayout(sharedPref: SharedPreferences) {
    val editor = sharedPref.edit()
    val activity = LocalContext.current as? Activity
    var greeting by remember { mutableStateOf(sharedPref.getString("greeting", "Ram Ram")) }
    var name by remember { mutableStateOf(sharedPref.getString("name", "Bhai Saryane")) }
    var welcome by remember {
        mutableStateOf(
            sharedPref.getString(
                "welcome",
                "Welcome to another wonderful day of coding!"
            )
        )
    }
    var language by remember { mutableStateOf(sharedPref.getString("language", "English")) }
    var expanded by remember { mutableStateOf(false) }
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(15, 155, 170),
        unfocusedBorderColor = Color(15, 155, 170),
        textColor = Color.White,
        focusedLabelColor = Color(15, 155, 170),
        unfocusedLabelColor = Color(15, 155, 170)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(40, 40, 40))
            .padding(12.dp)
    ) {
        Row {
            IconButton(onClick = {
                editor.apply {
                    putString("greeting",greeting)
                    putString("name",name)
                    putString("welcome",welcome)
                    putString("language",language)
                    apply()
                }
                activity?.finish()
                                 }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Text(
                text = "Preferences",
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = Fonts().playPenSansFamily(),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = greeting.toString(),
            onValueChange = { it: String ->
                greeting = it
            },
            label = { Text("Greeting", fontWeight = FontWeight.Bold) },
            colors = colors,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = name.toString(),
            onValueChange = { it: String ->
                name = it
            },
            label = { Text("Name", fontWeight = FontWeight.Bold) },
            colors = colors,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = welcome.toString(),
            onValueChange = { it: String ->
                welcome = it
            },
            label = { Text("Welcome Message", fontWeight = FontWeight.Bold) },
            colors = colors,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = language.toString(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = colors,
                label = {Text(text = "Language", fontWeight = FontWeight.Bold)}
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background((Color(40,40,40))),
            ) {
                DropdownMenuItem(
                    text = { Text(text = "English",color = Color.White) },
                    onClick = {
                        language = "English"
                        expanded = false
                    },
                    modifier = Modifier.border(1.dp,Color.White)
                )
                DropdownMenuItem(
                    text = { Text(text = "Hinglish",color = Color.White) },
                    onClick = {
                        language = "Hinglish"
                        expanded = false
                    },
                    modifier = Modifier.border(1.dp,Color.White)
                )
            }
        }
    }
    BackHandler {
        editor.apply {
            putString("greeting",greeting)
            putString("name",name)
            putString("welcome",welcome)
            putString("language",language)
            apply()
        }
        activity?.finish()
    }
}
