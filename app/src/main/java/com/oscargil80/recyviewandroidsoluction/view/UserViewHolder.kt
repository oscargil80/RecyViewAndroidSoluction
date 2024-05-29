package com.oscargil80.recyviewandroidsoluction.view

import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.oscargil80.recyviewandroidsoluction.R
import com.oscargil80.recyviewandroidsoluction.databinding.ListItemBinding
import com.oscargil80.recyviewandroidsoluction.model.UserData

class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {

    val binding = ListItemBinding.bind(v)
    fun render(

        UserData: UserData,
        onClickListener: (UserData) -> Unit,
        onClickDelete: (Int, v: View) -> Unit
    ) {
        binding.mTitulo.text = UserData.userName
        binding.mSubTitulo.text = UserData.userMb
        binding.mMenus.setOnClickListener {
            onClickDelete(adapterPosition, binding.mMenus)

        }
        itemView.setOnClickListener {
            onClickListener(UserData)
            Log.e("Render", UserData.toString())
        }
    }
}
