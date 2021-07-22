package com.example.aspasswordmanager.ui.home.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.aspasswordmanager.MainActivity
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.example.aspasswordmanager.ui.home.viewholder.HeaderViewHolder
import com.example.aspasswordmanager.ui.home.viewholder.ItemViewHolder
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModel
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import smartadapter.SmartRecyclerAdapter
import smartadapter.filter.FilterExtension
import smartadapter.get
import smartadapter.stickyheader.StickyHeaderItemDecorationExtension
import smartadapter.viewevent.listener.OnClickEventListener


class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private lateinit var passwordViewModel: PasswordViewModel
    private lateinit var adapter: SmartRecyclerAdapter
    private lateinit var contextMainActivity: MainActivity
    private lateinit var addBtn: FloatingActionButton
    private lateinit var recycleView: RecyclerView
    private lateinit var items: MutableList<Any>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root=inflater.inflate(R.layout.fragment_home, container, false)
        val color = ContextCompat.getColor(requireContext(),R.color.primary)
        val actionBar= (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.setBackgroundDrawable(ColorDrawable(color))
        requireActivity().window.statusBarColor=color
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn = view.findViewById(R.id.add)
        recycleView = view.findViewById(R.id.recyclerview)

        items= MutableList(0){}
        
        //                .items(listOf(header,item,item,"B",item,item,item,"C",item,item,item,item))
        adapter=SmartRecyclerAdapter.empty()
                .map(String::class, HeaderViewHolder::class)
                .map(PasswordEntity::class, ItemViewHolder::class)
                .add(
                        StickyHeaderItemDecorationExtension(
                                headerItemType = String::class
                        )
                ).add(
                        FilterExtension(
                                filterPredicate = { item, constraint ->

                                    when (item) {
                                        is PasswordEntity -> {
                                            val title=item.title
                                            if(title.startsWith(constraint,true))  true
                                            else false
                                        }
                                        is String -> {
                                            false
                                        }
                                        else -> true
                                    }
                                }
                        ) {
                            // for progress bar
                        }
                ).into(recycleView)
        Log.d(TAG, "onViewCreated: Adapter is intialized")

        passwordViewModel =ViewModelProvider(this, PasswordViewModelFactory(contextMainActivity)).get(
                PasswordViewModel::class.java
        )

        addBtn.setOnClickListener(listener)
        passwordViewModel.allWords.observe(viewLifecycleOwner, {
            Log.i(TAG, "onViewCreated: Observer called with data: $it")
            items = getModifiedList(it)

            adapter.setItems(items)
        })

        adapter.add(OnClickEventListener {
            if (it.viewHolder is ItemViewHolder) {
                val item: PasswordEntity = (it.viewHolder as ItemViewHolder).item
                Log.d(TAG, "onViewCreated: $item")
                val intent = Intent(context, ShowItemActivity::class.java)
                intent.putExtra("ITEM_INFO", item)
                startActivity(intent)
            }
        })
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && addBtn.visibility == View.VISIBLE) {
                    addBtn.visibility = View.GONE
                }
                if (dy < 0 && addBtn.visibility == View.GONE) {
                    addBtn.visibility = View.VISIBLE
                }
            }
        })


        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.app_bar_menu, menu)

        val searchItem=menu.findItem(R.id.search_view)
        val searchView=searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("queryText", query)
                if(query.isNotBlank()){
                    filter(query)
//                    searchPasswords(query)
                }else{
                    adapter.setItems(items)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.e("queryText", newText)
                 if(newText.isNotEmpty()){
                     filter(newText)
//                     searchPasswords(newText)
                 }else{
                     adapter.setItems(items)
                 }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)

    }

    fun filter(query: String?) {
        val filterExtension: FilterExtension = adapter.get()

        filterExtension.filter(lifecycleScope, query, autoSetNewItems = true)
    }
//    private fun searchPasswords(query: String) {
//      passwordViewModel.searchPasswords(query).observe(viewLifecycleOwner, {
//          Log.i(TAG, "onViewCreated: Observer called with data: $it")
//          val items: MutableList<Any> = getModifiedList(it)
//          adapter.clear()
//          adapter.addItems(items)
//      })
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.search_view -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // getting Main Activity context for viewmodel scope
    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextMainActivity= context as MainActivity
    }
   fun getModifiedList(it: List<PasswordEntity>):MutableList<Any>{
       val items: MutableList<Any> = MutableList(0) { index -> index }
       var i = 0
       while (i < it.size) {
           val title = it[i].title
           if (title.isNotEmpty()) {
               val header: String = title[0].toString()
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
       return items
   }

    val listener=View.OnClickListener { view ->
        when(view.id){
            R.id.add -> {
                val intent: Intent = Intent(context, AddItemActivity::class.java)
                startActivity(intent)
            }

        }
    }

}