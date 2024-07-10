package com.example.myapplicationpa2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationpa2.model.Reservation

class ReservationsAdapter : ListAdapter<Reservation, ReservationsAdapter.ReservationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reservationIdTextView: TextView = itemView.findViewById(R.id.reservationIdTextView)
        private val datesTextView: TextView = itemView.findViewById(R.id.datesTextView)
        private val numAdultsTextView: TextView = itemView.findViewById(R.id.numAdultsTextView)
        private val numChildrenTextView: TextView = itemView.findViewById(R.id.numChildrenTextView)
        private val numBabiesTextView: TextView = itemView.findViewById(R.id.numBabiesTextView)
        private val totalPriceTextView: TextView = itemView.findViewById(R.id.totalPriceTextView)

        fun bind(reservation: Reservation) {
            reservationIdTextView.text = "ID Réservation: ${reservation.id}"
            datesTextView.text = "Dates Réservées: ${reservation.dates.joinToString(", ")}"
            numAdultsTextView.text = "Nombre d'adultes: ${reservation.numAdults}"
            numChildrenTextView.text = "Nombre d'enfants: ${reservation.numChildren}"
            numBabiesTextView.text = "Nombre de bébés: ${reservation.numInfants}"
            totalPriceTextView.text = "Prix total: ${reservation.totalPrice}"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Reservation>() {
            override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation): Boolean {
                return oldItem == newItem
            }
        }
    }
}
