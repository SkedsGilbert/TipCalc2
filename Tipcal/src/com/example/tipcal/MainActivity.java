package com.example.tipcal;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	
	private static final String TOTAL_BILL = "TOTAL_BILL";
	private static final String CURRENT_TIP = "CURRENT_TIP";
	private static final String BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP";
	
	private double billBeforeTip;
	private int tipAmount;
//	private double tipAmount;
	private double finalBill;
	double checklistTotal = 0;
	long secondsYouWait = 0;
	
	EditText etBillBeforeTip;
	EditText etTipAmount;
//	EditText etFinalBill;
	
	TextView etFinalBill;
	TextView tvTipAmount;
	TextView amountToTipTextView;
	
	SeekBar tipSeekBar;
	
	private int[] checklistValues = new int[12];
	
	CheckBox friendlyCheckBox;
	CheckBox specialsCheckBox;
	CheckBox opinionCheckBox;
	
	RadioGroup howHotRadioGroup;
	RadioButton howColdRadio;
	RadioButton howWarmRadio;
	RadioButton howSmokinRadio;
	
	Spinner problemsSpinner;
	
	Button startTimerBttn;
	Button pauseTimerBttn;
	Button resetTimerBttn;
	
	Chronometer timeWaitingChronometer;
	
	long secondsYouWaited = 0;
	
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etBillBeforeTip = (EditText) findViewById(R.id.billEditText);
        etFinalBill = (TextView) findViewById(R.id.finalBillEditText);
        
        etBillBeforeTip.addTextChangedListener(billBeforeTipListener);
        
        tipSeekBar = (SeekBar) findViewById(R.id.tipSeekBar);
        tipSeekBar.setOnSeekBarChangeListener(tipSeekBarListener);
        tvTipAmount = (TextView) findViewById(R.id.tipEditText);
        amountToTipTextView = (TextView) findViewById(R.id.amountToTipTextView);
        
        friendlyCheckBox = (CheckBox) findViewById(R.id.friendlyCheckBox);
    	specialsCheckBox = (CheckBox) findViewById(R.id.specialsCheckBox);
    	opinionCheckBox = (CheckBox) findViewById(R.id.opinionCheckBox);
    	
    	setUpIntroCheckBoxes();
        
    	howHotRadioGroup = (RadioGroup) findViewById(R.id.howHotRadioGroup);
    	howColdRadio = (RadioButton) findViewById(R.id.howColdRadio);
    	howWarmRadio = (RadioButton) findViewById(R.id.howWarmRadio);
    	howSmokinRadio = (RadioButton) findViewById(R.id.howSmokinRadio);
    	
    	addChangeListenerToRadios();
    	
    	problemsSpinner = (Spinner) findViewById(R.id.problemsSpinner);
    	
    	addItemSelectListenerToSpinner();
    	
    	startTimerBttn = (Button) findViewById(R.id.startTimerBttn);
    	pauseTimerBttn = (Button) findViewById(R.id.pauseTimerBttn);
    	resetTimerBttn = (Button) findViewById(R.id.resetTimerBttn);
    	
    	setButtonOnClickListeners();
    	
    	timeWaitingChronometer = (Chronometer) findViewById(R.id.timeWaitingChronometer);

    }
    
    private TextWatcher billBeforeTipListener = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
			try{
				billBeforeTip = Double.parseDouble(s.toString());
			}catch(NumberFormatException nfe){
				billBeforeTip = 0.00;
			}
			
			updateTipFinalBill();
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
    	
    }; //End TextListener
    
    private OnSeekBarChangeListener tipSeekBarListener = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			
			
			
			tipAmount = (tipSeekBar.getProgress());
			//change the int to double if not working
			tvTipAmount.setText(Integer.toString(tipAmount));
			
			updateTipFinalBill();
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			
			etBillBeforeTip.clearFocus();
			
			
		}
    	
    };

    private void updateTipFinalBill(){
    	
    	double tipFromText = Double.parseDouble(tvTipAmount.getText().toString()) * .01;
    	double tipAmount = tipFromText + checklistTotal;
    	double finalBill = billBeforeTip + (billBeforeTip * tipAmount);
    	double amountToTip = finalBill - billBeforeTip;
    	amountToTipTextView.setText(String.format("%.02f", amountToTip));
    	etFinalBill.setText(String.format("%.02f", finalBill));
    }
    
    protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);

		outState.putDouble(TOTAL_BILL, finalBill);
		//Changed to Int here
		outState.putInt(CURRENT_TIP, tipAmount);
		outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);

	}
    
    private void setUpIntroCheckBoxes(){
    	
    	friendlyCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				checklistValues[0] = (friendlyCheckBox.isChecked())?4:0;
				
				setTipFromWaitressChecklist();
				updateTipFinalBill();
				
			}
    		
    	});
    	
    	specialsCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				checklistValues[1] = (specialsCheckBox.isChecked())?1:0;
				
				setTipFromWaitressChecklist();
				updateTipFinalBill();
				
			}
    		
    	});
    	
    	opinionCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				checklistValues[2] = (opinionCheckBox.isChecked())?2:0;
				
				setTipFromWaitressChecklist();
				updateTipFinalBill();
				
			}
    		
    	});
    }
    
    private void addChangeListenerToRadios(){
    	howHotRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				checklistValues[3] = (howColdRadio.isChecked())?-1:0;
				checklistValues[4] = (howWarmRadio.isChecked())?2:0;
				checklistValues[5] = (howSmokinRadio.isChecked())?4:0;
				setTipFromWaitressChecklist();
				updateTipFinalBill();
				
				System.out.println("This is checklistValues " + checklistValues[3]);
				System.out.println("This is checklistValues " + checklistValues[4]);
				System.out.println("This is checklistValues " + checklistValues[5]);
			}

			
		});
    }
    
    private void addItemSelectListenerToSpinner(){
    	
    	problemsSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				checklistValues[6] = (problemsSpinner.getSelectedItem()).equals("Bad")?-1:0;
				checklistValues[7] = (problemsSpinner.getSelectedItem()).equals("Ok")?1:0;
				checklistValues[8] = (problemsSpinner.getSelectedItem()).equals("Good")?2:0;
				setTipFromWaitressChecklist();
				updateTipFinalBill();
				
				System.out.println("This is checklistValues " + checklistValues[6]);
				System.out.println("This is checklistValues " + checklistValues[7]);
				System.out.println("This is checklistValues " + checklistValues[8]);
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    }
    
    private void setButtonOnClickListeners(){
    	startTimerBttn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				
				int stoppedMilliseconds = 0;
				String chronoText = timeWaitingChronometer.getText().toString();
				String array[] = chronoText.split(":");
				
				if(array.length == 2){
					stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 +
							Integer.parseInt(array[1]) * 1000;
				}else if(array.length == 3){
					
					stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000 +
							Integer.parseInt(array[1]) * 60 * 1000
							+ Integer.parseInt(array[2]) * 1000;
				}
				
				timeWaitingChronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
				secondsYouWaited = Long.parseLong(array[1]);
				updateTipBasedOnTime(secondsYouWaited);
				timeWaitingChronometer.start();
				
			}
    		
    	});
    	
    	pauseTimerBttn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				timeWaitingChronometer.stop();
				
			}
    		
    		
    	});
    	
    	resetTimerBttn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				
				timeWaitingChronometer.setBase(SystemClock.elapsedRealtime());
				secondsYouWaited = 0;
				
			}
    		
    	});
    }
    
    private void updateTipBasedOnTime(long secondsYouWaited){
    	checklistValues[9] = (secondsYouWaited > 10)?-2:2;
    	
    }
    
    private void setTipFromWaitressChecklist(){
    	
    	
    	for(int item : checklistValues){
//    		checklistTotal += item;
    		checklistTotal = checklistTotal + (item * .01);
    		System.out.println("This is checklistTotal " + checklistTotal);
    	}
    	
    	
    	
    	
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
*/
}
