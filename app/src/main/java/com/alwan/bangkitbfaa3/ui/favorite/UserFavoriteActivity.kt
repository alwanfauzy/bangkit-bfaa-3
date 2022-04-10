package com.alwan.bangkitbfaa3.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwan.bangkitbfaa3.R
import com.alwan.bangkitbfaa3.data.model.User
import com.alwan.bangkitbfaa3.databinding.ActivityUserFavoriteBinding
import com.alwan.bangkitbfaa3.ui.detail.UserDetailActivity
import com.alwan.bangkitbfaa3.ui.main.UserAdapter
import com.alwan.bangkitbfaa3.util.toArrayList

class UserFavoriteActivity : AppCompatActivity(), UserAdapter.UserCallback {
    private var _binding: ActivityUserFavoriteBinding? = null
    private val binding get() = _binding!!
    private val userAdapter = UserAdapter(this)
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.favorite_user)

        showLoading(true)
        setupRecyclerView()
        setupMainViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupMainViewModel() {
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        favoriteViewModel.getFavoriteUsers()!!.observe(this) { userItems ->
            userAdapter.setData(userItems.toArrayList())
            if (userItems.count() != 0) {
                showEmpty(false)
            } else {
                showEmpty(true)
            }
            showLoading(false)
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@UserFavoriteActivity)
            rvFavorite.adapter = userAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFavorite.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.imgEmptyFavorite.visibility = View.VISIBLE
            binding.tvEmptyFavorite.visibility = View.VISIBLE
        } else {
            binding.imgEmptyFavorite.visibility = View.GONE
            binding.tvEmptyFavorite.visibility = View.GONE
        }
    }

    override fun onUserClick(user: User) {
        val userDetailIntent = Intent(this@UserFavoriteActivity, UserDetailActivity::class.java)
        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(userDetailIntent)
    }
}