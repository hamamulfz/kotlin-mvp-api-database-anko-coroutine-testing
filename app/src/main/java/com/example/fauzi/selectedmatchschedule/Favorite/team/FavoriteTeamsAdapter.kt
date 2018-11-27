package com.example.fauzi.footbalclub

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fauzi.selectedmatchschedule.R.id.team_badge
import com.example.fauzi.selectedmatchschedule.R.id.team_name
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamsAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI2().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class TeamUI2 : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent,
                        height = wrapContent)
                padding = dip(5)

                orientation = LinearLayout.HORIZONTAL
                linearLayout {
                    lparams(width = matchParent,
                            height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(8)
                    backgroundColor = Color.WHITE

                    imageView {
                        id = team_badge
                    }.lparams {
                        height = dip(50)
                        width = dip(50)
                    }

                    textView {
                        id = team_name
                        textSize = 16f
                    }.lparams {
                        margin = dip(15)
                    }

                }
            }
        }
    }

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
        Picasso.get().load(favorite.teamBadge).into(teamBadge)
        teamName.text = favorite.teamName
        itemView.onClick { listener(favorite) }
    }
}