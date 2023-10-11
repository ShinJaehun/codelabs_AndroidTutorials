package com.shinjaehun.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.affirmations.adapter.ItemAdapter
import com.shinjaehun.affirmations.data.Datasource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDataset = Datasource().loadAffirmations()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        recyclerView.setHasFixedSize(true)
        // 활동 레이아웃에서 RecyclerView의 레이아웃 크기가 고정되어 있으므로
        // RecyclerView의 setHasFixedSize 매개변수를 true로 설정할 수 있습니다.
        // 성능을 개선하기 위해서만 이 설정이 필요합니다. 콘텐츠를 변경해도 RecyclerView의 레이아웃 크기가
        // 변경되지 않는다는 것을 아는 경우 이 설정을 사용합니다.
    }
}