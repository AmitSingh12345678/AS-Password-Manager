package com.example.aspasswordmanager.ui.home.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.aspasswordmanager.R
import com.example.aspasswordmanager.ui.home.database.PasswordEntity
import com.example.aspasswordmanager.ui.home.viewholder.HeaderViewHolder
import com.example.aspasswordmanager.ui.home.viewholder.ItemViewHolder
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModel
import com.example.aspasswordmanager.ui.home.viewmodel.PasswordViewModelFactory
import com.example.aspasswordmanager.utility.Constants
import com.example.aspasswordmanager.utility.Encryption
import com.google.android.material.floatingactionbutton.FloatingActionButton
import smartadapter.SmartRecyclerAdapter
import smartadapter.diffutil.DiffUtilExtension
import smartadapter.diffutil.SimpleDiffUtilExtension
import smartadapter.filter.FilterExtension
import smartadapter.get
import smartadapter.stickyheader.StickyHeaderItemDecorationExtension
import smartadapter.viewevent.listener.OnClickEventListener
import java.util.*
import kotlin.properties.Delegates
import kotlin.streams.asSequence


class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private lateinit var passwordViewModel: PasswordViewModel
    private lateinit var adapter: SmartRecyclerAdapter
    private lateinit var addBtn: FloatingActionButton
    private lateinit var recycleView: RecyclerView
    private lateinit var items: MutableList<Any>
    private lateinit var infoTextView: TextView
    private lateinit var arrowAnimation: LottieAnimationView
    private var KEY by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val color = ContextCompat.getColor(requireContext(), R.color.primary)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.setBackgroundDrawable(ColorDrawable(color))
        requireActivity().window.statusBarColor = color



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn = view.findViewById(R.id.add)
        recycleView = view.findViewById(R.id.recyclerview)
        infoTextView = view.findViewById(R.id.info_msg)
        arrowAnimation = view.findViewById(R.id.lottieAnimationView)
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            Constants.PASSWORD_STORE,
            Context.MODE_PRIVATE
        )
        KEY = sharedPref.getInt(Constants.USER_KEY, Constants.DEFAULT_KEY)

        items = MutableList(0) {}

        adapter = SmartRecyclerAdapter.empty()
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
                                val title = Encryption.decrypt(item.title, KEY)
                                title.startsWith(constraint, true)
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
            ).add(SimpleDiffUtilExtension(predicate))
            .into(recycleView)
        Log.d(TAG, "onViewCreated: Adapter is intialized")
        passwordViewModel =
            ViewModelProvider(this, PasswordViewModelFactory(requireActivity())).get(
                PasswordViewModel::class.java
            )
//    For generating random data
//        for(i in 0..100 ){
//            val title=Encryption.encrypt(getRandomString(),KEY)
//            val item=PasswordEntity(title,"","","","")
//            passwordViewModel.insert(item)
//        }

        addBtn.setOnClickListener(listener)
        passwordViewModel.allWords.observe(viewLifecycleOwner, { it ->
            Log.i(TAG, "onViewCreated: Observer called with data: $it ")
            for (i in it) {
                Log.i(TAG, "onViewCreated with item id: ${i.id}")
            }
            // Sorting list as in database, data is store in ecrypted form. Hence, we have to sort it here
            val sortedList = it.sortedWith(compareBy {
                Encryption.decrypt(it.title, KEY).toUpperCase(
                    Locale.ROOT
                )
            })
            // For adding header Char in items list
            items = getModifiedList(sortedList)

// To animate items of recycler view
//            val animationController: LayoutAnimationController =
//                AnimationUtils.loadLayoutAnimation(context, R.anim.recyclerview_animation)
//            recycleView.layoutAnimation = animationController
            adapter.setItems(items)
            if (adapter.itemCount == 0) {
                recycleView.visibility = View.GONE
                infoTextView.visibility = View.VISIBLE
                arrowAnimation.visibility = View.VISIBLE
            } else {
                recycleView.visibility = View.VISIBLE
                infoTextView.visibility = View.GONE
                arrowAnimation.visibility = View.GONE
            }
        })

        adapter.add(OnClickEventListener {
            if (it.viewHolder is ItemViewHolder) {
                val item: PasswordEntity = (it.viewHolder as ItemViewHolder).item
                Log.d(TAG, "onViewCreated : $item")
                val intent = Intent(context, ShowItemActivity::class.java)
                intent.putExtra(Constants.ITEM_INFO, item)
//                val avatar: ImageView = it.viewHolder.itemView.findViewById(R.id.avatar_image)
//                val title: TextView = it.viewHolder.itemView.findViewById(R.id.title)
//
//                val imageViewPair = Pair.create<View, String>(avatar, "avatar_transition_name")
//                val textViewPair = Pair.create<View, String>(title, "title_transition_name")
//                val options: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        requireActivity(), imageViewPair, textViewPair
//                    )
                val optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity())
                startActivity(intent,optionsCompat.toBundle())
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

    private val predicate = object : DiffUtilExtension.DiffPredicate<PasswordEntity> {
        override fun areItemsTheSame(oldItem: PasswordEntity, newItem: PasswordEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PasswordEntity, newItem: PasswordEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.app_bar_menu, menu)

        val searchItem = menu.findItem(R.id.search_view)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("queryText", query)
                if (query.isNotBlank()) {
                    filter(query)
                } else {
                    adapter.setItems(items)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.e("queryText", newText)
                if (newText.isNotEmpty()) {
                    filter(newText)
                } else {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when (item.itemId) {
            R.id.search_view -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun getModifiedList(it: List<PasswordEntity>): MutableList<Any> {
        val items: MutableList<Any> = MutableList(0) { index -> index }
        var i = 0
        while (i < it.size) {
            val title = Encryption.decrypt(it[i].title, KEY)
            if (title.isNotEmpty()) {
                val header: String = title[0].toString().toUpperCase(Locale.ROOT)
                items.add(header)
                while ((i < it.size) && (Encryption.decrypt(it[i].title, KEY)[0].equals(
                        header[0],
                        true
                    ))
                ) {
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

    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.add -> {
                val intent = Intent(context, AddItemActivity::class.java)
                val optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity())

                intent.putExtra(Constants.MSG, Constants.FOR_SHOW)
                startActivity(intent, optionsCompat.toBundle())
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getRandomString(): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return Random().ints(8, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
    }
}