package com.oscargil80.recyviewandroidsoluction

import androidx.recyclerview.widget.DiffUtil
import com.oscargil80.recyviewandroidsoluction.model.UserData

class userDataDiffUtil(private val oldList: List<UserData>,
                                      private val newList:List<UserData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].userId == newList[newItemPosition].userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == newList[newItemPosition]
    }


}