package com.example.notesdemo.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesdemo.model.notes.Note
import com.example.notesdemo.network.ApiInstance
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddNoteViewModel: ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val mutableLiveData = MutableLiveData<Note>()
    val TAG = "noteViewmodel"

    fun setNotes(jsonObject: JsonObject): MutableLiveData<Note> {
        compositeDisposable.addAll(
            ApiInstance.apiInterface.setNote(jsonObject)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response)} , {failure -> onFailure(failure)}))

        return mutableLiveData as MutableLiveData<Note>
    }

    private fun onFailure(failure: Any) {
        Log.e(TAG, "onFailure: $failure", )
    }

    private fun onResponse(response: Note) {
        Log.e(TAG, "onResponse: $response", )
        mutableLiveData.value = response
    }

}