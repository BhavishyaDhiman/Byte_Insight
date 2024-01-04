package com.rendezvous.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.MobileAds
import com.rendezvous.myapplication.ui.theme.ByteInsightTheme
import com.rendezvous.myapplication.ui.theme.Fonts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByteInsightTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val sharedPref = getSharedPreferences("myPref",Context.MODE_PRIVATE)
                    MobileAds.initialize(this)
                    MainContent(sharedPref)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(sharedPref: SharedPreferences) {
    val mContext = LocalContext.current
    val font = Fonts().playPenSansFamily()
    var state by remember { mutableIntStateOf(0) }
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet (
            drawerContainerColor = Color(40,40,40),
            modifier = Modifier
                .padding(8.dp)
                .border(width = 2.dp, color = Color.White)
        ){
            Text(
                text = "Menu",
                fontFamily = FontFamily.Cursive,
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
            )
            val drawerItems = listOf(
                MenuItem(
                    title = "Preferences",
                    icon = Icons.Filled.Settings,
                    onClick = {mContext.startActivity(Intent(mContext, Preferences::class.java))}
                ),
                MenuItem(
                    title = "About",
                    icon = Icons.Filled.Info,
                    onClick = {mContext.startActivity(Intent(mContext, AboutScreen::class.java))}
                ),
                MenuItem(
                    title = "Join Discord",
                    icon = ImageVector.vectorResource(R.drawable.discord),
                    onClick = {mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/pQNd7xmH")))}
                ),
                MenuItem(
                    title = "Support Us",
                    icon = Icons.Filled.Favorite,
                    onClick = {mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buymeacoffee.com/rendezvousx")))}
                )
            )
            Spacer(Modifier.height(16.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
            )
            Composables().DrawerBody(items = drawerItems)
        }
    }) {
        Box {
            Column(
                modifier = Modifier
                    .background(color = Color(40, 40, 40))
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heading),
                    contentDescription = null
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent, //Card background color
                        contentColor = Color.White  //Card content color,e.g.text
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .border(2.dp, Color(227, 131, 57), RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .clickable { state++ }
                ) {
                    if (state != -1) {
                        Text(
                            text = sharedPref.getString(
                                "greeting",
                                "Ram Ram"
                            ) + "\n" + sharedPref.getString("name", "Bhai Saryane"),
                            color = Color.White,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 40.sp,
                            lineHeight = 45.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(12.dp)
                        )
                        Text(
                            text = sharedPref.getString(
                                "welcome",
                                "Welcome to another wonderful day of coding!"
                            ).toString(),
                            color = Color.White,
                            fontSize = 15.sp,
                            lineHeight = 16.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = font,
                            modifier = Modifier
                                .padding(6.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent, //Card background color
                        contentColor = Color.White  //Card content color,e.g.text
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .border(2.dp, Color(15, 155, 170), RoundedCornerShape(20.dp))
                        .fillMaxWidth(),
                    onClick = {
                        mContext.startActivity(Intent(mContext, LessonActivity::class.java).also {
                            it.putExtra("lesson", "Learn")
                        })
                    }
                ) {
                    Text(
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        text = "   Learn",
                        color = Color.White,
                        fontSize = 35.sp,
                        lineHeight = 35.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(8.dp)
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent, //Card background color
                        contentColor = Color.White  //Card content color,e.g.text
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .border(2.dp, Color(15, 155, 170), RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .clickable { state++ },
                    onClick = {
                        mContext.startActivity(Intent(mContext, LessonActivity::class.java).also {
                            it.putExtra("lesson", "Practice")
                        })
                    }
                ) {
                    Text(
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        text = "   Practice",
                        color = Color.White,
                        fontSize = 35.sp,
                        lineHeight = 35.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(8.dp)
                    )
                }
            }
            Composables().AdmobBanner(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
