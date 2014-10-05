package com.tmatz.openwnntest;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import jp.co.omronsoft.openwnn.JAJP.OpenWnnEngineJAJP;
import jp.co.omronsoft.openwnn.WnnWord;
import jp.co.omronsoft.openwnn.ComposingText;
import jp.co.omronsoft.openwnn.StrSegment;
import jp.co.omronsoft.openwnn.JAJP.Romkan;
import jp.co.omronsoft.openwnn.LetterConverter;
import jp.co.omronsoft.openwnn.CandidateFilter;
import de.quist.app.errorreporter.ExceptionReporter;

public class MainActivity extends Activity
{
	private OpenWnnEngineJAJP mConvEngine;
	private final CandidateFilter mCandidateFilter = new CandidateFilter();
	private final LetterConverter mPreConv = new Romkan();
	private final ComposingText mText = new ComposingText();
	private TextView mTextView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
    	ExceptionReporter.register(this);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		mTextView = (TextView)findViewById(R.id.text_view);

		mConvEngine = new OpenWnnEngineJAJP(this, getFilesDir().getPath() + "/writableJAJP.dic");
		mConvEngine.init();
		mConvEngine.setFilter(mCandidateFilter);
		mConvEngine.setKeyboardType(OpenWnnEngineJAJP.KEYBOARD_QWERTY);
		//mConvEngine.setDictionary(OpenWnnEngineJAJP.DIC_LANG_JP);

		insert("k");
		insert("a");
		insert("n");
		insert("j");
		insert("i");
		insert("w");
		insert("o");
		insert("h");
		insert("e");
		insert("n");
		insert("k");
		insert("a");
		insert("n");
		insert("n");
		insert("s");
		insert("i");
		insert("m");
		insert("a");
		insert("s");
		insert("u");
		//insert(".");
		
		mText.setCursor(ComposingText.LAYER1, 0);

		int res;
		res = mConvEngine.convert(mText);
		//res = mConvEngine.predict(mText, 0, -1);
		show("num = " + res);
		if (mText.size(ComposingText.LAYER1) > 0)
		{
			String str = mText.toString(ComposingText.LAYER1, 0, 0);
			for (int i = 1; i < mText.size(ComposingText.LAYER1); ++i)
			{
				str = str + "|" + mText.toString(ComposingText.LAYER1, i, i);
			}
			show("layer1 = " + str);
		}
		{
			String str = mText.toString(ComposingText.LAYER2, 0, 0);
			for (int i = 1; i < mText.size(ComposingText.LAYER2); ++i)
			{
				str = str + "|" + mText.toString(ComposingText.LAYER2, i, i);
			}
			show("layer2 = " + str);
		}

		WnnWord word;
		while ((word = mConvEngine.getNextCandidate()) != null)
		{
			show(word.candidate);
		}

		//mCandidateFilter.filter = CandidateFilter.FILTER_NON_ASCII;
		for (int i = 0; i < mText.size(ComposingText.LAYER2); ++i)
		{
			if (mConvEngine.makeCandidateListOf(i) > 0 && (word = mConvEngine.getNextCandidate()) != null)
			{
				String str = word.candidate;
				while ((word = mConvEngine.getNextCandidate()) != null)
				{
					str = str + "|" + word.candidate;
				}
				show("candidates for " + i + " = " + str);
			}
		}
	}
	
	private void insert(String str)
	{
		mText.insertStrSegment(ComposingText.LAYER0, ComposingText.LAYER1, new StrSegment(str));
		if (!mPreConv.convert(mText))
		{
			show("pre conv failed at " + mText.getCursor(ComposingText.LAYER0));
		}
	}
	
	private void show(String str)
	{
		mTextView.append("\n" + str);
		//Toast.makeText(this, str, Toast.LENGTH_SHORT).show();		
	}
}
