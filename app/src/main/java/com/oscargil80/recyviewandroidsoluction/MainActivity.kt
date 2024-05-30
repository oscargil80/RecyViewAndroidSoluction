package com.oscargil80.recyviewandroidsoluction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oscargil80.recyviewandroidsoluction.databinding.ActivityMainBinding
import com.oscargil80.recyviewandroidsoluction.databinding.AddItemBinding
import com.oscargil80.recyviewandroidsoluction.model.UserData
import com.oscargil80.recyviewandroidsoluction.view.UserAdapter

class MainActivity : AppCompatActivity() {
    private var userMutableList: MutableList<UserData> =    SuperDataProvider.UserDataList.toMutableList()
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private  var llmanager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etFiltro.addTextChangedListener { userfiltro ->
        val userdatafiltrado =    userMutableList.filter { userName -> userName.userName.lowercase().contains(userfiltro.toString().lowercase()) }
            userAdapter.updateUserData(userdatafiltrado)
        }

        userAdapter = UserAdapter(
            userList = userMutableList,
            onClickListener = { userdata -> onItemSelected(userdata) },
            onClickDelete = { position, v -> onChangeItem(position, v) }
        )
        binding.mRecycler.layoutManager = llmanager
        binding.mRecycler.adapter = userAdapter
        binding.addingBtn.setOnClickListener { addInfo() }
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
                    var Nombre = userMutableList.elementAt(position).userName
                    var Numero = userMutableList.elementAt(position).userMb
                    name.setText(Nombre)
                    number.setText(Numero)
                    AlertDialog.Builder(this)
                        .setView(v)
                        .setPositiveButton("Ok") { dialog, _ ->
                            Nombre = name.text.toString()
                            Numero = number.text.toString()
                            userMutableList.set(position, UserData(Nombre, Numero))
                            userAdapter.notifyItemChanged(position)
                            Toast.makeText(this, "Informacion fue  Cambiado", Toast.LENGTH_LONG)
                                .show()
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
                    var nom = userMutableList[position].userName
                    AlertDialog.Builder(this)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_warning)
                        .setMessage("Estas Seguro que Deseas Eliminar a $nom?")
                        .setPositiveButton("Yes") { dialog, _ ->
                            userMutableList.removeAt(position)
                            userAdapter.notifyItemRemoved(position)
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

        val userName = binding.userName//v.findViewById<EditText>(R.id.userName)
        val userNo = binding.userNo//v.findViewById<EditText>(R.id.userNo)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { dialog, _ ->
            if(userName.text.toString().isNotBlank() || userNo.text.toString().isNotBlank())
            {
                val names = userName.text.toString()
                val number = userNo.text.toString()
                val posadd = 2//userMutableList.size-1
                userMutableList.add(posadd, UserData(names, number))
                userAdapter.notifyItemInserted(posadd)
                llmanager.scrollToPositionWithOffset(posadd,20 )
                dialog.dismiss()
            }
            else
                Toast.makeText(this, "No se agrego ningun elemento por estar vacio  ", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        addDialog.create()
        addDialog.show()
    }


}