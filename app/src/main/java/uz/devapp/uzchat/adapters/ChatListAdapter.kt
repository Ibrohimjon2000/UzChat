package uz.devapp.uzchat.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.databinding.ChatItemLayoutBinding
import uz.devapp.uzchat.screen.message.MessageActivity
import uz.devapp.uzchat.utils.Constants
import uz.devapp.uzchat.utils.loadImage

class ChatListAdapter(val items: List<ChatModel>) : RecyclerView.Adapter<ChatListAdapter.Vh>() {

    inner class Vh(val binding: ChatItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ChatItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val chatModel = items[position]

        holder.binding.tvFullname.text=chatModel.userModel.fullname
        holder.binding.tvTime.text=chatModel.time

        holder.itemView.setOnClickListener {
            val intent=Intent(it.context,MessageActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,chatModel)
            it.context.startActivity(intent)
        }
    }
}