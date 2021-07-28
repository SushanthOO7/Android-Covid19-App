package com.example.covidapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.HORIZONTAL,false)
        val symptomsList = ArrayList<Model>()
        symptomsList.add(Model(R.drawable.cough,"Dry Cough","A dry cough is one that does not produce phlegm or mucus."))
        symptomsList.add(Model(R.drawable.fever,"Fever","A fever is a temporary increase in your body temperature."))
        symptomsList.add(Model(R.drawable.headache,"Head Ache","A painful sensation in any part of the head, ranging from sharp to dull, that may occur with other symptoms."))

        val symptomsAdapter = SymptomsAdapter(symptomsList)

        recyclerView.adapter = symptomsAdapter


        recyclerViewPrecautions.layoutManager = LinearLayoutManager(this,LinearLayout.HORIZONTAL,false)
        val precautionsList = ArrayList<Model>()
        precautionsList.add(Model(R.drawable.vaccine,"Get Vaccinated","Get vaccinated and protect yourself and others from corona"))
        precautionsList.add(Model(R.drawable.handwash,"Hand Wash","Wash your hands well and often. Use hand sanitizer when you’re not near soap and water."))
        precautionsList.add(Model(R.drawable.mask,"Wear Mask","Masks are a key measure to suppress transmission and save lives."))

        val precautionsAdapter = PrecautionsAdapter(precautionsList)

        recyclerViewPrecautions.adapter = precautionsAdapter

        txtViewSymptoms.setOnClickListener {
            var intent = Intent(this@MainActivity,SymptomsActivity::class.java)
            startActivity(intent)
        }

        btnKnowMore.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mygov.in/covid-19"))
            startActivity(intent)
        }


        btnGithub.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/SushanthOO7"))
            startActivity(intent)
        }

        btnLinkedIn.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/sushanth-uv-7899911a7/"))
            startActivity(intent)
        }


        txtViewPrecautions.setOnClickListener {
            var intent = Intent(this@MainActivity,PrecautionActivity::class.java)
            startActivity(intent)
        }


        getGlobalData()
        getIndiaData()
        getStateData()

    }

    fun getGlobalData(){
        val url:String ="https://corona.lmao.ninja/v2/all/"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {

                var jsonObject = JSONObject(it.toString())

                //now set values in textview
                txtInfected.text = jsonObject.getString("cases")
                txtRecoverd.text = jsonObject.getString("recovered")
                txtDeceased.text = jsonObject.getString("deaths")

            },
            {
                Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_LONG).show()
                txtInfected.text = "-"
                txtRecoverd.text = "-"
                txtDeceased.text = "-"

            }
        )

        val requestQueue = Volley.newRequestQueue(this@MainActivity)
        requestQueue.add(stringRequest)
    }
    fun getIndiaData(){
        val url:String ="https://corona.lmao.ninja/v2/countries/india"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {

                var jsonObjecti = JSONObject(it.toString())


                //now set values in textview
                txtIndinfected.text = jsonObjecti.getString("cases")
                txtIndtoday.text = jsonObjecti.getString("todayCases")
                txtIndrecovered.text = jsonObjecti.getString("recovered")
                txtInddeceased.text = jsonObjecti.getString("deaths")
            },
            {
                Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_LONG).show()
                txtIndinfected.text = "-"
                txtIndtoday.text = "-"
                txtIndrecovered.text = "-"
                txtInddeceased.text = "-"
            }
        )

        val requestQueue = Volley.newRequestQueue(this@MainActivity)
        requestQueue.add(stringRequest)
    }
    fun getStateData(){
        val url:String ="https://api.covid19india.org/state_district_wise.json"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {

                var jsonObjects = JSONObject(it.toString())
                var kar = jsonObjects.getJSONObject("Karnataka")
                var dis = kar.getJSONObject("districtData")
                var bang = dis.getJSONObject("Bengaluru Urban")


                txtBengaluru.text = bang.getString("confirmed")
                txtBenrecover.text = bang.getString("recovered")



            },
            {
                Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_LONG).show()
                txtBengaluru.text = "-"
                txtBenrecover.text = "-"


            }
        )

        val requestQueue = Volley.newRequestQueue(this@MainActivity)
        requestQueue.add(stringRequest)
    }


}