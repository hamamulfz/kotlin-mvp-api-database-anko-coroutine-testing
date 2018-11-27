package com.example.fauzi.selectedmatchschedule.Favorite

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fauzi.selectedmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamsAdapter(
        private val items: List<Favorite>,
        private val clickListener: (Favorite) -> Unit): RecyclerView.Adapter<ViewHolder>(){

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


    fun bind(item: Favorite, clickListener: (Favorite) -> Unit) {
        dateOfMatch.text = item.dateOfMatch
        homeTeamScore.text = item.homeScore
        awayTeamScore.text = item.awayScore
        homeVsAwayTeam.text = item.awayTeamVsHomeTeam

        itemView.onClick { clickListener(item) }
    }
}

class RecyclerViewTeamMatch : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(10)

            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.WHITE

                textView {
                    id = R.id.date
                    textColor = Color.BLUE
                    gravity = Gravity.CENTER
                }.lparams(matchParent, wrapContent)

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = R.id.homeScore
                            textSize = 18f
                            text = "0"
                        }

                        textView {
                            text = "-"
                            textColor = Color.BLUE
                        }.lparams {
                            setMargins(dip(5), dip(2), dip(5), dip(0))
                        }

                        textView {
                            id = R.id.awayScore
                            textSize = 18f
                            text = "0"
                        }
                    }
                }
            }.lparams(matchParent, matchParent) {}

            textView {
                id = R.id.teamName
                gravity = Gravity.CENTER
                backgroundColor = Color.WHITE
                textSize = 18f
                text = "home vs away"
            }.lparams(matchParent, wrapContent, 1f)

        }
    }
}
