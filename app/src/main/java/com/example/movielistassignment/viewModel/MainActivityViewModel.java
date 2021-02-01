package com.example.movielistassignment.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movielistassignment.model.APIResponse;
import com.example.movielistassignment.model.Movie;
import com.example.movielistassignment.repository.FetchData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private final FetchData fetchData;
    private final CompositeDisposable disposable;

    public MainActivityViewModel() {
        fetchData = FetchData.getInstance();
        disposable = new CompositeDisposable();
        getData();
    }

    private void getData() {
        disposable.add(fetchData.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<APIResponse>() {
                    @Override
                    public void onSuccess(@NonNull APIResponse apiResponse) {
                        Log.d("TAG", "onSuccess of getData of MainActivityViewModel");
                        Log.d("TAG", "apiResponse length is :" + apiResponse.getMovieList().size());
                        mutableLiveData.setValue(apiResponse.getMovieList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError in getData of MainActivityViewModel");
                    }
                }));
    }

    public LiveData<List<Movie>> getListMovie() {
        return mutableLiveData;
    }
}
