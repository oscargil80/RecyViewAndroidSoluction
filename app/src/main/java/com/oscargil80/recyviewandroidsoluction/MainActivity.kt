package com.oscargil80.recyviewandroidsoluction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oscargil80.recyviewandroidsoluction.databinding.ActivityMainBinding
import com.oscargil80.recyviewandroidsoluction.databinding.AddItemBinding
import com.oscargil80.recyviewandroidsoluction.model.UserData
import com.oscargil80.recyviewandroidsoluction.view.UserAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var userList= SuperDataProvider.UserDataList
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private var llmanager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarFitro()
        iniciarRecycreView()
        configurarSwipe()
        binding.addingBtn.setOnClickListener { addInfo() }
    }

    private fun iniciarRecycreView() {
        userAdapter = UserAdapter(
            userList = userList,
            onClickListener = { userdata -> onItemSelected(userdata) },
            onClickDelete = { position, v -> onChangeItem(position, v) }
        )
        configurarSwipeGesture()
        binding.mRecycler.layoutManager = llmanager
        binding.mRecycler.adapter = userAdapter
    }

    private fun configurarSwipeGesture() {
        val swipegesture = object : SwipeGesture(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.bindingAdapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        userList = userList.minus( userList.elementAt(pos) )
                        userAdapter.updateList(userList)
                    }
                    ItemTouchHelper.RIGHT -> {
                        if(pos>0) {
                            Collections.swap(userList, pos, pos - 1)
                        }
                        userAdapter.updateUserData(userList)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipegesture)
        touchHelper.attachToRecyclerView(binding.mRecycler)
    }

    private fun configurarSwipe() {
        binding.swipe.setColorSchemeResources(R.color.red, R.color.naranja, R.color.azul)
        binding.swipe.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.verde
            )
        )
        binding.swipe.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipe.isRefreshing = false
            }, 2000)
            userAdapter.updateList(userList.sortedBy { it.userId })
        }
    }

    private fun configurarFitro() {
        binding.etFiltro.addTextChangedListener { userfiltro ->
            val userdatafiltrado = userList.filter { userName ->
                userName.userName.lowercase().contains(userfiltro.toString().lowercase())
            }
            userAdapter.updateUserData(userdatafiltrado)
        }
    }

    private fun onChangeItem(position: Int, v: View) {
        val popupMenus = PopupMenu(this, v)
        popupMenus.inflate(R.menu.show_menu)

        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editText -> {
                    val v = LayoutInflater.from(this).inflate(R.layout.add_item, null)
                    var binding = AddItemBinding.bind(v)
                    val name = binding.userName//v.findViewById<EditText>(R.id.userName)
                    val number = binding.userNo//v.findViewById<EditText>(R.id.userNo)
                    var po = userList.elementAt(position).userId
                    var Nombre = userList.elementAt(position).userName
                    var Numero = userList.elementAt(position).userMb

                    name.setText(Nombre)
                    number.setText(Numero)

                    AlertDialog.Builder(this)
                        .setView(v)
                        .setPositiveButton("Ok") { dialog, _ ->
                            Nombre = name.text.toString()
                            Numero = number.text.toString()
                            userList = userList.minus( userList.elementAt(position) )
                            userList =  userList.plus(UserData(po, Nombre, Numero))
                            userAdapter.updateList(userList.sortedBy { it.userId })
                            Toast.makeText(this, "Informacion fue  Cambiado", Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                    true
                }
                R.id.deleteText -> {
                    var nom = userList[position].userName
                     AlertDialog.Builder(this)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_warning)
                        .setMessage("Estas Seguro que Deseas Eliminar a $nom?")
                        .setPositiveButton("Yes") { dialog, _ ->
                            userList = userList.minus( userList.elementAt(position) )
                            userAdapter.updateList(userList)
                            Toast.makeText(this, "Elemento Borrado", Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }
                        .setNegativeButton("NO") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                    true
                }
                else -> true
            }
        }
        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
    }

    private fun onItemSelected(userData: UserData) {
        Toast.makeText(this, userData.userName, Toast.LENGTH_SHORT).show()
    }

    private fun addInfo() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item, null)
        var binding = AddItemBinding.bind(v)
        val userName = binding.userName
        val userNo = binding.userNo
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { dialog, _ ->
            if (userName.text.toString().isNotBlank() || userNo.text.toString().isNotBlank()) {
                val names = userName.text.toString()
                val number = userNo.text.toString()
                var po = 1
                if(userList.size>0)
                po  =  userList.sortedBy { it.userId }.elementAt(userList.lastIndex).userId+1
                userList =  userList.plus(UserData(po, names, number))
                userAdapter.updateList(userList)
                llmanager.scrollToPositionWithOffset(userList.size, 20)
                dialog.dismiss()
            } else {
                Toast.makeText(
                    this,
                    "No se agrego ningun elemento por estar vacio  ",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }


}