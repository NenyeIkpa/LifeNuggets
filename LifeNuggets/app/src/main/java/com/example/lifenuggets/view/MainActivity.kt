package com.example.lifenuggets.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifenuggets.R
import com.example.lifenuggets.utils.ApiUserClientService
import com.example.lifenuggets.model.adapter.OnClickUser
import com.example.lifenuggets.model.adapter.RVAdapter
import com.example.lifenuggets.model.ResponseItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnClickUser {

    lateinit var progressIndicator: ProgressBar
    lateinit var rView: RecyclerView
    lateinit var myAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        progressIndicator = findViewById(R.id.progressBar)
        rView = findViewById(R.id.rv)
        myAdapter = RVAdapter(this)
        firstNetworkCall()

    }



    private fun firstNetworkCall() {
    //display the progress bar here
    progressIndicator.visibility = View.VISIBLE
        ApiUserClientService.retrofitService.loadUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<ArrayList<ResponseItem>>{
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: ArrayList<ResponseItem>?) {
                    Log.d("HELLOHOWAREYOU", "DATA $t")

                    if (t != null) {
                        myAdapter.setData(t)
                    }
                    progressIndicator.visibility = View.INVISIBLE
                    rView.visibility = View.VISIBLE
                    rView.adapter = myAdapter
                    rView.layoutManager = LinearLayoutManager(this@MainActivity)


                }

                override fun onError(e: Throwable?) {
                   Toast.makeText(this@MainActivity, "Error retrieving data", Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {
                    Toast.makeText(this@MainActivity, "Data retrieval complete", Toast.LENGTH_LONG).show()
                }

            })



    }

    override fun onClick(id: Int) {
        val intent  = Intent(this, UserPost::class.java)
        intent.putExtra("id", id.toString())
        startActivity(intent)
    }


}
