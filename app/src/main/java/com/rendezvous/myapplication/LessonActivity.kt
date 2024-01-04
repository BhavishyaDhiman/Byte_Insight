package com.rendezvous.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.component1
import com.google.firebase.storage.component2
import com.google.firebase.storage.storage
import com.rendezvous.myapplication.ui.theme.ByteInsightTheme
import com.rendezvous.myapplication.ui.theme.Fonts

class LessonActivity : ComponentActivity() {
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
                    val lesson = intent.getStringExtra("lesson")
                    val storageRef = Firebase.storage.reference
                    val chapterList = storageRef.child(sharedPref.getString("language","English") + "/" + lesson)
                    LessonsContent(lesson = lesson.toString(), chapterList = chapterList)
                }
            }
        }
    }
}

@Composable
fun LessonsContent(lesson: String,chapterList: StorageReference) {
    val chapters: ArrayList<String> = remember{ArrayList()}
    val paths: ArrayList<String> = remember {ArrayList()}
    var open by remember {
        mutableStateOf(false)
    }
    chapterList.listAll().addOnSuccessListener { (_, prefixes) ->
        chapters.clear()
        paths.clear()
        for (prefix in prefixes) {
            chapters.add(prefix.name)
            paths.add(prefix.path)
        }
        open = true
    }
    val playPenSansFamily = Fonts().playPenSansFamily()

    Column (
        modifier = Modifier
            .background(color = Color(40, 40, 40))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            text = lesson,
            color = Color.White,
            fontSize = 45.sp,
            lineHeight = 45.sp,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (open) {
            LazyColumn {
                items(chapters) { item ->
                    val index = chapters.indexOf(item)
                    ListItem(index = index, heading = item.drop(2), font = playPenSansFamily,path = paths[index])
                }
            }
            Divider()
        }
    }
}

@Composable
fun ListItem (index: Int, heading:String,font:FontFamily , path:String) {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier.clickable {
            mContext.startActivity(
                Intent(mContext, PagerActivity::class.java).also{
                it.putExtra("path", path) }
            )
        }
    ) {
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Row (modifier = Modifier.wrapContentHeight()){
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .align(Alignment.CenterVertically)
                    .border(2.dp, Color(227, 131, 57), shape = CircleShape)
                    .background(Color.Transparent)
            ) {
                Text(
                    text = (index + 1).toString(),
                    color = Color(227, 131, 57),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = heading,
                color = Color.White,
                fontFamily = font,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                lineHeight = 25.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

