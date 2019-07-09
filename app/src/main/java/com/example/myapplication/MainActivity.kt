package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), MyAdapter.Listener {

    private val BASE_URL = "https://jsonplaceholder.typicode.com"
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myResultArrayList: ArrayList<Result>? = null
    private var myAdapter: MyAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        loadData()
    }

        private fun initRecyclerView() {
            val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
            result_list.layoutManager = layoutManager
        }



        private fun loadData() {
            val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RequestData::class.java)

            myCompositeDisposable?.add(requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse))

        }

        private fun handleResponse(ResultList: List<Result>) {
            myResultArrayList = ArrayList(ResultList)
            myAdapter = MyAdapter(myResultArrayList!!, this)
            result_list.adapter = myAdapter
        }

        override fun onDestroy() {
            super.onDestroy()
            myCompositeDisposable?.clear()
        }


        override fun onItemClick(result: Result) {
            Toast.makeText(this, "You clicked: ${result.albumId}", Toast.LENGTH_LONG).show()
        }

//        val button_click = findViewById(R.id.toaster) as Button
//        button_click.setOnClickListener {
//            Toast.makeText(getApplicationContext(),"Let's make a toast to you!",Toast.LENGTH_SHORT).show();
//        }
}
