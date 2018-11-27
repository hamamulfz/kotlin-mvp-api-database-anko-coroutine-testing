package com.example.fauzi.selectedmatchschedule.list.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.R.array.league_resource
import com.example.fauzi.selectedmatchschedule.R.color.colorAccent
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.detail.team.TeamDetailActivity
import com.example.fauzi.selectedmatchschedule.response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.response.TeamBadge
import com.example.fauzi.selectedmatchschedule.utils.invisible
import com.example.fauzi.selectedmatchschedule.utils.spaceConvertion
import com.example.fauzi.selectedmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView {


    private var teams: MutableList<TeamBadge> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String
    private lateinit var searchBar : EditText
    private lateinit var searchTeamButton : ImageButton

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamAdapter(teams)
        {
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }

        val spinnerItems = resources.getStringArray(league_resource)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                Log.d("tag", spaceConvertion(leagueName) )
                presenter.getTeamList(spaceConvertion(leagueName))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)


            linearLayout {
                lparams(width= matchParent, height = wrapContent)

                searchBar = editText {
                    id = R.id.search_team
                    hint = "Team Name"
                }.lparams(width = dip(0), height = wrapContent, weight = 5f)

                searchTeamButton = imageButton {
                    id = R.id.search_team_button
                    imageResource = R.drawable.ic_search
                    onClick {
                        Log.d("tag", searchBar.text.toString() )

                        if (!searchBar.text.toString().isNotEmpty()) {
                            presenter.getTeamList(leagueName)
                        } else {
                            presenter.searchTeam(searchBar.text.toString())
                        }
                    }
                }.lparams(width = dip(0), height = wrapContent, weight = 1f)
            }

            spinner = spinner() {
                id = R.id.spinner
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                        id = R.id.recycler_view
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<TeamBadge>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueList(data: List<LeagueBadge>) {

    }


}