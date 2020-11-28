package cl.giorgioarlandi.hoyse.data.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import cl.giorgioarlandi.hoyse.R
import cl.giorgioarlandi.hoyse.data.models.Mansion
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MansionesAdapter(private val mansiones: List<Mansion>,
                       private val context: Context
) :
    RecyclerView.Adapter<MansionesAdapter.ViewHolder>() {
    val TAG = this.javaClass.simpleName
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val cardView = itemView.findViewById<CardView>(R.id.mansion_card)
        val nameView = itemView.findViewById<TextView>(R.id.mansion_name)
        val priceView = itemView.findViewById<TextView>(R.id.mansion_price)
        val photoView = itemView.findViewById<ImageView>(R.id.mansion_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context

        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_mansion, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mansiones.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mansion: Mansion = mansiones[position]
        val card = holder.cardView
        val name = holder.nameView
        val price = holder.priceView
        val photo = holder.photoView

        name.text = context.getString(R.string.cargando)
        Picasso.get().load(mansion.photoLink).into(photo, object: Callback {
            override fun onSuccess() {
                name.text = mansion.name
                price.text = context.getString(R.string.precio, mansion.price)
                setOnClickListener(card, mansion)
            }

            override fun onError(e: Exception?) {
                e?.printStackTrace()
                name.text = mansion.name
                price.text = context.getString(R.string.precio, mansion.price)
                setOnClickListener(card, mansion)
            }
        })
    }

    private fun setOnClickListener(card: CardView, mansion: Mansion) {
        card.setOnClickListener {
         //   Common.currentHero = hero
         //   val intent = Intent(context, HeroActivity::class.java)
         //   context.startActivity(intent)
        }
    }
}