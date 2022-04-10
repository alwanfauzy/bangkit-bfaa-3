package com.alwan.bangkitbfaa3.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alwan.bangkitbfaa3.R
import com.alwan.bangkitbfaa3.data.model.User
import com.alwan.bangkitbfaa3.data.model.UserDetail
import com.alwan.bangkitbfaa3.databinding.ActivityUserDetailBinding
import com.alwan.bangkitbfaa3.ui.detail.follow.FollowPagerAdapter
import com.alwan.bangkitbfaa3.ui.favorite.FavoriteViewModel
import com.alwan.bangkitbfaa3.util.loadImage
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var extraUser: User
    private var statusFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.user_detail)
        supportActionBar?.elevation = 0f

        extraUser = intent.getParcelableExtra<User>(EXTRA_USER) as User
        extraUser.login?.let { setupViewModel(it) }
        setupViewPager()
        setStatusFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)

        val item: MenuItem = menu!!.findItem(R.id.menu_favorite)
        if (statusFavorite) {
            item.setIcon(R.drawable.ic_favorite)
        } else {
            item.setIcon(R.drawable.ic_favorite_border)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                if (statusFavorite) {
                    item.setIcon(R.drawable.ic_favorite_border)
                    statusFavorite = false
                    extraUser.id?.let { favoriteViewModel.removeFavoriteUser(it) }
                    showSnackbar(getString(R.string.remove_favorite))
                } else {
                    item.setIcon(R.drawable.ic_favorite)
                    statusFavorite = true
                    favoriteViewModel.addToFavorite(extraUser)
                    showSnackbar(getString(R.string.add_favorite))
                }
                true
            }
            else -> true
        }
    }

    private fun populateDetail(user: UserDetail) {
        with(binding) {
            imgDetailAvatar.loadImage(user.avatarUrl)
            tvDetailName.text = user.name ?: "-"
            tvDetailUsername.text = user.login ?: "-"
            tvDetailFollower.text = getString(R.string.detail_followers, user.followers)
            tvDetailFollowing.text = getString(R.string.detail_following, user.following)
            tvDetailCompany.text = user.company ?: "-"
            tvDetailLocation.text = user.location ?: "-"
            tvDetailRepository.text = user.publicRepos ?: "-"
        }
    }

    private fun setupViewModel(username: String) {
        showLoading(true)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        detailViewModel.setUserDetail(username)

        detailViewModel.userDetail.observe(this) {
            populateDetail(it)
            showLoading(false)
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = FollowPagerAdapter(this)
        val viewPager = binding.viewPagerDetail
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setStatusFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = extraUser.id?.let { favoriteViewModel.isFavoriteUser(it) }
            statusFavorite = count!! > 0
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.app_follower,
            R.string.app_following
        )
    }
}