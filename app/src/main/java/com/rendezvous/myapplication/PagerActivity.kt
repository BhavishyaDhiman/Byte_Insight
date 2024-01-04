package com.rendezvous.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.component1
import com.google.firebase.storage.component2
import com.google.firebase.storage.storage
import com.rendezvous.myapplication.ui.theme.ByteInsightTheme
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import kotlinx.coroutines.launch

class PagerActivity : ComponentActivity() {
    var mInterstitialAd: InterstitialAd? = null
    private fun loadInterstitialAd() {
        return InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    mInterstitialAd = p0
                }
            }
        )
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this@PagerActivity)
            mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    this@PagerActivity.finish()
                }
            }
        } else this@PagerActivity.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByteInsightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MobileAds.initialize(this)
                    val path = intent.getStringExtra("path")
                    val reference = Firebase.storage.reference.child(path!!)
                    val urls = remember { ArrayList<String>() }
                    var open by remember {
                        mutableStateOf(false)
                    }
                    var num by remember {
                        mutableIntStateOf(0)
                    }
                    remember {
                        reference.listAll().addOnSuccessListener { (indices, _) ->
                            urls.clear()

                            for (index in indices) {
                                index.downloadUrl.addOnSuccessListener {
                                    urls.add(
                                        FirebaseStorage.getInstance()
                                            .getReferenceFromUrl(it.toString()).name.take(2) + it.toString()
                                    )
                                    num++
                                }
                                open = true
                            }
                        }
                    }
                    loadInterstitialAd()
                    PagerContent(num = num, open = open, urls = urls)
                }

            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
    @Composable
    fun PagerContent(num: Int, open: Boolean, urls: ArrayList<String>) {
        val scope = rememberCoroutineScope()
        if (open && num != 0) {
            val urlList = ArrayList<String>()
            for (x in 1..(urls.size)) {
                for (url in urls) {
                    if (url.take(2).toInt() == x) {
                        urlList.add(url.drop(2))
                    }
                }
            }
            println(urlList)
            val pagerState = rememberPagerState {
                urlList.size
            }

            Box {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(40, 40, 40))

                ) { index ->
                    GlideImage(
                        model = urlList[index],
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                DotsIndicator(
                    dotCount = urlList.size,
                    type = ShiftIndicatorType(
                        dotsGraphic = DotGraphic(
                            color = Color(
                                15,
                                155,
                                170
                            )
                        )
                    ),
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.BottomCenter)
                )
                Row(modifier = Modifier.fillMaxSize()) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .alpha(0f),
                        onClick = {
                            scope.launch {
                                if (pagerState.canScrollBackward) pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1
                                )
                            }
                        },
                        shape = RectangleShape
                    ) {}
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .alpha(0f),
                        onClick = {
                            scope.launch {
                                if (pagerState.canScrollForward) pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                ) else showInterstitialAd()
                            }
                        },
                        shape = RectangleShape
                    ) {}
                }
            }

        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(40, 40, 40))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(alignment = Alignment.Center),
                    color = Color.White
                )
            }
        }
        BackHandler {
            showInterstitialAd()
        }
    }

}