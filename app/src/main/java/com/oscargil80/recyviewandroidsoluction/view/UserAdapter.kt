package com.oscargil80.recyviewandroidsoluction.view
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oscargil80.recyviewandroidsoluction.R
import com.oscargil80.recyviewandroidsoluction.model.UserData

class UserAdapter(
    private val userList: List<UserData>,
    private val  onClickListener:(UserData)->Unit,
    private val  onClickDelete: (Int, View) -> Unit)

:  RecyclerView.Adapter<UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userList[position]
        holder.render(item, onClickListener, onClickDelete)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
//private val  onClickDelete: (Int,) -> Unit)