package com.haotsang.wanandroid.ui.architecture

import android.view.View
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.appbar.AppBarLayout
import com.haotsang.wanandroid.base.BaseVmFragment
import com.haotsang.wanandroid.common.adapter.SimpleFragmentPagerAdapter
import com.haotsang.wanandroid.databinding.ArchitectureFragmentBinding
import com.haotsang.wanandroid.model.bean.Category
import com.haotsang.wanandroid.ui.architecture.children.TreeChildFragment
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.utils.ext.setOnItemClickListener

class ArchitectureFragment :
    BaseVmFragment<ArchitectureFragmentBinding, ArchitectureViewModel>(ArchitectureFragmentBinding::inflate) {


    private val titles = mutableListOf<String>()
    private val fragments = mutableListOf<TreeChildFragment>()
//    private var categoryFragment: SystemCategoryFragment? = null

    override fun viewModelClass(): Class<ArchitectureViewModel> = ArchitectureViewModel::class.java

    override fun observe() {
        mViewModel.run {
            getCategoriesObserve().observe(viewLifecycleOwner) { categories ->
                mBinding?.tabLayout?.visibility = View.VISIBLE
                mBinding?.viewPager?.visibility = View.VISIBLE
                val newCategories = categories?.filter {
                    it.children.isNotEmpty()
                }!!.toMutableList()
                setup(newCategories)
//                categoryFragment = SystemCategoryFragment.newInstance(newCategories.toArrayList())
            }
            loadingStatus.observe(viewLifecycleOwner) {
                mBinding?.progressBar?.isVisible = it
            }
            reloadStatus.observe(viewLifecycleOwner) {
                mBinding?.reloadView?.root?.isVisible = it
            }
        }
    }

    override fun initView() {
        mBinding?.reloadView?.btnReload?.setOnClickListener {
            mViewModel.getArticleCategory()
        }
        mBinding?.toolbar?.apply {
            menu.add("filter").setOnMenuItemClickListener {
                true
            }
        }

    }

    override fun initData() {
        mViewModel.getArticleCategory()
    }

    override fun setListener() {

    }

    private fun setup(categories: MutableList<Category>) {
        titles.clear()
        fragments.clear()
        categories.forEach {
            titles.add(it.name)
            fragments.add(TreeChildFragment.newInstance(it.children))
        }
        mBinding?.viewPager?.adapter =
            SimpleFragmentPagerAdapter(
                childFragmentManager,
                fragments,
                titles
            )
//        mBinding?.viewPager?.offscreenPageLimit = titles.size
        mBinding?.tabLayout?.setupWithViewPager(mBinding?.viewPager)
    }

    //for filter dialog use
    fun getCurrentChecked(): Pair<Int, Int> {
        if (fragments.isEmpty() || mBinding?.viewPager == null) return 0 to 0
        val first = mBinding?.viewPager?.currentItem!!
        val second = fragments[mBinding?.viewPager?.currentItem!!].checkedPosition
        return first to second
    }
    fun check(position: Pair<Int, Int>) {
        if (fragments.isEmpty() || mBinding?.viewPager == null) return
        mBinding?.viewPager?.currentItem = position.first
        fragments[position.first].check(position.second)
    }
}