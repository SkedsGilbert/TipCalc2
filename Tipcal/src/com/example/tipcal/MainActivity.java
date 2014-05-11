package com.example.tipcal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private static final String TOTAL_BILL = "TOTAL_BILL";
	private static final String CURRENT_TIP = "CURRENT_TIP";
	private static final String BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP";
	
	private double billBeforeTip;
	private int tipAmount;
//	private double tipAmount;
	private double finalBill;
	
	EditText etBillBeforeTip;
	EditText etTipAmount;
	EditText etFinalBill;
	
	TextView tvTipAmount;
	
	SeekBar tipSeekBar;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        etBillBeforeTip = (EditText) findViewById(R.id.billEditText);
//        etTipAmount = (EditText) findViewById(R.id.tipEditText);
        etFinalBill = (EditText) findViewById(R.id.finalBillEditText);
        
        etBillBeforeTip.addTextChangedListener(billBeforeTipListener);
        
        tipSeekBar = (SeekBar) findViewById(R.id.tipSeekBar);
        tipSeekBar.setOnSeekBarChangeListener(tipSeekBarListener);
        tvTipAmount = (TextView) findViewById(R.id.tipEditText);

    }
    
    private TextWatcher billBeforeTipListener = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			
		}
    	
    };

    private void updateTipFinalBill(){
    	
    	double tipAmount = Double.parseDouble(tvTipAmount.getText().toString()) * .01 ;
    	double finalBill = billBeforeTip + (billBeforeTip * tipAmount);
    	etFinalBill.setText(String.format("%.02f", finalBill));
    }
    
    protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);

		outState.putDouble(TOTAL_BILL, finalBill);
		//Changed to Int here
		outState.putInt(CURRENT_TIP, tipAmount);
		outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);

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
