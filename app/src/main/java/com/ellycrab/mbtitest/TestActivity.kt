package com.ellycrab.mbtitest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager:ViewPager2

    val questionResults = QuestionResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        //화면상의 viewpager 위젯을 메모리에 올린다.
        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)

        //응답이 3개가 모두 완료되어야만 화면을 넘기도록 false로 셋팅
        viewPager.isUserInputEnabled = false

    }

    //다음페이지 버튼 함수
    fun moveToNextQuestion(){ //currentItem은 현재 페이지
        if(viewPager.currentItem == 3){
            //마지막페이지 -> 결과 화면으로 이동
            val intent = Intent(this,ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(questionResults.results))
            startActivity(intent)

        }else{
            val nextItem = viewPager.currentItem+1
            if(nextItem < viewPager.adapter?.itemCount?:0){
                viewPager.setCurrentItem(nextItem,true)
            }
        }
    }

}

class QuestionResults{
    //질문지에서 1번 2번등을 저장해놓은 변수
    val results = mutableListOf<Int>()

    //질문선택시 더 많이 선택된 애를 추가해주는 function
    fun addResponse(response:List<Int>){//1,1,2
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent.let{
            if (it != null) {
                results.add(it)
            }
        }
    }
}