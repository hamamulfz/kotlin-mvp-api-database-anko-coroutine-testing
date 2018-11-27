package com.example.fauzi.selectedmatchschedule.detail.player

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.Gravity
import android.widget.*
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.response.PlayerDetail
import com.example.fauzi.selectedmatchschedule.utils.invisible
import com.example.fauzi.selectedmatchschedule.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {


    private lateinit var idPlayer: String
    private lateinit var playerHeight : TextView
    private lateinit var playerWeight : TextView
    private lateinit var playerImage : ImageView
    private lateinit var playerName : TextView
    private lateinit var playerPosition : TextView
   // private lateinit var descPlayer : ScrollView
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var progBar : ProgressBar
    private lateinit var presenter: PlayerDetaiPresenter
    private lateinit var  playerDesc : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        idPlayer = intent.getStringExtra("id")

        supportActionBar?.title = resources.getString(R.string.player_detail)



        linearLayout {
            lparams(matchParent,
                    wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                linearLayout {
                    lparams(matchParent,
                            wrapContent)
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = Color.WHITE


                    playerName = textView {
                        textSize = 30f
                        id = R.id.player_name
                        this.gravity = Gravity.CENTER
                    }.lparams(matchParent,
                            wrapContent) {}

                    scrollView {
                        isVerticalScrollBarEnabled = false

                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL

                                playerImage = imageView {
                                    id = R.id.player_images
                                }.lparams(matchParent,
                                        wrapContent) {}

                                linearLayout {
                                    orientation = LinearLayout.HORIZONTAL

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        this.gravity = Gravity.CENTER

                                        textView {
                                            this.gravity = Gravity.CENTER
                                            text = resources.getString(R.string.weight)

                                        }.lparams(matchParent, wrapContent)

                                        playerWeight = textView {
                                            this.gravity = Gravity.CENTER
                                            id = R.id.player_weight
                                            textSize = 40f
                                        }
                                    }.lparams(0,
                                            wrapContent,
                                            1f)

                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        this.gravity = Gravity.CENTER

                                        textView {
                                            this.gravity = Gravity.CENTER
                                            text = resources.getString(R.string.height)

                                        }

                                        playerHeight = textView {
                                            this.gravity = Gravity.CENTER
                                            id = R.id.player_height
                                            textSize = 40f
                                        }
                                    }.lparams(0,
                                            wrapContent,
                                            1f)
                                }

                                playerPosition = textView {
                                    this.gravity = Gravity.CENTER
                                    id = R.id.player_position

                                }

                                playerDesc = textView {
                                }
                            }



                            progBar = progressBar {
                            }.lparams {
                                centerHorizontally()
                            }
                        }
                    }

                }
            }
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerDetaiPresenter(this, request, gson)
        presenter.getPlayerDetail(idPlayer)

        swipeRefresh.onRefresh {
            presenter.getPlayerDetail(idPlayer)
        }
    }

    override fun showLoading() {
        progBar.visible()
    }

    override fun hideLoading() {
        progBar.invisible()
    }

    override fun showPlayerDetail(data: List<PlayerDetail>) {
        swipeRefresh.isRefreshing = false
        when (data[0].playerFanArt) {
            null ->  Picasso.get().load(data[0].playerMainImages).into(playerImage)
            else ->  Picasso.get().load(data[0].playerFanArt).into(playerImage)
        }
        playerName.text = data[0].playerName
        playerPosition.text = data[0].playerPosition
        playerHeight.text = data[0].playerHeight
        playerWeight.text = data[0].playerWeight
        playerDesc.text = data[0].playerDescription
    }
}



