package uz.devapp.uzchat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.uzchat.data.model.Message
import uz.devapp.uzchat.databinding.MessageItemLayoutBinding
import uz.devapp.uzchat.utils.PrefUtils

class MessageAdapter(val items: List<Message>) : RecyclerView.Adapter<MessageAdapter.Vh>() {
    inner class Vh(val binding: MessageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            MessageItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = items[position]
        holder.binding.lyOwn.visibility =
            if (item.user.id == PrefUtils.getUser().id) View.VISIBLE else View.GONE
        holder.binding.lyPartner.visibility =
            if (item.user.id != PrefUtils.getUser().id) View.VISIBLE else View.GONE

        holder.binding.tvMessage.text = item.message
        holder.binding.tvPartnerMessage.text = item.message
        holder.binding.tvTime.text = item.time
        holder.binding.tvPartnerTime.text = item.time
    }
}