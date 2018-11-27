package com.example.fauzi.selectedmatchschedule.DetailActivity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.fauzi.selectedmatchschedule.Api.ApiRepository
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.Response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.Response.TeamBadge
import com.example.fauzi.selectedmatchschedule.Response.MatchList
import com.example.fauzi.selectedmatchschedule.Utils.invisible
import com.example.fauzi.selectedmatchschedule.Utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity(), DetailView {

    override fun showHomeTeamBadge(dataHomeTeam: List<TeamBadge>) {
        val imgHomeTeamBadge = find<ImageView>(R.id.homeTeamBadge)
        Picasso.get()
                .load(dataHomeTeam[0].matchTeamBadge)
                .into(imgHomeTeamBadge)
    }

    override fun showAwayTeamBadge(dataAwayTeam: List<TeamBadge>) {
        val imgAwayTeamBadge = find<ImageView>(R.id.awayTeamBadge)
        Picasso.get()
                .load(dataAwayTeam[0].matchTeamBadge)
                .into(imgAwayTeamBadge)
    }

    override fun showLeagueBadge(dataLeague: List<LeagueBadge>) {
        val leagueBadgeUrl = find<ImageView>(R.id.leagueBadge)
        Picasso.get()
                .load(dataLeague[0].leagueBadgeUrl)
                .into(leagueBadgeUrl)
    }

    override fun startLoading() {
        progressBar.visible()    }

    override fun endLoading() {
        progressBar.invisible()
    }

    private lateinit var selectedMatch: MatchList
    private lateinit var presenter: DetailPresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedMatch = intent.getParcelableExtra("match")

        relativeLayout {
            lparams (width = matchParent, height = wrapContent){
                padding = dip(5)
            }

            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                //League Name and badge
                linearLayout {
                    lparams(width= matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER

                    imageView {
                        id = R.id.leagueBadge
                    }.lparams(100,100) {}

                    textView {
                        textSize = 20f
                        text = selectedMatch.leagueName
                        textColor = Color.BLUE
                    }
                }

                //line
                linearLayout {
                    view {
                        backgroundColor = Color.BLUE
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }
                }

                //Match Date
                textView{
                    gravity = Gravity.CENTER
                    textSize = 18f
                    text = selectedMatch.dateOfMatch
                    textColor = Color.BLUE
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER

                    //Home Badge dan Team Name
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        imageView(){
                            id = R.id.homeTeamBadge
                        }.lparams(100,100){}

                        textView {
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = selectedMatch.homeTeamName
                            textColor = Color.BLUE
                        }
                    }.lparams(width=0,height = wrapContent){
                        weight =0.4f                    }

                    //Match Score
                    linearLayout {
                        gravity = Gravity.CENTER

                        textView {
                            id = R.id.homeScore
                            padding = dip(4)
                            textSize = 20f
                            gravity = Gravity.CENTER_HORIZONTAL
                            text = "0"
                            text=selectedMatch.homeScore
                        }.lparams(width=0, height = wrapContent){
                            weight = 4.5f
                        }

                        textView {
                            text = "vs"
                        }.lparams(width=0, height = wrapContent){
                        weight = 1f
                    }

                        textView {
                            id = R.id.awayScore
                            padding = dip(4)
                            gravity = Gravity.CENTER_HORIZONTAL
                            textSize = 20f
                            text = "0"
                            text=selectedMatch.awayScore
                        }.lparams(width=0, height = wrapContent){
                            weight = 4.5f
                        }
                    }.lparams(0, wrapContent){
                        weight = 0.2f                    }

                    //Away Badge dan Team Name
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        imageView() {
                            id = R.id.awayTeamBadge
                        }.lparams(100,100) {}

                        textView {
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = selectedMatch.awayTeamName
                            textColor = Color.BLUE
                        }
                    }.lparams(0, wrapContent){
                        weight = 0.4f
                    }
                }

                //line
                linearLayout {
                    view {
                        backgroundColor = Color.BLUE
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }
                }

                scrollView {
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        topPadding = dip(10)
                        bottomPadding = dip(20)

                        //Goals Detail
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamGoalDetails
                                gravity = Gravity.RIGHT
                                if (text == ""){
                                    text = "-"
                                }
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Goals"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(4)
                                text = selectedMatch.awayTeamGoalDetails
                                if (text == ""){
                                    text = "-"
                                }
                            }.lparams(0, wrapContent, 4f)
                        }

                        //Formation
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamFormation
                                gravity = Gravity.RIGHT
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Formation"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamFormation
                            }.lparams(0, wrapContent, 4f)
                        }

                        //line
                        linearLayout {
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                        }


                        //LineUp
                        textView {
                            gravity = Gravity.CENTER
                            textSize = 15f
                            text = "Line Up"
                            padding = dip(5)
                            textColor = Color.WHITE
                            backgroundColor = Color.BLUE
                        }

                        //Forward
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamLineupForward
                                gravity = Gravity.RIGHT
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Forward"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamLineupForward
                            }.lparams(0, wrapContent, 4f)
                        }

                        //Mid Field
                        linearLayout {
                            topPadding = dip(8)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamLineupMidfield
                                gravity = Gravity.RIGHT
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Mid Field"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamLineupMidfield
                            }.lparams(0, wrapContent, 4f)
                        }

                        //Defense
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamLineupDefense
                                gravity = Gravity.RIGHT
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Defense"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamLineupDefense
                            }.lparams(0, wrapContent, 4f)
                        }

                        //Goal Keeper
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamLineupGoalkeeper
                                gravity = Gravity.RIGHT
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Goal Keeper"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamLineupGoalkeeper
                            }.lparams(0, wrapContent, 4f)
                        }

                        //substirus
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamLineupSubstitutes
                                gravity = Gravity.RIGHT
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Substitutes"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamLineupSubstitutes
                            }.lparams(0, wrapContent, 4f)
                        }

                        //line
                        linearLayout {
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                        }

                        //Match Card
                        textView {
                            gravity = Gravity.CENTER
                            textSize = 15f
                            text = "Match Card"
                            padding = dip(5)
                            textColor = Color.WHITE
                            backgroundColor = Color.BLUE
                        }

                        //Yellow Card
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamYellowCards
                                gravity = Gravity.RIGHT
                                if (text == ""){
                                    text = "-"
                                }
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Yellow Card"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamYellowCards
                                if (text == ""){
                                    text = "-"
                                }
                            }.lparams(0, wrapContent, 4f)
                        }

                        //red Card
                        linearLayout {
                            topPadding = dip(4)

                            textView {
                                rightPadding = dip(8)
                                text = selectedMatch.homeTeamRedCards
                                gravity = Gravity.RIGHT
                                if (text == ""){
                                    text = "-"
                                }
                            }.lparams(0, wrapContent, 4f)

                            textView {
                                gravity = Gravity.CENTER
                                leftPadding = dip(8)
                                rightPadding = dip(8)
                                text = "Red Card"
                                textColor = Color.BLUE
                            }.lparams(0, wrapContent, 2f)

                            textView {
                                gravity = Gravity.LEFT
                                leftPadding = dip(8)
                                text = selectedMatch.awayTeamRedCards
                                if (text == ""){
                                    text = "-"
                                }
                            }.lparams(0, wrapContent, 4f)
                        }

                    }


                }

            }

            progressBar = progressBar{
                id = R.id.progresBar
            }.lparams {
                centerInParent()
            }
        }

        progressBar = find(R.id.progresBar)

        presenter = DetailPresenter(this, ApiRepository(), Gson())
        presenter.getBadgeHomeTeam(selectedMatch.homeTeamId)
        presenter.getBadgeAwayTeam(selectedMatch.awayTeamId)
        presenter.getBadgeLeague(selectedMatch.leagueId)

        supportActionBar?.title = "Match Detail"
    }
}
