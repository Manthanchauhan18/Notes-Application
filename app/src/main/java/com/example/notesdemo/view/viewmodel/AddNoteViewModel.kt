package com.example.notesdemo.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesdemo.model.NotesApi
import com.example.notesdemo.network.ApiInstance
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddNoteViewModel: ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val mutableLiveData = MutableLiveData<NotesApi>()
    val TAG = "noteViewmodel"

    fun setNotes(androidId: String, jsonObject: JsonObject): MutableLiveData<NotesApi> {
        compositeDisposable.addAll(ApiInstance.apiInterface.setNote(androidId, jsonObject)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response)} , {failure -> onFailure(failure)}))

        return mutableLiveData as MutableLiveData<NotesApi>
    }

    private fun onFailure(failure: Any) {
        Log.e(TAG, "onFailure: $failure", )
    }

    private fun onResponse(response: NotesApi) {
        Log.e(TAG, "onResponse: $response", )
        mutableLiveData.value = response
    }

}