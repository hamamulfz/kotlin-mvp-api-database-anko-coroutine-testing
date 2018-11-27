package com.example.fauzi.selectedmatchschedule.favorite.match

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.utils.dateConvertion
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteMatchAdapter(
        private val items: List<FavoriteMatch>,
        private val clickListener: (FavoriteMatch) -> Unit): RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(RecyclerViewTeamMatch().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }
}


class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val dateOfMatch: TextView = view.findViewById(R.id.date)
    private val homeTeamScore: TextView = view.findViewById(R.id.homeScore)
    private val awayTeamScore: TextView = view.findViewById(R.id.awayScore)
    private val homeVsAwayTeam: TextView = view.findViewById(R.id.teamName)


    fun bind(item: FavoriteMatch, clickListener: (FavoriteMatch) -> Unit) {
        dateOfMatch.text = dateConvertion(item.dateOfMatch)
        homeTeamScore.text = item.homeScore
        awayTeamScore.text = item.awayScore
        homeVsAwayTeam.text = item.awayTeamVsHomeTeam

        itemView.onClick { clickListener(item) }
    }
}

class RecyclerViewTeamMatch : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent,
                    wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(5)

            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.WHITE

                textView {
                    id = R.id.date
                    textColor = Color.BLUE
                    gravity = Gravity.CENTER
                }.lparams(matchParent,
                        wrapContent)

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = R.id.homeScore
                            textSize = 18f
                            text = ctx.resources.getString(R.string.zero)
                        }

                        textView {
                            text = ctx.resources.getString(R.string.hypen)
                            textColor = Color.BLUE
                        }.lparams {
                            setMargins(dip(5), dip(2), dip(5), dip(0))
                        }

                        textView {
                            id = R.id.awayScore
                            textSize = 18f
                            text = ctx.resources.getString(R.string.zero)
                        }
                    }
                }
            }.lparams(matchParent,
                    matchParent) {}

            textView {
                id = R.id.teamName
                gravity = Gravity.CENTER
                backgroundColor = Color.WHITE
                textSize = 18f
                text = ctx.resources.getString(R.string.home_vs_away)
            }.lparams(matchParent,
                    wrapContent)
        }
    }
}
