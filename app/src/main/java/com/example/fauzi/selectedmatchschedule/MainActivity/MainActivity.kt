package com.example.fauzi.selectedmatchschedule.MainActivity

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.fauzi.selectedmatchschedule.Api.ApiRepository
import com.example.fauzi.selectedmatchschedule.DetailActivity.DetailActivity
import com.example.fauzi.selectedmatchschedule.MainAdapter
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.R.color.colorAccent
import com.example.fauzi.selectedmatchschedule.Response.MatchList
import com.example.fauzi.selectedmatchschedule.Utils.invisible
import com.example.fauzi.selectedmatchschedule.Utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private var teams : MutableList<MatchList> = mutableListOf()
    //private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: MainPresenter
    private lateinit var progressBar: ProgressBar
    var status : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        relativeLayout {
            lparams (width = matchParent, height = wrapContent)

            linearLayout {
                orientation = LinearLayout.VERTICAL

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        recyclerView = recyclerView {
                            id = R.id.recycler_view
                            layoutManager = LinearLayoutManager(ctx)
                        }.lparams(matchParent, matchParent) {}


                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                            centerVertically()
                        }
                    }

            }.lparams(){
                above(R.id.buttonNav)
            }

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                id = R.id.buttonNav

                button(" < Prev Match") {
                    id = R.id.prevButton
                    onClick {
                        presenter.getPrevMatchList()
                    }
                    backgroundColor = Color.GRAY

                    textColor = Color.WHITE
                }.lparams(width = 0, height = wrapContent) {
                    weight = 1f
                }

                button("Next Match >") {
                    id = R.id.nextButton
                    onClick {
                        presenter.getNextMatchList()
                    }
                    backgroundColor = Color.GRAY
                    textColor = Color.WHITE

                }.lparams(width = 0, height = wrapContent) {
                    weight = 1f
                }
            }.lparams(matchParent, wrapContent){
                alignParentBottom()
            }

        }

        adapter = MainAdapter(teams) {
            startActivity<DetailActivity>("match" to it)
        }
        recyclerView.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        presenter.getPrevMatchList()

    }


    override fun startLoading() {
        progressBar.visible()
    }

    override fun loadingEnd() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<MatchList>) {
        //swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
