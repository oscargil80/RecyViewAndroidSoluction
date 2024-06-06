package com.oscargil80.recyviewandroidsoluction.view
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oscargil80.recyviewandroidsoluction.R
import com.oscargil80.recyviewandroidsoluction.model.UserData
import com.oscargil80.recyviewandroidsoluction.userDataDiffUtil

class UserAdapter(
    private var userList: List<UserData>,
    private val  onClickListener:(UserData)->Unit,
    private val  onClickDelete: (Int, View) -> Unit,
    private var  onClickExpan: (Int, Boolean, View ) -> Unit
)
:  RecyclerView.Adapter<UserViewHolder>() {

    fun updateList(newList:List<UserData>){
        val userDataDiff = userDataDiffUtil(userList, newList )
        val result : DiffUtil.DiffResult = DiffUtil.calculateDiff(userDataDiff)
        userList= newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userList[position]
        holder.render(item, onClickListener, onClickDelete, onClickExpan)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

     fun updateUserData(userList: List<UserData>){
         this.userList = userList
         notifyDataSetChanged()
     }



}
//private val  onClickDelete: (Int,) -> Unit)