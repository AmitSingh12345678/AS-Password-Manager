package com.example.aspasswordmanager.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.aspasswordmanager.MainActivity
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import smartadapter.SmartRecyclerAdapter
import smartadapter.stickyheader.StickyHeaderItemDecorationExtension
import smartadapter.viewevent.listener.OnClickEventListener

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private lateinit var passwordViewModel: PasswordViewModel
    private lateinit var adapter: SmartRecyclerAdapter
    private lateinit var contextMainActivity: MainActivity
//    private lateinit var recycle_view:RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

    return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cntxt:Context=requireActivity().applicationContext!!

        val add_btn: FloatingActionButton=view.findViewById(R.id.add)
        val recycle_view:RecyclerView=view.findViewById(R.id.recyclerview)

        passwordViewModel =ViewModelProvider(this,PasswordViewModelFactory(contextMainActivity)).get(PasswordViewModel::class.java)

        add_btn.setOnClickListener {
            val intent: Intent = Intent(cntxt, AddItemActivity::class.java)
            startActivity(intent)
        }
        //                .items(listOf(header,item,item,"B",item,item,item,"C",item,item,item,item))
        adapter=SmartRecyclerAdapter.empty()
                .map(String::class,HeaderViewHolder::class)
                .map(PasswordEntity::class,ItemViewHolder::class)
                .add(StickyHeaderItemDecorationExtension(
                        headerItemType = String::class
                )).into<SmartRecyclerAdapter>(recycle_view)

        passwordViewModel.allWords.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "onViewCreated: Observer called with data: " + it)
            var items: MutableList<Any> = MutableList<Any>(0) { index -> index }
            var i:Int=0
            while(i<it.size){
                val title = it[i].title
                if (title.isNotEmpty()) {
                    var header: String = title[0].toString()
                    items.add(header)
                    while ((i < it.size) && (it[i].title[0] == header[0])) {
                        Log.d(TAG, "onViewCreated: $i")
                        items.add(it[i])
                        i++
                    }
                    i--
                }
                i++
            }
            adapter.clear()
            adapter.addItems(items)
        })

        adapter.add(OnClickEventListener {
            if(it.viewHolder is ItemViewHolder) {
                val item: PasswordEntity=(it.viewHolder as ItemViewHolder).item
                Log.d(TAG, "onViewCreated: " + item.toString())
                val intent:Intent=Intent(cntxt,ShowItemActivity::class.java)
                intent.putExtra("ITEM_INFO",item)
                startActivity(intent)
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextMainActivity= context as MainActivity
    }
}