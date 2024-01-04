package com.rendezvous.myapplication

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rendezvous.myapplication.ui.theme.ByteInsightTheme
import com.rendezvous.myapplication.ui.theme.Fonts

class AboutScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByteInsightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AboutActivityLayout()
                }
            }
        }
    }
}

@Composable
fun AboutActivityLayout() {
    val activity = LocalContext.current as? Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(40, 40, 40))
            .padding(12.dp)
    ) {
        Row {
            IconButton(onClick = {
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
                text = "About",
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = Fonts().playPenSansFamily(),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ){
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .border(3.dp, Color(15, 155, 170), CircleShape)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = """
                 I, a 16-year-old, developed this app along with my boys as a school-level entrepreneurship project to demonstrate our teamwork and entrepreneurship skills. However — unlike my boys — I thought something different about it. For me, this application serves the purpose of breaking down the barriers of technical illiteracy and making the art and science of coding, accessible to all. So, I dedicated most of myself to this app, compromising a little on my studies, just to see my idea come to life. Even though this app doesn't look too good or attractive right now, It is going to improve as we learn together. With this vision in my mind, I am going to create a Discord community for the learning journey of the members, so that they don't fall short of resources or support. Hope that you enjoy your coding journey.
                 Regards,
                 Bhavishya Dhiman,
                 Team Leader and Developer,
                 Byte Insight
               """.trimIndent(),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = Fonts().exo()
            )

        }
    }
}