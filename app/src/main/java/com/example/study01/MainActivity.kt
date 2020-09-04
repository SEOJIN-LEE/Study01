package com.example.study01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Volley의 RequestQueue 선언.
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)

        //api 주소 선언.
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=Suji-gu" + "&appid=1190eedefb73628e465e57acd6c00137"

        //API 호출
        val request = JsonObjectRequest(Request.Method.GET, url, null,

            Response.Listener { //데이터가 정상적으로 호출됐을 때 처리하는 부분.
                    response ->
                try { //response가 정상적으로 넘어온 경우
                    val weatherId: Int =
                        response.getJSONArray("weather").getJSONObject(0).getInt("id")
                    val description: String =
                        response.getJSONArray("weather").getJSONObject(0).getString("description")
                    val temp: Double = response.getJSONObject("main").getDouble("temp") - 273.15
                    val humidity: Int = response.getJSONObject("main").getInt("humidity")
                    val windSpeed: Double = response.getJSONObject("wind").getDouble("speed")
                    val cloudsAll: Int = response.getJSONObject("clouds").getInt("all")
                    val region: String = response.getString("name")
                    // JSON이 배열 형태로 돼있으면 getJSONArray로 배열의 이름 댄 후 배열 먼저 가져와서 그 중 몇 번째 오브젝트인지를
                    // getJSONObject로 가져온 다음에 받아오려는 정보의 데이터타입에 따라 getInt, getDouble등으로 함수 써서 가져옴.

                    val tv_region = findViewById<TextView>(R.id.tv_region)
                    val tv_temp = findViewById<TextView>(R.id.tv_temp)
                    val tv_description = findViewById<TextView>(R.id.tv_description)
                    val tv_humidity = findViewById<TextView>(R.id.tv_humidity)
                    val tv_cloud = findViewById<TextView>(R.id.tv_cloud)
                    val tv_windSpeed = findViewById<TextView>(R.id.tv_windSpeed)
                    val iv_weather = findViewById<ImageView>(R.id.iv_weather)

                    tv_temp.text = "${temp.toInt()}℃"
                    tv_humidity.text = "$humidity%"
                    tv_cloud.text = "$cloudsAll%"
                    tv_windSpeed.text = "" + windSpeed + "km/h"
                    tv_region.text = "용인시 수지구"

                    /* if (region == "Yongin") tv_region.text == "용인"
                    else if (region == "Suji-gu") tv_region.text == "용인시 수지구"
                    else if (region == "Seoul") tv_region.text == "서울"
                    else if (region == "Seongdong-gu") tv_region.text == "서울시 성동구"
                    else if (region == "Pohang") tv_region.text == "포항"
                    else if (region == "Yangdeokdong") tv_region.text == "포항시 양덕동"*/



                    if (weatherId / 100 == 2) { // 천둥번개
                        iv_weather.setImageResource(R.drawable.stomy01)
                        tv_description.text = "천둥번개 치는 날"
                    }
                    else if (weatherId / 100 == 3 || weatherId / 100 == 5) { // 가랑비 & 비(폭우포함) (비 오는 날)
                        val random = Random()
                        val i: Int = random.nextInt(5) + 1

                        if (i == 1) iv_weather.setImageResource(R.drawable.rainy01)
                        else if (i == 2) iv_weather.setImageResource(R.drawable.rainy02)
                        else if (i == 3) iv_weather.setImageResource(R.drawable.rainy03)
                        else if (i == 4) iv_weather.setImageResource(R.drawable.rainy04)
                        else if (i == 5) iv_weather.setImageResource(R.drawable.rainy05)
                        tv_description.text = "비 오는 날"
                    }
                    else if (weatherId / 100 == 6) { // 눈 오는 날
                        val random = Random()
                        val i: Int = random.nextInt(3) + 1

                        if (i == 1) iv_weather.setImageResource(R.drawable.snowy01)
                        else if (i == 2) iv_weather.setImageResource(R.drawable.snowy02)
                        else if (i == 3) iv_weather.setImageResource(R.drawable.snowy03)

                        tv_description.text = "눈 오는 날"
                    }
                    else if (weatherId / 100 == 7 || (weatherId / 100 == 8 && weatherId != 800)) {
                        // 안개 & 구름 (흐린날)
                        val random = Random()
                        val i: Int = random.nextInt(3) + 1

                        if (i == 1) iv_weather.setImageResource(R.drawable.cloudy01)
                        else if (i == 2) iv_weather.setImageResource(R.drawable.cloudy02)
                        else if (i == 3) iv_weather.setImageResource(R.drawable.cloudy03)
                        tv_description.text = "흐린 날"
                    }
                    else if (weatherId == 800) { // 맑은 날
                        val random = Random()
                        val i: Int = random.nextInt(4) + 1

                        if (i == 1) iv_weather.setImageResource(R.drawable.sunny01)
                        else if (i == 2) iv_weather.setImageResource(R.drawable.sunny02)
                        else if (i == 3) iv_weather.setImageResource(R.drawable.sunny03)
                        else if (i == 4) iv_weather.setImageResource(R.drawable.sunny04)
                        tv_description.text = "맑은 날"
                    }
                } catch (e: JSONException) { //response가 정상적으로 넘어오지 않은 경우.
                    //오류 내용을 Toast message로 출력.
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { //데이터가 정상적으로 호출되지 않았을 때 처리하는 부분
                //오류 내용을 Toast message로 출력.
                    error -> Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            })


        requestQueue.add(request)

        //val iv_poster = findViewById<ImageView>(R.id.iv_poster)


        /*bt_sunny.setOnClickListener { // button을 클릭했을 때
            // 텍스트뷰(textView)의 텍스트를 변경한다.
            tv_weather.text = "맑은 날"

            val random = Random()
            val i: Int = random.nextInt(4) + 1

            if (i == 1) iv_weather.setImageResource(R.drawable.sunny01)
            else if (i == 2) iv_weather.setImageResource(R.drawable.sunny02)
            else if (i == 3) iv_weather.setImageResource(R.drawable.sunny03)
            else if (i == 4) iv_weather.setImageResource(R.drawable.sunny04)
        }

        bt_cloudy.setOnClickListener { // button을 클릭했을 때
            // 텍스트뷰(textView)의 텍스트를 변경한다.
            tv_weather.text = "흐린 날"

            val random = Random()
            val i: Int = random.nextInt(3) + 1

            if (i == 1) iv_weather.setImageResource(R.drawable.cloudy01)
            else if (i == 2) iv_weather.setImageResource(R.drawable.cloudy02)
            else if (i == 3) iv_weather.setImageResource(R.drawable.cloudy03)
        }

        bt_rainy.setOnClickListener { // button을 클릭했을 때
            // 텍스트뷰(textView)의 텍스트를 변경한다.
            tv_weather.text = "비 오는 날"

            val random = Random()
            val i: Int = random.nextInt(5) + 1

            if (i == 1) iv_weather.setImageResource(R.drawable.rainy01)
            else if (i == 2) iv_weather.setImageResource(R.drawable.rainy02)
            else if (i == 3) iv_weather.setImageResource(R.drawable.rainy03)
            else if (i == 4) iv_weather.setImageResource(R.drawable.rainy04)
            else if (i == 5) iv_weather.setImageResource(R.drawable.rainy05)
        }*/
    }
}