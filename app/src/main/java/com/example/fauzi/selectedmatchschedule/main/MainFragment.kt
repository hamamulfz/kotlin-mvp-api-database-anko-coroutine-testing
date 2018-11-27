package com.example.fauzi.selectedmatchschedule.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.detail.DetailActivity
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.R.color.*
import com.example.fauzi.selectedmatchschedule.response.MatchList
import com.example.fauzi.selectedmatchschedule.utils.invisible
import com.example.fauzi.selectedmatchschedule.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainFragment : Fragment(), AnkoComponent<Context>, MainView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    private var teams : MutableList<MatchList> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: MainPresenter
    private lateinit var progressBar: ProgressBar
    var status : Int = 1


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        adapter = MainAdapter(teams) {
            context?.startActivity<DetailActivity>("match" to "${it.eventId}")
        }
        recyclerView.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        presenter.getPrevMatchList()

        swipeRefresh.onRefresh {
            if (status==1) {
                presenter.getPrevMatchList()
            } else {
                presenter.getNextMatchList()
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        relativeLayout {
            lparams (width = matchParent, height = wrapContent)

            linearLayout {
                orientation = LinearLayout.VERTICAL

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

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
                }

            }.lparams {
                bottomOf(R.id.buttonNav)
            }

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                id = R.id.buttonNav

                button {
                    id = R.id.prevButton
                    text = ctx.resources.getString(R.string.prev_match)
                    onClick {
                        presenter.getPrevMatchList()
                        status = 1
                    }
                    backgroundColor = colorPrimaryDark
                    textColor = Color.WHITE
                }.lparams(width = 0, height = wrapContent) {
                    weight = 1f
                }

                button {
                    id = R.id.nextButton
                    text = ctx.resources.getString(R.string.next_match)
                    onClick {
                        presenter.getNextMatchList()
                        status = 2
                    }
                    backgroundColor = colorPrimaryDark
                    textColor = Color.WHITE

                }.lparams(width = 0, height = wrapContent) {
                    weight = 1f
                }
            }.lparams(matchParent, wrapContent){
                alignParentStart()
            }

        }
    }

    override fun startLoading() {
        progressBar.visible()
    }

    override fun loadingEnd() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<MatchList>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}