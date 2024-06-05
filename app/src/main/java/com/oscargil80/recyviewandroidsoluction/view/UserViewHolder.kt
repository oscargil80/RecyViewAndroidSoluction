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
        onClickDelete: (Int, v: View) -> Unit,
        onClickExpan: (Int, bol: Boolean, v: View)-> Unit

    ) {
        binding.mTitulo.text = UserData.userId.toString() + " - " + UserData.userName
        binding.mSubTitulo.text = UserData.userMb
        binding.langDesc.text = UserData.desc

        val isExpandable: Boolean = UserData.isExpandable
        binding.langDesc.visibility = if (isExpandable) View.VISIBLE else View.GONE

        binding.consLayout.setOnClickListener {
           UserData.isExpandable = !UserData.isExpandable
           onClickExpan(bindingAdapterPosition, UserData.isExpandable, v)
        }

        binding.mMenus.setOnClickListener {
            onClickDelete(bindingAdapterPosition, binding.mMenus)
            // onClickDelete(getBindingAdapterPosition(), binding.mMenus)

        }
        itemView.setOnClickListener {
            onClickListener(UserData)
        }
    }
}
