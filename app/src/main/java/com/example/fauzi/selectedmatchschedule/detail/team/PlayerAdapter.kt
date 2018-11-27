package com.example.fauzi.selectedmatchschedule.detail.team

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.response.PlayerDetail
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter(private val player: List<PlayerDetail>, private val listener: (PlayerDetail) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = player.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent,
                        height = wrapContent)
                padding = dip(5)

                linearLayout {
                    lparams(matchParent,
                            wrapContent)

                    padding = dip(8)
                    backgroundColor = Color.WHITE

                    orientation = LinearLayout.HORIZONTAL

                imageView {
                        id = R.id.player_badge
                    }
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }
            }
        }
    }
}



class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerBadge: ImageView = view.find(R.id.player_badge)

    fun bindItem(player: PlayerDetail, listener: (PlayerDetail) -> Unit) {
        Picasso.get().load(player.playerHeadIcon).into(playerBadge)
        playerName.text = player.playerName
        itemView.onClick { listener(player) }
    }
}
