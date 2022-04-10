package com.alwan.bangkitbfaa3.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.bangkitbfaa3.R
import com.alwan.bangkitbfaa3.data.model.User
import com.alwan.bangkitbfaa3.databinding.ActivityMainBinding
import com.alwan.bangkitbfaa3.ui.detail.UserDetailActivity
import com.alwan.bangkitbfaa3.ui.favorite.UserFavoriteActivity
import com.alwan.bangkitbfaa3.ui.setting.SettingsActivity

class MainActivity : AppCompatActivity(), UserAdapter.UserCallback {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val userAdapter = UserAdapter(this)
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchView: SearchView
    private var extraSearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMainViewModel()
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showNotFound(state: Boolean) {
        binding.notFoundMain.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarMain.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showStartSearch(state: Boolean) {
        binding.imgStart.visibility = if (state) View.VISIBLE else View.GONE
        binding.tvStart.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun statusNotFound() = binding.notFoundMain.isVisible

    private fun setupRecyclerView() {
        with(binding) {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = userAdapter
        }
    }

    private fun setupMainViewModel() {
        showStartSearch(true)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.users.observe(this) { userItems ->
            userAdapter.setData(userItems)
            if (userItems.isNotEmpty() || !statusNotFound()) {
                showStartSearch(false)
            }

            if (userItems.count() != 0) {
                showNotFound(false)
            } else {
                showNotFound(true)
            }
            showLoading(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.maxWidth = Integer.MAX_VALUE

        if (extraSearch != null && extraSearch != "") {
            searchView.run {
                onActionViewExpanded()
                requestFocusFromTouch()
                setQuery(extraSearch, false)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showStartSearch(false)
                showNotFound(false)
                showLoading(true)
                mainViewModel.searchUsers(query)
                closeKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this@MainActivity, UserFavoriteActivity::class.java))
                true
            }
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onUserClick(user: User) {
        val userDetailIntent = Intent(this, UserDetailActivity::class.java)
        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(userDetailIntent)
    }
}