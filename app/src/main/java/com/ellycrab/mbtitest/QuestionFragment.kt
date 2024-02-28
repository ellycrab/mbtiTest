package com.ellycrab.mbtitest

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

//다음 버튼을 눌렀을때 글자만 바뀌게끔
class QuestionFragment: Fragment() {

    private var questionType:Int = 0

    //질문에 타이틀은 한개씩 있음
    private val questionTitle = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title
    )

    //그런데 타이틀 안에 질문들은 3개씩 있음
    private val questionTexts = listOf(
        //1번 질문지에대한 3개의 질문
        listOf(R.string.question1_1,R.string.question1_2,R.string.question1_3),
        listOf(R.string.question2_1,R.string.question2_2,R.string.question2_3),
        listOf(R.string.question3_1,R.string.question3_2,R.string.question3_3),
        listOf(R.string.question4_1,R.string.question4_2,R.string.question4_3)
    )
    //응답은 질문지 하나에 2개의 응답이 있다.
    private val questionAnswers = listOf(
        listOf(
            listOf(R.string.question1_1_answer1,R.string.question1_1_answer2),
            listOf(R.string.question1_2_answer1,R.string.question1_2_answer2),
            listOf(R.string.question1_3_answer1,R.string.question1_3_answer2)
        ),
        listOf(
            listOf(R.string.question2_1_answer1,R.string.question2_1_answer2),
            listOf(R.string.question2_2_answer1,R.string.question2_2_answer2),
            listOf(R.string.question2_3_answer1,R.string.question2_3_answer2)
        ),
        listOf(
            listOf(R.string.question3_1_answer1,R.string.question3_1_answer2),
            listOf(R.string.question3_2_answer1,R.string.question3_2_answer2),
            listOf(R.string.question3_3_answer1,R.string.question3_3_answer2)
        ),
        listOf(
            listOf(R.string.question4_1_answer1,R.string.question4_1_answer2),
            listOf(R.string.question4_2_answer1,R.string.question4_2_answer2),
            listOf(R.string.question4_3_answer1,R.string.question4_3_answer2)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            questionType = it.getInt(ARG_QUESTION_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question,container,false)
        val title: TextView = view.findViewById(R.id.tv_question_title)
        title.text = getString(questionTitle[questionType])

        val questionTextView = listOf<TextView>(
            view.findViewById(R.id.tv_question_1),
            view.findViewById(R.id.tv_question_2),
            view.findViewById(R.id.tv_question_3)

        )

        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.rg_answer_1),
            view.findViewById(R.id.rg_answer_2),
            view.findViewById(R.id.rg_answer_3)
        )

        for(i in questionTextView.indices){
            questionTextView[i].text = getString(questionTexts[questionType][i])

            val radioButton1 = answerRadioGroup[i].getChildAt(0) as RadioButton
            val radioButton2 = answerRadioGroup[i].getChildAt(1) as RadioButton

            radioButton1.text = getString(questionAnswers[questionType][i][0])
            radioButton2.text = getString(questionAnswers[questionType][i][1])
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.rg_answer_1),
            view.findViewById(R.id.rg_answer_2),
            view.findViewById(R.id.rg_answer_3)
        )

        val btn_next: Button = view.findViewById(R.id.btn_next)

        btn_next.setOnClickListener {
            val isAllAnswered = answerRadioGroup.all { it.checkedRadioButtonId!=-1 }


            if(isAllAnswered){
                val response = answerRadioGroup.map { radioGroup -> //2,1,2
                    val firstRadioButton = radioGroup.getChildAt(0) as RadioButton
                    if(firstRadioButton.isChecked) 1 else 2
                }
                (activity as? TestActivity)?.questionResults?.addResponse(response)
                (activity as? TestActivity)?.moveToNextQuestion()


            }else{
                Toast.makeText(context,"모든질문에 답하여라",Toast.LENGTH_SHORT).show()
            }
        }
    }


    //생성자
    companion object{
        //새로운 페이지 번호를 받아야함
        private const val ARG_QUESTION_TYPE = "questionType"

        //ViewPagerAdapter 에서 에러난부분 "newInstance"
        //TestActivity에서 새로운 페이지 넘길때마다 nextItem을 줌
        //그것을 instance에서 받아줌
        fun newInstance(questionType:Int):QuestionFragment{
            val fragment = QuestionFragment()
            //데이터를 받을때는 Bundle로 받는다.
            val args = Bundle()
            //새로운 페이지번호를 전달한다.
            args.putInt(ARG_QUESTION_TYPE,questionType)
            fragment.arguments = args
            return fragment
        }
    }


}