package com.haotsang.wanandroid.ui.settngs

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import androidx.recyclerview.widget.RecyclerView
import com.haotsang.wanandroid.App
import com.haotsang.wanandroid.R
import com.haotsang.wanandroid.model.api.RetrofitClient
import com.haotsang.wanandroid.model.store.UserInfoStore
import com.haotsang.wanandroid.ui.main.MainActivity
import com.haotsang.wanandroid.ui.main.MainViewModel
import com.haotsang.wanandroid.utils.DataStoreUtils
import com.haotsang.wanandroid.utils.FileHelper

class SettingsPreferenceFragment : PreferenceFragmentCompat() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val selectPictureLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            FileHelper.takeReadPermissions(requireContext(), uri)

            val path = uri.toString()
            DataStoreUtils.putData(DataStoreUtils.ds_home_bg.first, path)
            mainViewModel.homeBgObserver.postValue(path)
        }
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<Preference>("set")?.apply {
            setOnPreferenceClickListener {
                selectPictureLauncher.launch(arrayOf("image/*"))
                true
            }
        }

        findPreference<Preference>("reset")?.apply {
            setOnPreferenceClickListener {
                DataStoreUtils.putData(DataStoreUtils.ds_home_bg.first, "")
                mainViewModel.homeBgObserver.postValue("")
                true
            }
        }

        findPreference<Preference>("logout")?.apply {
            setOnPreferenceClickListener {
                UserInfoStore.clearUserInfo()
                RetrofitClient.clearCookie()

                mainViewModel.userStatusInfo.postValue(null)
                true
            }
        }
    }

    override fun onCreateAdapter(preferenceScreen: PreferenceScreen): RecyclerView.Adapter<*> {
        return super.onCreateAdapter(preferenceScreen)
    }

}